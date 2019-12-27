package com.company.spmu.bpm;

import com.company.spmu.repositories.bpm.ProcDefinitionsRepository;
import com.company.spmu.repositories.bpm.ProcModelRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.haulmont.bpm.core.ProcessRepositoryManager;
import com.haulmont.bpm.entity.ProcDefinition;
import com.haulmont.bpm.entity.ProcModel;
import com.haulmont.bpm.service.ModelService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.sys.AppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Component
public class ProcessModelDeployer {
    private static final Logger log = LoggerFactory.getLogger(ProcessModelDeployer.class);
    private static final String MODELS_DIR = "spmu.bpm.processModelsDir";

    @Inject
    protected DataManager dataManager;
    @Inject
    protected ModelService modelService;
    @Inject
    protected Metadata metadata;
    @Inject
    protected ProcessRepositoryManager processRepositoryManager;
    @Inject
    protected ProcModelRepository procModelRepository;
    @Inject
    protected ProcDefinitionsRepository procDefinitionsRepository;

    public void deployModels() {
        String modelsDir = AppContext.getProperty(MODELS_DIR);
        if (Strings.isNullOrEmpty(modelsDir)) {
            log.error("Models directory specified by the app.modelsDir property not found");
            return;
        }
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(modelsDir))) {
            for (Path path : stream) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode modelJsonNode = objectMapper.readTree(path.toFile());
                JsonNode propertiesNode = modelJsonNode.get("properties");
                if (propertiesNode == null || propertiesNode.get("name") == null) {
                    log.warn("Cannot parse the model in the {}", path.getFileName());
                    continue;
                }
                String modelName = propertiesNode.get("name").asText();
                deployProcModel(modelJsonNode, modelName);
            }
        } catch (Exception e) {
            log.error("Error on deploying the model", e);
        }
    }

    private void deployProcModel(JsonNode modelJsonNode, String modelName) {
        List<ProcModel> firstProcModelByName = procModelRepository.findByName(modelName);
        Optional<ProcModel> existingProcModelOptional = firstProcModelByName.stream().findFirst();
        if (existingProcModelOptional.isPresent()) {
            log.info("Starting update and deploy {} process model", modelName);
            ProcModel model = existingProcModelOptional.get();
            updateModel(modelJsonNode, model);
            deployModel(model);
        } else {
            log.info("Starting create and deploy {} process model", modelName);
            ProcModel newModel = createModel(modelJsonNode, modelName);
            deployModel(newModel);
        }
        log.info("Process model {} has been deployed", modelName);
    }

    private ProcModel createModel(JsonNode modelJsonNode, String modelName) {
        String actModelId = modelService.createModel(modelName);
        modelService.updateModel(actModelId, modelName, "", modelJsonNode.toString(), "");
        ProcModel procModel = metadata.create(ProcModel.class);
        procModel.setName(modelName);
        procModel.setActModelId(actModelId);
        return dataManager.commit(procModel);
    }

    private void updateModel(JsonNode modelJsonNode, ProcModel model) {
        modelService.updateModel(model.getActModelId(), model.getName(), "", modelJsonNode.toString(), "");
    }

    private void deployModel(ProcModel procModel) {
        List<ProcDefinition> procDefinitionsByModel = procDefinitionsRepository.findProcDefinitionsByModel(procModel);
        ProcDefinition procDefinition = procDefinitionsByModel.isEmpty() ? null : procDefinitionsByModel.get(0);
        String processXml = processRepositoryManager.convertModelToProcessXml(procModel.getActModelId());
        processRepositoryManager.deployProcessFromXml(processXml, procDefinition, procModel);
    }

}

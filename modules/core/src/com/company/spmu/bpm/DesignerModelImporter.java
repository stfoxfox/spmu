package com.company.spmu.bpm;

import com.google.common.base.Strings;
import com.haulmont.bpm.service.StencilSetService;
import com.haulmont.cuba.core.sys.AppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class DesignerModelImporter {
    private static final Logger log = LoggerFactory.getLogger(DesignerModelImporter.class);

    private static final String MODELS_DIR = "spmu.bpm.designerModelsDir";

    @Inject
    StencilSetService stencilSetService;

    public void importModels() {
        String modelsDir = AppContext.getProperty(MODELS_DIR);

        if (Strings.isNullOrEmpty(modelsDir)) {
            log.error("Designer models directory specified by the spmu.bpm.designerModelsDir property not found");
            return;
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(modelsDir))) {
            for (Path file : stream) {
                log.info("Starting {} import", file.getFileName());
                stencilSetService.importStencilSet(Files.readAllBytes(file));
                log.info("{} has been imported", file.getFileName());
            }
        } catch (Exception e) {
            log.error("Error on deploying the model:", e);
        }
    }
}
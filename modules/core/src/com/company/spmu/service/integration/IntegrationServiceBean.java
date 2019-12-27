package com.company.spmu.service.integration;

import com.company.spmu.bpm.DesignerModelImporter;
import com.company.spmu.entity.ConnectionLog;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import org.glassfish.jersey.client.ClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Base64;

/**
 * сервис для отправки/приятия даных из УИМ внут
 */
@Service(IntegrationService.NAME)
public class IntegrationServiceBean implements IntegrationService {
    private static final Logger LOG = LoggerFactory.getLogger(DesignerModelImporter.class);

    @Inject
    private Metadata metadata;
    @Inject
    private Persistence persistence;
    @Inject
    private DataManager dataManager;

    private static final String BASE_UIM_URL = "http://ufkn/pilot/";
    private static final String BASE_USERNAME = "ODataUser";
    private static final String BASE_PASSWORD = "5ue2aGV7";

    /**
     * получение данных из УМИПвнут
     * @param additionalUrl - дополнительный Url для сущности
     * @return
     */
    @Override
    public String getData(String additionalUrl) {
        String responseString = new String();

        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(getURI());

        Builder builder = target
                .path(additionalUrl)
                .request()
                .header("Authorization", "Basic ".concat(getCredential()))
                .accept(MediaType.APPLICATION_JSON);

        if (builder.get().getStatus() != 404) {
            responseString = builder.get(String.class);
        }

        createConnectionLog(
                target.path(additionalUrl).getUri().toString(),
                "",
                builder.get().getStatus(),
                responseString);

        return responseString;
    }

    /**
     * отправка данных в УМИПвнут
     * @param data - строка json данных
     * @param additionalUrl - дополнительный Url для сущности
     */
    @Override
    public void sendData(String data, String additionalUrl) {
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(getURI());

        try {
            String response = target
                    .path(additionalUrl)
                    .request()
                    .header("Authorization", "Basic ".concat(getCredential()))
                    .accept(MediaType.APPLICATION_JSON)
                    .post(Entity.json(data), String.class);

            createConnectionLog(target.path(additionalUrl).getUri().toString(), data, 200, response);
            System.out.println(response);
        } catch (Exception e) {
            LOG.error("Error during posting data to {}", BASE_UIM_URL, e);
        }
    }

    /**
     * формирование начального Url
     * @return
     */
    private static URI getURI() {
        return UriBuilder.fromUri(BASE_UIM_URL).build();
    }

    /**
     * формирование данных для входа
     * @return
     */
    private static String getCredential() {
        String credential = BASE_USERNAME.concat(":").concat(BASE_PASSWORD);
        return new String(Base64.getEncoder().encode(credential.getBytes()));
    }

    /**
     * Создание записи в логе подключений ConnectionLog
     * @param url
     * @param request
     * @param code
     * @param response
     */
    private void createConnectionLog(String url, String request, Integer code, String response) {
        try (Transaction tx = this.persistence.getTransaction()) {
            ConnectionLog log = this.metadata.create(ConnectionLog.class);
            log.setUrl(url);
            log.setHttpCode(code);
            log.setRequest(request);
            log.setResponse(response);
            dataManager.commit(log);

            tx.commit();
        }
    }
}
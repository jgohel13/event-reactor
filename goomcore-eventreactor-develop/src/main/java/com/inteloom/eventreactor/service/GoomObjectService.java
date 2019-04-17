package com.inteloom.eventreactor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inteloom.eventreactor.config.ApplicationProperties;
import com.inteloom.eventreactor.domain.ObjectDefinition;
import com.inteloom.eventreactor.service.dto.GoomObjectDTO;
import com.inteloom.eventreactor.service.dto.UserTokenDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ghumphries on 2017-11-15.
 */
@Service
public class GoomObjectService {

    private static Logger logger = LoggerFactory.getLogger(GoomObjectService.class);

    private final ApplicationProperties applicationProperties;

    private final UserTokenService userTokenService;

    private final ObjectMapper objectMapper;

    public GoomObjectService(ApplicationProperties applicationProperties, UserTokenService userTokenService, ObjectMapper objectMapper) {
        this.applicationProperties = applicationProperties;
        this.userTokenService = userTokenService;
        this.objectMapper = objectMapper;
    }

    public void createGoomObject(GoomObjectDTO goomObjectDTO, ObjectDefinition objectDefinition, UserTokenDTO userTokenDTO) throws IOException {
        URL url = new URL(applicationProperties.getDataService() + "/api/entities/" + objectDefinition.getApiPath());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setDoOutput(true);
        con.addRequestProperty("Content-Type", "application/json");
        con.setRequestMethod("POST");

        String userTokenEncoded = userTokenService.userTokenToBase64String(userTokenDTO);
        con.setRequestProperty("UserToken", userTokenEncoded);

        objectMapper.writeValue(con.getOutputStream(), goomObjectDTO);
        int responseCode = con.getResponseCode();
        logger.info("response: " + responseCode);

        if (responseCode != HttpURLConnection.HTTP_CREATED) {
            throw new RuntimeException("Failed creating goom object: Response code " + responseCode);
        }
    }
}

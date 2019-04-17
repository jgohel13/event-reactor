package com.inteloom.eventreactor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inteloom.eventreactor.service.dto.UserTokenDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;

/**
 * Created by ghumphries on 2017-05-19.
 */
@Service
public class UserTokenService {

    private final Logger log = LoggerFactory.getLogger(UserTokenService.class);

    private ObjectMapper objectMapper;

    public UserTokenService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public UserTokenDTO base64StringToUserToken(String userToken) throws IOException {
        String decoded = new String(Base64.getDecoder().decode(userToken));
        try {
            return objectMapper.readValue(decoded, UserTokenDTO.class);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    public String userTokenToBase64String(UserTokenDTO userToken) throws IOException {
        try {
            String userTokenString = objectMapper.writeValueAsString(userToken);
            return new String(Base64.getEncoder().encode(userTokenString.getBytes()));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

}

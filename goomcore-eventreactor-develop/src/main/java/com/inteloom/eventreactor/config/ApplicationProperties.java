package com.inteloom.eventreactor.config;

import com.inteloom.eventreactor.service.dto.UserTokenDTO;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by ghumphries on 2017-11-14.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    private String dataService = "localhost:8686";

    private UserTokenDTO systemUser;

    public String getDataService() {
        return dataService;
    }

    public void setDataService(String dataService) {
        this.dataService = dataService;
    }

    public UserTokenDTO getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(UserTokenDTO systemUser) {
        this.systemUser = systemUser;
    }
}

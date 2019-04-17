package com.inteloom.eventreactor.domain;

/**
 * Created by ghumphries on 2017-11-17.
 */
public enum ObjectDefinition {
    EMAIL_TASK("email-tasks");

    private final String apiPath;

    ObjectDefinition(String apiPath) {
        this.apiPath = apiPath;
    }

    public String getApiPath() {
        return apiPath;
    }
}

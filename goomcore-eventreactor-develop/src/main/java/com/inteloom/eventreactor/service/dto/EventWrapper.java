package com.inteloom.eventreactor.service.dto;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

/**
 * Created by ghumphries on 2017-11-21.
 */
public class EventWrapper<T> {
    @NotNull
    private String key;

    @NotNull
    private T data;

    @NotNull
    private ZonedDateTime publishedOn;

    @NotNull
    private UserTokenDTO triggeredByUser;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ZonedDateTime getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(ZonedDateTime publishedOn) {
        this.publishedOn = publishedOn;
    }

    public UserTokenDTO getTriggeredByUser() {
        return triggeredByUser;
    }

    public void setTriggeredByUser(UserTokenDTO triggeredByUser) {
        this.triggeredByUser = triggeredByUser;
    }
}

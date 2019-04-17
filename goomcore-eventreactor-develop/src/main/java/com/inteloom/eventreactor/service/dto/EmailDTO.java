package com.inteloom.eventreactor.service.dto;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ghumphries on 2017-11-16.
 */
public class EmailDTO {
    @NotNull
    private Long id;

    private Set<String> tos = new HashSet<>();

    private Set<String> froms = new HashSet<>();

    private Set<GoomObjectDTO> goomObjects = new HashSet<>();

    @NotNull
    private String subject;

    @NotNull
    private String body;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<String> getTos() {
        return tos;
    }

    public void setTos(Set<String> tos) {
        this.tos = tos;
    }

    public Set<String> getFroms() {
        return froms;
    }

    public void setFroms(Set<String> froms) {
        this.froms = froms;
    }

    public Set<GoomObjectDTO> getGoomObjects() {
        return goomObjects;
    }

    public void setGoomObjects(Set<GoomObjectDTO> goomObjects) {
        this.goomObjects = goomObjects;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

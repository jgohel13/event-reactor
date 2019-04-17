package com.inteloom.eventreactor.service.dto;


import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the GoomObject entity.
 */
public class GoomObjectDTO implements Serializable {

    private Long id;

    private String key;

    private String title;

    private String description;

    private Boolean active;

    private Long parentGoomObjectId;

    private Instant creationDate;

    private Double timeActual;

    private ObjectNode attributes;

    private ObjectDefinitionDTO goomObjectDefinition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getParentGoomObjectId() {
        return parentGoomObjectId;
    }

    public void setParentGoomObjectId(Long goomObjectId) {
        this.parentGoomObjectId = goomObjectId;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Double getTimeActual() {
        return timeActual;
    }

    public void setTimeActual(Double timeActual) {
        this.timeActual = timeActual;
    }

    public ObjectNode getAttributes() {
        return attributes;
    }

    public void setAttributes(ObjectNode attributes) {
        this.attributes = attributes;
    }

    public ObjectDefinitionDTO getGoomObjectDefinition() {
        return goomObjectDefinition;
    }

    public void setGoomObjectDefinition(ObjectDefinitionDTO goomObjectDefinition) {
        this.goomObjectDefinition = goomObjectDefinition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GoomObjectDTO goomObjectDTO = (GoomObjectDTO) o;
        if (goomObjectDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), goomObjectDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GoomObjectWithJsonDTO{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", active=" + active +
                ", parentGoomObjectId=" + parentGoomObjectId +
                ", creationDate=" + creationDate +
                ", timeActual=" + timeActual +
                '}';
    }
}

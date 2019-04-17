package com.inteloom.eventreactor.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the GoomObject entity.
 */
public class ObjectDefinitionDTO implements Serializable {

    private Long id;

    private String context;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ObjectDefinitionDTO objectDefinitionDTO = (ObjectDefinitionDTO) o;
        if (objectDefinitionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), objectDefinitionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ObjectDefinitionDTO{" +
                "id=" + id +
                ", context='" + context + '\'' +
                '}';
    }
}

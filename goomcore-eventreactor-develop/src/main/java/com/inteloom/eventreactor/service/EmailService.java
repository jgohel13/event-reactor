package com.inteloom.eventreactor.service;

import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.inteloom.eventreactor.config.ApplicationProperties;
import com.inteloom.eventreactor.domain.ObjectDefinition;
import com.inteloom.eventreactor.service.dto.EmailDTO;
import com.inteloom.eventreactor.service.dto.EventWrapper;
import com.inteloom.eventreactor.service.dto.GoomObjectDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * Created by ghumphries on 2017-11-22.
 */
@Service
public class EmailService {

    private final GoomObjectService goomObjectService;

    private final ApplicationProperties applicationProperties;

    public EmailService(GoomObjectService goomObjectService, ApplicationProperties applicationProperties) {
        this.goomObjectService = goomObjectService;
        this.applicationProperties = applicationProperties;
    }

    public void createEmailTasksForEmail(EventWrapper<EmailDTO> eventWrapper) throws IOException {
        for (GoomObjectDTO assignedGoomObject : eventWrapper.getData().getGoomObjects()) {
            GoomObjectDTO emailTask = new GoomObjectDTO();
            emailTask.setTitle(assignedGoomObject.getTitle() + " - " + eventWrapper.getData().getSubject());
            emailTask.setDescription(eventWrapper.getData().getBody());
            ObjectNode attributes = new ObjectNode(null);

            attributes.set("deadline", new TextNode(eventWrapper.getPublishedOn().plusDays(2).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)));

            attributes.set("previousEmail", new ObjectNode(null));
            ((ObjectNode) attributes.get("previousEmail")).set("id", new LongNode(eventWrapper.getData().getId()));

            attributes.set("goomObject", new ObjectNode(null));
            ((ObjectNode) attributes.get("goomObject")).set("id", new LongNode(assignedGoomObject.getId()));

            if ("lareau.entity.clientCommercial".equals(assignedGoomObject.getGoomObjectDefinition().getContext())
                    || "lareau.entity.clientPersonal".equals(assignedGoomObject.getGoomObjectDefinition().getContext())) {
                attributes.set("client", new ObjectNode(null));
                ((ObjectNode) attributes.get("client")).set("id", new LongNode(assignedGoomObject.getId()));
            }

            emailTask.setAttributes(attributes);

            if (eventWrapper.getTriggeredByUser() != null) {
                goomObjectService.createGoomObject(emailTask, ObjectDefinition.EMAIL_TASK, eventWrapper.getTriggeredByUser());
            } else {
                goomObjectService.createGoomObject(emailTask, ObjectDefinition.EMAIL_TASK, applicationProperties.getSystemUser());
            }
        }
    }
}

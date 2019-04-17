package com.inteloom.eventreactor.messaging;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inteloom.eventreactor.service.EmailService;
import com.inteloom.eventreactor.service.dto.EmailDTO;
import com.inteloom.eventreactor.service.dto.EventWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * Created by ghumphries on 2017-11-14.
 */
@EnableBinding(EmailConfiguration.Email.class)
public class EmailConfiguration {

    private static Logger logger = LoggerFactory.getLogger(EmailConfiguration.class);

    private final EmailService emailService;

    private final ObjectMapper objectMapper;

    public EmailConfiguration(EmailService emailService, ObjectMapper objectMapper) {
        this.emailService = emailService;
        this.objectMapper = objectMapper;
    }

    @StreamListener(Email.EMAIL_INBOUND)
    public void inboundEmail(EventWrapper<EmailDTO> eventWrapper) throws IOException {
        logger.info("Email message received: " + eventWrapper);
        eventWrapper = objectMapper.convertValue(eventWrapper, new TypeReference<EventWrapper<EmailDTO>>(){});
        if (!eventWrapper.getData().getGoomObjects().isEmpty()) {
            emailService.createEmailTasksForEmail(eventWrapper);
        }
    }

    @StreamListener(Email.EMAIL_OUTBOUND)
    public void outboundEmail(EventWrapper<EmailDTO> eventWrapper) throws IOException {
        logger.info("Email message sent: " + eventWrapper);
        eventWrapper = objectMapper.convertValue(eventWrapper, new TypeReference<EventWrapper<EmailDTO>>(){});
    }

    @StreamListener(Email.EMAIL_ASSIGNED)
    public void assignedEmail(EventWrapper<EmailDTO> eventWrapper) throws IOException {
        logger.info("Email message assigned: " + eventWrapper);
        eventWrapper = objectMapper.convertValue(eventWrapper, new TypeReference<EventWrapper<EmailDTO>>(){});
        emailService.createEmailTasksForEmail(eventWrapper);
    }

    @Component
    public interface Email {
        String EMAIL_INBOUND = "email-inbound";

        String EMAIL_OUTBOUND = "email-outbound";

        String EMAIL_ASSIGNED = "email-assigned";

        @Input(EMAIL_INBOUND)
        SubscribableChannel received();

        @Input(EMAIL_OUTBOUND)
        SubscribableChannel sent();

        @Input(EMAIL_ASSIGNED)
        SubscribableChannel assigned();
    }
}

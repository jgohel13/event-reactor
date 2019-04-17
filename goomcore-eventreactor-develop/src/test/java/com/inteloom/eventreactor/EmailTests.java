package com.inteloom.eventreactor;

import com.fasterxml.jackson.databind.JsonNode;
import com.inteloom.eventreactor.domain.ObjectDefinition;
import com.inteloom.eventreactor.service.EmailService;
import com.inteloom.eventreactor.service.GoomObjectService;
import com.inteloom.eventreactor.service.dto.*;
import cucumber.api.java.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.reset;

/**
 * Created by ghumphries on 2017-11-22.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@SuppressWarnings("FieldCanBeLocal")
public class EmailTests {
    private final Long GOOM_OBJECT_ID = 1L;
    private final String GOOM_OBJECT_TITLE = "Test Title";
    private final String GOOM_OBJECT_KEY = "TEST-1";

    private final Long OBJECT_DEFINITION_ID = 2L;
    private final String OBJECT_DEFINITION_DEFAULT_CONTEXT = "entity.test";
    private final String OBJECT_DEFINITION_DEFAULT_CLIENT_PERSONAL_CONTEXT = "lareau.entity.clientPersonal";
    private final String OBJECT_DEFINITION_DEFAULT_CLIENT_COMMERCIAL_CONTEXT = "lareau.entity.clientCommercial";

    private final Long EMAIL_ID = 3L;
    private final String EMAIL_FROM = "testFrom@example.com";
    private final String EMAIL_TO = "testTo@example.com";
    private final String EMAIL_SUBJECT = "test";
    private final String EMAIL_BODY = "test";
    private final ZonedDateTime EMAIL_DATE = ZonedDateTime.parse("2017-01-01T00:00:00.000-01:00");
    private final ZonedDateTime EXPECTED_DEADLINE = EMAIL_DATE.plusDays(2);

    private final Long USER_TOKEN_ID = 4L;
    private final String USER_TOKEN_FIRSTNAME = "test";
    private final String USER_TOKEN_LASTNAME = "test";
    private final String USER_TOKEN_USERNAME = "testuser";
    private final String USER_TOKEN_EMAIL = "test@example.com";

    private final String EMAIL_TASK_TITLE = GOOM_OBJECT_TITLE + " - " + EMAIL_SUBJECT;

    @MockBean
    private GoomObjectService goomObjectService;

    @Autowired
    private EmailService emailService;

    @Before
    public void resetMocks() {
        MockitoAnnotations.initMocks(this);
        reset(goomObjectService);
    }

    @Test
    public void testCreateGoomObject() throws IOException {
        EventWrapper<EmailDTO> eventWrapper = getEmailWrapper();
        UserTokenDTO userToken = getUserToken();
        GoomObjectDTO goomObject = getGoomObject();

        eventWrapper.getData().getGoomObjects().add(goomObject);
        eventWrapper.setTriggeredByUser(userToken);

        emailService.createEmailTasksForEmail(eventWrapper);

        Mockito.verify(goomObjectService)
                .createGoomObject(Mockito.any(), Mockito.eq(ObjectDefinition.EMAIL_TASK), Mockito.any());
    }

    @Test
    public void testUseUserToken() throws IOException {
        EventWrapper<EmailDTO> eventWrapper = getEmailWrapper();
        UserTokenDTO userToken = getUserToken();
        GoomObjectDTO goomObject = getGoomObject();

        eventWrapper.getData().getGoomObjects().add(goomObject);
        eventWrapper.setTriggeredByUser(userToken);

        emailService.createEmailTasksForEmail(eventWrapper);

        Mockito.verify(goomObjectService)
                .createGoomObject(Mockito.any(), Mockito.any(), Mockito.eq(userToken));
    }

    @Test
    public void testCreateEmailTitle() throws IOException {
        EventWrapper<EmailDTO> eventWrapper = getEmailWrapper();
        UserTokenDTO userToken = getUserToken();
        GoomObjectDTO goomObject = getGoomObject();

        eventWrapper.getData().getGoomObjects().add(goomObject);
        eventWrapper.setTriggeredByUser(userToken);

        emailService.createEmailTasksForEmail(eventWrapper);

        ArgumentCaptor<GoomObjectDTO> goomObjectCaptor = ArgumentCaptor.forClass(GoomObjectDTO.class);
        Mockito.verify(goomObjectService)
                .createGoomObject(goomObjectCaptor.capture(), Mockito.any(), Mockito.any());

        assertEquals(goomObjectCaptor.getValue().getTitle(), EMAIL_TASK_TITLE);
        assertEquals(goomObjectCaptor.getValue().getDescription(), EMAIL_BODY);

        assertNotNull(goomObjectCaptor.getValue().getAttributes());
    }

    @Test
    public void testAddEmailId() throws IOException {
        EventWrapper<EmailDTO> eventWrapper = getEmailWrapper();
        UserTokenDTO userToken = getUserToken();
        GoomObjectDTO goomObject = getGoomObject();

        eventWrapper.getData().getGoomObjects().add(goomObject);
        eventWrapper.setTriggeredByUser(userToken);

        emailService.createEmailTasksForEmail(eventWrapper);

        ArgumentCaptor<GoomObjectDTO> goomObjectCaptor = ArgumentCaptor.forClass(GoomObjectDTO.class);
        Mockito.verify(goomObjectService)
                .createGoomObject(goomObjectCaptor.capture(), Mockito.any(), Mockito.any());

        JsonNode previousEmail = goomObjectCaptor.getValue().getAttributes().get("previousEmail");
        assertNotNull(previousEmail);
        assertNotNull(previousEmail.get("id"));
        assertEquals(previousEmail.get("id").asText(), EMAIL_ID.toString());
    }

    @Test
    public void testAddGoomObjectId() throws IOException {
        EventWrapper<EmailDTO> eventWrapper = getEmailWrapper();
        UserTokenDTO userToken = getUserToken();
        GoomObjectDTO goomObject = getGoomObject();

        eventWrapper.getData().getGoomObjects().add(goomObject);
        eventWrapper.setTriggeredByUser(userToken);

        emailService.createEmailTasksForEmail(eventWrapper);

        ArgumentCaptor<GoomObjectDTO> goomObjectCaptor = ArgumentCaptor.forClass(GoomObjectDTO.class);
        Mockito.verify(goomObjectService)
                .createGoomObject(goomObjectCaptor.capture(), Mockito.any(), Mockito.any());

        JsonNode goomObjectJson = goomObjectCaptor.getValue().getAttributes().get("goomObject");
        assertNotNull(goomObjectJson);
        assertNotNull(goomObjectJson.get("id"));
        assertEquals(goomObjectJson.get("id").asText(), GOOM_OBJECT_ID.toString());
    }

    @Test
    public void testDeadline() throws IOException {
        EventWrapper<EmailDTO> eventWrapper = getEmailWrapper();
        UserTokenDTO userToken = getUserToken();
        GoomObjectDTO goomObject = getGoomObject();

        eventWrapper.getData().getGoomObjects().add(goomObject);
        eventWrapper.setTriggeredByUser(userToken);

        emailService.createEmailTasksForEmail(eventWrapper);

        ArgumentCaptor<GoomObjectDTO> goomObjectCaptor = ArgumentCaptor.forClass(GoomObjectDTO.class);
        Mockito.verify(goomObjectService)
                .createGoomObject(goomObjectCaptor.capture(), Mockito.any(), Mockito.any());

        assertEquals(goomObjectCaptor.getValue().getAttributes().get("deadline").asText(),
                EXPECTED_DEADLINE.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }

    private EventWrapper<EmailDTO> getEmailWrapper() {
        EventWrapper<EmailDTO> eventWrapper = new EventWrapper<>();
        eventWrapper.setData(new EmailDTO());
        eventWrapper.setPublishedOn(EMAIL_DATE);
        eventWrapper.getData().setId(EMAIL_ID);
        eventWrapper.getData().getFroms().add(EMAIL_FROM);
        eventWrapper.getData().getTos().add(EMAIL_TO);
        eventWrapper.getData().setSubject(EMAIL_SUBJECT);
        eventWrapper.getData().setBody(EMAIL_BODY);

        return eventWrapper;
    }

    private UserTokenDTO getUserToken() {
        UserTokenDTO userTokenDTO = new UserTokenDTO();
        userTokenDTO.setId(USER_TOKEN_ID);
        userTokenDTO.setFirstname(USER_TOKEN_FIRSTNAME);
        userTokenDTO.setLastname(USER_TOKEN_LASTNAME);
        userTokenDTO.setUsername(USER_TOKEN_USERNAME);
        userTokenDTO.setEmail(USER_TOKEN_EMAIL);

        return userTokenDTO;
    }

    private GoomObjectDTO getGoomObject() {
        GoomObjectDTO goomObject = new GoomObjectDTO();
        goomObject.setId(GOOM_OBJECT_ID);
        goomObject.setTitle(GOOM_OBJECT_TITLE);
        goomObject.setKey(GOOM_OBJECT_KEY);

        ObjectDefinitionDTO objectDefinitionDTO = new ObjectDefinitionDTO();
        objectDefinitionDTO.setId(OBJECT_DEFINITION_ID);
        objectDefinitionDTO.setContext(OBJECT_DEFINITION_DEFAULT_CONTEXT);
        goomObject.setGoomObjectDefinition(objectDefinitionDTO);

        return goomObject;
    }
}

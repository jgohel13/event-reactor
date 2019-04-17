package com.inteloom.eventreactor.cucumber;

import com.fasterxml.jackson.databind.JsonNode;
import com.inteloom.eventreactor.config.ApplicationProperties;
import com.inteloom.eventreactor.domain.ObjectDefinition;
import com.inteloom.eventreactor.messaging.EmailConfiguration;
import com.inteloom.eventreactor.service.GoomObjectService;
import com.inteloom.eventreactor.service.dto.*;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.reset;

@SpringBootTest
public class EmailStep {

    @MockBean
    @Autowired
    private GoomObjectService goomObjectService;

    @Autowired
    private EmailConfiguration emailConfiguration;

    @Autowired
    private ApplicationProperties applicationProperties;

    private EventWrapper<EmailDTO> eventWrapper;

    private UserTokenDTO userTokenDTO;

    private ArgumentCaptor<UserTokenDTO> userTokenDTOCaptor;

    private ArgumentCaptor<GoomObjectDTO> goomObjectCaptor;

    @Before
    public void resetMocks() {
        MockitoAnnotations.initMocks(this);
        reset(goomObjectService);

        goomObjectCaptor = ArgumentCaptor.forClass(GoomObjectDTO.class);
        userTokenDTOCaptor = ArgumentCaptor.forClass(UserTokenDTO.class);
    }

    @Given("^an email exists$")
    public void anEmailExists() {
        eventWrapper = new EventWrapper<>();
        eventWrapper.setData(new EmailDTO());
        eventWrapper.setPublishedOn(ZonedDateTime.parse("2017-01-01T00:00:00.000-01:00"));
        eventWrapper.getData().setId(1L);
        eventWrapper.getData().getFroms().add("testFrom@example.com");
        eventWrapper.getData().getTos().add("testTo@example.com");
        eventWrapper.getData().setSubject("test");
        eventWrapper.getData().setBody("test");
    }

    @And("^the email has an assigned goom object$")
    public void theEmailHasAGoomObject() throws Throwable {
        GoomObjectDTO goomObject = new GoomObjectDTO();
        goomObject.setId(1L);
        goomObject.setTitle("Test Title");
        goomObject.setKey("TEST-1");
        ObjectDefinitionDTO objectDefinitionDTO = new ObjectDefinitionDTO();
        objectDefinitionDTO.setId(1L);
        objectDefinitionDTO.setContext("entity.test");
        goomObject.setGoomObjectDefinition(objectDefinitionDTO);
        eventWrapper.getData().getGoomObjects().add(goomObject);
    }

    @And("^the message has a user token$")
    public void theMessageHasAUserToken() {
        userTokenDTO = new UserTokenDTO();
        userTokenDTO.setId(1L);
        userTokenDTO.setFirstname("test");
        userTokenDTO.setLastname("test");
        userTokenDTO.setUsername("testuser");
        userTokenDTO.setEmail("test@example.com");
        eventWrapper.setTriggeredByUser(userTokenDTO);
    }

    @When("^the email is received$")
    public void anEmailIsReceived() throws IOException {
        eventWrapper.setKey("communication.email.inbound");
        emailConfiguration.inboundEmail(eventWrapper);
    }

    @When("^the email is sent")
    public void anEmailIsSent() throws IOException {
        eventWrapper.setKey("communication.email.outbound");
        emailConfiguration.outboundEmail(eventWrapper);
    }

    @When("^the email is assigned to an object$")
    public void anEmailIsAssigned() throws IOException {
        eventWrapper.setKey("email.assigned");
        emailConfiguration.assignedEmail(eventWrapper);
    }

    @Then("^create an email task object$")
    public void createAnObject() throws IOException {
        Mockito.verify(goomObjectService).createGoomObject(goomObjectCaptor.capture(), Mockito.eq(ObjectDefinition.EMAIL_TASK), userTokenDTOCaptor.capture());
    }

    @And("^the assigned object is a commercial client$")
    public void theAssignedObjectIsACommercialClient() throws Throwable {
        eventWrapper.getData().getGoomObjects().iterator().next().getGoomObjectDefinition().setContext("lareau.entity.clientCommercial");
    }

    @And("^the assigned object is a personal client$")
    public void theAssignedObjectIsAPersonalClient() throws Throwable {
        eventWrapper.getData().getGoomObjects().iterator().next().getGoomObjectDefinition().setContext("lareau.entity.clientPersonal");
    }

    @And("^link the client to the created object$")
    public void addTheClientSIdToTheCreatedObject() throws Throwable {
        JsonNode client = goomObjectCaptor.getValue().getAttributes().get("client");
        assertNotNull(client);
        assertNotNull(client.get("id"));
        assertEquals(client.get("id").asText(),
                eventWrapper.getData().getGoomObjects().iterator().next().getId().toString());
    }

    @Then("^don't create an email task object$")
    public void donTCreateAnEmailTaskObject() throws Throwable {
        Mockito.verify(goomObjectService, Mockito.never()).createGoomObject(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @And("^use the system user token$")
    public void useTheSystemUserToken() {
        assertEquals(applicationProperties.getSystemUser(), userTokenDTOCaptor.getValue());
    }

    @And("^use the user token received in the event$")
    public void useTheUserTokenReceivedInTheEvent() {
        assertEquals(userTokenDTO, userTokenDTOCaptor.getValue());
    }
}

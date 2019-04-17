package com.inteloom.eventreactor.cucumber;

import com.inteloom.eventreactor.messaging.SmsConfiguration;
import com.inteloom.eventreactor.service.GoomObjectService;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Created by ghumphries on 2017-11-16.
 */
public class SmsStep {

    @Mock
    private GoomObjectService goomObjectService;

    @InjectMocks
    private SmsConfiguration smsConfiguration;

    @Before
    public void resetMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Given("^an sms exists$")
    public void anSmsExists() {
        throw new PendingException();
    }

    @And("^sms is from \"([^\"]*)\"$")
    public void isFrom(String fromNumber) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^sms is received$")
    public void smsIsReceived() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}

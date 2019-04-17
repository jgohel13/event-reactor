package com.inteloom.eventreactor.cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/email", glue = {"com.inteloom.eventreactor.cucumber"})
public class CucumberTest {

}

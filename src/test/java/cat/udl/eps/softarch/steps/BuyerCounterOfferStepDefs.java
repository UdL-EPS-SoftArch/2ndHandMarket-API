package cat.udl.eps.softarch.steps;

import cat.udl.eps.softarch.Softarch1617Application;
import cat.udl.eps.softarch.domain.BuyerCounterOffer;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.constraints.AssertTrue;

/**
 * Created by xavier on 27/09/16.
 */


@ContextConfiguration(
        classes = {Softarch1617Application.class}, loader = SpringBootContextLoader.class)
@DirtiesContext
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class BuyerCounterOfferStepDefs {
    private static final Logger logger = LoggerFactory.getLogger(cat.udl.eps.softarch.steps.PostAdvertisementStepDefs.class);

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    private ResultActions result;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .build();
    }

    private BuyerCounterOffer buyerOffer= new BuyerCounterOffer();
    private BuyerCounterOffer buyerOffer2= new BuyerCounterOffer();

    @When("^I post a new buyer counter offer with a lower price value (\\d+\\.\\d+)$")
    public void iPostANewBuyerCounterOfferWithALowerPriceValue(float newValue) throws Throwable {
        buyerOffer.setValue(110);
        Assert.assertTrue(newValue < buyerOffer.getValue());
    }

    @Then("^A counter-offer id has been generated$")
    public void aCounterOfferIdHasBeenGenerated() throws Throwable {
        //org.assertj.core.api.AssertFalse(buyerOffer.getId().equals(buyerOffer2.getId()));
    }

    @And("^There is a new buyer counter offer with a lower price value \"([^\"]*)\"$")
    public void thereIsANewBuyerCounterOfferWithALowerPriceValue(String newValue) throws Throwable {
        //org.assertj.core.api.AssertFalse(buyerOffer.getId().equals(null));
    }
}

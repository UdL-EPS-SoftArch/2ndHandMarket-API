package cat.udl.eps.softarch.steps;

import cat.udl.eps.softarch.Softarch1617Application;
import cat.udl.eps.softarch.domain.SellerCounterOffer;
import cat.udl.eps.softarch.domain.SellerOffer;
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
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;

/**
 * Created by ierathenz on 2/10/16.
 */


@ContextConfiguration(
        classes = {Softarch1617Application.class}, loader = SpringBootContextLoader.class)
@DirtiesContext
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class SellerCounterOfferStepDefs {
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

    private SellerCounterOffer sellerOffer1 = new SellerCounterOffer();
    private SellerCounterOffer sellerOffer2 = new SellerCounterOffer();
    private String agent = new String("agent");
    private SellerOffer sellerOffer= new SellerOffer();

    @When("^I change the offer value to (\\d+\\.\\d+)$")
    public void iChangeTheOfferValueTo(float newValue) throws Throwable {
        sellerOffer1.setValue(20.5f);
        sellerOffer1.setAgent(agent);
        sellerOffer1.setRespondsTo(sellerOffer);
        Assert.assertTrue(newValue > sellerOffer1.getValue());
        //throw new PendingException();
    }

    @Then("^A counter-offer id has been generated$")
    public void aCounterOfferIdHasBeenGenerated() throws Throwable {
        Assert.assertFalse(Objects.equals(sellerOffer1, sellerOffer2));
        //throw new PendingException();
    }

    @And("^There is a new seller counter offer with an upper price value (\\d+\\.\\d+)$")
    public void thereIsANewSellerCounterOfferWithALowerPriceValue(float newValue) throws Throwable {
        Assert.assertFalse(Objects.equals(sellerOffer1, null));
        sellerOffer2.setValue(newValue);
        Assert.assertTrue(sellerOffer2.getValue() == newValue);
        Assert.assertTrue(sellerOffer1.getAgent().equals(agent));
        Assert.assertTrue(sellerOffer1.getRespondsTo().equals(sellerOffer));
    }

}



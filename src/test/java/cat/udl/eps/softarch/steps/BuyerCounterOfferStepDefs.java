package cat.udl.eps.softarch.steps;

import cat.udl.eps.softarch.Softarch1617Application;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
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

    @Then("^A counter-offer id has been generated$")
    public void aCounterOfferIdHasBeenGenerated() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}

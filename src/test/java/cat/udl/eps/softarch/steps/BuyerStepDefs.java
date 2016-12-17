package cat.udl.eps.softarch.steps;

import cat.udl.eps.softarch.Softarch1617Application;
import cat.udl.eps.softarch.domain.Buyer;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
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

import java.util.Objects;


/**
 * Created by jennifer on 27/09/16.
 */

@ContextConfiguration(
        classes = {Softarch1617Application.class}, loader = SpringBootContextLoader.class
)
@DirtiesContext
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class BuyerStepDefs {

    private static final Logger logger = LoggerFactory.getLogger(BuyerStepDefs.class);

    @Autowired private WebApplicationContext wac;
    private MockMvc mockMvc;
    private ResultActions result;

    private ObjectMapper mapper = new ObjectMapper();

    private Buyer buyer = new Buyer();

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .build();
    }

    @When("^I create a buyer with name \"([^\"]*)\" and lastname \"([^\"]*)\"$")
    public void iCreateABuyerWithNameAndLastname(String name, String lastname) throws Throwable {
        buyer.setName(name);
        buyer.setLastname(lastname);

    }

    @And("^I fill in name with \"([^\"]*)\"$")
    public void iFillInNameWith(String name) throws Throwable {
        buyer.setName(name);
    }

    @And("^I fill in lastname with \"([^\"]*)\"$")
    public void iFillInLastnameWith(String lastname) throws Throwable {
        buyer.setLastname(lastname);
    }


    @Then("^A buyer have id$")
    public void aBuyerHaveId() throws Throwable {
        org.junit.Assert.assertTrue(Objects.nonNull(buyer));
    }


    @Then("^A buyer add product$")
    public void aBuyerAddProduct()throws Throwable{
        org.junit.Assert.assertEquals(buyer.getCount(),buyer.getProducts().size());
    }
}

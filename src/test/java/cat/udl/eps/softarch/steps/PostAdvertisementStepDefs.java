package cat.udl.eps.softarch.steps;

import cat.udl.eps.softarch.Softarch1617Application;
import cat.udl.eps.softarch.domain.Advertisement;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.Matchers;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static cat.udl.eps.softarch.steps.AuthenticationStepDefs.authenticate;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Created by http://rhizomik.net/~roberto/
 */
@ContextConfiguration(
        classes = {Softarch1617Application.class}, loader = SpringBootContextLoader.class)

@DirtiesContext
@RunWith(SpringRunner.class)
@WebAppConfiguration

public class PostAdvertisementStepDefs {
    private static final Logger logger = LoggerFactory.getLogger(PostAdvertisementStepDefs.class);

    @Autowired private WebApplicationContext wac;
    private MockMvc mockMvc;
    private ResultActions result;

    private ObjectMapper mapper = new ObjectMapper();

    private Advertisement ad;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @When("^I create a new advertisement$")
    public void iCreateANewAdvertisement() throws Throwable {
        ad = new Advertisement();
    }

    @And("^I post the advertisement$")
    public void iPostTheAdvertisement() throws Throwable {
        String message = mapper.writeValueAsString(ad);

        result = mockMvc.perform(post("/advertisements")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON)
                .with(authenticate()))
                .andDo(print());
    }

    @And("^I put the advertisement with id \"([^\"]*)\"$")
    public void iPutTheAdvertisement(Long id) throws Throwable {
        String message = mapper.writeValueAsString(ad);

        result = mockMvc.perform(put("/advertisements/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON)
                .with(authenticate()))
                .andDo(print());
    }

    @And("^I delete the advertisement with id \"([^\"]*)\"$")
    public void iDeleteTheAdvertisementWithId(Long id) throws Throwable {
        String message = mapper.writeValueAsString(ad);

        result = mockMvc.perform(delete("/advertisements/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(authenticate()))
                .andDo(print());
    }

    @When("^I post an advertisement with title \"([^\"]*)\" and price \"([^\"]*)\"$")
    public void iPostAnAdvertisementWithTitle(String title, Double price) throws Throwable {
        Advertisement ad = new Advertisement();
        ad.setTitle(title);
        ad.setPrice(price);

        String message = mapper.writeValueAsString(ad);

        result = mockMvc.perform(post("/advertisements")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON)
                .with(authenticate()))
                .andDo(print());
    }

    @Then("^The status is (\\d+)$")
    public void theStatusIs(int status) throws Throwable {
        result.andExpect(status().is(status));
    }

    @And("^I fill in title with \"([^\"]*)\"$")
    public void iFillInTitleWith(String title) throws Throwable {
        ad.setTitle(title);
    }

    @And("^I fill in description with \"([^\"]*)\"$")
    public void iFillInDescriptionWith(String description) throws Throwable {
        ad.setDescription(description);
    }

    @And("^I fill in price with \"([^\"]*)\"$")
    public void iFillInPriceWith(Double price) throws Throwable {
        ad.setPrice(price);
    }

    @And("^I fill in negotiablePrice with \"([^\"]*)\"$")
    public void iFillInNegotiablePriceWith(Boolean negotiablePrice) throws Throwable {
        ad.setNegotiablePrice(negotiablePrice);
    }

    @And("^I fill in paidShipping with \"([^\"]*)\"$")
    public void iFillInPaidShippingWith(Boolean paidShipping) throws Throwable {
        ad.setPaidShipping(paidShipping);
    }

    @And("^I fill in tags with \"([^\"]*)\"$")
    public void iFillInTagsWith(String tags) throws Throwable {
        ad.setTags(stringTagsToSet(tags));
    }

    @And("^I fill in category with \"([^\"]*)\"$")
    public void iFillInCategoryWith(String category) throws Throwable {
        ad.setCategory(category);
    }

    @And("^I fill in brand with \"([^\"]*)\"$")
    public void iFillInBrandWith(String brand) throws Throwable {
        ad.setBrand(brand);
    }

    @And("^I fill in color with \"([^\"]*)\"$")
    public void iFillInColorWith(String color) throws Throwable {
        ad.setColor(color);
    }

    @And("^I fill in weight with \"([^\"]*)\"$")
    public void iFillInWeightWith(Double weight) throws Throwable {
        ad.setWeight(weight);
    }

    @Then("^There is no advertisement$")
    public void thereIsNoAdvertisement() throws Throwable {
        assertTrue(result == null);
    }

    @And("^There is an advertisement with title \"([^\"]*)\"$")
    public void thereIsAnAdvertisementWithTitle(String title) throws Throwable {
        String location = result.andReturn().getResponse().getHeader("Location");

        mockMvc.perform(get(location)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(title)))
                .andDo(print());
    }

    @And("^There is an advertisement with description \"([^\"]*)\"$")
    public void thereIsAnAdvertisementWithDescription(String description) throws Throwable {
        thereIsAn("$.description", description);
    }

    @And("^There is an advertisement with price \"([^\"]*)\"$")
    public void thereIsAnAdvertisementWithPrice(Double price) throws Throwable {
        thereIsAn("$.price", price);
    }

    @And("^There is an advertisement with negotiablePrice \"([^\"]*)\"$")
    public void thereIsAnAdvertisementWithNegotiablePrice(Boolean negotiablePrice) throws Throwable {
        thereIsAn("$.negotiablePrice", negotiablePrice);
    }

    @And("^There is an advertisement with paidShipping \"([^\"]*)\"$")
    public void thereIsAnAdvertisementWithPaidShipping(Boolean paidShipping) throws Throwable {
        thereIsAn("$.paidShipping", paidShipping);
    }

    @And("^There is an advertisement with tags \"([^\"]*)\"$")
    public void thereIsAnAdvertisementWithTags(String tags) throws Throwable {
        String location = result.andReturn().getResponse().getHeader("Location");
        String[] tagsList = tags.split(",");

        mockMvc.perform(get(location)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tags", Matchers.containsInAnyOrder(tagsList)));
    }

    @And("^There is an advertisement with category \"([^\"]*)\"$")
    public void thereIsAnAdvertisementWithCategory(String category) throws Throwable {
        thereIsAn("$.category", category);
    }

    @And("^There is an advertisement with brand \"([^\"]*)\"$")
    public void thereIsAnAdvertisementWithBrand(String brand) throws Throwable {
        thereIsAn("$.brand", brand);
    }

    @And("^There is an advertisement with color \"([^\"]*)\"$")
    public void thereIsAnAdvertisementWithColor(String color) throws Throwable {
        thereIsAn("$.color", color);
    }

    @And("^There is an advertisement with weight \"([^\"]*)\"$")
    public void thereIsAnAdvertisementWithWeight(Double weight) throws Throwable {
        thereIsAn("$.weight", weight);
    }

    private <T> void thereIsAn(String jsonPath, T expected) throws Throwable {
        String location = result.andReturn().getResponse().getHeader("Location");

        mockMvc.perform(get(location)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(jsonPath, is(expected)));
    }

    private Set<String> stringTagsToSet(String tags) {
        String[] tagsList = tags.split(",");
        return new HashSet<>(Arrays.asList(tagsList));
    }

    @Then("^The advertisement error message is \"([^\"]*)\"$")
    public void theStatusIs(String message) throws Throwable {
        if (result.andReturn().getResponse().getContentAsString().isEmpty())
            result.andExpect(status().reason(Matchers.is(message)));
        else
            result.andExpect(jsonPath("$.message", hasItem(message)));
    }

    @Then("^There are no advertisements$")
    public void thereAreNoAdvertisements() throws Throwable {
        mockMvc.perform(get("/advertisements")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.advertisements", is(empty())))
                .andDo(print());
    }
}

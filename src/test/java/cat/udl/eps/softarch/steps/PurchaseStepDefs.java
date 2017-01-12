package cat.udl.eps.softarch.steps;

import cat.udl.eps.softarch.domain.User;
import cat.udl.eps.softarch.repository.AdvertisementRepository;
import cat.udl.eps.softarch.repository.PurchaseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static cat.udl.eps.softarch.steps.AuthenticationStepDefs.authenticate;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PurchaseStepDefs  extends AbstractStepDefs {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseStepDefs.class);

    @Autowired private WebApplicationContext wac;
    private MockMvc mockMvc;
    private ResultActions result;
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired private AdvertisementRepository advertisementRepository;
    @Autowired private PurchaseRepository purchaseRepository;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @And("^I post a purchase to advertisement \"([^\"]*)\" who \"([^\"]*)\"$")
    public void iPostAPurchaseToAdvertisement(long advertisementId, String username) throws Throwable {
        String message = "{ \"advertisements\": [\"/advertisement/" + advertisementId + "\"] }";

        result = mockMvc.perform(post("/users/" + username + "/purchases/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON)
                .with(authenticate()))
                .andDo(print());
    }

    @And("^I \"([^\"]*)\" post a mass purchase to advertisements \"([^\"]*)\" and \"([^\"]*)\"$")
    public void iPostAMassPurchaseToAdvertisementsAnd(String username,String firstAdvertisementId, String secondAdvertisementId) throws Throwable {
        String message = "{ \"advertisements\": [\"/advertisement/" + firstAdvertisementId + "\", \"/advertisement/" + secondAdvertisementId + "\"] }";

        result = mockMvc.perform(post("/users/"+username+"/purchases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON)
                .with(authenticate()))
                .andDo(print());
    }

    @And("^I \"([^\"]*)\" post a purchase to no advertisement$")
    public void iPostAPurchaseToNoAdvertisement(String username) throws Throwable {
        String message = "{ \"advertisements\": [] }";

        result = mockMvc.perform(post("users/"+ username+ "/purchases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON)
                .with(authenticate()))
                .andDo(print());
    }

    @Then("^The purchase status is (\\d+)$")
    public void thePurchaseStatusIs(int status) throws Throwable {
        result.andExpect(status().is(status));
    }

    @And("^There is an advertisement with purchase")
    public void thereIsAnAdvertisementWithPurchase() throws Throwable {
        String advertisementLocation = "/advertisements/1/purchase";

        result = mockMvc.perform(get(advertisementLocation)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Then("^There is a purchase with (\\d+) advertisements$")
    public void thereIsAPurchaseWithAdvertisements(int numberAdvertisements) throws Throwable {

        String advertisementLocation = "/purchases/{username}/1/advertisements";

        result = mockMvc.perform(get(advertisementLocation)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.advertisements.*", Matchers.hasSize(numberAdvertisements)))
                .andDo(print());
    }

    @And("^There is a purchase with advertisement title \"([^\"]*)\"$")
    public void thereIsAPurchaseWithAdvertisementTitle(String advertisementTitle) throws Throwable {
        String advertisementLocation = "/purchases/{username}/1/advertisements";

        result = mockMvc.perform(get(advertisementLocation)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.advertisements[0].title", is(advertisementTitle)))
                .andDo(print());
    }

    @And("^There is a purchase with purchaser \"([^\"]*)\"$")
    public void thereIsAPurchaseWithPurchaser(User purchaser) throws Throwable {
        thereIsA("$.purchaser", purchaser);
    }

    @And("^There is a purchase with date$")
    public void thereIsAPurchaseWithDate() throws Throwable {
        String advertisementLocation = "/purchases/{username}/1";

        // We can't verify the exact date that the DB recorded, but we can verify that it is set.
        result = mockMvc.perform(get(advertisementLocation)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.createdAt", Matchers.notNullValue()))
                .andDo(print());
    }

    @And("^There is a purchase with total \"([^\"]*)\"$")
    public void thereIsAPurchaseWithTotal(double total) throws Throwable {
        thereIsA("$.total", total);
    }

    @And("^There are (\\d+) purchases$")
    public void thereArePurchases(int numPurchases) throws Throwable {
        result = mockMvc.perform(get("/purchases/{username}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.purchases.*", Matchers.hasSize(numPurchases)))
                .andDo(print());
    }

    @And("^I put purchase \"([^\"]*)\" with advertisement \"([^\"]*)\"$")
    public void iPutPurchaseWithAdvertisement(String purchaseId, String advertisementId) throws Throwable {
        String message = "{ \"advertisements\": [\"/advertisement/" + advertisementId + "\"] }";

        result = mockMvc.perform(put("/purchases/{username}/" + purchaseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON)
                .with(authenticate()))
                .andDo(print());
    }

    @And("^I patch purchase \"([^\"]*)\" with advertisement \"([^\"]*)\"$")
    public void iPatchPurchaseWithAdvertisement(String purchaseId, String advertisementId) throws Throwable {
        String message = "{ \"advertisements\": [\"/advertisement/" + advertisementId + "\"] }";

        result = mockMvc.perform(put("/purchases/{username}" + purchaseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON)
                .with(authenticate()))
                .andDo(print());
    }

    @And("^I delete purchase \"([^\"]*)\"$")
    public void iDeletePurchase(String purchaseId) throws Throwable {
        result = mockMvc.perform(delete("/purchases/{username}/" + purchaseId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(authenticate()))
                .andDo(print());
    }

    private <T> void thereIsA(String jsonPath, T expected) throws Throwable {
        String location = "/purchases/{username}/1";

        mockMvc.perform(get(location)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(jsonPath, is(expected)));
    }
}

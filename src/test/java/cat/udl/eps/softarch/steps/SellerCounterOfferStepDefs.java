package cat.udl.eps.softarch.steps;

import cat.udl.eps.softarch.Softarch1617Application;
import cat.udl.eps.softarch.domain.Advertisement;
import cat.udl.eps.softarch.domain.BuyerCounterOffer;
import cat.udl.eps.softarch.domain.SellerCounterOffer;
import cat.udl.eps.softarch.domain.SellerOffer;
import cat.udl.eps.softarch.repository.AdvertisementRepository;
import cat.udl.eps.softarch.repository.BuyerCounterOfferRepository;
import cat.udl.eps.softarch.repository.SellerOfferRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Autowired  private WebApplicationContext wac;
    private MockMvc mockMvc;
    private ResultActions result;
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired AdvertisementRepository advertisementRepository;
    @Autowired SellerOfferRepository sellerOfferRepository;
    @Autowired BuyerCounterOfferRepository buyerCounterOfferRepository;

    private Advertisement advertisement;
    private SellerOffer sellerOffer;
    private BuyerCounterOffer buyerCounterOffer;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .build();
    }

    @Given("^There is an existing advertisement with title \"([^\"]*)\"$")
    public void thereIsAnAdvertisementWithTitle(String title) throws Throwable {
        advertisement = new Advertisement();
        advertisement.setTitle(title);
        advertisement = advertisementRepository.save(advertisement);
    }

    @And("^There is a seller offer for the previous advertisement with price value (\\d+\\.\\d+)$")
    public void thereIsASellerOfferForThePreviousAdvertisementWithPriceValue(float value) throws Throwable {
        sellerOffer = new SellerOffer();
        sellerOffer.setAdvertisement(advertisement);
        sellerOffer.setValue(value);
        sellerOffer = sellerOfferRepository.save(sellerOffer);
    }

    @And("^There is a buyer counter offer for the previous seller offer with price value (\\d+\\.\\d+)$")
    public void thereIsABuyerCounterOfferForThePreviousSellerOfferWithPriceValue(float value) throws Throwable {
        buyerCounterOffer = new BuyerCounterOffer();
        buyerCounterOffer.setRespondsTo(sellerOffer);
        buyerCounterOffer.setValue(value);
        buyerCounterOffer = buyerCounterOfferRepository.save(buyerCounterOffer);
    }

    @When("^I create a seller counter offer for the previous buyer counter offer with price value (\\d+\\.\\d+)$")
    public void iCreateASellerCounterOfferForThePreviousBuyerCounterOfferWithPriceValue(float value) throws Throwable {
        SellerCounterOffer sellerCounterOffer = new SellerCounterOffer();
        sellerCounterOffer.setRespondsTo(buyerCounterOffer);
        sellerCounterOffer.setValue(value);

        String message = mapper.writeValueAsString(sellerCounterOffer);

        result = mockMvc.perform(
                post("/sellerCounterOffers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(message)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Then("^There is a new seller counter offer with price value (\\d+\\.\\d+)$")
    public void thereIsANewSellerCounterOfferWithPriceValue(float value) throws Throwable {
        String location = result.andReturn().getResponse().getHeader("Location");

        mockMvc.perform(get(location)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(value));
    }
}



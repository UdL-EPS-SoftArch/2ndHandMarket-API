package cat.udl.eps.softarch.steps;

import cat.udl.eps.softarch.domain.Advertisement;
import cat.udl.eps.softarch.repository.AdvertisementRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RestMediaTypes;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AddWishListStepDefs extends AbstractStepDefs {
    private static final Logger logger = LoggerFactory.getLogger(AddWishListStepDefs.class);

    @Autowired private WebApplicationContext wac;
    @Autowired private ObjectMapper mapper;
    @Autowired private AdvertisementRepository advertisementRepository;

    private MockMvc mockMvc;
    private ResultActions result;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @And("^I post the advertisement with title \"([^\"]*)\" to \"([^\"]*)\" wishlist$")
    public void iPostAWishlistToAdvertisement(String title, String username) throws Throwable {
        Advertisement advertisement = advertisementRepository.findByTitleContainingIgnoreCase(title).get(0);

        result = mockMvc.perform(post("/users/" + username +"/wishes")
                    .contentType(RestMediaTypes.TEXT_URI_LIST)
                    .content(advertisement.getUri()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Then("^User \"([^\"]*)\" has a wishlist with (\\d+) advertisement$")
    public void userHasAWishlistWithAdvertisement(String username, int count) throws Throwable {
        result = mockMvc.perform(get("/users/" + username +"/wishes")
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$._embedded.advertisements", hasSize(count)));
    }

    @And("^The advertisement with title \"([^\"]*)\" has a wisher named \"([^\"]*)\"$")
    public void theAdvertisementWithTitleHasAWisherNamed(String title, String username) throws Throwable {
        Advertisement advertisement = advertisementRepository.findByTitleContainingIgnoreCase(title).get(0);

        result = mockMvc.perform(get(advertisement.getUri()+"/wishers")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$._embedded.users[0].name", is(username)));
    }
}
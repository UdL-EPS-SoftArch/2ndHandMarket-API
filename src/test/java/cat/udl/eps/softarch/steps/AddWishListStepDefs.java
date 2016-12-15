package cat.udl.eps.softarch.steps;

import cat.udl.eps.softarch.Softarch1617Application;
import cat.udl.eps.softarch.domain.Advertisement;
import cat.udl.eps.softarch.domain.User;
import cat.udl.eps.softarch.domain.WishList;
import cat.udl.eps.softarch.repository.AdvertisementRepository;
import cat.udl.eps.softarch.repository.WishlistRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.hamcrest.Matchers;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.WebApplicationContext;

import javax.xml.bind.DatatypeConverter;
import java.net.URLConnection;

import static cat.udl.eps.softarch.steps.AuthenticationStepDefs.authenticate;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(
        classes = {Softarch1617Application.class}, loader = SpringBootContextLoader.class)
@DirtiesContext
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class AddWishListStepDefs {
    private static final Logger logger = LoggerFactory.getLogger(AddWishListStepDefs.class);

    @Autowired private WebApplicationContext wac;
    @Autowired private ObjectMapper mapper;
    @Autowired private MockMvc mockMvc;
    @Autowired private ResultActions result;
    private User user;

    @Autowired private AdvertisementRepository advertisementRepository;
    @Autowired private WishlistRepository wishlistRepository;
    public Advertisement advertisement;
    public WishList wishList;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @When("^I add advertisement to the list \"([^\"]*)\" $")
    public void iAddAdvertisementToTheList(String advname) throws Throwable {
        wishList  = new WishList();
        advertisement = new Advertisement();
        advertisement.setTitle(advname);
        wishList.setTowish(advertisement);

        String message = mapper.writeValueAsString(wishList);

        result = mockMvc.perform(
                post("/wishlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(message)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(authenticate()))
                .andDo(print());
    }

/*
    @When("^I add advertisement to the list \"([^\"]*)\" $")
    public void iAddAdvertisementToTheList(String advertisementId)throws Exception {
        wishList = new WishList();
        advertisement = new Advertisement();
        wishList.AddAdvertisement(advertisement);

        String message = "{ \"advertisement\": \"/advertisements/" + advertisementId + "\" }";

        result = mockMvc.perform(
                post("/wishlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(message)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(authenticate()))
                .andDo(print());
    }

     @Then("^There are (\\d+) advertisements to the wishlist $")
    public void thereAreAdvertisementsToTheWishList(int num) throws Exception {
         result
                 .andExpect(jsonPath("$._embedded.wishlist", hasSize(num)));
     }
*/





}

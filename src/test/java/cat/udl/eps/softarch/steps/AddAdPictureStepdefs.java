package cat.udl.eps.softarch.steps;

import cat.udl.eps.softarch.Softarch1617Application;
import cat.udl.eps.softarch.domain.Advertisement;
import cat.udl.eps.softarch.domain.Picture;
import cat.udl.eps.softarch.repository.AdvertisementRepository;
import cat.udl.eps.softarch.repository.PictureRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
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
import java.time.ZonedDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
public class AddAdPictureStepdefs {
    private static final Logger logger = LoggerFactory.getLogger(PostAdvertisementStepDefs.class);

    @Autowired private WebApplicationContext wac;
    @Autowired private AdvertisementRepository advertisementRepository;
    @Autowired private PictureRepository pictureRepository;
    @Autowired private ObjectMapper mapper;

    private MockMvc mockMvc;
    private ResultActions result;

    private Advertisement advertisement;
    private String currentUsername;
    private String currentPassword;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Given("^I login as \"([^\"]*)\" with password \"([^\"]*)\"$")
    public void iLoginAsWithPassword(String username, String password) throws Throwable {
        this.currentUsername = username;
        this.currentPassword = password;
    }

    @Given("^I'm not logged in$")
    public void iMNotLoggedIn() throws Throwable {
        this.currentUsername = this.currentPassword = null;
    }

    @Given("^There is an existing advertisement with title \"([^\"]*)\" and price (\\d+\\.\\d+)$")
    public void thereIsAnAdvertisementWithTitleAndPrice(String title, double price) throws Throwable {
        advertisement = new Advertisement();
        advertisement.setTitle(title);
        advertisement.setPrice(price);
        advertisement = advertisementRepository.save(advertisement);
    }

    @And("^The previous advertisement has already a picture with filename \"([^\"]*)\"$")
    public void thePreviousAdvertisementHasPicture(String filename) throws Throwable {
        Picture picture = new Picture();
        picture.setFilename(filename);
        Resource file = wac.getResource("classpath:"+filename);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        FileCopyUtils.copy(file.getInputStream(), output);
        String contentType = URLConnection.guessContentTypeFromName(filename);
        picture.setContent("data:" + contentType + ";base64," + DatatypeConverter.printBase64Binary(output.toByteArray()));
        picture.setDepicts(advertisement);
        pictureRepository.save(picture);
    }

    @When("^I list the previous advertisement pictures$")
    public void iListThePreviousAdvertisementPictures() throws Throwable {
        result = mockMvc.perform(get(advertisement.getUri() + "/pictures")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Then("^I get a list containing (\\d+) picture(?:s)?$")
    public void iGetAListContainingPicture(int count) throws Throwable {
        result
            .andExpect(jsonPath("$._embedded.pictures", hasSize(count)));
    }

    @When("^I add a picture with filename \"([^\"]*)\" of the previous advertisement$")
    public void iAddAPictureWithFilenameToThePreviousAdvertisement(String filename) throws Throwable {
        Picture picture = new Picture();
        picture.setFilename(filename);
        picture.setDepicts(advertisement);
        String message = mapper.writeValueAsString(picture);

        result = mockMvc.perform(
                post("/pictures")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(message)
                    .accept(MediaType.APPLICATION_JSON)
                    .with( (currentUsername!=null ? httpBasic(currentUsername, currentPassword) : anonymous()) ))
                .andDo(print());
    }

    @And("^Picture number (\\d+) has filename \"([^\"]*)\", owner \"([^\"]*)\" and was just created$")
    public void pictureNumberHasFilenameAndOwner(int i, String filename, String owner) throws Throwable {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime justBeforeNow = ZonedDateTime.now().minusSeconds(10);

        result
            .andExpect(jsonPath("$._embedded.pictures[" + (i-1) + "].filename", is(filename)))
            .andExpect(jsonPath("$._embedded.pictures[" + (i-1) + "].owner", is(owner)))
            .andExpect(jsonPath("$._embedded.pictures[" + (i-1) + "].published", lessThan(now.toString())))
            .andExpect(jsonPath("$._embedded.pictures[" + (i-1) + "].published", greaterThan(justBeforeNow.toString())));
    }

    @Then("^The error message is \"([^\"]*)\"$")
    public void theStatusIs(String message) throws Throwable {
        if (result.andReturn().getResponse().getContentAsString().isEmpty())
            result.andExpect(status().reason(is(message)));
        else
            result.andExpect(jsonPath("$..message", hasItem(message)));
    }
}
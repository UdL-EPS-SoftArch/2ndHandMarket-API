package cat.udl.eps.softarch.steps;

import cat.udl.eps.softarch.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static cat.udl.eps.softarch.steps.AuthenticationStepDefs.authenticate;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by jennifer on 4/10/16.
 */
public class UserStepDefs extends AbstractStepDefs {
    private static final Logger logger = LoggerFactory.getLogger(UserStepDefs.class);

    @Autowired private WebApplicationContext wac;
    @Autowired private UserRepository userRepository;

    private MockMvc mockMvc;
    private ResultActions result;
    private ObjectMapper mapper = new ObjectMapper();
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @And("^I can login with username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void iCanLoginWithUsernameAndPassword(String username, String password) throws Throwable {
        result = mockMvc.perform(
                get("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(httpBasic(username, password)))
                .andExpect(status().isOk());
    }

    @And("^I cannot login with username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void iCannotLoginWithUsernameAndPassword(String username, String password) throws Throwable {
        result = mockMvc.perform(
                get("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(httpBasic(username, password)))
                .andExpect(status().isUnauthorized());
    }

    @When("^I update username \"([^\"]*)\" password to \"([^\"]*)\"$")
    public void iUpdateUsernamePasswordTo(String username, String password) throws Throwable {
        String message = String.format("{\"username\":\"%s\",\"name\":\"%s\",\"password\":\"%s\"}",
                username, username, password);

        result = mockMvc.perform(
                put("/users/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(message)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @When("^I create an user with username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void iCreateAnUserWithUsernameAndPassword(String username, String password) throws Throwable {
        iCreateAnUserWithUsernameAndPasswordAndDisplayName(username, password, "");
    }

    @When("^I create an user with username \"([^\"]*)\" and password \"([^\"]*)\" and display name \"([^\"]*)\"$")
    public void iCreateAnUserWithUsernameAndPasswordAndDisplayName(String username, String password, String displayName) throws Throwable {
        String message = String.format("{\"username\":\"%s\",\"name\":\"%s\",\"password\":\"%s\",\"displayName\":\"%s\"}",
                username, username, password, displayName);

        result = mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(message)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Then("^I delete username \"([^\"]*)\"$")
    public void iDeleteUsername(String username) throws Throwable {
        result = mockMvc.perform(delete("/users/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(authenticate()))
                        .andDo(print());
    }

    @Then("^There is a registered user with username \"([^\"]*)\"$")
    public void thereIsARegisteredUserWithUsername(String username) throws Throwable {
        result = mockMvc.perform(
                get("/users/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(username)));
    }

    @Then("^There is a registered user with display name \"([^\"]*)\"$")
    public void thereIsARegisteredUserWithDisplayName(String displayName) throws Throwable {
        result = mockMvc.perform(
                get("/users/search/findByDisplayName?displayName={displayName}", displayName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.users[0].displayName", is(displayName)));
    }

    @Then("^The user status is (\\d+)$")
    public void theUserStatusIs(int status) throws Throwable {
        result.andExpect(status().is(status));
    }
}

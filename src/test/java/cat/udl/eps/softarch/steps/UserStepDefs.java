package cat.udl.eps.softarch.steps;

import cat.udl.eps.softarch.Softarch1617Application;
import cat.udl.eps.softarch.domain.User;
import cat.udl.eps.softarch.repository.UserRepository;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by jennifer on 4/10/16.
 */
@ContextConfiguration(
        classes = {Softarch1617Application.class}, loader = SpringBootContextLoader.class
)
@DirtiesContext
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class UserStepDefs {
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

    @Given("^There is an existing user with username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void iCreateAnUserWithNameAndLastname(String username, String password) throws Throwable {
        User user = new User();
        user.setUsername(username);
        user.setName(username);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
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
                username, username, passwordEncoder.encode(password));

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
        String message = String.format("{\"username\":\"%s\",\"name\":\"%s\",\"password\":\"%s\"}",
                username, username, passwordEncoder.encode(password));

        result = mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(message)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
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
}

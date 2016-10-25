package cat.udl.eps.softarch.steps;

import cat.udl.eps.softarch.Softarch1617Application;
import cat.udl.eps.softarch.domain.Advertisement;
import cat.udl.eps.softarch.domain.PrivateMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import java.util.LongSummaryStatistics;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by jap9. modified 28/09/16.
 */

@ContextConfiguration(
        classes = {Softarch1617Application.class}, loader = SpringBootContextLoader.class)
@DirtiesContext
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class SendPrivateMessageStepDefs {

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

    @When("^A private message with title \"([^\"]*)\" and body \"([^\"]*)\" is sent from \"([^\"]*)\" to user \"([^\"]*)\"$")
    public void iSendAPrivateMessageWithTitleAndBodyToUser(String title, String body, String sender, String destination) throws Throwable {

        PrivateMessage pMessage = new PrivateMessage();

        pMessage.setTitle(title);
        pMessage.setBody(body);
        pMessage.setSender(sender);
        pMessage.setDestination(destination);

        String message = mapper.writeValueAsString(pMessage);

        result = mockMvc.perform(post("/privateMessages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @And("^There is a private message with title \"([^\"]*)\"$")
    public void thereIsAnPrivateMessageWithTitle(String title) throws Throwable {
        String location = result.andReturn().getResponse().getHeader("Location");

        mockMvc.perform(get(location)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(title)))
                .andDo(print());
    }

    @And("^There is a private message with body \"([^\"]*)\"$")
    public void thereIsAnPrivateMessageWithBody(String body) throws Throwable {
        thereIsAn("$.body", body);
    }

    @And("^There is a private message sent to user \"([^\"]*)\"$")
    public void thereIsAnPrivateMessageSentToUser(String user) throws Throwable {
        thereIsAn("$.destination", user);
    }

    @And("^There is a private message sent from user \"([^\"]*)\"$")
    public void thereIsAnPrivateMessageSentFromUser(String sender) throws Throwable {
        thereIsAn("$.sender", sender);
    }

    private <T> void thereIsAn(String jsonPath, T expected) throws Throwable {
        String location = result.andReturn().getResponse().getHeader("Location");

        mockMvc.perform(get(location)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(jsonPath, is(expected)));
    }
}

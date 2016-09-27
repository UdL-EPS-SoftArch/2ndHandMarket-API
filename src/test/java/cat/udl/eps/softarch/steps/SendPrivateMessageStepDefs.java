package cat.udl.eps.softarch.steps;

import cat.udl.eps.softarch.Softarch1617Application;
import cat.udl.eps.softarch.domain.PrivateMessage;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import java.util.LongSummaryStatistics;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jap9 on 22/09/16.
 */
@ContextConfiguration(
        classes = {Softarch1617Application.class}, loader = SpringBootContextLoader.class)
@DirtiesContext
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class SendPrivateMessageStepDefs {

    private PrivateMessage message;
    private ResultActions result;

    @When("^I send a private message with title \"([^\"]*)\"$")
    public void iSendAPrivateMessageWithTitle(String title) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        message = new PrivateMessage();
        message.setTitle(title);
    }

    @Then("^An id is generated for this message$")
    public void anIdIsGeneratedForThisMessage() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Random randomGenerator = new Random();
        long randomId = randomGenerator.nextInt(100);
        message.setId(randomId);
    }

    @And("^There is a message sent with title \"([^\"]*)\"$")
    public void thereIsAMessageSentWithTitle(String titleSent) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertThat(message.getTitle().equals(titleSent));
    }


}

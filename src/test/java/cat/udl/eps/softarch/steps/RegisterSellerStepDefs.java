package cat.udl.eps.softarch.steps;

import cat.udl.eps.softarch.Softarch1617Application;
import cat.udl.eps.softarch.domain.RegisterSeller;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import java.util.Random;

/**
 * Created by Carles on 22/09/2016.
 */
@ContextConfiguration(
        classes = {Softarch1617Application.class}, loader = SpringBootContextLoader.class)
@DirtiesContext
@RunWith(SpringRunner.class)
@WebAppConfiguration

public class RegisterSellerStepDefs {

    private RegisterSeller seller;


    @When("^I create a seller with name \"([^\"]*)\" and mail \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void iCreateASellerWithNameAndMailAndPassword(String name, String mail, String password) throws Throwable {

        seller = new RegisterSeller();
        seller.setName(name);
        seller.setMail(mail);
        seller.setPassword(password);
    }
    @Then("^An id is generated for this seller$")
    public void anIdIsGeneratedForThisSeller() throws Throwable {

        Random randomGenerator = new Random();
        long randomId = randomGenerator.nextInt(100);
        seller.setId(randomId);
    }

    @And("^There is a seller with name \"([^\"]*)\"$")
    public void thereIsASellerWithName(String nameSeller) throws Throwable {

        seller.getName().equals(nameSeller);
    }

    @And("^There is a seller with mail \"([^\"]*)\"$")
    public void thereIsASellerWithMail(String mailSeller) throws Throwable {

        seller.getMail().equals(mailSeller);    }

    @And("^There is a seller with password \"([^\"]*)\"$")
    public void andThereIsASellerWithPassword(String passwordSeller) throws Throwable{

        seller.getPassword().equals(passwordSeller);
    }

}

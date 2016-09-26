package cat.udl.eps.softarch.steps;

import cat.udl.eps.softarch.domain.Advertisement;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;


/**
 * Created by srblimp.
 */
public class SearchAdvertisementStepDefs {

    private Advertisement advert = new Advertisement();

    @When("^I search an advertisement with the keyWord \"([^\"]*)\"")
    public void SearchAdvertisement(String keyWords) throws Throwable{
        advert.setKeyWords(keyWords);

    }

    @Then("^There is an advertisement with keyWord \"([^\"]*)\"$")
    public void thereIsAnAdvertisementWithKeyWord(String keyWord) throws Throwable {
        assertThat(advert.getKeyWords(),equalTo(keyWord));
    }
}

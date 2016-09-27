package cat.udl.eps.softarch.steps;

import cat.udl.eps.softarch.domain.Advertisement;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;


/**
 * Created by srblimp.
 */
public class SearchAdvertisementStepDefs {

    private Advertisement advert = new Advertisement();

    @When("^I search an advertisement with the keyWord \"([^\"]*)\"")
    public void SearchAdvertisement(String keyword) throws Throwable{
        Set<String> tags = new HashSet<>();
        tags.add(keyword);
        advert.setTags(tags);

    }

    @Then("^There is an advertisement with keyWord \"([^\"]*)\"$")
    public void thereIsAnAdvertisementWithKeyWord(String keyWord) throws Throwable {
        assertTrue(CollectionUtils.containsInstance(advert.getTags(), keyWord));
    }
}

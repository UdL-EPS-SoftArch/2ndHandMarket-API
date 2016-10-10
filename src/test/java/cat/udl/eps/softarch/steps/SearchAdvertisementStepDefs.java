package cat.udl.eps.softarch.steps;

import cat.udl.eps.softarch.domain.Advertisement;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import cat.udl.eps.softarch.repository.AdvertisementRepository;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created by srblimp.
 */
public class SearchAdvertisementStepDefs {

    @Autowired private AdvertisementRepository advertisementRepository;

    private Advertisement advert = new Advertisement();

    @Given("^There is an advertisement with the tag \"([^\"]*)\"$")
    public void SearchAdvertisement(String tag) throws Throwable{
        //Advertisement advert = new Advertisement();
        Set<String> tags = new HashSet<>();
        tags.add(tag);
        advert.setTags(tags);
        advert.setTitle(tag + "new");
        advert = advertisementRepository.save(advert);
    }

    @Then("^I get an advertisement with tag \"([^\"]*)\"$")
    public void thereIsAnAdvertisementWithKeyWord(String tag) throws Throwable {
        assertTrue(CollectionUtils.containsInstance(advert.getTags(), tag));
    }

    @Then("^There is not an advertisement with tag \"([^\"]*)\"$")
    public void thereIsnTAnAdvertisementWithTag(String tag) throws Throwable {
        assertFalse(CollectionUtils.containsInstance(advert.getTags(), tag));
    }

}

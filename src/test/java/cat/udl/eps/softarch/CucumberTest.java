package cat.udl.eps.softarch;

/**
 * Created by http://rhizomik.net/~roberto/
 */

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin={"pretty"}, features="src/test/resources")
public class CucumberTest {
}

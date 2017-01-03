package cat.udl.eps.softarch.steps;

import cat.udl.eps.softarch.Softarch1617Application;
import cat.udl.eps.softarch.config.WebSecurityTestConfig;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@ContextConfiguration(
        classes = {Softarch1617Application.class, WebSecurityTestConfig.class}, loader = SpringBootContextLoader.class
)
@DirtiesContext
@RunWith(SpringRunner.class)
@WebAppConfiguration
@ActiveProfiles("Test")
public abstract class AbstractStepDefs { }

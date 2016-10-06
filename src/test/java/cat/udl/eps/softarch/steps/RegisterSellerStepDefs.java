package cat.udl.eps.softarch.steps;

import cat.udl.eps.softarch.Softarch1617Application;
import cat.udl.eps.softarch.domain.RegisterSeller;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
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

import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Carles on 22/09/2016.
 */
@ContextConfiguration(
        classes = {Softarch1617Application.class}, loader = SpringBootContextLoader.class)
@DirtiesContext
@RunWith(SpringRunner.class)
@WebAppConfiguration

public class RegisterSellerStepDefs {


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


    @When("^I create a seller with name \"([^\"]*)\" and mail \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void iCreateASellerWithNameAndMailAndPassword(String name, String mail, String password) throws Throwable {

        RegisterSeller se = new RegisterSeller();
        se.setName(name);
        se.setMail(mail);
        se.setPassword(password);

        String message = mapper.writeValueAsString(se);

        result = mockMvc.perform(post("/registerSellers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }


    @And("^There is a seller with name \"([^\"]*)\"$")
    public void thereIsASellerWithName(String name) throws Throwable {
        String location = result.andReturn().getResponse().getHeader("Location");

         mockMvc.perform(get(location)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andDo(print());
    }

    @And("^There is a seller with mail \"([^\"]*)\"$")
    public void thereIsASellerWithMail(String mail) throws Throwable {

        thereIsAn("$.mail", mail);   }

    @And("^There is a seller with password \"([^\"]*)\"$")
    public void andThereIsASellerWithPassword(String password) throws Throwable{

        thereIsAn("$.password", password);

    }
    private <T> void thereIsAn(String jsonPath, T expected) throws Throwable {
        String location = result.andReturn().getResponse().getHeader("Location");

        mockMvc.perform(get(location)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(jsonPath, is(expected)));
    }

}

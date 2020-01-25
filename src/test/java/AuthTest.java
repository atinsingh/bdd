import cucumber.api.CucumberOptions;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import gherkin.deps.com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;


public class AuthTest {

    Response response;
    String basePath;

    @Given("^Api server is is running at \"([^\"]*)\"$")
    public void apiServerIsIsRunningAt(String basePath) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
       this.basePath = basePath;
    }


    @When("^User calls \"([^\"]*)\" end point with username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void userCallsEndPointWithUsernameAndPassword(String arg0, String arg1, String arg2) throws Throwable {
        RestAssured.baseURI = basePath;
        Map<String,String> map = new HashMap<String, String>();
        map.put("username",arg1);
        map.put("password",arg2);
        response = RestAssured.given().headers(Collections.singletonMap("Content-type","application/json")).body(map).post(arg0);
    }

    @Then("^api should return status code (\\d+)$")
    public void apiShouldReturnStatusCode(int code) throws Throwable {
      response.then().assertThat().statusCode(code);

    }

    @And("^api should also have \"([^\"]*)\" in body$")
    public void apiShouldAlsoHaveInBody(String arg0) throws Throwable {
        System.out.println(response.body().prettyPrint());
        response.then().assertThat().body("$", hasKey("id_token"));
    }
}

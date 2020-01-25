import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;

public class TagTest {
    Response response;
    String token;
    int id;
    @Given("^User is authenticated with username \"([^\"]*)\" password \"([^\"]*)\"$")
    public void userIsAuthenticatedWithUsernamePassword(String arg0, String arg1) throws Throwable {

        response = RestAssured.given()
                .headers(Collections.singletonMap("Content-type", "application/json"))
                .body(new UserData(arg0,arg1))
                .post("http://pragrablog.herokuapp.com/api/authenticate");
        token = response.jsonPath().get("id_token");
    }

    @When("^user calls \"([^\"]*)\" with tag name as \"([^\"]*)\"$")
    public void userCallsWithTagNameAs(String arg0, String arg1) throws Throwable {
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-type", "application/json");
        headers.put("Authorization", "Bearer "+token);
      response = RestAssured.given().headers(headers).body(Collections.singletonMap("name",arg1))
                .post("http://pragrablog.herokuapp.com"+arg0);
    }

    @Then("^api should ruturn with status (\\d+) in response$")
    public void apiShouldRuturnWithStatusInResponse(int arg0) throws Throwable {
        response.then().assertThat().statusCode(arg0);
    }

    @And("^it should have id as key$")
    public void itShouldHaveIdAsKey() throws Throwable {
       response.then().assertThat().body("$", hasKey("id"));
       id = response.jsonPath().get("id");
    }

    @And("^response should also have cucumber in response$")
    public void responseShouldAlsoHaveCucumberInResponse() throws Throwable {
        Map<String,String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer "+token);
        Response response1 = RestAssured.given().headers(headers).get("http://pragrablog.herokuapp.com/api/tags/" + id);
        response1.then().assertThat().statusCode(200);
        response1.then().assertThat().body("name",equalTo("cucumber"));
    }
}

class UserData {
    private String username;
    private String password;

    public UserData() {
    }

    public UserData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

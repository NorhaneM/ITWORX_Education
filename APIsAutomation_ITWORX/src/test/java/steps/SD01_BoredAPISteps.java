package steps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

public class SD01_BoredAPISteps  {

    private Response response;

    @Given("the BoredAPI endpoint is available")
    public void givenTheBoredAPIEndpointIsAvailable() {
        RestAssured.baseURI = "https://www.boredapi.com/api";
    }

    @When("a GET request is made to the API")
    public void whenAGetRequestIsMadeToTheAPI() {
        response = RestAssured.given().when().get("/activity");
    }

    @Then("the response code should be {int}")
    public void thenTheResponseCodeShouldBe(int expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);
    }

    @Then("the response schema should match the expected structure")
    public void thenTheResponseSchemaShouldMatchTheExpectedStructure() {
        response.then().body("activity", not(emptyOrNullString()))
                .body("type", not(emptyOrNullString()))
                .body("participants", greaterThanOrEqualTo(0))
                .body("price", not(empty()))
                .body("link", not(empty()))
                .body("key", not(empty()))
                .body("accessibility", greaterThanOrEqualTo(0.0f));
    }
}

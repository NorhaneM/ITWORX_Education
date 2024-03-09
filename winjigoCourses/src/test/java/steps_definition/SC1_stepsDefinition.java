package steps_definition;
import Pages.coursePage;
import Pages.loginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.io.IOException;


public class SC1_stepsDefinition extends loginPage {
    public SC1_stepsDefinition() throws IOException {
        super();
    }
    loginPage login = new loginPage();
    coursePage course = new coursePage();

    @Given("user navigate to home page")
    public void navigate_home() {
        login.navigateLoginPage();
        login.loginData();
    }

    @When("user open courses page from left side navigation bar")
    public void course_Page() {
        course.coursePageTab().click();
        // Get the current URL
        String currentUrl = Hooks.driver.getCurrentUrl();
        // Print current URL to console
        System.out.println("Current URL: " + currentUrl);
        // Assert on the current URL
        Assert.assertEquals(currentUrl, "https://swinji.azurewebsites.net/Course#!/list/", "URL doesn't match");
        // Print information after the assertion
        System.out.println("Assertion passed! Current URL is: " + currentUrl);
    }
    @And("user click on create course button and fill mandatory data")
    public void createCourse() {
        course.createCourseBtn().click();
        course.courseData();
    }

    @Then("user back to courses list page and assert that course title is displayed")
    public void searchCourse() {
        course.coursePageTab().click();
        course.searchCourseName();
    }
    }

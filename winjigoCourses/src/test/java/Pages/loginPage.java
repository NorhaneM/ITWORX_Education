package Pages;

import org.openqa.selenium.By;
import org.testng.Assert;
import steps_definition.Hooks;
import java.io.IOException;

public class loginPage extends driverActions.driverActions {
    // Locators
    private final By usernameField = By.id("Email");
    private final By passwordField = By.id("inputPassword");
    private final By loginButton = By.id("btnLogin");
    public loginPage() throws IOException {
    }
    String username = prop.getProperty("username");
    String password =prop.getProperty("password");

    public void navigateLoginPage() {
        // Navigate to the URL
        navigateTo("URL");
        System.out.println("Navigating to the home page...");
        // Get the current URL
        String currentUrl = Hooks.driver.getCurrentUrl();
        // Print current URL to console
        System.out.println("Current URL: " + currentUrl);
        // Assert on the current URL
        Assert.assertEquals(currentUrl, "https://swinji.azurewebsites.net/account/login", "URL doesn't match");
        // Print information after the assertion
        System.out.println("Assertion passed! Current URL is: " + currentUrl);
    }
        public void loginData() {
            waitclickable(usernameField);
            Hooks.driver.findElement(usernameField).sendKeys(username);
            waitclickable(passwordField);
            Hooks.driver.findElement(passwordField).sendKeys(password);
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
            waitclickable(loginButton);
            Hooks.driver.findElement(loginButton).click();
        }
    }


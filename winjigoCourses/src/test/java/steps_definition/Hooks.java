package steps_definition;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class Hooks {
    public static WebDriver driver;

    @Before
    public void open_browser() {
        System.out.println("Opening the browser...");
            ChromeOptions options = new ChromeOptions();
            // Disable pop-ups
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-infobars");
            options.addArguments("--disable-notifications");
            options.addArguments("--ignore-certificate-errors");
            // Initialize ChromeDriver with the configured options
            driver = new ChromeDriver(options);
        // Maximize window
        driver.manage().window().maximize();

        // Set implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Set page load timeout
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));

    }

    @After
    public void quit_browser() {
        System.out.println("Quitting the browser...");
        if (driver != null) {
            driver.quit();
        }
    }
}

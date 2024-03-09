package driverActions;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import steps_definition.Hooks;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class driverActions {
    ;
    public static Properties prop;

    public driverActions() throws IOException {
        prop = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\java\\settings\\settings.properties");
        prop.load(fis);
    }

    public static JavascriptExecutor js = (JavascriptExecutor) Hooks.driver;


    //*****************************************wait element to be clickbale *************************************************//
    public static void waitclickable(By element) {
        new WebDriverWait(Hooks.driver, Duration.ofSeconds(30)).until(ExpectedConditions.elementToBeClickable(element));
    }

    //****************************************Navigate to *************************************************//
    public void navigateTo(String URL) {
        Hooks.driver.navigate().to(prop.getProperty(URL));

    }
    //*****************************************wait element to be visible *************************************************//
}
package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import steps_definition.Hooks;

import java.io.IOException;

public class coursePage extends driverActions.driverActions {
    String script = "arguments[0].style.border = '2px solid purple';";
    String fullCourseName = prop.getProperty("courseName");

    // Locators
    private final By courseBtn = By.id("btnMyCoursesList");
    // private final By courseBtn = By.xpath("//a[contains(@href, 'courses')]"); // another way to locate the element
    private final By createBtn = By.id("btnListAddCourse");
    // private final By createBtn = By.xpath("//a[@class='btn btn-success']"); // another way to locate the element
    private final By courseName = By.id("txtCourseName");
    private final By selectGrade = By.id("courseGrade");
    private final By selectTeacher = By.id("teacherOnBehalf");
    private final By selectTestTeacher = By.id("lnkCourseOwnerMail");
    private final By createCourseBtn = By.id("btnSaveAsDraftCourse");
    private final By searchBar = By.id("txtCourseSearch");
    private final By linkCourse = By.xpath("//a[@id='lnkListCourseSelcted' and @title='WebAutomation by Nourhane Mahrous']");
    private final By descendingOrder = By.id("CoursesOrderBy");
    public coursePage() throws IOException {
    }
   public WebElement coursePageTab() {
       waitclickable(courseBtn);
       WebElement coursePage = Hooks.driver.findElement(courseBtn);
       // Apply CSS style to change border color
       js.executeScript(script, Hooks.driver.findElement(courseBtn));
       return coursePage ;
   }
    public WebElement createCourseBtn() {
        waitclickable(createBtn);
        WebElement addCourse = Hooks.driver.findElement(createBtn);
        // Apply CSS style to change border color
        js.executeScript(script, Hooks.driver.findElement(createBtn));
        return addCourse;
    }
    public void courseData() {
        waitclickable(courseName);
        Hooks.driver.findElement(courseName).sendKeys(fullCourseName);
        // Apply CSS style to change border color
        js.executeScript(script, Hooks.driver.findElement(courseName));


        waitclickable(selectGrade);
        WebElement selectGradeYear = Hooks.driver.findElement(selectGrade);
        Select dropdown = new Select(selectGradeYear);
        dropdown.selectByValue("number:6");
        // Apply CSS style to change border color
        js.executeScript(script, Hooks.driver.findElement(selectGrade));

        waitclickable(selectTeacher);
        WebElement element = Hooks.driver.findElement(selectTeacher);
        // Apply CSS style to change border color
        js.executeScript(script, Hooks.driver.findElement(selectTeacher));
        element.click();


        waitclickable(selectTestTeacher);
        WebElement testTeacher = Hooks.driver.findElement(selectTestTeacher);
        testTeacher.click();


        waitclickable(createCourseBtn);
        WebElement createBtn = Hooks.driver.findElement(createCourseBtn);
        createBtn.click();
        // Apply CSS style to change border color
        js.executeScript(script, Hooks.driver.findElement(createCourseBtn));
        Assert.assertTrue(Hooks.driver.findElement(createCourseBtn).isDisplayed());
        System.out.println("Assertion passed! The Course has been added successfully");
    }

    public void searchCourseName() {
        waitclickable(searchBar);
        WebElement searchCourse = Hooks.driver.findElement(searchBar);
        searchCourse.sendKeys(fullCourseName);
        searchCourse.sendKeys(Keys.ENTER);

        waitclickable(descendingOrder);
        WebElement selectOrder = Hooks.driver.findElement(descendingOrder);
        Select dropdown = new Select(selectOrder);
        dropdown.selectByValue("3");

        WebElement courseNameText = Hooks.driver.findElement(linkCourse);
        //  Assert that the course name appears
        Assert.assertTrue(courseNameText.isDisplayed(), "webAutomation by Nourhane Mahrous");
        // Print information after the assertion
        System.out.println("Assertion passed! The Course 'webAutomation by Nourhane Mahrous' is displayed.");
        // Apply CSS style to change border color
        js.executeScript(script, Hooks.driver.findElement(linkCourse));
    }
}

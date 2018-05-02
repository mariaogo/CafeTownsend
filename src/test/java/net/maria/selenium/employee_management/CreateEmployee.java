package net.maria.selenium.employee_management;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CreateEmployee {
    public static WebDriver driver;

    @BeforeClass
    public static void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "/Users/mariaogonowska/workspace/CafeTownsend/drivers/chromedriver");
        driver = new ChromeDriver();
    }

    @AfterClass
    public static void afterClass() {
        //driver.quit();
    }

    public void successfulLogin() {
        driver.get("http://cafetownsend-angular-rails.herokuapp.com/login");
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.findElement(By.cssSelector("#login-form > fieldset > label:nth-child(3) > input")) != null;
            }
        });

        final WebElement userNameElement = driver.findElement(By.cssSelector("#login-form > fieldset > label:nth-child(3) > input"));
        userNameElement.sendKeys("Luke");

        final WebElement passwordElement = driver.findElement(By.cssSelector("#login-form > fieldset > label:nth-child(4) > input"));
        passwordElement.sendKeys("Skywalker");

        driver.findElement(By.tagName("button")).click();

        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {

                WebElement headerElement = d.findElement(By.cssSelector("body > div > header > div"));
                String top = headerElement.getCssValue("top");
                System.out.println("top = " + top);
                return top.equals("0px");
            }

        });
    }

    @Test
    public void checkCreateEmployFormular() {
        successfulLogin();

        final boolean createButtonIsDisplayed = driver.findElement(By.id("bAdd")).isDisplayed();
        System.out.println("createButtonIsDisplayed = " + createButtonIsDisplayed);
        Assert.assertEquals(true, createButtonIsDisplayed);

        driver.findElement(By.id("bAdd")).click();

        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.findElement(By.tagName("input")) != null;
            }
        });
        driver.findElement(By.tagName("input")).isDisplayed();
        driver.findElement(By.tagName("button")).isDisplayed();

        WebElement firstInputField = driver.findElement(By.cssSelector("body > div > div > div > form > fieldset > label:nth-child(3) > span"));
        String firstName = firstInputField.getText();
        System.out.print(firstName);

        Assert.assertEquals(
                "First name:",
                firstName
        );
    }
}

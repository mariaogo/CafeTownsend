package net.maria.selenium.login;

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

public class LoginTest {
    public static WebDriver driver;

    @BeforeClass
    public static void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "/Users/mariaogonowska/workspace/CafeTownsend/drivers/chromedriver");
        driver = new ChromeDriver();
    }

    @AfterClass
    public static void afterClass() {
        driver.quit();
    }


    @Test
    public void successfulLoginTest() {
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

        WebElement loggedInPage = driver.findElement(By.id("greetings"));

        String textLoggedinPage = loggedInPage.getText();

        System.out.print(textLoggedinPage);

        Assert.assertTrue(
                "Hello Luke".equals(textLoggedinPage)
        );

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void wrongPasswordTest() {
        driver.get("http://cafetownsend-angular-rails.herokuapp.com/login");

        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.findElement(By.cssSelector("#login-form > fieldset > label:nth-child(3) > input")) != null;
            }
        });

        final WebElement userNameElement = driver.findElement(By.cssSelector("#login-form > fieldset > label:nth-child(3) > input"));
        userNameElement.sendKeys("Luke");

        final WebElement passwordElement = driver.findElement(By.cssSelector("#login-form > fieldset > label:nth-child(4) > input"));
        passwordElement.sendKeys("Milkyway");

        driver.findElement(By.tagName("button")).click();

        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                WebElement errorMessageElement = d.findElement(By.cssSelector("#login-form > fieldset > p.error-message.ng-binding"));
                return errorMessageElement != null && errorMessageElement.isDisplayed();
            }
        });


        WebElement errorMessage = driver.findElement(By.cssSelector("#login-form > fieldset > p.error-message.ng-binding"));
        String inlogged = errorMessage.getText();
        System.out.print(inlogged);

        Assert.assertEquals(
                "Invalid username or password!",
                inlogged
        );
    }
}

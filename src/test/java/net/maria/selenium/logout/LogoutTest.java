package net.maria.selenium.logout;
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

public class LogoutTest {
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

        WebElement userNameElement = driver.findElement(By.cssSelector("#login-form > fieldset > label:nth-child(3) > input"));
        userNameElement.sendKeys("Luke");

        WebElement passwordElement = driver.findElement(By.cssSelector("#login-form > fieldset > label:nth-child(4) > input"));
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
    public void successfulLogout() {
        successfulLogin();

        final boolean isDisplayed = driver.findElement(By.cssSelector("body > div > header > div > p.main-button")).isDisplayed();
        System.out.println("isDisplayed = " + isDisplayed);

        driver.findElement(By.cssSelector("body > div > header > div > p.main-button")).click();
        final boolean displayedLoginButton = driver.findElement(By.cssSelector("#login-form > fieldset > button")).isDisplayed();

        Assert.assertTrue(displayedLoginButton);
    }
}
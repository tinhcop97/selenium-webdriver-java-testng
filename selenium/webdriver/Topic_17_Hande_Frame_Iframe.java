package webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Topic_17_Hande_Frame_Iframe {
    WebDriver driver;
    JavascriptExecutor js;
    WebDriverWait explicitwait;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    Actions action;
    JavascriptExecutor jsExcuter;


    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
        } else {
            System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver"); //mac
        }

        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;

        explicitwait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        action = new Actions(driver);
        jsExcuter = (JavascriptExecutor) driver;
    }

    @Test
    public void TC_01_FixedPoupInDOM() throws InterruptedException {
        driver.get("https://ngoaingu24h.vn/");

        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Đăng nhập']"));
        loginButton.click();
        Thread.sleep(1000);

        Assert.assertTrue(driver.findElement(By.xpath("//div[@role='dialog']")).isDisplayed());

        WebElement username = driver.findElement(By.xpath("//input[@autocomplete='username']"));
        WebElement password = driver.findElement(By.xpath("//input[@autocomplete='new-password']"));

        username.sendKeys("automationfc");
        password.sendKeys("automationfc");


    }




    @AfterClass
    public void afterClass() {
        driver.quit();
    }


}

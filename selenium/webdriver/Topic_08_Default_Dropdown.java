package webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Topic_08_Default_Dropdown {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    String emailAdress, firstName, lastName, password, companyName, day, month, year;
    Random rand = new Random();
    String randomNum = String.valueOf(rand.nextInt(99999));
    String passportNumber = "E00007730";
    String comment = "this is textarea\ncan enter";

    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver"); //mac
        }

        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        firstName = "Elon";
        lastName = "Musk";
        emailAdress = "elonmusk" + randomNum + "@gmail.com";
        password = "123456789";
        companyName = "SpaceX";
        day = "1";
        month = "May";
        year = "1980";


    }

    @Test
    public void TC_01_register() {
        driver.get("https://demo.nopcommerce.com/register");

        driver.findElement(By.id("FirstName")).sendKeys(firstName);
        driver.findElement(By.id("LastName")).sendKeys(lastName);

        new Select(driver.findElement(By.name("DateOfBirthDay"))).selectByVisibleText(day);
        new Select(driver.findElement(By.name("DateOfBirthMonth"))).selectByVisibleText(month);
        new Select(driver.findElement(By.name("DateOfBirthYear"))).selectByVisibleText(year);

        driver.findElement(By.id("Email")).sendKeys(emailAdress);
        driver.findElement(By.id("Company")).sendKeys(companyName);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.id("ConfirmPassword")).sendKeys(password);
        driver.findElement(By.id("register-button")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("div.result")).getText(), "Your registration completed");
        sleepInSecond(3);
        driver.get("https://demo.nopcommerce.com/login?returnUrl=%2Fcustomer%2Finfo");
        driver.findElement(By.id("Email")).sendKeys(emailAdress);
        driver.findElement(By.id("Password")).sendKeys(password);
        sleepInSecond(1);
        driver.findElement(By.id("Password")).sendKeys(Keys.ENTER);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Đợi trong vòng 10 giây
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            alert.dismiss(); // hoặc alert.accept() tùy thuộc vào hành động bạn muốn thực hiện
        } catch (TimeoutException e) {
            // Nếu không có dialog alert xuất hiện trong thời gian chờ, bỏ qua và tiếp tục
        }
        driver.findElement(By.xpath("//a[text()='My account']")).click();

        Assert.assertEquals(new Select(driver.findElement(By.name("DateOfBirthDay"))).getFirstSelectedOption().getText(),day);
        Assert.assertEquals(new Select(driver.findElement(By.name("DateOfBirthMonth"))).getFirstSelectedOption().getText(),month);
        Assert.assertEquals(new Select(driver.findElement(By.name("DateOfBirthYear"))).getFirstSelectedOption().getText(),year);
    }



    @Test
    public void TC_02() {
        driver.get("https://rode.com/en/support/where-to-buy");

        if (driver.findElement(By.id("RODE-GDPRx")).isDisplayed() == true){
            driver.findElement(By.xpath("//div[@id='RODE-GDPRx']//button[contains(text(),'Allow All')]")).click();
            driver.findElement(By.xpath("//button[text()='OK']")).click();
        }
        Select countryDropdown = new Select(driver.findElement(By.id("country")));
        Assert.assertFalse(countryDropdown.isMultiple());

        sleepInSecond(1);
        countryDropdown.selectByVisibleText("Vietnam");
        Assert.assertEquals(driver.findElement(By.id("country")).getAttribute("value"),"Vietnam");
        driver.findElement(By.xpath("//button[text()='Search']")).click();

        List<WebElement> distributorName = driver.findElements(By.xpath("//div[@class='my-3']//h4[@class='text-left']"));
        for (WebElement element : distributorName){
            System.out.println(element.getText());
        }

    }

    @Test
    public void TC_03() {
        driver.get("https://applitools.com/automating-tests-chrome-devtools-recorder-webinar/");

        if (driver.findElement(By.id("onetrust-pc-btn-handler")).isDisplayed() == true){
            driver.findElement(By.id("onetrust-pc-btn-handler")).click();
            driver.findElement(By.xpath("//button[@class='save-preference-btn-handler onetrust-close-btn-handler']")).click();
        }
        sleepInSecond(1);

        new Select(driver.findElement(By.id("Person_Role__c"))).selectByVisibleText("SDET / Test Automation Engineer");
        sleepInSecond(1);
        new Select(driver.findElement(By.id("Test_Framework__c"))).selectByVisibleText("Selenium");
        sleepInSecond(1);
        new Select(driver.findElement(By.id("Self_Report_Country__c"))).selectByVisibleText("Vietnam");

    }

    public void sleepInSecond(long timeInSecond) {
        try {
            Thread.sleep(1000 * timeInSecond); // Ngủ 1 giây
        } catch (InterruptedException e) {
            // Xử lý hoặc log exception ở đây
            Thread.currentThread().interrupt(); // Bảo vệ lại trạng thái interrupt
        }
    }

    @AfterClass
    public void afterClass() {
//        driver.quit();
    }
}

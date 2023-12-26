package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Topic_07_Textbox_Textarea {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    String emailAdress, firstName, lastName, password, fullName;
    Random rand = new Random();
    String employeeID = String.valueOf(rand.nextInt(99999));
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

        emailAdress = "Automation" + rand.nextInt(9999) + "@gmail.com";
        firstName = "Automation";
        lastName = "FC";
        fullName = firstName + " " + lastName;
        password = "12345678";


    }

    @Test
    public void TC_01_createNewAccount() {
        driver.get("http://live.techpanda.org/");


        driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
        driver.findElement(By.xpath("//span[text()='Create an Account']")).click();

        driver.findElement(By.id("firstname")).sendKeys(firstName);
        driver.findElement(By.id("lastname")).sendKeys(lastName);
        driver.findElement(By.id("email_address")).sendKeys(emailAdress);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("confirmation")).sendKeys(password);
        driver.findElement(By.cssSelector("button[title='Register']")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("li[class='success-msg'] span")).getText(),"Thank you for registering with Main Website Store.");
        String contactInfo = driver.findElement(By.xpath("//a[text()='Change Password']/parent::p/parent::div")).getText();
        Assert.assertTrue(contactInfo.contains(fullName));
        Assert.assertTrue(contactInfo.contains(emailAdress));

        driver.findElement(By.xpath("//div[@class='account-cart-wrapper']//span[text()='Account']")).click();
        driver.findElement(By.xpath("//a[text()='Log Out']")).click();

        Assert.assertTrue(driver.findElement(By.xpath("//img[contains(@src,'logo.gif') and @class='large']")).isDisplayed());
    }

    @Test
    public void TC_02_createEmployee() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        driver.findElement(By.name("username")).sendKeys("Admin");
        driver.findElement(By.name("password")).sendKeys("admin123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.findElement(By.xpath("//span[text()='PIM']")).click();
        sleepInSecond(5);
        driver.findElement(By.xpath("//div[@class='orangehrm-header-container']//button[@type='button']")).click();
        sleepInSecond(5);


        //create employee
        driver.findElement(By.xpath("//label[@class='oxd-label']/parent::div/following-sibling::div//input")).sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);;
        driver.findElement(By.xpath("//label[@class='oxd-label']/parent::div/following-sibling::div//input")).sendKeys(employeeID);
        driver.findElement(By.name("firstName")).sendKeys("Tinh");
        driver.findElement(By.name("middleName")).sendKeys("Thanh");
        driver.findElement(By.name("lastName")).sendKeys("Nguyen");

        String getFirstName = driver.findElement(By.name("firstName")).getAttribute("value");
        String getLastName = driver.findElement(By.name("lastName")).getAttribute("value");
        String getEmployeeID = driver.findElement(By.xpath("//label[@class='oxd-label']/parent::div/following-sibling::div//input")).getAttribute("value");

        driver.findElement(By.xpath("//span[@class='oxd-switch-input oxd-switch-input--active --label-right']")).click();
        driver.findElement(By.xpath("//div[@class='oxd-form-row user-form-header']/following-sibling::div[1]//input[@class='oxd-input oxd-input--active']")).sendKeys("AutomationFC" + employeeID);
        driver.findElement(By.xpath("//div[@class='oxd-grid-item oxd-grid-item--gutters user-password-cell']//input")).sendKeys("Password!23");
        driver.findElement(By.xpath("//div[@class='oxd-grid-item oxd-grid-item--gutters user-password-cell']/following-sibling::div//input")).sendKeys("Password!23");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        //verify info
        Assert.assertEquals(driver.findElement(By.name("firstName")).getAttribute("value"),getFirstName);
        Assert.assertEquals(driver.findElement(By.name("lastName")).getAttribute("value"),getLastName);
        Assert.assertEquals(driver.findElement(By.xpath("//label[@class='oxd-label']/parent::div/following-sibling::div//input")).getAttribute("value"),getEmployeeID);

        driver.findElement(By.xpath("//a[text()='Immigration']")).click();
        driver.findElement(By.xpath("//h6[text()='Assigned Immigration Records']/following-sibling::button")).click();
        driver.findElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div//input")).sendKeys(passportNumber);
        driver.findElement(By.xpath("//label[text()='Comments']/parent::div/following-sibling::div//textarea")).sendKeys(comment);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.findElement(By.xpath("//i[@class='oxd-icon bi-pencil-fill']")).click();

        //verify info
        sleepInSecond(3);
        Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div//input")).getAttribute("value"),passportNumber);
        Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Comments']/parent::div/following-sibling::div//textarea")).getAttribute("value"),comment);

        //logout
        driver.findElement(By.xpath("//span[@class='oxd-userdropdown-tab']")).click();
        driver.findElement(By.xpath("//a[text()='Logout']")).click();

        driver.findElement(By.name("username")).sendKeys("AutomationFC" + employeeID);
        driver.findElement(By.name("password")).sendKeys("Password!23");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.findElement(By.xpath("//span[text()='My Info']")).click();

        sleepInSecond(3);

        Assert.assertEquals(driver.findElement(By.name("firstName")).getAttribute("value"),getFirstName);
        Assert.assertEquals(driver.findElement(By.name("lastName")).getAttribute("value"),getLastName);
        Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div//input")).getAttribute("value"),getEmployeeID);

        driver.findElement(By.xpath("//a[text()='Immigration']")).click();
        driver.findElement(By.xpath("//i[@class='oxd-icon bi-pencil-fill']")).click();

        sleepInSecond(3);

        Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div//input")).getAttribute("value"),passportNumber);
        Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Comments']/parent::div/following-sibling::div//textarea")).getAttribute("value"),comment);

    }



        public void sleepInSecond(long timeInSecond) {
        try {
            Thread.sleep(1000*timeInSecond); // Ngủ 1 giây
        } catch (InterruptedException e) {
            // Xử lý hoặc log exception ở đây
            Thread.currentThread().interrupt(); // Bảo vệ lại trạng thái interrupt
        }
    }



    @AfterClass
    public void afterClass(){
//        driver.quit();
    }
}

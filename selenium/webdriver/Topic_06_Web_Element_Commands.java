package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Topic_06_Web_Element_Commands {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    By emailTextbox = By.id("mail");
    By under18Radio = By.id("under_18");
    By educationTextarea = By.id("edu");
    By nameUser5 = By.xpath("//h5[text()='Name: User5']");
    By jobRole1 = By.id("job1");
    By jobRole2 = By.id("job2");
    By jobRole3 = By.id("job3");
    By developmentCheckbox = By.id("development");
    By disabledCheckbox = By.id("check-disbaled");
    By slider1 = By.id("slider-1");
    By slider2 = By.id("slider-2");
    By password = By.id("disable_password");
    By disableAge = By.id("radio-disabled");
    By biography = By.id("bio");
    By javaCheckbox = By.id("java");


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
    }

    @Test
    public void TC_01_isDisplayed() {
        driver.get("https://automationfc.github.io/basic-form/index.html");

        if (driver.findElement(emailTextbox).isDisplayed()){
            driver.findElement(emailTextbox).sendKeys("Automation Testing");
            System.out.println("Email textbox is displayed");
        } else {
            System.out.println("Email textbox is not displayed");
        }

        if (driver.findElement(under18Radio).isDisplayed()){
            driver.findElement(under18Radio).click();
            System.out.println("Under 18 age is displayed");
        } else {
            System.out.println("Under 18 age is not displayed");
        }

        if (driver.findElement(educationTextarea).isDisplayed()){
            driver.findElement(educationTextarea).sendKeys("Automation Testing");
            System.out.println("Education is displayed");
        } else {
            System.out.println("Education is not displayed");
        }

        if (driver.findElement(nameUser5).isDisplayed()){
            System.out.println("nameUser5 is displayed");
        } else {
            System.out.println("nameUser5 is not displayed");
        }


    }

    @Test
    public void TC_02_isEnabled() {
        driver.get("https://automationfc.github.io/basic-form/index.html");

        if (driver.findElement(emailTextbox).isEnabled()){
            System.out.println("email is enabled");
        } else {
            System.out.println("email is disable");
        }

        if (driver.findElement(under18Radio).isEnabled()){
            System.out.println("age under 18 is enabled");
        } else {
            System.out.println("age under 18 is disable");
        }

        if (driver.findElement(educationTextarea).isEnabled()){
            System.out.println("education is enabled");
        } else {
            System.out.println("education is disable");
        }

        if (driver.findElement(jobRole1).isEnabled() && driver.findElement(jobRole2).isEnabled()){
            System.out.println("job Role1 & Role2 is enabled");
        } else {
            System.out.println("one of job Role1 & Role2 is disable");
        }

        if (driver.findElement(developmentCheckbox).isEnabled()){
            System.out.println("developmentCheckbox is enabled");
        } else {
            System.out.println("developmentCheckbox is disable");
        }

        if (driver.findElement(slider1).isEnabled()){
            System.out.println("slider1 is enabled");
        } else {
            System.out.println("slider1 is disable");
        }

        if (driver.findElement(password).isEnabled()){
            System.out.println("password is enabled");
        } else {
            System.out.println("password is disable");
        }

        if (driver.findElement(disableAge).isEnabled()){
            System.out.println("disableAge is enabled");
        } else {
            System.out.println("disableAge is disable");
        }

        if (driver.findElement(biography).isEnabled()){
            System.out.println("biography is enabled");
        } else {
            System.out.println("biography is disable");
        }

        if (driver.findElement(jobRole3).isEnabled()){
            System.out.println("jobRole3 is enabled");
        } else {
            System.out.println("jobRole3 is disable");
        }

        if (driver.findElement(disabledCheckbox).isEnabled()){
            System.out.println("disabledCheckbox is enabled");
        } else {
            System.out.println("disabledCheckbox is disable");
        }

        if (driver.findElement(slider2).isEnabled()){
            System.out.println("slider2 is enabled");
        } else {
            System.out.println("slider2 is disable");
        }

    }

    @Test
    public void TC_03_isSelected() {
        driver.get("https://automationfc.github.io/basic-form/index.html");

        driver.findElement(under18Radio).click();
        driver.findElement(javaCheckbox).click();
        if (driver.findElement(under18Radio).isSelected()){
            System.out.println("under18Radio is selected");
        } else {
            System.out.println("under18Radio is not selected");
        }
        if (driver.findElement(javaCheckbox).isSelected()){
            System.out.println("javaCheckbox is selected");
        } else {
            System.out.println("javaCheckbox is not selected");
        }

        driver.findElement(javaCheckbox).click();
        if (driver.findElement(javaCheckbox).isSelected()){
            System.out.println("javaCheckbox is selected");
        } else {
            System.out.println("javaCheckbox is not selected");
        }
    }

    @Test
    public void TC_04_MailChimp() {
        driver.get("https://login.mailchimp.com/signup/");

        driver.findElement(By.id("email")).sendKeys("tinh@gmail.com");

        By passwordTexbox = By.id("new_password");
        By signbutton = By.id("create-account-enabled");


        driver.findElement(passwordTexbox).sendKeys("abc");
//        driver.findElement(signbutton).click();
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='lowercase-char completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='uppercase-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='number-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='special-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='username-check completed']")).isDisplayed());

        driver.findElement(passwordTexbox).clear();
        driver.findElement(passwordTexbox).sendKeys("ABC");
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='lowercase-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='uppercase-char completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='number-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='special-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='username-check completed']")).isDisplayed());

        driver.findElement(passwordTexbox).clear();
        driver.findElement(passwordTexbox).sendKeys("1");
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='lowercase-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='uppercase-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='number-char completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='special-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='username-check completed']")).isDisplayed());

        driver.findElement(passwordTexbox).clear();
        driver.findElement(passwordTexbox).sendKeys("@");
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='lowercase-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='uppercase-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='number-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='special-char completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='username-check completed']")).isDisplayed());

        driver.findElement(passwordTexbox).clear();
        driver.findElement(passwordTexbox).sendKeys("@");
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='lowercase-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='uppercase-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='number-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='special-char completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='username-check completed']")).isDisplayed());

        driver.findElement(passwordTexbox).clear();
        driver.findElement(passwordTexbox).sendKeys("12345678");
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='lowercase-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='uppercase-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='number-char completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='special-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='username-check completed']")).isDisplayed());

        driver.findElement(passwordTexbox).clear();
        driver.findElement(passwordTexbox).sendKeys("tinh@gmail.com");
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='lowercase-char completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='uppercase-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='number-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='special-char completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='username-check not-completed']")).isDisplayed());



    }

    @AfterClass
    public void afterClass(){
        driver.quit();
    }
}

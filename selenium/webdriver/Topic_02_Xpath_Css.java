package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_02_Xpath_Css {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
        }

        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
//        driver.get("https://alada.vn/tai-khoan/dang-ky.html");
    }

    @Test
    public void TC_01_Empty_Data() {
        driver.get("https://alada.vn/tai-khoan/dang-ky.html");

        driver.findElement(By.xpath("//div[@class='form frmRegister']//button[text()='ĐĂNG KÝ']")).click();
        Assert.assertEquals(driver.findElement(By.id("txtFirstname-error")).getText(),"Vui lòng nhập họ tên");
        Assert.assertEquals(driver.findElement(By.id("txtEmail-error")).getText(),"Vui lòng nhập email");
        Assert.assertEquals(driver.findElement(By.id("txtCEmail-error")).getText(),"Vui lòng nhập lại địa chỉ email");
        Assert.assertEquals(driver.findElement(By.id("txtPassword-error")).getText(),"Vui lòng nhập mật khẩu");
        Assert.assertEquals(driver.findElement(By.id("txtCPassword-error")).getText(),"Vui lòng nhập lại mật khẩu");
        Assert.assertEquals(driver.findElement(By.id("txtPhone-error")).getText(),"Vui lòng nhập số điện thoại.");
    }

    @Test
    public void TC_02_Invalid_Email() {
        driver.get("https://alada.vn/tai-khoan/dang-ky.html");

        driver.findElement(By.id("txtFirstname")).sendKeys("Tinh Nguyen");
        driver.findElement(By.id("txtEmail")).sendKeys("com");
        driver.findElement(By.id("txtCEmail")).sendKeys("com");
        driver.findElement(By.id("txtPassword")).sendKeys("123");
        driver.findElement(By.id("txtCPassword")).sendKeys("123");
        driver.findElement(By.id("txtPhone")).sendKeys("0948123111");

        driver.findElement(By.xpath("//div[@class='form frmRegister']//button[text()='ĐĂNG KÝ']")).click();
        Assert.assertEquals(driver.findElement(By.id("txtEmail-error")).getText(),"Vui lòng nhập email hợp lệ");
        Assert.assertEquals(driver.findElement(By.id("txtCEmail-error")).getText(),"Vui lòng nhập email hợp lệ");


    }
    @Test
    public void TC_03_Empty_Data() {
        driver.get("https://alada.vn/tai-khoan/dang-ky.html");

        driver.findElement(By.id("txtFirstname")).sendKeys("Tinh Nguyen");
        driver.findElement(By.id("txtEmail")).sendKeys("tinh@gmail.com");
        driver.findElement(By.id("txtCEmail")).sendKeys("tinh@gmail.con");
        driver.findElement(By.id("txtPassword")).sendKeys("123");
        driver.findElement(By.id("txtCPassword")).sendKeys("123");
        driver.findElement(By.id("txtPhone")).sendKeys("0948123111");

        driver.findElement(By.xpath("//div[@class='form frmRegister']//button[text()='ĐĂNG KÝ']")).click();
        Assert.assertEquals(driver.findElement(By.id("txtCEmail-error")).getText(),"Email nhập lại không đúng");

    }
    @Test
    public void TC_04_Empty_Data() {
        driver.get("https://alada.vn/tai-khoan/dang-ky.html");

        driver.findElement(By.id("txtFirstname")).sendKeys("Tinh Nguyen");
        driver.findElement(By.id("txtEmail")).sendKeys("tinh@gmail.com");
        driver.findElement(By.id("txtCEmail")).sendKeys("tinh@gmail.com");
        driver.findElement(By.id("txtPassword")).sendKeys("123");
        driver.findElement(By.id("txtCPassword")).sendKeys("123");
        driver.findElement(By.id("txtPhone")).sendKeys("0948123111");

        driver.findElement(By.xpath("//div[@class='form frmRegister']//button[text()='ĐĂNG KÝ']")).click();
        Assert.assertEquals(driver.findElement(By.id("txtPassword-error")).getText(),"Mật khẩu phải có ít nhất 6 ký tự");
        Assert.assertEquals(driver.findElement(By.id("txtCPassword-error")).getText(),"Mật khẩu phải có ít nhất 6 ký tự");
    }
    @Test
    public void TC_05_Empty_Data() {
        driver.get("https://alada.vn/tai-khoan/dang-ky.html");

        driver.findElement(By.id("txtFirstname")).sendKeys("Tinh Nguyen");
        driver.findElement(By.id("txtEmail")).sendKeys("tinh@gmail.com");
        driver.findElement(By.id("txtCEmail")).sendKeys("tinh@gmail.com");
        driver.findElement(By.id("txtPassword")).sendKeys("123456");
        driver.findElement(By.id("txtCPassword")).sendKeys("123455");
        driver.findElement(By.id("txtPhone")).sendKeys("0948123111");

        driver.findElement(By.xpath("//div[@class='form frmRegister']//button[text()='ĐĂNG KÝ']")).click();
        Assert.assertEquals(driver.findElement(By.id("txtCPassword-error")).getText(),"Mật khẩu bạn nhập không khớp");
    }
    @Test
    public void TC_06_Empty_Data() {
        driver.get("https://alada.vn/tai-khoan/dang-ky.html");

        driver.findElement(By.id("txtFirstname")).sendKeys("Tinh Nguyen");
        driver.findElement(By.id("txtEmail")).sendKeys("tinh@gmail.com");
        driver.findElement(By.id("txtCEmail")).sendKeys("tinh@gmail.com");
        driver.findElement(By.id("txtPassword")).sendKeys("123456");
        driver.findElement(By.id("txtCPassword")).sendKeys("123456");
        driver.findElement(By.id("txtPhone")).sendKeys("09481231");

        driver.findElement(By.xpath("//div[@class='form frmRegister']//button[text()='ĐĂNG KÝ']")).click();
        Assert.assertEquals(driver.findElement(By.id("txtPhone-error")).getText(),"Số điện thoại phải từ 10-11 số.");
    }

    @AfterClass
    public void afterClass(){
        driver.quit();
    }

}
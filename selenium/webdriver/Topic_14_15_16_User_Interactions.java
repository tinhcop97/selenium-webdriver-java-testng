package webdriver;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.Color;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.swing.*;

public class Topic_14_15_16_User_Interactions {
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
    public void TC_01_Hover() {
        driver.get("https://automationfc.github.io/jquery-tooltip/");

        WebElement ageTextbox = driver.findElement(By.cssSelector("input#age"));
        action.moveToElement(ageTextbox).perform();

        Assert.assertTrue(driver.findElement(By.cssSelector("div.ui-tooltip-content")).isDisplayed());

        Assert.assertEquals(driver.findElement(By.cssSelector("div.ui-tooltip-content")).getText(), "We ask for your age only for statistical purposes.");
    }

    @Test
    public void TC_02_Hover() throws InterruptedException {
        driver.get("https://www.myntra.com/");

        WebElement kids = driver.findElement(By.xpath("//a[@data-group='kids']"));
        action.moveToElement(kids).perform();
        Thread.sleep(1000);

        action.click(driver.findElement(By.xpath("//a[@href='/kids-home-bath']"))).perform();
        Assert.assertEquals(driver.findElement(By.cssSelector("span.breadcrumbs-crumb")).getText(), "Kids Home Bath");
    }

    @Test
    public void TC_03_Hover() throws InterruptedException {
        driver.get("https://www.fahasa.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        try {
            // Đợi đến khi phần tử có thể click được
            WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.brz-popup2__close")));

            // Click vào phần tử khi nó có thể thao tác
            closeButton.click();
        } catch (TimeoutException e) {
            // Xử lý trường hợp không tìm thấy phần tử trong thời gian chờ
            System.out.println("Phần tử không xuất hiện hoặc không thể click.");
        }

        WebElement iconMenu = driver.findElement(By.cssSelector("span.icon_menu"));
        action.moveToElement(iconMenu).perform();
        Thread.sleep(1000);

        action.moveToElement(driver.findElement(By.xpath("//ul[@class='nav navbar-nav verticalmenu']//span[@class='menu-title' and text()='Sách Trong Nước']"))).perform();
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='fhs_column_stretch']//span[@class='menu-title' and text()='Sách Trong Nước']")).getText(), "Sách Trong Nước");
    }

    @Test
    public void TC_04_ClickAndHold() throws InterruptedException {
        driver.get("https://automationfc.github.io/jquery-selectable/");

        List<WebElement> allNumbers = driver.findElements(By.cssSelector("ol.ui-selectable>li"));

        Assert.assertEquals(allNumbers.size(), 20);
        action.clickAndHold(allNumbers.get(0))
                .moveToElement(allNumbers.get(3))
                .release()
                .perform();

        List<WebElement> allNumbersSelected = driver.findElements(By.cssSelector("ol.ui-selectable>li.ui-selected"));
        Assert.assertEquals(allNumbersSelected.size(), 4);

    }

    @Test
    public void TC_05_ClickAndHoldRandom() throws InterruptedException {
        driver.get("https://automationfc.github.io/jquery-selectable/");

        List<WebElement> allNumbers = driver.findElements(By.cssSelector("ol.ui-selectable>li"));

        Assert.assertEquals(allNumbers.size(), 20);

        //Nhấn Ctrl chưa nhả ra
        action.keyDown(Keys.CONTROL).perform();
        action.click(allNumbers.get(0))
                .click(allNumbers.get(2))
                .click(allNumbers.get(5))
                .click(allNumbers.get(10))
                .perform();
        //Nhả Ctrl
        action.keyDown(Keys.CONTROL).perform();
        List<WebElement> allNumbersSelected = driver.findElements(By.cssSelector("ol.ui-selectable>li.ui-selected"));
        Assert.assertEquals(allNumbersSelected.size(), 4);

    }

    @Test
    public void TC_06_DoubleClick() throws InterruptedException {
        driver.get("https://swisnl.github.io/jQuery-contextMenu/demo.html");

        action.doubleClick(driver.findElement(By.xpath("//button[@ondblclick='doubleClickMe()']"))).perform();

        Assert.assertEquals(driver.findElement(By.xpath("//p[@id='demo']")).getText(), "Hello Automation Guys!");

    }

    @Test
    public void TC_07_RightClick() throws InterruptedException {
        driver.get("https://swisnl.github.io/jQuery-contextMenu/demo.html");

        action.contextClick(driver.findElement(By.xpath("//span[@class='context-menu-one btn btn-neutral']"))).perform();
        Thread.sleep(2000);
        Assert.assertTrue(driver.findElement(By.cssSelector("li.context-menu-icon-quit")).isDisplayed());

        action.moveToElement(driver.findElement(By.cssSelector("li.context-menu-icon-quit"))).perform();
        Assert.assertTrue(driver.findElement(By.cssSelector("li.context-menu-icon-quit.context-menu-visible.context-menu-hover")).isDisplayed());

        action.click(driver.findElement(By.cssSelector("li.context-menu-icon-quit.context-menu-visible.context-menu-hover"))).perform();
        Thread.sleep(2000);

        Alert alert = driver.switchTo().alert();
        alert.accept();
        Assert.assertFalse(driver.findElement(By.cssSelector("li.context-menu-icon-quit")).isDisplayed());
    }

    @Test
    public void TC_08_DragAndDropHTML4() throws InterruptedException {
        driver.get("https://automationfc.github.io/kendo-drag-drop/");

        WebElement smallCircle = driver.findElement(By.cssSelector("div#draggable"));
        WebElement bigCircle = driver.findElement(By.cssSelector("div#droptarget"));

        action.dragAndDrop(smallCircle, bigCircle).perform();
        Assert.assertEquals(bigCircle.getText(),"You did great!");
        Assert.assertEquals(Color.fromString(bigCircle.getCssValue("background-color")).asHex().toString(),"#03a9f4");
    }

    @Test
    public void TC_09_DragAndDropHTML5() throws InterruptedException,IOException {
        driver.get("https://automationfc.github.io/drag-drop-html5/");

        String jqueryDragAndDrop = getContentFile(projectPath + "\\dragAndDrop\\dragAndDrop.js");

        //Drag A to B
        jsExcuter.executeScript(jqueryDragAndDrop);
        Thread.sleep(2000);

        Assert.assertEquals(driver.findElement(By.cssSelector("div#column-a")).getText(),"B");
        Assert.assertEquals(driver.findElement(By.cssSelector("div#column-b")).getText(),"A");
    }

    public String getContentFile(String filePath) throws IOException {
        Charset cs = Charset.forName("UTF-8");
        try (FileInputStream stream = new FileInputStream(filePath);
             Reader reader = new BufferedReader(new InputStreamReader(stream, cs))) {

            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[8192];
            int read;
            while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
                builder.append(buffer, 0, read);
            }
            return builder.toString();
        }
    }



    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
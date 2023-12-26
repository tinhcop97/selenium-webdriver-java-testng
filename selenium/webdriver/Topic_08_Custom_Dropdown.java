package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class Topic_08_Custom_Dropdown {
    WebDriver driver;
    WebDriverWait explicitwait;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");


    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver"); //mac
        }

        driver = new FirefoxDriver();
        explicitwait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();


    }

    @Test
    public void TC_01_jquery() {
        driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");

        driver.findElement(By.id("speed-button")).click();

        explicitwait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//li[@class='ui-menu-item']//div")));

        List<WebElement> speedDropdownItems = driver.findElements(By.xpath("//li[@class='ui-menu-item']//div"));
        for (WebElement item : speedDropdownItems) {
            String itemText = item.getText();
            System.out.println(itemText);

            if (itemText.equals("Faster")) {
                item.click();
                Assert.assertEquals(driver.findElement(By.xpath("//span[@id='speed-button']//span[@class='ui-selectmenu-text']")).getText(), itemText);
                break;
            } else {
                System.out.println("chưa tìm thấy Faster");
            }

        }


    }


    @Test
    public void TC_02_reactJS() {
        driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");

        driver.findElement(By.cssSelector("div#root>div")).click();

        explicitwait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//span[@class='text']")));

        List<WebElement> speedDropdownItems = driver.findElements(By.xpath("//span[@class='text']"));
        for (WebElement item : speedDropdownItems) {
            String itemText = item.getText();
            System.out.println(itemText);

            if (itemText.equals("Matt")) {
                item.click();
                Assert.assertEquals(driver.findElement(By.cssSelector("div[role='alert']")).getText(), itemText);
                break;
            } else {
                System.out.println("chưa tìm thấy Matt");
            }

        }


    }

    @Test
    public void TC_03_vueJS() {
        driver.get("https://mikerodham.github.io/vue-dropdowns/");

        driver.findElement(By.cssSelector("li.dropdown-toggle")).click();

        explicitwait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("ul.dropdown-menu>li")));

        List<WebElement> speedDropdownItems = driver.findElements(By.cssSelector("ul.dropdown-menu>li"));
        for (WebElement item : speedDropdownItems) {
            String itemText = item.getText();
            System.out.println(itemText);

            if (itemText.equals("Third Option")) {
                item.click();
                Assert.assertEquals(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText(), itemText);
                break;
            } else {
                System.out.println("chưa tìm thấy Third Option");
            }

        }

    }

    @Test
    public void TC_04_Editable() {
        driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");

        enterAndSelectItemInCombo("input.search","div[role='listbox']","Bangladesh");

    }

    public void sleepInSecond(long timeInSecond) {
        try {
            Thread.sleep(1000 * timeInSecond); // Ngủ 1 giây
        } catch (InterruptedException e) {
            // Xử lý hoặc log exception ở đây
            Thread.currentThread().interrupt(); // Bảo vệ lại trạng thái interrupt
        }
    }

    public void selectItemInDropdown(String parentCss, String allItemsCss, String expectItemValue){
        driver.findElement(By.cssSelector(parentCss)).click();

        explicitwait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(allItemsCss)));

        List<WebElement> speedDropdownItems = driver.findElements(By.cssSelector(allItemsCss));
        for (WebElement item : speedDropdownItems) {
            String itemText = item.getText();
            System.out.println(itemText);

            if (itemText.equals(expectItemValue)) {
                item.click();
                break;
            } else {
                System.out.println("chưa tìm thấy" + " " + expectItemValue);
            }

        }
    }

    public void enterAndSelectItemInCombo(String comboCss, String allItemsCss, String expectItemValue){
        driver.findElement(By.cssSelector(comboCss)).sendKeys(expectItemValue);

        explicitwait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(allItemsCss)));

        List<WebElement> speedDropdownItems = driver.findElements(By.cssSelector(allItemsCss));
        for (WebElement item : speedDropdownItems) {
            String itemText = item.getText();
            System.out.println(itemText);

            if (itemText.equals(expectItemValue)) {
                item.click();
                break;
            } else {
                System.out.println("chưa tìm thấy" + " " + expectItemValue);
            }

        }
    }

    @AfterClass
    public void afterClass() {
//        driver.quit();
    }
}

package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;

public class Topic_10_Button_Radio_Checkbox {
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
    public void TC_01_Button() {
        driver.get("https://www.fahasa.com/customer/account/create");

        driver.findElement(By.id("popup-login-tab_list")).click();

        By loginButton = By.cssSelector("fhs-btn-login");

        //verify login button disable
        Assert.assertFalse(driver.findElement(loginButton).isEnabled());

        String loginButtonBackgroundColor = driver.findElement(loginButton).getCssValue("background-color");


    }


    @Test
    public void TC_getByDate() {
        driver.get("https://rongbachkim.com/soicau.html?ngay=2024-01-03&limit=1&exactlimit=0&lon=0&nhay=1&db=1");

        List<WebElement> items = driver.findElements(By.xpath("//div[contains(text(),'Kết quả')]/following-sibling::a"));
        Set<String> uniqueItems = new HashSet<>();
        StringBuilder output = new StringBuilder();

        for (WebElement item : items) {
            String itemText = item.getText();
            if (!uniqueItems.contains(itemText)) {
                uniqueItems.add(itemText);
                output.append(itemText).append(" ");
            }
        }
        System.out.println(output.toString().trim());

    }

    @Test
    public void TC_getByDateToDate() {
        String fromDate = "2023-12-27";
        String toDate = "2024-01-02";

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Row headerRow = sheet.createRow(0);
        Cell headerCellDate = headerRow.createCell(0);
        Cell headerCellOutput = headerRow.createCell(1);

        headerCellDate.setCellValue("Ngày");
        headerCellOutput.setCellValue("Kết quả");

        int rowNum = 1;

        LocalDate start = LocalDate.parse(fromDate, formatter);
        LocalDate end = LocalDate.parse(toDate, formatter);

        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            String formattedDate = date.format(formatter);

            Row row = sheet.createRow(rowNum++);

            Cell dateCell = row.createCell(0);
            dateCell.setCellValue(formattedDate);

            String url = "https://rongbachkim.com/soicau.html?ngay=" + formattedDate + "&limit=1&exactlimit=0&lon=0&nhay=1&db=1";
            driver.get(url);

            List<WebElement> items = driver.findElements(By.xpath("//div[contains(text(),'Kết quả')]/following-sibling::a"));
            Set<String> uniqueItems = new HashSet<>();
            StringBuilder output = new StringBuilder();

            for (WebElement item : items) {
                String itemText = item.getText();
                if (!uniqueItems.contains(itemText)) {
                    uniqueItems.add(itemText);
                    output.append(itemText).append(" ");
                }
            }

            Cell outputCell = row.createCell(1);
            outputCell.setCellValue(output.toString().trim());
        }

        try (FileOutputStream outputStream = new FileOutputStream("data.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver.quit();

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

package webdriver;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.Color;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class Topic_10_Button_Radio_Checkbox {
    WebDriver driver;
    JavascriptExecutor js;
    WebDriverWait explicitwait;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");


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


    }

    @Test
    public void TC_01_Button() {
        driver.get("https://www.fahasa.com/customer/account/create");

        driver.findElement(By.xpath("//a[text()='Đăng nhập']/parent::li")).click();

        By loginButton = By.cssSelector("button.fhs-btn-login");

        //verify login button disable
        Assert.assertFalse(driver.findElement(loginButton).isEnabled());

        //verify nút đăng nhập lúc disable có màu xám
        String loginDisableBackgroundHexa = driver.findElement(loginButton).getCssValue("background-image");
        System.out.println(loginDisableBackgroundHexa);
        Assert.assertTrue(loginDisableBackgroundHexa.contains("rgb(224, 224, 224)"));

        //nhập email pass
        driver.findElement(By.cssSelector("input#login_username")).sendKeys("a@gmail.com");
        driver.findElement(By.cssSelector("input#login_password")).sendKeys("123456");


        //verify login button enable
        Assert.assertTrue(driver.findElement(loginButton).isEnabled());
        String loginButtonEnableBackgroundHexa = driver.findElement(loginButton).getCssValue("background-color");
        Color loginBackgroundColor = Color.fromString(loginButtonEnableBackgroundHexa);
        System.out.println(loginBackgroundColor.asHex());
        Assert.assertEquals(loginBackgroundColor.asHex().toUpperCase(), "#C92127");


    }

    @Test
    public void TC_02_Button() {
        driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");

        driver.findElement(By.xpath("//input[@id='eq5']")).click();

        //verify checkbox is selected
        Assert.assertTrue(driver.findElement(By.xpath("//input[@id='eq5']")).isSelected());

        driver.findElement(By.xpath("//input[@id='eq5']")).click();

        //verify checkbox is selected
        Assert.assertFalse(driver.findElement(By.xpath("//input[@id='eq5']")).isSelected());

        driver.get("https://demos.telerik.com/kendo-ui/radiobutton/index");

        driver.findElement(By.id("engine3")).click();

        if (!driver.findElement(By.id("engine3")).isSelected()) {
            driver.findElement(By.id("engine3")).click();
            System.out.println("có phải chọn lại");
        }


    }

    @Test
    public void TC_03_Button() {
        driver.get("https://material.angular.io/components/radio/examples");

        driver.findElement(By.xpath("//input[@id='mat-radio-4-input']")).click();

        if (!driver.findElement(By.xpath("//input[@id='mat-radio-4-input']")).isSelected()) {
            driver.findElement(By.xpath("//input[@id='mat-radio-4-input']")).click();
            System.out.println("có phải chọn lại");
        }

        driver.get("https://material.angular.io/components/checkbox/examples");

        driver.findElement(By.xpath("//input[@id='mat-mdc-checkbox-1-input']")).click();
        driver.findElement(By.xpath("//input[@id='mat-mdc-checkbox-2-input']")).click();

        if (driver.findElement(By.xpath("//input[@id='mat-mdc-checkbox-1-input']")).isSelected() && driver.findElement(By.xpath("//input[@id='mat-mdc-checkbox-2-input']")).isSelected()) {
            driver.findElement(By.xpath("//input[@id='mat-mdc-checkbox-1-input']")).click();
            driver.findElement(By.xpath("//input[@id='mat-mdc-checkbox-2-input']")).click();
            System.out.println(driver.findElement(By.xpath("//input[@id='mat-mdc-checkbox-1-input']")).isSelected());
            System.out.println(driver.findElement(By.xpath("//input[@id='mat-mdc-checkbox-2-input']")).isSelected());
        }

    }


    @Test
    public void TC_04_Checkbox() {
        driver.get("https://automationfc.github.io/multiple-fields/");

        List<WebElement> allCheckboxes = driver.findElements(By.cssSelector("div.form-single-column input[type='checkbox']"));

        for (WebElement checkbox : allCheckboxes) {
            if (!checkbox.isSelected()) checkbox.click();
        }

        //verify tất cả đc chọn

        for (WebElement checkbox : allCheckboxes) {
            Assert.assertTrue(checkbox.isSelected());
        }

        driver.manage().deleteAllCookies();
        driver.navigate().refresh();

        // chọn checkbox có text là Heart Attack

        allCheckboxes = driver.findElements(By.cssSelector("div.form-single-column input[type='checkbox']"));

        for (WebElement checkbox : allCheckboxes) {
            if (checkbox.getAttribute("value").equals("Heart Attack") && !checkbox.isSelected()) {
                checkbox.click();
            }
        }

        for (WebElement checkbox : allCheckboxes) {
            if (checkbox.getAttribute("value").equals("Heart Attack")) {
                Assert.assertTrue(checkbox.isSelected());
            } else {
                Assert.assertFalse(checkbox.isSelected());
            }
        }


    }

    @Test
    public void TC_05_RadioButton() {
        driver.get("https://login.ubuntu.com/");
        //var newUserRadio = $$("input#id_new_user)[0];
        //newUserRadio.click();
        By newUserRadioInput = By.cssSelector("input#id_new_user");

        js.executeScript("arguments[0].click();",driver.findElement(newUserRadioInput));
        Assert.assertTrue(driver.findElement(newUserRadioInput).isSelected());

        By tempCheckbox = By.cssSelector("input#id_accept_tos");
        js.executeScript("arguments[0].click();",driver.findElement(tempCheckbox));
        Assert.assertTrue(driver.findElement(newUserRadioInput).isSelected());

    }

    @Test
    public void TC_06_RadioButton2() {
        driver.get("https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");

        By canThoRadio = By.xpath("//div[@data-value='Cần Thơ']");

        Assert.assertEquals(driver.findElement(canThoRadio).getAttribute("aria-checked"),"false");

        driver.findElement(canThoRadio).click();

        Assert.assertEquals(driver.findElement(canThoRadio).getAttribute("aria-checked"),"true");
    }

    @Test
    public void TC_07_AcceptAlert() {
        driver.get("https://automationfc.github.io/basic-form/index.html");

        By alertButton = By.cssSelector("button[onclick='jsAlert()']");

        driver.findElement(alertButton).click();

        Alert alert = driver.switchTo().alert();

        Assert.assertEquals(alert.getText(),"I am a JS Alert");

        alert.accept();
    }

    @Test
    public void TC_08_ConfirmAlert() {
        driver.get("https://automationfc.github.io/basic-form/index.html");

        By alertButton = By.cssSelector("button[onclick='jsConfirm()']");

        driver.findElement(alertButton).click();

        Alert alert = driver.switchTo().alert();

        Assert.assertEquals(alert.getText(),"I am a JS Confirm");

        alert.dismiss();

        WebElement result = driver.findElement(By.xpath("//div[@class='example']//p[@id='result']\n"));
        System.out.println(result.getText());
        Assert.assertEquals(result.getText(),"You clicked: Cancel");
    }

    @Test
    public void TC_09_PromptAlert() {
        driver.get("https://automationfc.github.io/basic-form/index.html");

        By alertButton = By.cssSelector("button[onclick='jsPrompt()']");

        driver.findElement(alertButton).click();

        Alert alert = driver.switchTo().alert();

        Assert.assertEquals(alert.getText(),"I am a JS prompt");

        String inputPromptAlert = "automationfc";

        alert.sendKeys(inputPromptAlert);
        alert.accept();

        WebElement result = driver.findElement(By.xpath("//div[@class='example']//p[@id='result']\n"));
        System.out.println(result.getText());
        Assert.assertEquals(result.getText(),"You entered: " + inputPromptAlert);
    }

    @Test
    public void TC_11_AuthenticationAlert() {
        String username = "admin";
        String password = "admin";
        driver.get("https://"+ username + ":" + password + "@" + "the-internet.herokuapp.com/basic_auth");

        Assert.assertEquals(driver.findElement(By.cssSelector("div.example>p")).getText(),
                "Congratulations! You must have the proper credentials.");

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
        String fromDate = "2023-01-01";
        String toDate = "2023-12-31";

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Row headerRow = sheet.createRow(0);
        Cell headerCellDate = headerRow.createCell(0);
        Cell headerCellOutput = headerRow.createCell(1);
        Cell headerCellResult = headerRow.createCell(2);
        Cell headerCellx = headerRow.createCell(3);
        Cell headerCellQuantity = headerRow.createCell(4);

        headerCellDate.setCellValue("Ngày");
        headerCellOutput.setCellValue("Dàn");
        headerCellResult.setCellValue("Kết quả");
        headerCellx.setCellValue("");
        headerCellQuantity.setCellValue("Số lượng");

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
        // check Kết quả
        try (FileInputStream inputStream = new FileInputStream("data.xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
            sheet = workbook.getSheet("Data");
            if (sheet == null) {
                sheet = workbook.createSheet("Data");
            }
        } catch (IOException e) {
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("Data");
        }

        int newRowNum = 1;
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            String formattedDate = date.format(formatter);

            Row row = sheet.getRow(newRowNum);
            if (row == null) {
                row = sheet.createRow(newRowNum);
            }

            Cell dateCell = row.createCell(0);
            dateCell.setCellValue(formattedDate);

            String url = "https://rongbachkim.com/ketqua.html#" + formattedDate + ",0";
            driver.get(url);
            driver.navigate().refresh();

            WebElement daysInput = driver.findElement(By.xpath("//input[@id='days']"));
            daysInput.clear();
            daysInput.sendKeys("1");

            driver.findElement(By.xpath("//input[@name='daudit']")).click();
            driver.findElement(By.xpath("//input[@name='showdbonly']")).click();
            driver.findElement(By.xpath("//input[@value=' Xem kết quả ']")).click();

            WebElement dbResult = driver.findElement(By.xpath("//td[text()='ĐB']/following-sibling::td"));
            String resultText = dbResult.getText().substring(dbResult.getText().length() - 2);

            Cell resultCell = row.createCell(2);
            resultCell.setCellValue(resultText);

            newRowNum++;
        }

        try (FileOutputStream outputStream = new FileOutputStream("data.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int lastRowNum = sheet.getLastRowNum() + 1;

        for (int i = 1; i < lastRowNum; i++) {
            Row row = sheet.getRow(i);
            Cell cellColumn2 = row.getCell(1);
            Cell cellColumn3 = row.getCell(2);
            Cell cellColumn4 = row.createCell(3);

            if (cellColumn2 != null && cellColumn3 != null) {
                String valueColumn2 = cellColumn2.getStringCellValue();
                String valueColumn3 = cellColumn3.getStringCellValue();

                // Split valueColumn2 into an array of strings
                String[] column2Values = valueColumn2.split(" ");

                boolean found = false;
                for (String item : column2Values) {
                    if (item.equals(valueColumn3)) {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    cellColumn4.setCellValue("x");
                }
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream("data.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 1; i < lastRowNum; i++) {
            Row row = sheet.getRow(i);
            Cell cellColumn2 = row.getCell(1);
            Cell cellColumn5 = row.createCell(4); // Cột 5 là cột E

            if (cellColumn2 != null) {
                String valueColumn2 = cellColumn2.getStringCellValue();

                // Split valueColumn2 into an array of strings
                String[] column2Values = valueColumn2.split(" ");

                // Ghi số lượng phần tử vào cột 5
                cellColumn5.setCellValue(column2Values.length);
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream("data.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        driver.quit();

    }

    @Test
    public void TC_getResult35Days() {
        Workbook workbook = new XSSFWorkbook();
        boolean isFirstDai = true;
        String username = "";
        String password = "";
        driver.get("https://vuabet88.info/home");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2)); // Chờ tối đa 2 giây
        WebElement banner = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='toto-modal__close-button'] | //button[@class='toto-modal__header-close-button']")));

        // Nếu phần tử banner xuất hiện, thực hiện hành động của bạn
        if (banner != null) {
            // Thực hiện hành động khi banner xuất hiện
            banner.click(); // Ví dụ: click vào phần tử banner
        }

        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.xpath("//div[@class='checkbox']//span/label")).click();
        driver.findElement(By.xpath("//button[text()='Đăng nhập']")).click();

        // Mảng chứa các đài và tên sheet tương ứng
        String[][] daiVaSheet = {{"VIET_NAM_22H_00", "22H_00"}, {"VIET_NAM_24H_00", "24H_00"}, {"VIET_NAM_01H30", "01H30"}, {"VIET_NAM_3H_00", "3H_00"}, {"VIET_NAM_04H30", "04H30"}, {"VIET_NAM_06H00", "06H00"}, {"VIET_NAM_7H_30", "7H_30"}, {"VIET_NAM_9H_00", "9H_00"}, {"VIET_NAM_10H50", "10H50"}, {"VIET_NAM_12H_00", "12H_00"}, {"VIET_NAM_14H_00", "14H_00"}, {"VIET_NAM_16H_30", "16H_30"}, {"VIET_NAM_17H_30", "17H_30"}, {"VIET_NAM_19H_30", "19H_30"}, {"THANG_LONG_VIP_19H15", "TLVIP"}, {"HA_NOI_20H_15", "HN"}, {"MIEN_BAC", "MB"}
//                {"VIET_NAM_247", "VN247"}
                // Thêm các đài và tên sheet tương ứng khác vào đây nếu cần
        };

        // Lặp qua mảng để lấy dữ liệu từ mỗi đài và lưu vào sheet tương ứng
        for (String[] dai : daiVaSheet) {
            String daiName = dai[0];
            String sheetName = dai[1];
            Sheet sheet = workbook.createSheet(sheetName);
            // Tạo hàng và ô cho tiêu đề cột
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Ngày");
            headerRow.createCell(1).setCellValue("Giải ĐB");
            headerRow.createCell(2).setCellValue("3D");
            headerRow.createCell(3).setCellValue("4D");
            // Đặt độ rộng cột tự động dựa trên nội dung của cột
            sheet.autoSizeColumn(0);

            String url = "https://vuabet88.info/lottery/betting?schedulerId=" + daiName;
            if (isFirstDai) {
                driver.get(url);
//            driver.navigate().refresh();
//                driver.findElement(By.id("username")).sendKeys(username);
//                driver.findElement(By.id("password")).sendKeys(password);
//                driver.findElement(By.xpath("//input[@name='rememberLogin']//following-sibling::label")).click();
//                driver.findElement(By.xpath("//button[text()='Đăng nhập']")).click();
                sleepInSecond(3);
                isFirstDai = false; // Đánh dấu đã xử lý xong đài đầu tiên
            }
            driver.get(url);
            explicitwait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//h5[text()='Kết quả xổ số']")));

            // Xác định dropdown element
            WebElement dropdown = driver.findElement(By.xpath("//select"));

            // Khởi tạo Select object
            Select select = new Select(dropdown);

            // Lấy danh sách tất cả các option
            List<WebElement> options = select.getOptions();

            // Lặp qua danh sách các option và lấy text của từng option
            for (int i = 0; i < options.size(); i++) {
                WebElement option = options.get(i);
                String optionText = option.getText();
//                System.out.println("Text của option " + (i + 1) + " là: " + optionText);
                String giaiDB = driver.findElement(By.xpath("//div[@class='toto-lottery-betting__sidebar-widget']//tbody/tr[1]//div[@class='toto-lottery-result-board__result-list']")).getText();
                String giai3D = giaiDB.substring(2, 4);
                String giai4D = giaiDB.substring(1, 3);
                giaiDB = giaiDB.substring(giaiDB.length() - 2);
//                System.out.println(giaiDB);

                // Ghi dữ liệu vào hàng mới trong tệp Excel
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(optionText);
                row.createCell(1).setCellValue(giaiDB);
                row.createCell(2).setCellValue(giai3D);
                row.createCell(3).setCellValue(giai4D);

                if (i < options.size() - 1) {
                    select.selectByIndex(i + 1); // Chọn option theo index
                    // Hoặc select.selectByVisibleText(options.get(i + 1).getText()); // Chọn option theo text
                }
            }
        }

        // Lưu workbook vào tệp Excel
        try (FileOutputStream outputStream = new FileOutputStream("Data30day.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TC_checkTanSuat4D() {
        try {
            FileInputStream file = new FileInputStream("data.xlsx");
            Workbook workbook = new XSSFWorkbook(file);

            // Bước 1: Truy cập vào sheet MB
            Sheet mbSheet = workbook.getSheet("MB");
            Sheet s96Sheet = workbook.getSheet("96s");

            // Lấy ngày cần check và ngày trước đó 29 ngày
            String targetDateStr = "04-05-2024"; // Ngày cần check
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate targetDate = LocalDate.parse(targetDateStr, formatter);
            LocalDate previousDate = targetDate.minusDays(1); // Lùi lại 1 ngày

            // Chuyển định dạng ngày về chuỗi để so sánh
            String previousDateStr = previousDate.format(formatter);

            // Tìm index của hàng có ngày là previousDateStr
            int rowIndex = -1; // Khởi tạo biến rowIndex
            for (Row row : mbSheet) {
                Cell dateCell = row.getCell(0); // Ô chứa ngày
                if (dateCell != null && dateCell.getCellType() == CellType.STRING) {
                    String currentDateStr = dateCell.getStringCellValue();
                    if (currentDateStr.equals(previousDateStr)) {
                        // Tìm thấy ngày trước đó 29 ngày, lấy index của hàng
                        rowIndex = row.getRowNum();
                        break; // Dừng việc duyệt khi đã tìm thấy ngày trước đó 29 ngày
                    }
                }
            }

            // Kiểm tra nếu không tìm thấy ngày trước đó 29 ngày
            if (rowIndex != -1) {
                // Lặp qua 30 hàng từ rowIndex và lấy dữ liệu từ cột B để lưu vào sheet 96s
                for (int i = 0; i < 30; i++) {
                    Row mbRow = mbSheet.getRow(rowIndex + i); // Lấy hàng thứ i
                    Row s96Row = s96Sheet.getRow(2); // Lấy hàng thứ 3
                    if (mbRow != null && s96Row != null) {
                        Cell dataCell = mbRow.getCell(1); // Ô chứa dữ liệu cột B
                        if (dataCell != null && dataCell.getCellType() == CellType.STRING) {
                            String dataValue = dataCell.getStringCellValue();

                            // Lưu dữ liệu vào sheet 96s
                            Cell targetCell = s96Row.createCell(2 + i); // Bắt đầu từ cột C
                            targetCell.setCellValue(dataValue); // Chỉ lưu giá trị, không sao chép công thức

                            int startRow = 4; // Ví dụ: hàng bắt đầu
                            int endRow = 5003; // Ví dụ: hàng kết thúc
                            int startColumn = 2; // Ví dụ: cột bắt đầu (C là cột thứ 3)
                            int endColumn = 31; // Ví dụ: cột kết thúc (AF là cột thứ 32)

                            // Lặp qua từng ô trong phạm vi và đặt công thức
                            for (int rowIndexRange = startRow; rowIndexRange <= endRow; rowIndexRange++) {
                                Row currentRow = s96Sheet.getRow(rowIndexRange);
                                if (currentRow == null) {
                                    currentRow = s96Sheet.createRow(rowIndexRange);
                                }
                                for (int columnIndexRange = startColumn; columnIndexRange <= endColumn; columnIndexRange++) {
                                    Cell currentCell = currentRow.getCell(columnIndexRange);
                                    if (currentCell == null) {
                                        currentCell = currentRow.createCell(columnIndexRange);
                                    }
                                    String formula = currentCell.getCellFormula(); // Lấy công thức hiện tại
                                    currentCell.setCellFormula(formula); // Đặt lại công thức
                                }
                            }


                        }
                    }
                }
            } else {
                System.out.println("Không tìm thấy ngày trước đó 1 ngày trong sheet MB.");
            }

            int filterColumnIndex = 39; // Chỉ mục của cột AN (0-based index)
            String filterValue = "29"; // Giá trị cần filter
            String extractedData = "";

            // Duyệt qua từng hàng để thực hiện filter
            for (Row row : s96Sheet) {
                Cell filterCell = row.getCell(filterColumnIndex); // Ô chứa giá trị cần filter
                if (filterCell != null && filterCell.getCellType() == CellType.STRING) {
                    String cellValue = filterCell.getStringCellValue();
                    if (cellValue.equals(filterValue)) {
                        // Là dòng cần lấy, lấy dữ liệu từ cột B và lưu vào biến extractedData
                        Cell dataCell = row.getCell(1); // Cột B
                        if (dataCell != null && dataCell.getCellType() == CellType.STRING) {
                            extractedData += dataCell.getStringCellValue(); // Thêm giá trị vào biến
                        }
                    }
                }
            }
            System.out.println(extractedData);

            // Lưu các thay đổi vào file Excel
            FileOutputStream outFile = new FileOutputStream("data.xlsx");
            workbook.write(outFile);
            outFile.close();

            // Đóng file
            workbook.close();
            file.close();
            driver.quit();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testDataProcessor() {
        // Dữ liệu đầu vào
        String[] row1 = {"00", "05", "11", "18", "24", "27", "30", "32", "36", "38", "44", "46", "47", "56", "58", "62", "67", "69", "70", "71", "72", "73", "76", "85", "86", "89", "91", "93", "94", "96"};
        String[] row2 = {"00", "02", "15", "18", "21", "22", "25", "28", "30", "36", "37", "39", "43", "45", "47", "48", "49", "52", "57", "58", "63", "65", "66", "67", "71", "82", "84", "85", "89", "92"};
        int[] kq = {};

        // Kết quả mong đợi
        List<Integer> expectedDistancesRow1 = List.of(6, 7);
        List<Integer> expectedDistancesRow2 = List.of(7, 7);

        // Thực hiện kiểm tra
        List<Integer> maxDistancesRow1 = getMaxDistances(row1, kq);
        List<Integer> maxDistancesRow2 = getMaxDistances(row2, kq);

        Assert.assertEquals(expectedDistancesRow1, maxDistancesRow1);
        Assert.assertEquals(expectedDistancesRow2, maxDistancesRow2);
    }

    // Phương thức tính toán khoảng cách lớn nhất cho một hàng dữ liệu
    private List<Integer> getMaxDistances(String[] row, int[] kq) {
        List<Integer> maxDistances = new ArrayList<>();
        for (int i = 0; i < kq.length; i++) {
            char[] rowArray = row[i].toCharArray();
            int maxDistance = 0;
            int lastXIndex = -1;
            int firstXIndex = -1;

            // Tìm vị trí của các ký tự 'x'
            for (int j = 0; j < rowArray.length; j++) {
                if (rowArray[j] == 'x') {
                    if (firstXIndex == -1) {
                        firstXIndex = j;
                    }
                    lastXIndex = j;
                }
            }

            // Tính khoảng cách từ đầu và cuối của cột
            if (firstXIndex != -1) {
                maxDistance = Math.max(maxDistance, firstXIndex);
                maxDistance = Math.max(maxDistance, rowArray.length - 1 - lastXIndex);
            }

            maxDistances.add(maxDistance);
        }
        return maxDistances;
    }

    @Test
    public void TC_ccxs() throws IOException {
        // Khởi tạo Workbook và Sheet cho Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("MB");

        // Đặt tiêu đề cho các cột
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Ngày");
        headerRow.createCell(1).setCellValue("7x loại 1x");

        // Khởi tạo biến để lưu trữ kết quả log
        StringBuilder valueOfDate = new StringBuilder();

        // Danh sách các URL cần truy cập
        List<String> urls = Arrays.asList("https://congcuxoso.net/MienBac/DacBiet/ThanhVienChotSo/DacBietEventMucSoVaDan9x0x_V2.aspx", "https://congcuxoso.net/MienBac/DacBiet/ThanhVienChotSo/DacBietThiDauMucSoVaDan9x0x_V2.aspx", "https://congcuxoso.net/MienBac/DacBiet/ThanhVienChotSo/DacBietMoBatMucSoVaDan9x0x_V2.aspx", "https://congcuxoso.net/MienBac/DacBiet/ThanhVienChotSo/DacBietDanMucSoVaDan9x0x_V2.aspx", "https://congcuxoso.net/MienBac/DacBiet/ThanhVienChotSo/DacBietLoaiMucSoVaDan9x0x_V2.aspx");

        // Thiết lập ngày bắt đầu và ngày kết thúc
        LocalDate startDate = LocalDate.parse("14/11/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate endDate = LocalDate.parse("14/11/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Biến để theo dõi số dòng Excel
        int rowNum = 1; // Bắt đầu từ dòng 2

        // Lặp qua các ngày từ startDate đến endDate
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            String formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            // Lặp qua từng URL trong danh sách
            for (String url : urls) {
//                valueOfDate.append("Kết quả cho URL ").append(url).append(" ngày ").append(formattedDate).append(":\n");
                valueOfDate.append(retrieveResults(url, formattedDate)).append("\n");
            }

            // Tiếp tục thực hiện các thao tác trên trang khác
            driver.get("https://taodanxoso.kangdh.com/");

            // Clear hộp input
            WebElement clearButton = driver.findElement(By.xpath("//input[@id='k_btnClear3']"));
            clearButton.click();

            // Nhập kết quả vào textarea
            WebElement inputTextarea = driver.findElement(By.xpath("//textarea[@id='MainContent_txtInput']"));
            inputTextarea.clear();
            inputTextarea.sendKeys(valueOfDate.toString());

            // Nhấn nút Tạo Mức 2D
            WebElement create2DButton = driver.findElement(By.xpath("//input[@value='Tạo Mức 2D']"));
            create2DButton.click();

            // Nhấn nút Dàn Xuôi
            WebElement danXuoiButton = driver.findElement(By.xpath("//button[text()='Dàn Xuôi']"));
            danXuoiButton.click();

            // Đợi cho đến khi textarea có nội dung
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(driver -> !driver.findElement(By.xpath("//textarea[@id='catdanfinal']")).getAttribute("value").isEmpty());

            // Lấy kết quả từ ô textarea
            WebElement resultTextarea = driver.findElement(By.xpath("//textarea[@id='catdanfinal']"));
            String finalResult = resultTextarea.getAttribute("value");

//            // In kết quả cuối cùng ra console
//            System.out.println("Kết quả Dàn Xuôi cho ngày " + formattedDate + ": ");
//            System.out.println(finalResult);

            // Phân tách các dàn
            String[] lines = finalResult.split("\n");
            String lineUnder7x = "";
            String lineUnder1x = "";

            // Lặp qua từng dòng để tìm dàn 7x và dàn 1x
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].startsWith("7x") && i + 1 < lines.length) {
                    lineUnder7x = lines[i + 1]; // Dòng dưới dàn 7x
                } else if (lines[i].startsWith("1x") && i + 1 < lines.length) {
                    lineUnder1x = lines[i + 1]; // Dòng dưới dàn 1x
                }
            }

//            // In ra các dòng dưới dàn 7x và 1x
//            System.out.println("Dòng dưới dàn 7x: " + lineUnder7x);
//            System.out.println("Dòng dưới dàn 1x: " + lineUnder1x);

            // Tạo một tập hợp để lưu các số từ dòng 1x
            Set<String> set1x = new HashSet<>(Arrays.asList(lineUnder1x.split(",")));

            // Tách dòng 7x thành mảng các số
            String[] numbers7x = lineUnder7x.split(",");

            // Duyệt qua mảng 7x và loại bỏ các số có trong 1x
            StringBuilder result7x = new StringBuilder();
            for (String number : numbers7x) {
                if (!set1x.contains(number)) {
                    result7x.append(number).append(","); // Thêm số vào kết quả nếu không có trong 1x
                }
            }

            // Xóa dấu phẩy cuối cùng nếu có
            if (result7x.length() > 0) {
                result7x.setLength(result7x.length() - 1);
            }

//            // In ra kết quả
//            System.out.println("Dòng dưới dàn 7x sau khi loại bỏ dàn 1x: " + result7x.toString());

            // Lưu kết quả vào Excel
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(formattedDate);
            row.createCell(1).setCellValue(result7x.toString());

            // Reset valueOfDate cho vòng lặp tiếp theo
            valueOfDate.setLength(0);
        }

        // Lưu Workbook vào tệp Excel
        try (FileOutputStream fileOut = new FileOutputStream("data.xlsx")) {
            workbook.write(fileOut);
        } finally {
            workbook.close();
        }
    }

    private String retrieveResults(String url, String date) {
        driver.get(url);

        WebElement dateInput = driver.findElement(By.xpath("//input[@id='MainContent_txtNgay']"));
        dateInput.clear();
        dateInput.sendKeys(date);

        WebElement xemButton = driver.findElement(By.xpath("//input[@value='Xem']"));
        xemButton.click();

        sleepInSecond(1);

        List<WebElement> results = driver.findElements(By.xpath("//td[@style='width:799px;']"));

        // Biến lưu trữ kết quả cho từng URL
        StringBuilder resultString = new StringBuilder();

        for (WebElement result : results) {
            resultString.append(result.getText()).append("\n");
        }

        return resultString.toString();
    }

    @Test
    public void TC_ccxs_results() throws IOException {
        // Khởi tạo Workbook và Sheet cho Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("MB");

        // Đặt tiêu đề cho các cột
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Ngày");
        headerRow.createCell(1).setCellValue("KQ");
        headerRow.createCell(2).setCellValue("Event");
        headerRow.createCell(3).setCellValue("ThiDau");
        headerRow.createCell(4).setCellValue("MoBat");
        headerRow.createCell(5).setCellValue("Dan");
        headerRow.createCell(6).setCellValue("Loai");

        // Khởi tạo biến để lưu trữ kết quả log
        StringBuilder valueOfDate = new StringBuilder();

        // Danh sách các URL cần truy cập
        List<String> urls = Arrays.asList("https://congcuxoso.net/MienBac/DacBiet/ThanhVienChotSo/DacBietEventMucSoVaDan9x0x_V2.aspx", "https://congcuxoso.net/MienBac/DacBiet/ThanhVienChotSo/DacBietThiDauMucSoVaDan9x0x_V2.aspx", "https://congcuxoso.net/MienBac/DacBiet/ThanhVienChotSo/DacBietMoBatMucSoVaDan9x0x_V2.aspx", "https://congcuxoso.net/MienBac/DacBiet/ThanhVienChotSo/DacBietDanMucSoVaDan9x0x_V2.aspx", "https://congcuxoso.net/MienBac/DacBiet/ThanhVienChotSo/DacBietLoaiMucSoVaDan9x0x_V2.aspx");

        // Thiết lập ngày bắt đầu và ngày kết thúc
        LocalDate startDate = LocalDate.parse("01/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate endDate = LocalDate.parse("14/11/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Biến để theo dõi số dòng Excel
        int rowNum = 1; // Bắt đầu từ dòng 2

        // Lặp qua các ngày từ startDate đến endDate
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            String formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            Row row = sheet.createRow(rowNum++);

            // Lưu ngày vào cột đầu tiên của dòng
            row.createCell(0).setCellValue(formattedDate);

            // Lấy kết quả của ngày đó
            driver.get("https://ketqua04.net/so-ket-qua");

            WebElement dateInput = driver.findElement(By.xpath("//input[@id='date']"));
            dateInput.clear();
            dateInput.sendKeys(date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

            WebElement countInput = driver.findElement(By.xpath("//input[@id='count']"));
            countInput.clear();
            countInput.sendKeys("1");
            driver.findElement(By.xpath("//button[@type='submit']")).click();
//            sleepInSecond(3);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // thời gian chờ tối đa là 10 giây
            WebElement giaiDacBiet = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='rs_0_0']")));

            // Lấy giá trị của phần tử
            String value = giaiDacBiet.getText();
            String DB = value.substring(value.length() - 2);

            // Lưu giá trị DB vào cột thứ 2 của dòng hiện tại
            row.createCell(1).setCellValue(DB);

            // Lặp qua từng URL trong danh sách
            for (int urlIndex = 0; urlIndex < urls.size(); urlIndex++) {
                String result = analysByDate(urls.get(urlIndex), formattedDate, DB);
                row.createCell(2 + urlIndex).setCellValue(result);
            }

        }

        // Lưu Workbook vào tệp Excel
        try (FileOutputStream fileOut = new FileOutputStream("data.xlsx")) {
            workbook.write(fileOut);
        } finally {
            workbook.close();
        }

        driver.quit();
    }

    private String analysByDate(String url, String date, String DB) {
        driver.get(url);

        WebElement dateInput = driver.findElement(By.xpath("//input[@id='MainContent_txtNgay']"));
        dateInput.clear();
        dateInput.sendKeys(date);

        WebElement xemButton = driver.findElement(By.xpath("//input[@value='Xem']"));
        xemButton.click();

        sleepInSecond(1);

        for (int i = 10; i >= 1; i--) {
            try {
                WebElement cell = driver.findElement(By.xpath("//table[@id='MainContent_dgvDan9x0x']//tr[@align='center'][" + i + "]/td[@style='width:799px;']"));
                if (cell.getText().contains(DB)) {
                    WebElement precedingCell = driver.findElement(By.xpath("//table[@id='MainContent_dgvDan9x0x']//tr[@align='center'][" + i + "]/td[@style='width:799px;']/preceding-sibling::td"));
                    return precedingCell.getText().substring(0, 1);
                }
            } catch (NoSuchElementException e) {
                // Nếu không tìm thấy phần tử, tiếp tục vòng lặp
                continue;
            }
        }

        return "G";
    }

    private void checkValue0x1x(String url, String date, Row row, String DB, int urlIndex) {
        driver.get(url);

        // Nhập ngày vào trường nhập liệu
        WebElement dateInput = driver.findElement(By.xpath("//input[@id='MainContent_txtNgay']"));
        dateInput.clear();
        dateInput.sendKeys(date);

        // Nhấn nút "Xem"
        WebElement xemButton = driver.findElement(By.xpath("//input[@value='Xem']"));
        xemButton.click();
        sleepInSecond(1);

        // Chờ và kiểm tra giá trị 0x và 1x
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Kiểm tra ô `0x`
            WebElement td0x = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(), '0x')]/following-sibling::td")));
            String td0xValue = td0x.getText();
//            System.out.println("Giá trị của 0x tại URL " + url + ": " + td0xValue);

            if (td0xValue.contains(DB)) {
                row.createCell(2 + urlIndex).setCellValue("x"); // Ghi vào cột 2, 3, 4, hoặc 5 tùy vào urlIndex
            }

            // Kiểm tra ô `1x`
            WebElement td1x = driver.findElement(By.xpath("//td[contains(text(), '1x')]/following-sibling::td"));
            String td1xValue = td1x.getText();
//            System.out.println("Giá trị của 1x tại URL " + url + ": " + td1xValue);

            if (td1xValue.contains(DB)) {
                row.createCell(8 + urlIndex).setCellValue("x"); // Ghi vào cột 8, 9, 10, hoặc 11 tùy vào urlIndex
            }
        } catch (Exception e) {
            System.out.println("Không tìm thấy 0x hoặc 1x cho URL: " + url);
        }
    }

    @Test
    public void TC_dau_duoi() throws IOException {
        // Khởi tạo Workbook và Sheet cho Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("MB");

        // Đặt tiêu đề cho các cột
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Ngày");
        headerRow.createCell(1).setCellValue("KQ");
        headerRow.createCell(2).setCellValue("0");
        headerRow.createCell(3).setCellValue("1");
        headerRow.createCell(4).setCellValue("2");
        headerRow.createCell(5).setCellValue("3");
        headerRow.createCell(6).setCellValue("4");
        headerRow.createCell(7).setCellValue("5");
        headerRow.createCell(8).setCellValue("6");
        headerRow.createCell(9).setCellValue("7");
        headerRow.createCell(10).setCellValue("8");
        headerRow.createCell(11).setCellValue("9");
        headerRow.createCell(12).setCellValue("8x8");
        headerRow.createCell(13).setCellValue("Húp");
        headerRow.createCell(14).setCellValue("6x6");
        headerRow.createCell(15).setCellValue("Húp");


        // Thiết lập ngày bắt đầu và ngày kết thúc
        LocalDate startDate = LocalDate.parse("15/11/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate endDate = LocalDate.parse("17/11/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Biến để theo dõi số dòng Excel
        int rowNum = 1; // Bắt đầu từ dòng 2

        // Lặp qua các ngày từ startDate đến endDate
        for (LocalDate date = endDate; !date.isBefore(startDate); date = date.minusDays(1)) {
            String formattedDate = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            Row row = sheet.createRow(rowNum++);

            // Lưu ngày vào cột đầu tiên của dòng
            row.createCell(0).setCellValue(formattedDate);

            // Lấy kết quả của ngày đó
            driver.get("https://ketqua04.net/so-ket-qua");

            WebElement dateInput = driver.findElement(By.xpath("//input[@id='date']"));
            dateInput.clear();
            dateInput.sendKeys(date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

            WebElement countInput = driver.findElement(By.xpath("//input[@id='count']"));
            countInput.clear();
            countInput.sendKeys("1");
            driver.findElement(By.xpath("//button[@type='submit']")).click();
//            sleepInSecond(3);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // thời gian chờ tối đa là 10 giây
            WebElement giaiDacBiet = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='rs_0_0']")));

            // Lấy giá trị của phần tử
            String value = giaiDacBiet.getText();
            String DB = value.substring(value.length() - 2);

            // Lưu giá trị DB vào cột thứ 2 của dòng hiện tại
            row.createCell(1).setCellValue(DB);

            String formattedPreviousDate = date.plusDays(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            driver.get("https://ketqua04.net/dau-duoi-loto");

            WebElement dateInputDauDuoi = driver.findElement(By.xpath("//input[@id='date']"));
            dateInputDauDuoi.clear();
            dateInputDauDuoi.sendKeys(date.minusDays(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            dateInputDauDuoi.sendKeys(Keys.ENTER);

            WebElement countInputDauDuoi = driver.findElement(By.xpath("//input[@id='count']"));
            countInputDauDuoi.clear();
            countInputDauDuoi.sendKeys("4");
            driver.findElement(By.xpath("//button[@type='submit']")).click();

            // Đợi phần tử cần lấy dữ liệu xuất hiện
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            List<WebElement> tdElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//td/b[text()='Tổng'][position() <= 2]/../following-sibling::td")));

            // Lấy giá trị lần lượt từ các <td> này
            for (int i = 0; i < 10; i++) {  // Chạy từ i=0 đến i=9
                // Lấy giá trị của 2 cột liên tiếp, tương ứng với i và i+10
                String text1 = tdElements.get(i).getText().replace(" lần", "").trim();
                String text2 = tdElements.get(i + 10).getText().replace(" lần", "").trim();

                // Chuyển đổi sang số nguyên
                int value1 = Integer.parseInt(text1);
                int value2 = Integer.parseInt(text2);

                // Thực hiện phép cộng
                int sum = value1 + value2;

                // Lưu vào cột tương ứng (từ cột 2 đến cột 11)
                row.createCell(2 + i).setCellValue(sum);
            }

            // Khai báo danh sách labels chỉ chứa các chỉ số từ 0 đến 9
            List<String> labels = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");


            // Lưu giá trị từ các cột 2 đến 11 vào danh sách với chỉ số cột
            List<Map.Entry<Integer, String>> valueWithLabels = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                // Lấy giá trị của các cột từ 2 đến 11
                int sum = (int) row.getCell(2 + i).getNumericCellValue();
                valueWithLabels.add(new AbstractMap.SimpleEntry<>(sum, labels.get(i))); // Lưu cặp giá trị và label
            }

//            System.out.println("Danh sách giá trị và cột ban đầu (valueWithLabels): " + valueWithLabels);

            // Sắp xếp theo giá trị giảm dần
            valueWithLabels.sort((entry1, entry2) -> entry2.getKey() - entry1.getKey());

//            System.out.println("Danh sách giá trị sau khi sắp xếp giảm dần (valueWithLabels): " + valueWithLabels);

            // Lấy top 4 ở trên (4 giá trị lớn nhất)
            StringBuilder top4Upper = new StringBuilder();
//            System.out.println("Top 4 lớn nhất:");
            for (int i = 0; i < 4; i++) {
                int valueTop4Up = valueWithLabels.get(i).getKey();
                String labelTop4Up = valueWithLabels.get(i).getValue();
//                System.out.println("Giá trị top 4 ở trên: " + valueTop4Up + " tại " + labelTop4Up);
                top4Upper.append(labelTop4Up); // Lấy tên cột tương ứng
            }

            // Lấy top 4 ở dưới (4 giá trị nhỏ nhất trong 6 còn lại)
            StringBuilder top4Lower = new StringBuilder();
//            System.out.println("Top 4 nhỏ nhất:");
            for (int i = 9; i > 5; i--) {
                int valueTop4Down = valueWithLabels.get(i).getKey();
                String labelTop4Down = valueWithLabels.get(i).getValue();
//                System.out.println("Giá trị top 4 ở dưới: " + valueTop4Down + " tại " + labelTop4Down);
                top4Lower.append(labelTop4Down); // Lấy tên cột tương ứng
            }

            // Kết quả cuối cùng
            String result8 = top4Upper.toString() + top4Lower.toString();
//            System.out.println("Kết quả cuối cùng (result): " + result8);

            // Lưu kết quả vào cell 12
            row.createCell(12).setCellValue(result8);

            // Lấy top 3 ở trên (3 giá trị lớn nhất)
            StringBuilder top3Upper = new StringBuilder();
//            System.out.println("Top 3 lớn nhất:");
            for (int i = 0; i < 3; i++) {
                int valueTop3Up = valueWithLabels.get(i).getKey();
                String labelTop3Up = valueWithLabels.get(i).getValue();
//                System.out.println("Giá trị top 3 ở trên: " + valueTop3Up + " tại " + labelTop3Up);
                top3Upper.append(labelTop3Up); // Lấy tên cột tương ứng
            }

            // Lấy top 3 ở dưới (3 giá trị nhỏ nhất trong 6 còn lại)
            StringBuilder top3Lower = new StringBuilder();
//            System.out.println("Top 3 nhỏ nhất:");
            for (int i = 9; i > 6; i--) {
                int valueTop3Down = valueWithLabels.get(i).getKey();
                String labelTop3Down = valueWithLabels.get(i).getValue();
//                System.out.println("Giá trị top 3 ở dưới: " + valueTop3Down + " tại " + labelTop3Down);
                top3Lower.append(labelTop3Down); // Lấy tên cột tương ứng
            }

            // Kết quả cuối cùng
            String result6 = top3Upper.toString() + top3Lower.toString();
//            System.out.println("Kết quả cuối cùng (result): " + result6);

            // Lưu kết quả vào cell 14
            row.createCell(14).setCellValue(result6);

            //verify

            // Lấy giá trị từ cell 1 (giả sử là cell 1 chứa giá trị "92")
            String cell1Value = row.getCell(1).getStringCellValue();

            // Lấy giá trị từ cell 12 (giả sử cell 12 chứa kết quả "16209543")
            String cell12Value = row.getCell(12).getStringCellValue();

            // Lấy giá trị từ cell 14 (giả sử cell 14 chứa kết quả "127954")
            String cell14Value = row.getCell(14).getStringCellValue();

            // Kiểm tra xem tất cả các ký tự trong cell 1 có xuất hiện trong cell 12 hay không
            boolean containsAllInCell12 = true;
            for (char c : cell1Value.toCharArray()) {
                if (cell12Value.indexOf(c) == -1) {
                    containsAllInCell12 = false;
                    break;
                }
            }

            // Kiểm tra xem tất cả các ký tự trong cell 1 có xuất hiện trong cell 14 hay không
            boolean containsAllInCell14 = true;
            for (char c : cell1Value.toCharArray()) {
                if (cell14Value.indexOf(c) == -1) {
                    containsAllInCell14 = false;
                    break;
                }
            }

            // Kiểm tra nếu không có chứa tất cả ký tự của cell 1, lưu vào cell 13 hoặc cell 15
            if (!containsAllInCell12) {
                row.createCell(13).setCellValue("x");
            }

            if (!containsAllInCell14) {
                row.createCell(15).setCellValue("x");
            }


        }

        // Lưu Workbook vào tệp Excel
        try (FileOutputStream fileOut = new FileOutputStream("data.xlsx")) {
            workbook.write(fileOut);
        } finally {
            workbook.close();
        }

        driver.quit();
    }

    @Test
    public void TC_nguoc() throws IOException {
        // Khởi tạo Workbook và Sheet cho Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("DauDuoiTongHieu");

        // Đặt tiêu đề cho các cột
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Ngày");
        headerRow.createCell(1).setCellValue("KQ");
        headerRow.createCell(2).setCellValue("9x0xNguoc");
        headerRow.createCell(3).setCellValue("MucNguoc");
        headerRow.createCell(4).setCellValue("9x0xDeu");
        headerRow.createCell(5).setCellValue("MucDeu");
        headerRow.createCell(6).setCellValue("9x0xUDSM");
        headerRow.createCell(7).setCellValue("Muc");


        // Thiết lập ngày bắt đầu và ngày kết thúc
        LocalDate startDate = LocalDate.parse("01/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate endDate = LocalDate.parse("04/01/2025", DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Biến để theo dõi số dòng Excel
        int rowNum = 1; // Bắt đầu từ dòng 2

        // Lặp qua các ngày từ startDate đến endDate
        for (LocalDate date = endDate; !date.isBefore(startDate); date = date.minusDays(1)) {
            String formattedDate = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            Row row = sheet.createRow(rowNum++);

            // Lưu ngày vào cột đầu tiên của dòng
            row.createCell(0).setCellValue(formattedDate);

            // Lấy kết quả của ngày đó
            driver.get("https://ketqua04.net/so-ket-qua");

            WebElement dateInput = driver.findElement(By.xpath("//input[@id='date']"));
            dateInput.clear();
            dateInput.sendKeys(date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

            WebElement countInput = driver.findElement(By.xpath("//input[@id='count']"));
            countInput.clear();
            countInput.sendKeys("1");
            driver.findElement(By.xpath("//button[@type='submit']")).click();
//            sleepInSecond(3);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // thời gian chờ tối đa là 10 giây
            WebElement giaiDacBiet = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='rs_0_0']")));

            // Lấy giá trị của phần tử
            String value = giaiDacBiet.getText();
            String DB = value.substring(value.length() - 2);

            // Lưu giá trị DB vào cột thứ 2 của dòng hiện tại
            row.createCell(1).setCellValue(DB);

            String formattedPreviousDate = date.minusDays(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            driver.get("https://ketqua04.net/so-ket-qua");

            WebElement dateInput1 = driver.findElement(By.xpath("//input[@id='date']"));
            dateInput1.clear();
            dateInput1.sendKeys(formattedPreviousDate);

            WebElement countInput1 = driver.findElement(By.xpath("//input[@id='count']"));
            countInput1.clear();
            countInput1.sendKeys("1");
            driver.findElement(By.xpath("//button[@type='submit']")).click();
//            sleepInSecond(1);


            List<WebElement> prizes = driver.findElements(By.xpath("//div[contains(@id, 'rs')]"));
            StringBuilder allPrizes = new StringBuilder();

            for (WebElement prize : prizes) {
                allPrizes.append(prize.getText());
            }

//            System.out.println("Tất cả giải thưởng:\n" + allPrizes);


            // 1. Đếm số lần xuất hiện các số từ 0-9
            Map<String, Integer> repeatCounts = countDigitOccurrencesFormatted(allPrizes.toString());

            // 4. Dàn chạm mặc định
            Map<String, List<String>> danChamMap = generateDefaultDanChamMap();

            // 5. Tạo chuỗi kết quả
            String result = generateAllChamRepeats(danChamMap, repeatCounts);

            // In kết quả
//            System.out.println("Generated Cham Repeats:\n" + result);

            // Tiếp tục thực hiện các thao tác trên trang khác
            driver.get("https://taodanxoso.kangdh.com/");

            // Clear hộp input
            WebElement clearButton = driver.findElement(By.xpath("//input[@id='k_btnClear3']"));
            clearButton.click();

            // Nhập kết quả vào textarea
            WebElement inputTextarea = driver.findElement(By.xpath("//textarea[@id='MainContent_txtInput']"));
            inputTextarea.clear();
            inputTextarea.sendKeys(result);

            // Nhấn nút Tạo Mức 2D
            WebElement create2DButton = driver.findElement(By.xpath("//input[@value='Tạo Mức 2D']"));
            create2DButton.click();

            // Nhấn nút Dàn Ngược
            WebElement danNguocButton = driver.findElement(By.xpath("//button[text()='Chém Dưới']"));
            danNguocButton.click();

            // Đợi cho đến khi textarea có nội dung
            WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait2.until(driver -> !driver.findElement(By.xpath("//textarea[@id='catdanfinal']")).getAttribute("value").isEmpty());

            // Lấy kết quả từ ô textarea
            WebElement resultTextarea = driver.findElement(By.xpath("//textarea[@id='catdanfinal']"));
            String finalResultDanNguoc = resultTextarea.getAttribute("value");

            // Nhấn nút Dàn Đều
            WebElement danDeuButton = driver.findElement(By.xpath("//button[text()='Chém 2 Đầu']"));
            danDeuButton.click();

            // Đợi cho đến khi textarea có nội dung
            WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait3.until(driver -> !driver.findElement(By.xpath("//textarea[@id='catdanfinal']")).getAttribute("value").isEmpty());

            // Lấy kết quả từ ô textarea
            String finalResultDanDeu = resultTextarea.getAttribute("value");

            clearButton.click();
            inputTextarea.clear();
            inputTextarea.sendKeys(finalResultDanNguoc + "\n" + finalResultDanDeu);
            create2DButton.click();

            // Nhấn nút Dàn Xuôi
            WebElement danXuoiButton = driver.findElement(By.xpath("//button[text()='Chém Trên']"));
            danXuoiButton.click();

            // Đợi cho đến khi textarea có nội dung
            WebDriverWait wait4 = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait4.until(driver -> !driver.findElement(By.xpath("//textarea[@id='catdanfinal']")).getAttribute("value").isEmpty());

            // Lấy kết quả từ ô textarea
            String finalResult = resultTextarea.getAttribute("value");

//            System.out.println(finalResultDanNguoc + '\n' + finalResultDanDeu);

            // Lưu giá trị dau duoi tong hieu nguoc vào cột thứ 3 của dòng hiện tại
            row.createCell(2).setCellValue(finalResultDanNguoc);
            row.createCell(4).setCellValue(finalResultDanDeu);
            row.createCell(6).setCellValue(finalResult);

            // Bước 1: Tách finalResult thành các dàn chạm
            Map<String, String> dan9x0xNguoc = parseFinalResult(finalResultDanNguoc);
            Map<String, String> dan9x0xDeu = parseFinalResult(finalResultDanDeu);
            Map<String, String> dan9x0x = parseFinalResult(finalResult);

            // Bước 2: Tìm dàn chứa DB và cập nhật kết quả
            String mucNguoc = findMucContainingDB(dan9x0xNguoc, DB);
            String mucDeu = findMucContainingDB(dan9x0xDeu, DB);
            String muc = findMucContainingDB(dan9x0x, DB);

            // Bước 3: Cập nhật vào cột thứ 3
//            System.out.println("Kết quả dàn chứa DB: " + muc);
            row.createCell(3).setCellValue(mucNguoc);  // Cập nhật kết quả vào bảng (cột 3)
            row.createCell(5).setCellValue(mucDeu);  // Cập nhật kết quả vào bảng (cột 3)
            row.createCell(7).setCellValue(muc);  // Cập nhật kết quả vào bảng (cột 3)

            // Kiểm tra xem muc có chứa "7", "8", "9" hoặc "không" không
            if (muc.contains("7") || muc.contains("8") || muc.contains("9") || muc.contains("Không")) {
                row.createCell(8).setCellValue("x");
            }


        }

        // Lưu Workbook vào tệp Excel
        try (FileOutputStream fileOut = new FileOutputStream("data.xlsx")) {
            workbook.write(fileOut);
        } finally {
            workbook.close();
        }


        driver.quit();
    }

    // Hàm tách finalResult thành các dàn chạm
    private static Map<String, String> parseFinalResult(String finalResult) {
        Map<String, String> danChamMap = new LinkedHashMap<>();
        String[] lines = finalResult.split("\n");

        for (int i = 0; i < lines.length; i += 2) {
            String cham = lines[i].trim();
            String numbers = (i + 1 < lines.length) ? lines[i + 1].trim() : "";
            danChamMap.put(cham, numbers);
        }

        return danChamMap;
    }

    // Hàm tìm dàn chứa DB
    private static String findMucContainingDB(Map<String, String> danChamMap, String DB) {
        // Tạo một danh sách các entry từ danChamMap và đảo ngược danh sách này
        List<Map.Entry<String, String>> reversedEntries = new ArrayList<>(danChamMap.entrySet());
        Collections.reverse(reversedEntries);  // Đảo ngược danh sách

        // Duyệt qua các entry đã đảo ngược (từ dưới lên trên)
        for (Map.Entry<String, String> entry : reversedEntries) {
            String cham = entry.getKey();
            String numbers = entry.getValue();

            // Kiểm tra nếu DB có trong dàn này
            if (numbers.contains(DB)) {
                return cham;  // Trả về dàn chạm chứa DB
            }
        }

        return "Không tìm thấy dàn chứa " + DB;  // Nếu không có dàn nào chứa DB
    }

    // Hàm đếm số lần xuất hiện các số từ 0-9
    private static Map<String, Integer> countDigitOccurrencesFormatted(String input) {
        int[] digitCounts = new int[10];

        // Duyệt qua chuỗi và đếm các chữ số từ 0 đến 9
        for (char ch : input.toCharArray()) {
            if (Character.isDigit(ch)) {
                digitCounts[Character.getNumericValue(ch)]++;
            }
        }

        // Tạo Map kết quả với các số lần xuất hiện cho mỗi chữ số
        Map<String, Integer> formattedResult = new LinkedHashMap<>();
        for (int i = 0; i < digitCounts.length; i++) {
            formattedResult.put("Chạm " + i, digitCounts[i]);
        }

        return formattedResult;
    }


    // Hàm tạo dàn chạm mặc định
    private static Map<String, List<String>> generateDefaultDanChamMap() {
        Map<String, List<String>> danChamMap = new LinkedHashMap<>();

        // Dàn chạm cố định (theo mã JavaScript của bạn)
        danChamMap.put("Chạm 0", Arrays.asList("00", "10", "20", "30", "40", "50", "60", "70", "80", "90", "01", "02", "03", "04", "05", "06", "07", "08", "09", "19", "28", "37", "46", "55", "64", "73", "82", "91", "11", "22", "33", "44", "66", "77", "88", "99"));
        danChamMap.put("Chạm 1", Arrays.asList("01", "11", "21", "31", "41", "51", "61", "71", "81", "91", "10", "12", "13", "14", "15", "16", "17", "18", "19", "29", "38", "47", "56", "65", "74", "83", "92", "09", "32", "43", "54", "76", "87", "98"));
        danChamMap.put("Chạm 2", Arrays.asList("02", "12", "22", "32", "42", "52", "62", "72", "82", "92", "20", "21", "23", "24", "25", "26", "27", "28", "29", "11", "39", "48", "57", "66", "75", "84", "93", "08", "19", "31", "53", "64", "86", "97"));
        danChamMap.put("Chạm 3", Arrays.asList("03", "13", "23", "33", "43", "53", "63", "73", "83", "93", "30", "31", "32", "34", "35", "36", "37", "38", "39", "12", "21", "49", "58", "67", "76", "85", "94", "07", "18", "29", "41", "52", "74", "96"));
        danChamMap.put("Chạm 4", Arrays.asList("04", "14", "24", "34", "44", "54", "64", "74", "84", "94", "40", "41", "42", "43", "45", "46", "47", "48", "49", "13", "22", "31", "59", "68", "77", "86", "95", "06", "17", "28", "39", "51", "62", "73"));
        danChamMap.put("Chạm 5", Arrays.asList("05", "15", "25", "35", "45", "55", "65", "75", "85", "95", "50", "51", "52", "53", "54", "56", "57", "58", "59", "14", "23", "32", "41", "69", "78", "87", "96", "16", "27", "38", "49", "61", "72", "83", "94"));
        danChamMap.put("Chạm 6", Arrays.asList("06", "16", "26", "36", "46", "56", "66", "76", "86", "96", "60", "61", "62", "63", "64", "65", "67", "68", "69", "15", "24", "33", "42", "51", "79", "88", "97", "04", "37", "48", "59", "71", "82", "93"));
        danChamMap.put("Chạm 7", Arrays.asList("07", "17", "27", "37", "47", "57", "67", "77", "87", "97", "70", "71", "72", "73", "74", "75", "76", "78", "79", "16", "25", "34", "43", "52", "61", "89", "98", "03", "14", "36", "58", "69", "81", "92"));
        danChamMap.put("Chạm 8", Arrays.asList("08", "18", "28", "38", "48", "58", "68", "78", "88", "98", "80", "81", "82", "83", "84", "85", "86", "87", "89", "17", "26", "35", "44", "53", "62", "71", "99", "02", "13", "24", "46", "57", "79", "91"));
        danChamMap.put("Chạm 9", Arrays.asList("09", "19", "29", "39", "49", "59", "69", "79", "89", "99", "90", "91", "92", "93", "94", "95", "96", "97", "98", "18", "27", "36", "45", "54", "63", "72", "81", "01", "12", "23", "34", "56", "67", "78"));

        return danChamMap;
    }

    // Hàm tạo chuỗi lặp cho các dàn chạm
    private static String generateAllChamRepeats(Map<String, List<String>> danChamMap, Map<String, Integer> repeatCounts) {
        StringBuilder result = new StringBuilder();
        for (String cham : danChamMap.keySet()) {
            List<String> chamList = danChamMap.get(cham);
            int repeatCount = repeatCounts.getOrDefault(cham, 0);

            result.append(cham).append(":\n");
            for (int i = 0; i < repeatCount; i++) {
                result.append(String.join(", ", chamList)).append("\n");
            }
            result.append("\n");
        }
        return result.toString().trim();
    }

    @Test
    public void xsmn() throws IOException {
        // Khởi tạo Workbook và Sheet cho Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("xsmn");

        // Đặt tiêu đề cho các cột
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Ngày");
        headerRow.createCell(1).setCellValue("KQ2D");
        headerRow.createCell(2).setCellValue("KQ3D");



        // Thiết lập ngày bắt đầu và ngày kết thúc
        LocalDate startDate = LocalDate.parse("08/02/2025", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate endDate = LocalDate.parse("11/02/2025", DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Biến để theo dõi số dòng Excel
        int rowNum = 1; // Bắt đầu từ dòng 2

        // Lặp qua các ngày từ startDate đến endDate
        for (LocalDate date = endDate; !date.isBefore(startDate); date = date.minusDays(1)) {
            String formattedDate = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            Row row = sheet.createRow(rowNum++);

            // Lưu ngày vào cột đầu tiên của dòng
            row.createCell(0).setCellValue(formattedDate);

            // Lấy kết quả của ngày đó
            driver.get("https://ketqua04.net/so-ket-qua");

            WebElement dateInput = driver.findElement(By.xpath("//input[@id='date']"));
            dateInput.clear();
            dateInput.sendKeys(date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

            WebElement countInput = driver.findElement(By.xpath("//input[@id='count']"));
            countInput.clear();
            countInput.sendKeys("1");
            driver.findElement(By.xpath("//button[@type='submit']")).click();
//            sleepInSecond(3);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // thời gian chờ tối đa là 10 giây
            WebElement giaiDacBiet = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='rs_0_0']")));

            // Lấy giá trị của phần tử
            String value = giaiDacBiet.getText();
            String DB2D = value.substring(value.length() - 2);
            String DB3D = value.substring(value.length() - 3);

//            System.out.println("Giá trị gốc: " + giaiDacBiet.getText());
//            System.out.println(value + DB2D + DB3D );

            // Lưu giá trị DB vào cột thứ 2 của dòng hiện tại
            row.createCell(1).setCellValue(DB2D);
            row.createCell(2).setCellValue(DB3D);

            String formattedPreviousDate = date.minusDays(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            driver.get("https://ketqua04.net/so-ket-qua");

            WebElement dateInput1 = driver.findElement(By.xpath("//input[@id='date']"));
            dateInput1.clear();
            dateInput1.sendKeys(formattedPreviousDate);

            WebElement countInput1 = driver.findElement(By.xpath("//input[@id='count']"));
            countInput1.clear();
            countInput1.sendKeys("1");
            driver.findElement(By.xpath("//button[@type='submit']")).click();
//            sleepInSecond(1);


            List<WebElement> prizes = driver.findElements(By.xpath("//div[contains(@id, 'rs')]"));

            int columnIndex = 3; // Bắt đầu từ cột 3 (Excel index 0-based)

            for (WebElement prize : prizes) {
                String text = prize.getText();

                // Lấy 2 số cuối nếu độ dài >= 2, nếu không thì lấy nguyên text
                String lastTwoDigits = text.length() >= 2 ? text.substring(text.length() - 2) : text;

                row.createCell(columnIndex).setCellValue(lastTwoDigits);
                columnIndex++; // Di chuyển sang cột tiếp theo
            }

            // Duyệt từ dòng 2 (index 1) đến dòng cuối cùng
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row rowxsmn = sheet.getRow(i);

                // Kiểm tra nếu rowxsmn không phải là null
                if (rowxsmn == null) {
                    continue; // Nếu dòng trống, bỏ qua
                }

                // StringBuilder để lưu tất cả kết quả từ các cell
                StringBuilder allResults = new StringBuilder();

                // Duyệt qua các cell từ cột E (index 4) đến cột AE (index 30)
                for (int cellIndex = 4; cellIndex <= 30; cellIndex++) {
                    // Lấy cell tại vị trí cellIndex
                    Cell cell = rowxsmn.getCell(cellIndex);

                    // Kiểm tra nếu cell là null hoặc không có giá trị
                    if (cell != null) {
                        // Lấy giá trị của cell nếu nó không phải là null
                        String lastTwoDigits = cell.getStringCellValue();

                        // Tạo URL với {lastTwoDigits}
                        String url = "https://dudoanketquaxoso.com/thong-ke-nhung-ngay-de-ve-" + lastTwoDigits + ".html";

                        // Truy cập vào trang web
                        driver.get(url);

                        // Tìm các phần tử có XPath //td[@style='width: 40%']
                        List<WebElement> elements = driver.findElements(By.xpath("//td[@style='width: 40%']"));

                        // StringBuilder để lưu dữ liệu lấy được từ các phần tử
                        StringBuilder result = new StringBuilder();

                        // Lặp qua các phần tử tìm được và lấy text
                        for (WebElement element : elements) {
                            result.append(element.getText()).append("\n"); // Lưu giá trị vào StringBuilder
                        }

                        // Thêm kết quả từ từng URL vào allResults
                        allResults.append(result.toString()).append("\n");  // Thêm kết quả vào allResults
                    } else {
                        // Nếu cell là null, có thể bỏ qua hoặc thêm giá trị mặc định
                        allResults.append("No Data").append("\n"); // Thêm thông báo mặc định nếu không có giá trị
                    }
                }

                // Sau khi lấy dữ liệu từ tất cả các cell
                String resultData = allResults.toString();

                // Kiểm tra nếu dữ liệu vượt quá giới hạn
                final int MAX_CELL_LENGTH = 32767; // Giới hạn độ dài cho mỗi ô trong Excel

                // Nếu dữ liệu vượt quá giới hạn, chia thành nhiều phần
                if (resultData.length() > MAX_CELL_LENGTH) {
                    int startIndex = 0;
                    int partIndex = 0;

                    // Chia nhỏ và lưu vào các cell tiếp theo trong các cột AG, AH, AI, AJ...
                    while (startIndex < resultData.length()) {
                        // Lấy phần nhỏ từ chuỗi gốc
                        String part = resultData.substring(startIndex, Math.min(startIndex + MAX_CELL_LENGTH, resultData.length()));

                        // Lưu vào cột AG (index 32) trở đi
                        rowxsmn.createCell(32 + partIndex).setCellValue(part);

                        // Cập nhật chỉ số bắt đầu cho phần tiếp theo
                        startIndex += MAX_CELL_LENGTH;
                        partIndex++; // Chuyển đến cell tiếp theo
                    }
                } else {
                    // Nếu không vượt quá giới hạn, lưu vào cột AG (index 32)
                    rowxsmn.createCell(32).setCellValue(resultData);
                }

//                // Lấy giá trị từ cột AG (index 32) đến cột cuối cùng có dữ liệu trong dòng
//                StringBuilder columnDData = new StringBuilder();
//                int lastCellIndex = rowxsmn.getPhysicalNumberOfCells(); // Cột cuối cùng có dữ liệu
//
//                // Duyệt qua các cột từ AG (index 32) đến cột cuối cùng có giá trị
//                for (int j = 32; j < lastCellIndex; j++) {
//                    String cellValue = rowxsmn.getCell(j) != null ? rowxsmn.getCell(j).getStringCellValue() : ""; // Kiểm tra cell có giá trị không
//                    columnDData.append(cellValue).append("\n"); // Nối các giá trị với nhau (có thể thêm \n giữa các giá trị nếu cần)
//                }
//
//                // Tiếp tục thực hiện các thao tác trên trang khác
//                driver.get("https://taodanxoso.kangdh.com/");
//
//                // Clear hộp input
//                WebElement clearButton = driver.findElement(By.xpath("//input[@id='k_btnClear3']"));
//                clearButton.click();
//
//                // Nhập kết quả vào textarea
//                WebElement inputTextarea = driver.findElement(By.xpath("//textarea[@id='MainContent_txtInput']"));
//                inputTextarea.clear();
//                inputTextarea.sendKeys(columnDData.toString()); // Gửi chuỗi đã nối vào textarea
//
//
//                // Nhấn nút Tạo Mức 2D
//                WebElement create2DButton = driver.findElement(By.xpath("//input[@value='Tạo Mức 2D']"));
//                create2DButton.click();
//
//                // Nhấn nút Dàn Xuôi
//                WebElement danXuoiButton = driver.findElement(By.xpath("//button[text()='Chém Trên']"));
//                danXuoiButton.click();
//
//                // Đợi cho đến khi textarea có nội dung
//                WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(10));
//                wait3.until(driver -> !driver.findElement(By.xpath("//textarea[@id='catdanfinal']")).getAttribute("value").isEmpty());
//
////                // Lấy kết quả từ ô textarea
////                WebElement resultTextarea = driver.findElement(By.xpath("//textarea[@id='catdanfinal']"));
////                String finalResult = resultTextarea.getAttribute("value");
////                // Lưu ngày vào cột đầu tiên của dòng
////                row.createCell(2).setCellValue(finalResult);
//
//                //Kiểm tra kqa
//                String KQngay = rowxsmn.getCell(1).getStringCellValue();
//                WebElement inputTextKQngay = driver.findElement(By.xpath("//textarea[@id='kdh_txtKQ']"));
//                inputTextKQngay.clear();
//                inputTextKQngay.sendKeys(KQngay);
//
//                // Nhấn nút ktra
//                WebElement ktraKQ = driver.findElement(By.xpath("//input[@value='K.tra KQ']"));
//                ktraKQ.click();
//                // Tìm element lb_dlmucso và lấy text
//                WebElement lbDlmucso = driver.findElement(By.xpath("//label[@id='lb_dlmucso']"));
//                String textDlmucso = lbDlmucso.getText();
//
//                // Tìm element lb_dldan và lấy text
//                WebElement lbDldan = driver.findElement(By.xpath("//label[@id='lb_dldan']"));
//                String textDldan = lbDldan.getText();
//
//                // In ra để kiểm tra
//                System.out.println("Text của lb_dlmucso: " + textDlmucso);
//                System.out.println("Text của lb_dldan: " + textDldan);
//
//                // Xử lý lấy giá trị sau "Mức:" và trước "("
//                String mucValue = "";
//                Matcher matcherMuc = Pattern.compile("Mức:\\s*(.*?)\\s*\\(").matcher(textDlmucso);
//                if (matcherMuc.find()) {
//                    mucValue = matcherMuc.group(1).replaceAll("\\s+", "").trim(); // Xóa khoảng trắng thừa
//                }
//
//                // Xử lý lấy giá trị sau "dàn:"
//                String danValue = "";
//                Matcher matcherDan = Pattern.compile("dàn:\\s*(.*)").matcher(textDldan);
//                if (matcherDan.find()) {
//                    danValue = matcherDan.group(1).trim();
//                }
//
//                // Lưu vào file Excel
////                row.createCell(3).setCellValue(mucValue);  // Lưu mức vào cột 3
//                row.createCell(3).setCellValue(danValue);  // Lưu dàn vào cột 4
            }






        }

        // Lưu file Excel
        try (FileOutputStream fileOut = new FileOutputStream("xsmn.xlsx")) {
            workbook.write(fileOut);
        }
        workbook.close();
        driver.quit();
    }


    @Test
    public void TC_tachdan() throws IOException {
        // Khởi tạo Workbook và Sheet cho Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("TachDan");

        // Đặt tiêu đề cho các cột
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Ngày");
        headerRow.createCell(1).setCellValue("KQ2D");
        headerRow.createCell(2).setCellValue("Tách 2D");
        headerRow.createCell(3).setCellValue("SL 2D");
        headerRow.createCell(4).setCellValue("Húp 2D");
        headerRow.createCell(6).setCellValue("Tách 3D");
        headerRow.createCell(7).setCellValue("SL 3D");
        headerRow.createCell(8).setCellValue("3D loại 7d");
        headerRow.createCell(9).setCellValue("SL 3D loại 7d");
        headerRow.createCell(10).setCellValue("KQ3D");
        headerRow.createCell(11).setCellValue("Húp 3D");
        headerRow.createCell(12).setCellValue("2D Bù");
        headerRow.createCell(13).setCellValue("3D Bù");
        headerRow.createCell(14).setCellValue("3D Today");
        headerRow.createCell(15).setCellValue("Húp Today");
        headerRow.createCell(18).setCellValue("SL Today");


        // Thiết lập ngày bắt đầu và ngày kết thúc
        LocalDate startDate = LocalDate.parse("01/02/2025", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate endDate = LocalDate.parse("02/02/2025", DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Biến để theo dõi số dòng Excel
        int rowNum = 1; // Bắt đầu từ dòng 2

        // Lặp qua các ngày từ startDate đến endDate
        for (LocalDate date = endDate; !date.isBefore(startDate); date = date.minusDays(1)) {
            String formattedDate = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            Row row = sheet.createRow(rowNum++);

            // Lưu ngày vào cột đầu tiên của dòng
            row.createCell(0).setCellValue(formattedDate);

            // Lấy kết quả của ngày đó
            driver.get("https://ketqua04.net/so-ket-qua");

            WebElement dateInput = driver.findElement(By.xpath("//input[@id='date']"));
            dateInput.clear();
            dateInput.sendKeys(date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

            WebElement countInput = driver.findElement(By.xpath("//input[@id='count']"));
            countInput.clear();
            countInput.sendKeys("1");
            driver.findElement(By.xpath("//button[@type='submit']")).click();
//            sleepInSecond(3);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // thời gian chờ tối đa là 10 giây
            WebElement giaiDacBiet = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='rs_0_0']")));

            // Lấy giá trị của phần tử
            String value = giaiDacBiet.getText();
            String DB2D = value.substring(value.length() - 2);
            String DB3D = value.substring(value.length() - 3);

//            System.out.println("Giá trị gốc: " + giaiDacBiet.getText());
//            System.out.println(value + DB2D + DB3D );

            // Lưu giá trị DB vào cột thứ 2 của dòng hiện tại
            row.createCell(1).setCellValue(DB2D);
            row.createCell(10).setCellValue(DB3D);

            String formattedPreviousDate = date.minusDays(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            driver.get("https://ketqua04.net/so-ket-qua");

            WebElement dateInput1 = driver.findElement(By.xpath("//input[@id='date']"));
            dateInput1.clear();
            dateInput1.sendKeys(formattedPreviousDate);

            WebElement countInput1 = driver.findElement(By.xpath("//input[@id='count']"));
            countInput1.clear();
            countInput1.sendKeys("1");
            driver.findElement(By.xpath("//button[@type='submit']")).click();
//            sleepInSecond(1);


            List<WebElement> prizes = driver.findElements(By.xpath("//div[contains(@id, 'rs')]"));
            StringBuilder allPrizes = new StringBuilder();

            for (WebElement prize : prizes) {
                allPrizes.append(prize.getText());
            }

//            System.out.println("Tất cả giải thưởng:\n" + allPrizes);

            // Xử lý chuỗi allPrizes để tách thành các cặp số
            String allPrizesString = allPrizes.toString();
            List<String> pairs = new ArrayList<>();

            for (int i = 0; i < allPrizesString.length() - 1; i++) {
                pairs.add(allPrizesString.substring(i, i + 2));
            }

            // Loại bỏ trùng lặp và sắp xếp
            Set<String> uniquePairs = new TreeSet<>(pairs);

            // Đếm tổng số cặp số
            int pairCount = uniquePairs.size();

            // Lưu kết quả vào Excel
            row.createCell(2).setCellValue(String.join(", ", uniquePairs)); // Các cặp số
            row.createCell(3).setCellValue(pairCount); // Tổng số cặp số

            List<String> triples = new ArrayList<>();

            for (int i = 0; i < allPrizesString.length() - 2; i++) {
                triples.add(allPrizesString.substring(i, i + 3)); // Tách bộ 3 số
            }

            // Loại bỏ trùng lặp và sắp xếp
            Set<String> uniqueTriples = new TreeSet<>(triples);

            // Đếm tổng số bộ 3 số
            int tripleCount = uniqueTriples.size();

            // Lưu các bộ 3 số đã xử lý vào Excel (cột G)
            row.createCell(6).setCellValue(String.join(", ", uniqueTriples)); // Các bộ 3 số (cột G)

            // Lưu số lượng bộ 3 số vào cột H
            row.createCell(7).setCellValue(tripleCount); // Tổng số bộ 3 số (cột H)

            // Tạo tập hợp các bộ số từ 000 đến 999
            Set<String> fullSet = new TreeSet<>();
            for (int i = 0; i <= 999; i++) {
                fullSet.add(String.format("%03d", i)); // Đảm bảo định dạng 3 chữ số
            }
//            System.out.println("Initial fullSet (000-999): " + fullSet); // Log fullSet ban đầu

            // Kiểm tra cột C (các cặp số) có chứa giá trị ở cột B (2D) hay không
            if (!uniquePairs.contains(DB2D)) {
                row.createCell(4).setCellValue("x"); // Điền "x" vào cột E nếu không chứa
            } else {
                row.createCell(4).setCellValue("Húp");
            }

            // Kiểm tra giá trị của cột E và tính giá trị cho cột F
            Cell cellE = row.getCell(4); // Lấy giá trị ở cột E
            if ("Húp".equals(cellE.getStringCellValue())) {
                // Nếu cột E = "Húp", cột F = 99.5 - giá trị ở cột D
                double valueD = row.getCell(3).getNumericCellValue(); // Lấy giá trị ở cột D
                row.createCell(5).setCellValue(99.5 - valueD); // Điền giá trị vào cột F
            } else {
                // Nếu cột E không phải "Húp", cột F = - giá trị ở cột D
                double valueD = row.getCell(3).getNumericCellValue(); // Lấy giá trị ở cột D
                row.createCell(5).setCellValue(-valueD); // Điền giá trị vào cột F
            }

            // Tính tổng tất cả các giá trị trong cột F từ F2 trở đi
            double sumF = 0;
            for (int i = 1; i < sheet.getLastRowNum() + 1; i++) { // Bắt đầu từ dòng 2 (index = 1)
                Row currentRow = sheet.getRow(i);
                if (currentRow != null) {
                    Cell cellF = currentRow.getCell(5); // Cột F là index 5
                    if (cellF != null && cellF.getCellType() == CellType.NUMERIC) {
                        sumF += cellF.getNumericCellValue();
                    }
                }
            }

            // Điền giá trị tổng vào ô F1
            Row row1 = sheet.getRow(0); // Lấy dòng 1 (index = 0)
            if (row1 == null) {
                row1 = sheet.createRow(0); // Nếu dòng 1 chưa tồn tại, tạo mới
            }
            Cell cellF1 = row1.createCell(5); // Cột F (index 5)
            cellF1.setCellValue(sumF); // Gán giá trị tổng vào F1
        }
        // Sau khi hoàn tất vòng lặp và đã có dữ liệu trong sheet:
        // Lấy các bộ số từ cột G của 7 hàng liên tiếp
        for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Bắt đầu từ hàng thứ 2 (index = 1)
            Set<String> allTriples = new TreeSet<>();
            for (int offset = 0; offset < 7; offset++) { // Lấy 7 hàng liên tiếp
                Row currentRow = sheet.getRow(i + offset);
                if (currentRow != null) {
                    Cell cellG = currentRow.getCell(6); // Cột G (index = 6)
                    if (cellG != null && cellG.getCellType() == CellType.STRING) {
                        String cellValue = cellG.getStringCellValue();
                        String[] triplesInRow = cellValue.split(", ");
                        allTriples.addAll(Arrays.asList(triplesInRow));
                    }
                }
            }

            // Tạo tập hợp các bộ số từ 000 đến 999
            Set<String> fullSet = new TreeSet<>();
            for (int j = 0; j <= 999; j++) {
                fullSet.add(String.format("%03d", j));
            }

            // Loại bỏ các bộ số đã có
            fullSet.removeAll(allTriples);

            // Ghép các bộ số còn lại thành chuỗi
            StringBuilder remainingSet = new StringBuilder();
            for (String remainingTriple : fullSet) {
                if (remainingSet.length() > 0) {
                    remainingSet.append(", ");
                }
                remainingSet.append(remainingTriple);
            }

            // Đếm số phần tử còn lại
            int remainingCount = fullSet.size();

            // Lưu vào cột I (index = 8) và cột J (index = 9)
            Row row = sheet.getRow(i);
            if (row != null) {
                // Lưu danh sách các bộ số còn lại vào cột I
                row.createCell(8).setCellValue(remainingSet.toString());

                // Lưu số lượng phần tử vào cột J
                row.createCell(9).setCellValue(remainingCount);
            }

            // Kiểm tra cột I (các cặp số còn lại) có chứa giá trị ở cột K (3D) hay không
            Cell cellI = row.getCell(8); // Cột I (index 8)
            Cell cellJ = row.getCell(10); // Cột J (index 6, 3D)

            if (cellI != null && cellJ != null && cellI.getCellType() == CellType.STRING && cellJ.getCellType() == CellType.STRING) {
                String cellIValue = cellI.getStringCellValue(); // Lấy giá trị từ cột I
                String cellJValue = cellJ.getStringCellValue(); // Lấy giá trị từ cột J

                // Kiểm tra các giá trị trong cột J có nằm trong cột I hay không
                String[] jValues = cellJValue.split(", "); // Tách các giá trị ở cột J
                boolean containsAll = true;

                for (String jValue : jValues) {
                    if (!cellIValue.contains(jValue)) {
                        containsAll = false; // Nếu có giá trị không nằm trong cột I
                        break;
                    }
                }

                // Ghi kết quả vào cột K (index 9)
                if (containsAll) {
                    row.createCell(11).setCellValue("Húp"); // Ghi kết quả nếu tất cả đều nằm trong cột I
                } else {
                    row.createCell(11).setCellValue("x"); // Ghi kết quả nếu có giá trị không nằm trong cột I
                }
            } else {
                row.createCell(11).setCellValue("Không thể kiểm tra"); // Ghi thông báo nếu cột I hoặc J không hợp lệ
            }
        }

        // Dàn 2D bù và 3D bù
        headerRow.createCell(16).setCellValue("00,01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99");
        headerRow.createCell(17).setCellValue("000,003,004,006,011,012,013,014,015,018,019,022,024,028,030,031,032,036,039,040,041,042,043,044,045,046,047,050,053,054,056,060,061,062,063,065,067,069,070,071,076,080,081,082,085,086,088,089,091,093,094,095,097,098,099,100,102,106,107,108,109,110,111,112,113,114,115,116,118,119,120,121,123,125,126,127,128,129,130,131,132,133,135,136,138,139,141,142,144,146,148,151,154,155,157,158,159,162,167,169,170,171,174,177,178,180,181,183,184,185,186,188,190,191,192,194,195,196,198,200,202,204,206,208,209,211,212,213,218,219,220,221,222,223,224,225,226,228,229,230,232,236,237,238,239,241,242,243,244,245,247,248,249,251,252,255,256,257,260,261,263,264,265,266,267,269,273,274,275,278,282,283,285,286,287,288,289,295,296,298,299,300,303,305,307,308,309,313,317,320,322,323,324,325,327,329,330,331,332,333,334,335,342,344,346,347,348,349,351,354,356,358,359,361,363,364,365,367,368,369,370,371,375,376,380,382,383,384,386,388,389,391,394,396,397,399,400,401,404,405,406,408,409,410,412,414,415,419,422,424,426,427,430,431,433,436,437,438,439,440,441,442,443,444,448,449,452,454,457,458,459,460,461,462,465,466,467,468,470,472,474,475,477,479,480,481,482,483,484,486,487,488,489,490,493,494,496,497,499,500,502,503,505,507,508,509,510,512,513,521,522,523,524,525,526,528,529,530,532,534,535,538,540,543,544,545,546,548,549,550,551,553,554,555,556,557,561,562,563,566,567,570,571,572,576,577,578,579,580,581,586,592,593,594,596,597,599,600,601,604,609,610,612,613,614,615,616,617,618,619,620,623,624,627,629,631,632,634,635,636,638,639,640,645,647,649,650,651,657,658,659,660,661,662,663,664,665,668,669,670,675,676,677,678,679,681,683,684,685,686,687,688,691,694,695,696,697,698,699,701,702,703,706,707,708,711,712,713,714,715,716,720,721,723,724,726,728,732,733,736,738,741,743,746,747,748,751,753,754,755,756,757,758,759,761,762,763,764,765,766,768,770,772,775,776,777,779,780,781,782,783,787,788,789,793,795,796,797,798,799,801,805,808,811,813,815,817,818,819,821,823,826,830,832,836,840,841,842,843,844,845,850,851,852,853,855,856,857,858,859,860,861,864,865,866,867,869,870,872,875,876,877,879,880,882,883,885,888,890,892,894,896,897,901,902,903,904,909,911,913,914,915,916,917,918,924,925,927,929,930,931,932,936,938,939,940,942,945,946,948,949,950,952,953,954,957,958,959,960,963,964,965,966,967,969,971,972,973,974,977,978,979,980,982,985,987,990,991,993,994,996,999");

        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                // Lấy giá trị cột C và I
                String value2D = headerRow.getCell(16).getStringCellValue();  // Giá trị 2D bù từ header cột 16
                String value3D = headerRow.getCell(17).getStringCellValue();  // Giá trị 3D bù từ header cột 17


                String valueC = row.getCell(2) != null ? row.getCell(2).getStringCellValue() : "";  // Cột C
                String valueI = row.getCell(8) != null ? row.getCell(8).getStringCellValue() : "";  // Cột I


                // Ghép giá trị cột C với value2D bù, loại bỏ trùng lặp
                Set<String> combined2D = new TreeSet<>();
                String[] valueCArray = valueC.split(",");  // Tách chuỗi cột C thành mảng
                String[] value2DArray = value2D.split(",");  // Tách chuỗi 2D bù thành mảng

                // Thêm tất cả giá trị từ cột C vào Set sau khi loại bỏ khoảng trắng
                for (String val : valueCArray) {
                    combined2D.add(val.trim());  // Loại bỏ khoảng trắng trước và sau
                }

                // Thêm tất cả giá trị từ value2D bù vào Set (loại bỏ trùng lặp tự động)
                for (String val : value2DArray) {
                    combined2D.add(val.trim());  // Loại bỏ khoảng trắng trước và sau
                }

                // Lưu vào cột M
                row.createCell(12).setCellValue(String.join(", ", combined2D));

                // Thêm số từ 0 đến 9 vào trước mỗi giá trị trong combined2D
                Set<String> finalCombined2D = new TreeSet<>();
                for (int j = 0; j <= 9; j++) {
                    for (String val : combined2D) {
                        // Thêm số 0-9 vào trước từng giá trị
                        finalCombined2D.add(String.format("%d%s", j, val));
                    }
                }

                // Ghép giá trị cột I với value3D bù, loại bỏ trùng lặp
                Set<String> combined3D = new TreeSet<>();
                String[] valueIArray = valueI.split(",");  // Tách chuỗi cột I thành mảng
                String[] value3DArray = value3D.split(",");  // Tách chuỗi 3D bù thành mảng

                // Thêm tất cả giá trị từ cột I vào Set sau khi loại bỏ khoảng trắng
                for (String val : valueIArray) {
                    combined3D.add(val.trim());  // Loại bỏ khoảng trắng trước và sau
                }

                // Thêm tất cả giá trị từ value3D bù vào Set (loại bỏ trùng lặp tự động)
                for (String val : value3DArray) {
                    combined3D.add(val.trim());  // Loại bỏ khoảng trắng trước và sau
                }

                // Lưu vào cột N
                row.createCell(13).setCellValue(String.join(", ", combined3D));

                // Tìm giao nhau của finalCombined2D và combined3D
                Set<String> commonValues = new TreeSet<>(finalCombined2D);
                commonValues.retainAll(combined3D);  // Giữ lại chỉ những phần tử chung giữa finalCombined2D và combined3D

                // Lưu vào cột O
                row.createCell(14).setCellValue(String.join(", ", commonValues));

                // Kiểm tra giá trị ở cột K có nằm trong commonValues không
                String valueK = row.getCell(10) != null ? row.getCell(10).getStringCellValue().trim() : "";  // Cột K

                if (commonValues.contains(valueK)) {
                    // Nếu có, lưu "Húp" vào cột P
                    row.createCell(15).setCellValue("Húp");
                } else {
                    // Nếu không, lưu "X" vào cột P
                    row.createCell(15).setCellValue("X");
                }

                // Đếm số phần tử trong commonValues và lưu vào cột S
                int commonValuesSize = commonValues.size();
                row.createCell(18).setCellValue(commonValuesSize);  // Cột S (cột 18)
            }
        }


        // Lưu file Excel
        try (FileOutputStream fileOut = new FileOutputStream("TachDan.xlsx")) {
            workbook.write(fileOut);
        }
        workbook.close();
        driver.close();
    }

    @Test
    public void TC_ccxs_0x1x() throws IOException {
        // Khởi tạo Workbook và Sheet cho Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("0x1x");

        // Đặt tiêu đề cho các cột
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Ngày");
        headerRow.createCell(1).setCellValue("KQ");
        headerRow.createCell(2).setCellValue("0xEvent");
        headerRow.createCell(3).setCellValue("0xThiDau");
        headerRow.createCell(4).setCellValue("0xMoBat");
        headerRow.createCell(5).setCellValue("0xDanMucSo");
//        headerRow.createCell(6).setCellValue("0xLoai");
        headerRow.createCell(8).setCellValue("1xEvent");
        headerRow.createCell(9).setCellValue("1xThiDau");
        headerRow.createCell(10).setCellValue("1xMoBat");
        headerRow.createCell(11).setCellValue("1xDanMucSo");
//        headerRow.createCell(12).setCellValue("1xLoai");

        // Danh sách các URL cần truy cập
        List<String> urls = Arrays.asList("https://congcuxoso.net/MienBac/DacBiet/ThanhVienChotSo/DacBietEventMucSoVaDan9x0x_V2.aspx", "https://congcuxoso.net/MienBac/DacBiet/ThanhVienChotSo/DacBietThiDauMucSoVaDan9x0x_V2.aspx", "https://congcuxoso.net/MienBac/DacBiet/ThanhVienChotSo/DacBietMoBatMucSoVaDan9x0x_V2.aspx", "https://congcuxoso.net/MienBac/DacBiet/ThanhVienChotSo/DacBietDanMucSoVaDan9x0x_V2.aspx"
//                "https://congcuxoso.net/MienBac/DacBiet/ThanhVienChotSo/DacBietLoaiMucSoVaDan9x0x_V2.aspx"
        );

        // Thiết lập ngày bắt đầu và ngày kết thúc
        LocalDate startDate = LocalDate.parse("02/11/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate endDate = LocalDate.parse("02/11/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Biến để theo dõi số dòng Excel
        int rowNum = 1; // Bắt đầu từ dòng 2

        // Lặp qua các ngày từ startDate đến endDate
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            String formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            Row row = sheet.createRow(rowNum++);

            // Lưu ngày vào cột đầu tiên của dòng
            row.createCell(0).setCellValue(formattedDate);

            // Lấy kết quả của ngày đó
            driver.get("https://ketqua04.net/so-ket-qua");

            WebElement dateInput = driver.findElement(By.xpath("//input[@id='date']"));
            dateInput.clear();
            dateInput.sendKeys(date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

            WebElement countInput = driver.findElement(By.xpath("//input[@id='count']"));
            countInput.clear();
            countInput.sendKeys("1");
            driver.findElement(By.xpath("//button[@type='submit']")).click();
//            sleepInSecond(3);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // thời gian chờ tối đa là 10 giây
            WebElement giaiDacBiet = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='rs_0_0']")));

            // Lấy giá trị của phần tử
            String value = giaiDacBiet.getText();
            String DB = value.substring(value.length() - 2);

            // Lưu giá trị DB vào cột thứ 2 của dòng hiện tại
            row.createCell(1).setCellValue(DB);

            // Lặp qua từng URL trong danh sách và gọi hàm `checkValue0x1x` cho mỗi URL
            for (int i = 0; i < urls.size(); i++) {
                checkValue0x1x(urls.get(i), formattedDate, row, DB, i);
            }
        }

        // Lưu Workbook vào tệp Excel
        try (FileOutputStream fileOut = new FileOutputStream("data.xlsx")) {
            workbook.write(fileOut);
        } finally {
            workbook.close();
        }
    }

    @Test
    public void TC_ccxs_DanDacBietKhung1Ngay_30days() throws IOException {
        // Khởi tạo Workbook và Sheet cho Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheetKQ = workbook.createSheet("DanDacBietKhung1Ngay");
        Sheet sheetXuLy = workbook.createSheet("XuLy");

        // Đặt tiêu đề cho các cột
        Row headerRow = sheetKQ.createRow(0);
        headerRow.createCell(0).setCellValue("Ngày");
        headerRow.createCell(1).setCellValue("KQ");
        headerRow.createCell(2).setCellValue("UDSM");
        headerRow.createCell(3).setCellValue("Mức");
        headerRow.createCell(4).setCellValue("Dàn");

        // Thiết lập ngày bắt đầu và ngày kết thúc
        LocalDate startDate = LocalDate.parse("05/02/2025", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate endDate = LocalDate.parse("05/02/2025", DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Biến để theo dõi số dòng Excel
        int rowNum = 1; // Bắt đầu từ dòng 2

        // Lặp qua các ngày từ startDate đến endDate
        for (LocalDate date = endDate; !date.isBefore(startDate); date = date.minusDays(1)) {
            String formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String formattedBefore1Date = date.minusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String formattedBefore30Date = date.minusDays(32).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            Row row = sheetKQ.createRow(rowNum++);

            // Lưu ngày vào cột đầu tiên của dòng
            row.createCell(0).setCellValue(formattedDate);

            // Lấy kết quả của ngày đó
            driver.get("https://ketqua04.net/so-ket-qua");

            WebElement dateInput = driver.findElement(By.xpath("//input[@id='date']"));
            dateInput.clear();
            dateInput.sendKeys(date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

            WebElement countInput = driver.findElement(By.xpath("//input[@id='count']"));
            countInput.clear();
            countInput.sendKeys("1");
            driver.findElement(By.xpath("//button[@type='submit']")).click();
//            sleepInSecond(3);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // thời gian chờ tối đa là 10 giây
            WebElement giaiDacBiet = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='rs_0_0']")));

            // Lấy giá trị của phần tử
            String value = giaiDacBiet.getText();
            String DB = value.substring(value.length() - 2);

            // Lưu giá trị DB vào cột thứ 2 của dòng hiện tại
            row.createCell(1).setCellValue(DB);

            driver.get("https://congcuxoso.com/MienBac/DacBiet/ThanhVienChotSo/DanDacBietKhung1NgayView2FRNew.aspx");

            WebElement fromDateInput = driver.findElement(By.xpath("//input[@id='MainContent_txtNgay1']"));
            WebElement toDateInput = driver.findElement(By.xpath("//input[@id='MainContent_txtNgay2']"));
            WebElement submit = driver.findElement(By.xpath("//input[@id='MainContent_btnLoad']"));
            fromDateInput.clear();
            fromDateInput.sendKeys(formattedDate);
            toDateInput.clear();
            toDateInput.sendKeys(formattedDate);
            submit.click();

            // Chờ cho loading xuất hiện
            WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
            // Chờ đến khi phần tử loading xuất hiện
            try {
                wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_UpdateProgress1")));
            } catch (TimeoutException e) {
                System.out.println("Phần tử loading không xuất hiện, tiếp tục chạy...");
            }

            // Chờ cho loading biến mất
            wait1.until(ExpectedConditions.invisibilityOfElementLocated(By.id("MainContent_UpdateProgress1")));


            // Tìm tất cả các phần tử
            List<WebElement> names = driver.findElements(By.xpath("//td[@style='background-color:#FFCC66;']"));
            // Tìm lại danh sách các phần tử có background-color #0066FF
            List<WebElement> dans = driver.findElements(By.xpath("//td[@style='background-color:#FFCC66;']/following-sibling::td[1]"));

            // Tạo sheet, hoặc lấy sheet hiện tại từ workbook
            int rowNumXuLy = 0;  // Biến chỉ số dòng, bắt đầu từ dòng 0

            // Lưu các giá trị vào cột A (từ phần tử màu vàng)
            for (WebElement name : names) {
                try {
                    // Lấy giá trị của phần tử
                    String text = name.getText();

                    // Tạo một dòng mới trong sheet
                    Row rowXuLy = sheetXuLy.createRow(rowNumXuLy);

                    // Lưu giá trị vào cột A (chỉ số 0 là cột A)
                    Cell cellA = rowXuLy.createCell(0);
                    cellA.setCellValue(text);

                    System.out.println("Cột A: " + text);  // In ra console
                    rowNumXuLy++;  // Tăng chỉ số dòng
                } catch (StaleElementReferenceException e) {
                    // Xử lý nếu có lỗi StaleElementReferenceException
                }
            }
            rowNumXuLy = 0;
            // Lưu các giá trị vào cột B (từ phần tử màu xanh)
            for (int i = 0; i < dans.size(); i++) {
                try {
                    // Lấy giá trị của phần tử
                    WebElement dan = dans.get(i);
                    String text = dan.getText();

                    // Kiểm tra nếu dòng đã tồn tại, nếu chưa tạo dòng mới
                    Row rowXuLy = sheetXuLy.getRow(i);
                    if (rowXuLy == null) {
                        rowXuLy = sheetXuLy.createRow(i);
                    }

                    // Lưu giá trị vào cột B (chỉ số 1 là cột B)
                    Cell cellB = rowXuLy.createCell(1);
                    cellB.setCellValue(text);

                    System.out.println("Cột B: " + text);  // In ra console
                } catch (StaleElementReferenceException e) {
                    // Xử lý nếu có lỗi StaleElementReferenceException
                }
            }

            // Tìm lại các phần tử sau mỗi lần thao tác
            fromDateInput = driver.findElement(By.xpath("//input[@id='MainContent_txtNgay1']"));
            toDateInput = driver.findElement(By.xpath("//input[@id='MainContent_txtNgay2']"));
            submit = driver.findElement(By.xpath("//input[@id='MainContent_btnLoad']"));
            fromDateInput.clear();
            fromDateInput.sendKeys(formattedBefore30Date);
            toDateInput.clear();
            toDateInput.sendKeys(formattedBefore1Date);
            submit.click();

            // Chờ cho loading xuất hiện
            WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
            // Chờ đến khi phần tử loading xuất hiện
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_UpdateProgress1")));

            // Chờ cho loading biến mất
            wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.id("MainContent_UpdateProgress1")));

            // Tìm lại các phần tử sau khi thao tác
            names = driver.findElements(By.xpath("//td[@style='background-color:#FFCC66;']"));
            rowNumXuLy = 0;
            // Lưu các giá trị vào cột C (từ phần tử màu vàng sau khi thao tác)
            for (WebElement name : names) {
                try {
                    // Lấy giá trị của phần tử
                    String text = name.getText();

                    // Kiểm tra nếu dòng đã tồn tại, nếu chưa tạo dòng mới
                    Row rowXuLy = sheetXuLy.getRow(rowNumXuLy);
                    if (rowXuLy == null) {
                        rowXuLy = sheetXuLy.createRow(rowNumXuLy);
                    }

                    // Lưu giá trị vào cột C (chỉ số 2 là cột C)
                    Cell cellC = rowXuLy.createCell(2);
                    cellC.setCellValue(text);

                    System.out.println("Cột C: " + text);  // In ra console
                    rowNumXuLy++;  // Tăng chỉ số dòng
                } catch (StaleElementReferenceException e) {
                    // Xử lý nếu có lỗi StaleElementReferenceException
                }
            }

            // Bước 1: Lưu dữ liệu từ cột A và B vào Map (name -> dàn)
            Map<String, String> nameToDanMap = new HashMap<>();

            int lastRow = sheetXuLy.getLastRowNum();
            for (int i = 0; i <= lastRow; i++) {
                Row rowMap = sheetXuLy.getRow(i);
                if (rowMap != null) {
                    Cell nameCell = rowMap.getCell(0);  // Cột A (name)
                    Cell danCell = rowMap.getCell(1);   // Cột B (dàn)

                    if (nameCell != null && danCell != null) {
                        String name = nameCell.getStringCellValue().trim();
                        String dan = danCell.getStringCellValue().trim();
                        nameToDanMap.put(name, dan);
                    }
                }
            }

            // Bước 2: Mapping từ cột C sang cột D
            for (int i = 0; i <= lastRow; i++) {
                Row rowMap = sheetXuLy.getRow(i);
                if (rowMap != null) {
                    Cell name30daysCell = rowMap.getCell(2); // Cột C (name của 30 ngày trước)
                    if (name30daysCell != null) {
                        String name30days = name30daysCell.getStringCellValue().trim();
                        String dan30days = nameToDanMap.getOrDefault(name30days, "Không có dữ liệu");

                        // Ghi giá trị vào cột D
                        Cell dan30daysCell = rowMap.createCell(3); // Cột D (dàn tương ứng)
                        dan30daysCell.setCellValue(dan30days);

                        System.out.println("Mapping: " + name30days + " -> " + dan30days);
                    }
                }
            }

            // Lấy số dòng cuối cùng trong sheetXuLy
            StringBuilder columnDData = new StringBuilder(); // Dùng StringBuilder để lưu dữ liệu

            // Lặp qua từng dòng để lấy dữ liệu cột D (index 3)
            for (int i = 0; i <= lastRow; i++) {
                Row rowMap = sheetXuLy.getRow(i);
                if (rowMap != null) {
                    Cell cellD = rowMap.getCell(3);  // Cột D (index 3)
                    if (cellD != null) {
                        columnDData.append(cellD.getStringCellValue()).append("\n"); // Xuống dòng sau mỗi giá trị
                    }
                }
            }

            // Tiếp tục thực hiện các thao tác trên trang khác
            driver.get("https://taodanxoso.kangdh.com/");

            // Clear hộp input
            WebElement clearButton = driver.findElement(By.xpath("//input[@id='k_btnClear3']"));
            clearButton.click();

            // Nhập kết quả vào textarea
            WebElement inputTextarea = driver.findElement(By.xpath("//textarea[@id='MainContent_txtInput']"));
            inputTextarea.clear();
            inputTextarea.sendKeys(columnDData.toString());

            // Nhấn nút Tạo Mức 2D
            WebElement create2DButton = driver.findElement(By.xpath("//input[@value='Tạo Mức 2D']"));
            create2DButton.click();

            // Nhấn nút Dàn Xuôi
            WebElement danXuoiButton = driver.findElement(By.xpath("//button[text()='Chém Trên']"));
            danXuoiButton.click();

            // Đợi cho đến khi textarea có nội dung
            WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait3.until(driver -> !driver.findElement(By.xpath("//textarea[@id='catdanfinal']")).getAttribute("value").isEmpty());

            // Lấy kết quả từ ô textarea
            WebElement resultTextarea = driver.findElement(By.xpath("//textarea[@id='catdanfinal']"));
            String finalResult = resultTextarea.getAttribute("value");
            // Lưu ngày vào cột đầu tiên của dòng
            row.createCell(2).setCellValue(finalResult);

            //Kiểm tra kqa
            String KQngay = row.getCell(1).getStringCellValue();
            WebElement inputTextKQngay = driver.findElement(By.xpath("//textarea[@id='kdh_txtKQ']"));
            inputTextKQngay.clear();
            inputTextKQngay.sendKeys(KQngay);

            // Nhấn nút Dàn Xuôi
            WebElement ktraKQ = driver.findElement(By.xpath("//input[@value='K.tra KQ']"));
            ktraKQ.click();
            // Tìm element lb_dlmucso và lấy text
            WebElement lbDlmucso = driver.findElement(By.xpath("//label[@id='lb_dlmucso']"));
            String textDlmucso = lbDlmucso.getText();

            // Tìm element lb_dldan và lấy text
            WebElement lbDldan = driver.findElement(By.xpath("//label[@id='lb_dldan']"));
            String textDldan = lbDldan.getText();

            // In ra để kiểm tra
            System.out.println("Text của lb_dlmucso: " + textDlmucso);
            System.out.println("Text của lb_dldan: " + textDldan);

            // Xử lý lấy giá trị sau "Mức:" và trước "("
            String mucValue = "";
            Matcher matcherMuc = Pattern.compile("Mức:\\s*(.*?)\\s*\\(").matcher(textDlmucso);
            if (matcherMuc.find()) {
                mucValue = matcherMuc.group(1).replaceAll("\\s+", "").trim(); // Xóa khoảng trắng thừa
            }

            // Xử lý lấy giá trị sau "dàn:"
            String danValue = "";
            Matcher matcherDan = Pattern.compile("dàn:\\s*(.*)").matcher(textDldan);
            if (matcherDan.find()) {
                danValue = matcherDan.group(1).trim();
            }

            // Lưu vào file Excel
            row.createCell(3).setCellValue(mucValue);  // Lưu mức vào cột 3
            row.createCell(4).setCellValue(danValue);  // Lưu dàn vào cột 4

            // In ra kiểm tra
            System.out.println("Mức: " + mucValue);
            System.out.println("Dàn: " + danValue);


            // Xóa toàn bộ dữ liệu nhưng giữ lại tiêu đề nếu có
            for (int i = sheetXuLy.getLastRowNum(); i >= 0; i--) { // Bỏ qua hàng đầu tiên nếu đó là tiêu đề
                Row rowXoa = sheetXuLy.getRow(i);
                if (rowXoa != null) {
                    sheetXuLy.removeRow(rowXoa);
                }
            }

        }

        // Lưu Workbook vào tệp Excel
        try (FileOutputStream fileOut = new FileOutputStream("DanDacBietKhung1Ngay.xlsx")) {
            workbook.write(fileOut);
        } finally {
            workbook.close();
            driver.quit();
        }
    }

    @Test
    public void TC_ccxs_LoaiDacBietHangNgay_30days() throws IOException {
        // Khởi tạo Workbook và Sheet cho Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheetKQ = workbook.createSheet("LoaiDacBietHangNgay");
        Sheet sheetXuLy = workbook.createSheet("XuLy");

        // Đặt tiêu đề cho các cột
        Row headerRow = sheetKQ.createRow(0);
        headerRow.createCell(0).setCellValue("Ngày");
        headerRow.createCell(1).setCellValue("KQ");
        headerRow.createCell(2).setCellValue("UDSM");
        headerRow.createCell(3).setCellValue("Mức");
        headerRow.createCell(4).setCellValue("Dàn");

        // Thiết lập ngày bắt đầu và ngày kết thúc
        LocalDate startDate = LocalDate.parse("01/01/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate endDate = LocalDate.parse("30/07/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Biến để theo dõi số dòng Excel
        int rowNum = 1; // Bắt đầu từ dòng 2

        // Lặp qua các ngày từ startDate đến endDate
        for (LocalDate date = endDate; !date.isBefore(startDate); date = date.minusDays(1)) {
            String formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String formattedBefore1Date = date.minusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String formattedBefore30Date = date.minusDays(32).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            Row row = sheetKQ.createRow(rowNum++);

            // Lưu ngày vào cột đầu tiên của dòng
            row.createCell(0).setCellValue(formattedDate);

            // Lấy kết quả của ngày đó
            driver.get("https://ketqua04.net/so-ket-qua");

            WebElement dateInput = driver.findElement(By.xpath("//input[@id='date']"));
            dateInput.clear();
            dateInput.sendKeys(date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

            WebElement countInput = driver.findElement(By.xpath("//input[@id='count']"));
            countInput.clear();
            countInput.sendKeys("1");
            driver.findElement(By.xpath("//button[@type='submit']")).click();
//            sleepInSecond(3);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // thời gian chờ tối đa là 10 giây
            WebElement giaiDacBiet = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='rs_0_0']")));

            // Lấy giá trị của phần tử
            String value = giaiDacBiet.getText();
            String DB = value.substring(value.length() - 2);

            // Lưu giá trị DB vào cột thứ 2 của dòng hiện tại
            row.createCell(1).setCellValue(DB);

            driver.get("https://congcuxoso.com/MienBac/DacBiet/ThanhVienChotSo/LoaiDacBietHangNgayView2FRNew.aspx");

            WebElement fromDateInput = driver.findElement(By.xpath("//input[@id='MainContent_txtNgay1']"));
            WebElement toDateInput = driver.findElement(By.xpath("//input[@id='MainContent_txtNgay2']"));
            WebElement submit = driver.findElement(By.xpath("//input[@id='MainContent_btnLoad']"));
            fromDateInput.clear();
            fromDateInput.sendKeys(formattedDate);
            toDateInput.clear();
            toDateInput.sendKeys(formattedDate);
            submit.click();

            // Chờ cho loading xuất hiện
            WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
            // Chờ đến khi phần tử loading xuất hiện
            try {
                wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_UpdateProgress1")));
            } catch (TimeoutException e) {
                System.out.println("Phần tử loading không xuất hiện, tiếp tục chạy...");
            }

            // Chờ cho loading biến mất
            wait1.until(ExpectedConditions.invisibilityOfElementLocated(By.id("MainContent_UpdateProgress1")));


            // Tìm tất cả các phần tử
            List<WebElement> names = driver.findElements(By.xpath("//td[@style='background-color:#FFCC66;']"));
            // Tìm lại danh sách các phần tử có background-color #0066FF
            List<WebElement> dans = driver.findElements(By.xpath("//td[@style='background-color:#FFCC66;']/following-sibling::td[1]"));

            // Tạo sheet, hoặc lấy sheet hiện tại từ workbook
            int rowNumXuLy = 0;  // Biến chỉ số dòng, bắt đầu từ dòng 0

            // Lưu các giá trị vào cột A (từ phần tử màu vàng)
            for (WebElement name : names) {
                try {
                    // Lấy giá trị của phần tử
                    String text = name.getText();

                    // Tạo một dòng mới trong sheet
                    Row rowXuLy = sheetXuLy.createRow(rowNumXuLy);

                    // Lưu giá trị vào cột A (chỉ số 0 là cột A)
                    Cell cellA = rowXuLy.createCell(0);
                    cellA.setCellValue(text);

                    System.out.println("Cột A: " + text);  // In ra console
                    rowNumXuLy++;  // Tăng chỉ số dòng
                } catch (StaleElementReferenceException e) {
                    // Xử lý nếu có lỗi StaleElementReferenceException
                }
            }
            rowNumXuLy = 0;
            // Lưu các giá trị vào cột B (từ phần tử màu xanh)
            for (int i = 0; i < dans.size(); i++) {
                try {
                    // Lấy giá trị của phần tử
                    WebElement dan = dans.get(i);
                    String text = dan.getText();

                    // Kiểm tra nếu dòng đã tồn tại, nếu chưa tạo dòng mới
                    Row rowXuLy = sheetXuLy.getRow(i);
                    if (rowXuLy == null) {
                        rowXuLy = sheetXuLy.createRow(i);
                    }

                    // Lưu giá trị vào cột B (chỉ số 1 là cột B)
                    Cell cellB = rowXuLy.createCell(1);
                    cellB.setCellValue(text);

                    System.out.println("Cột B: " + text);  // In ra console
                } catch (StaleElementReferenceException e) {
                    // Xử lý nếu có lỗi StaleElementReferenceException
                }
            }

            // Tìm lại các phần tử sau mỗi lần thao tác
            fromDateInput = driver.findElement(By.xpath("//input[@id='MainContent_txtNgay1']"));
            toDateInput = driver.findElement(By.xpath("//input[@id='MainContent_txtNgay2']"));
            submit = driver.findElement(By.xpath("//input[@id='MainContent_btnLoad']"));
            fromDateInput.clear();
            fromDateInput.sendKeys(formattedBefore30Date);
            toDateInput.clear();
            toDateInput.sendKeys(formattedBefore1Date);
            submit.click();

            // Chờ cho loading xuất hiện
            WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(20));
            // Chờ đến khi phần tử loading xuất hiện
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_UpdateProgress1")));

            // Chờ cho loading biến mất
            wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.id("MainContent_UpdateProgress1")));

            // Tìm lại các phần tử sau khi thao tác
            names = driver.findElements(By.xpath("//td[@style='background-color:#FFCC66;']"));
            rowNumXuLy = 0;
            // Lưu các giá trị vào cột C (từ phần tử màu vàng sau khi thao tác)
            for (WebElement name : names) {
                try {
                    // Lấy giá trị của phần tử
                    String text = name.getText();

                    // Kiểm tra nếu dòng đã tồn tại, nếu chưa tạo dòng mới
                    Row rowXuLy = sheetXuLy.getRow(rowNumXuLy);
                    if (rowXuLy == null) {
                        rowXuLy = sheetXuLy.createRow(rowNumXuLy);
                    }

                    // Lưu giá trị vào cột C (chỉ số 2 là cột C)
                    Cell cellC = rowXuLy.createCell(2);
                    cellC.setCellValue(text);

                    System.out.println("Cột C: " + text);  // In ra console
                    rowNumXuLy++;  // Tăng chỉ số dòng
                } catch (StaleElementReferenceException e) {
                    // Xử lý nếu có lỗi StaleElementReferenceException
                }
            }

            // Bước 1: Lưu dữ liệu từ cột A và B vào Map (name -> dàn)
            Map<String, String> nameToDanMap = new HashMap<>();

            int lastRow = sheetXuLy.getLastRowNum();
            for (int i = 0; i <= lastRow; i++) {
                Row rowMap = sheetXuLy.getRow(i);
                if (rowMap != null) {
                    Cell nameCell = rowMap.getCell(0);  // Cột A (name)
                    Cell danCell = rowMap.getCell(1);   // Cột B (dàn)

                    if (nameCell != null && danCell != null) {
                        String name = nameCell.getStringCellValue().trim();
                        String dan = danCell.getStringCellValue().trim();
                        nameToDanMap.put(name, dan);
                    }
                }
            }

            // Bước 2: Mapping từ cột C sang cột D
            for (int i = 0; i <= lastRow; i++) {
                Row rowMap = sheetXuLy.getRow(i);
                if (rowMap != null) {
                    Cell name30daysCell = rowMap.getCell(2); // Cột C (name của 30 ngày trước)
                    if (name30daysCell != null) {
                        String name30days = name30daysCell.getStringCellValue().trim();
                        String dan30days = nameToDanMap.getOrDefault(name30days, "Không có dữ liệu");

                        // Ghi giá trị vào cột D
                        Cell dan30daysCell = rowMap.createCell(3); // Cột D (dàn tương ứng)
                        dan30daysCell.setCellValue(dan30days);

                        System.out.println("Mapping: " + name30days + " -> " + dan30days);
                    }
                }
            }

            // Lấy số dòng cuối cùng trong sheetXuLy
            StringBuilder columnDData = new StringBuilder(); // Dùng StringBuilder để lưu dữ liệu

            // Lặp qua từng dòng để lấy dữ liệu cột D (index 3)
            for (int i = 0; i <= lastRow; i++) {
                Row rowMap = sheetXuLy.getRow(i);
                if (rowMap != null) {
                    Cell cellD = rowMap.getCell(3);  // Cột D (index 3)
                    if (cellD != null) {
                        columnDData.append(cellD.getStringCellValue()).append("\n"); // Xuống dòng sau mỗi giá trị
                    }
                }
            }

            // Tiếp tục thực hiện các thao tác trên trang khác
            driver.get("https://taodanxoso.kangdh.com/");

            // Clear hộp input
            WebElement clearButton = driver.findElement(By.xpath("//input[@id='k_btnClear3']"));
            clearButton.click();

            // Nhập kết quả vào textarea
            WebElement inputTextarea = driver.findElement(By.xpath("//textarea[@id='MainContent_txtInput']"));
            inputTextarea.clear();
            inputTextarea.sendKeys(columnDData.toString());

            // Nhấn nút Tạo Mức 2D
            WebElement create2DButton = driver.findElement(By.xpath("//input[@value='Tạo Mức 2D']"));
            create2DButton.click();

            // Nhấn nút Dàn Xuôi
            WebElement danXuoiButton = driver.findElement(By.xpath("//button[text()='Chém Dưới']"));
            danXuoiButton.click();

            // Đợi cho đến khi textarea có nội dung
            WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait3.until(driver -> !driver.findElement(By.xpath("//textarea[@id='catdanfinal']")).getAttribute("value").isEmpty());

            // Lấy kết quả từ ô textarea
            WebElement resultTextarea = driver.findElement(By.xpath("//textarea[@id='catdanfinal']"));
            String finalResult = resultTextarea.getAttribute("value");
            // Lưu ngày vào cột đầu tiên của dòng
            row.createCell(2).setCellValue(finalResult);

            //Kiểm tra kqa
            String KQngay = row.getCell(1).getStringCellValue();
            WebElement inputTextKQngay = driver.findElement(By.xpath("//textarea[@id='kdh_txtKQ']"));
            inputTextKQngay.clear();
            inputTextKQngay.sendKeys(KQngay);

            // Nhấn nút Dàn Xuôi
            WebElement ktraKQ = driver.findElement(By.xpath("//input[@value='K.tra KQ']"));
            ktraKQ.click();
            // Tìm element lb_dlmucso và lấy text
            WebElement lbDlmucso = driver.findElement(By.xpath("//label[@id='lb_dlmucso']"));
            String textDlmucso = lbDlmucso.getText();

            // Tìm element lb_dldan và lấy text
            WebElement lbDldan = driver.findElement(By.xpath("//label[@id='lb_dldan']"));
            String textDldan = lbDldan.getText();

            // In ra để kiểm tra
            System.out.println("Text của lb_dlmucso: " + textDlmucso);
            System.out.println("Text của lb_dldan: " + textDldan);

            // Xử lý lấy giá trị sau "Mức:" và trước "("
            String mucValue = "";
            Matcher matcherMuc = Pattern.compile("Mức:\\s*(.*?)\\s*\\(").matcher(textDlmucso);
            if (matcherMuc.find()) {
                mucValue = matcherMuc.group(1).replaceAll("\\s+", "").trim(); // Xóa khoảng trắng thừa
            }

            // Xử lý lấy giá trị sau "dàn:"
            String danValue = "";
            Matcher matcherDan = Pattern.compile("dàn:\\s*(.*)").matcher(textDldan);
            if (matcherDan.find()) {
                danValue = matcherDan.group(1).trim();
            }

            // Lưu vào file Excel
            row.createCell(3).setCellValue(mucValue);  // Lưu mức vào cột 3
            row.createCell(4).setCellValue(danValue);  // Lưu dàn vào cột 4

            // In ra kiểm tra
            System.out.println("Mức: " + mucValue);
            System.out.println("Dàn: " + danValue);


            // Xóa toàn bộ dữ liệu nhưng giữ lại tiêu đề nếu có
            for (int i = sheetXuLy.getLastRowNum(); i >= 0; i--) { // Bỏ qua hàng đầu tiên nếu đó là tiêu đề
                Row rowXoa = sheetXuLy.getRow(i);
                if (rowXoa != null) {
                    sheetXuLy.removeRow(rowXoa);
                }
            }

        }

        // Lưu Workbook vào tệp Excel
        try (FileOutputStream fileOut = new FileOutputStream("LoaiDacBietHangNgay.xlsx")) {
            workbook.write(fileOut);
        } finally {
            workbook.close();
            driver.quit();
        }
    }

    @Test
    public void TC_4D() throws IOException {
        // Cấu hình startDate và endDate theo định dạng DD-MM-YYYY
        String startDateStr = "03-11-2024";
        // Xem cho ngày mai thì endDate để ngày hôm nay
        String endDateStr = "05-11-2024";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDate = LocalDate.parse(startDateStr, formatter);
        LocalDate endDate = LocalDate.parse(endDateStr, formatter);

        // Tạo workbook và sheet mới
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("4D");

        // Tạo hàng tiêu đề
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Ngày");
        headerRow.createCell(1).setCellValue("KQ");
        headerRow.createCell(2).setCellValue("Xịt");
        headerRow.createCell(3).setCellValue("Mức");

        // Biến rowNum để bắt đầu từ hàng 1
        int rowNum = 1;

        // Vòng lặp từ endDate về startDate
        while (!endDate.isBefore(startDate)) {
            String currentDateStr = endDate.format(formatter);
            driver.get("https://ketqua04.net/so-ket-qua");

            WebElement dateInput = driver.findElement(By.xpath("//input[@id='date']"));
            dateInput.clear();
            dateInput.sendKeys(currentDateStr);

            WebElement countInput = driver.findElement(By.xpath("//input[@id='count']"));
            countInput.clear();
            countInput.sendKeys("300");
            driver.findElement(By.xpath("//button[@type='submit']")).click();
            sleepInSecond(3);

            List<WebElement> prizes = driver.findElements(By.xpath("//td/div/div[contains(@class, 'phoi-size')]"));

            // Tạo hàng mới và điền ngày vào cột A (A2, A3, ...)
            Row row = sheet.createRow(rowNum++);
            // Cộng thêm 1 ngày vào currentDateStr và ghi vào ô
            String nextDateStr = endDate.plusDays(1).format(formatter);
            row.createCell(0).setCellValue(nextDateStr);

            int cellNum = 4; // Bắt đầu từ cột B
            for (WebElement prize : prizes) {
                String prizeText = prize.getText();
                // Kiểm tra và chỉ lấy 3 chữ số cuối nếu prizeText có nhiều hơn 3 chữ số
                if (prizeText.matches("\\d{3,}")) {
                    if (prizeText.length() > 3) {
                        prizeText = prizeText.substring(prizeText.length() - 3);  // Lấy 3 ký tự cuối
                    }
                    row.createCell(cellNum++).setCellValue(prizeText);
                }
            }

            // Lùi ngày thêm 1 ngày
            endDate = endDate.minusDays(1);
        }

        // Gán giá trị B_i = E_{i-1}, bắt đầu từ hàng thứ 3 (i = 2)
        for (int i = 2; i <= sheet.getLastRowNum(); i++) {
            Row currentRow = sheet.getRow(i);
            Row previousRow = sheet.getRow(i - 1);

            // Kiểm tra nếu hàng trước đó tồn tại và ô D tại hàng i-1 có giá trị
            if (previousRow != null && previousRow.getCell(4) != null) {
                Cell cellBi = currentRow.createCell(1); // Ô B của hàng i
                cellBi.setCellValue(previousRow.getCell(4).getStringCellValue()); // Gán giá trị từ D_{i-1}
            }
        }

//        // Duyệt qua các hàng, bắt đầu từ hàng thứ 3 (i = 2)
//        for (int i = 2; i <= sheet.getLastRowNum(); i++) {
//            Row currentRow = sheet.getRow(i);
//            Cell cellB = currentRow.getCell(1);  // Ô B của hàng i
//            Cell cellC = currentRow.createCell(2);  // Ô C của hàng i
//
//            // Kiểm tra nếu ô B có giá trị
//            if (cellB != null && cellB.getCellType() == CellType.STRING) {
//                String valueB = cellB.getStringCellValue();  // Lấy giá trị của ô B
//
//                // Biến để theo dõi nếu tìm thấy giá trị B trong hàng
//                boolean found = false;
//
//                // Kiểm tra từ cột D đến cột cuối cùng của hàng hiện tại
//                for (int j = 3; j < currentRow.getLastCellNum(); j++) {
//                    Cell cell = currentRow.getCell(j);
//                    if (cell != null && cell.getCellType() == CellType.STRING) {
//                        String cellValue = cell.getStringCellValue();
//
//                        // So sánh giá trị với ô B
//                        if (valueB.equals(cellValue)) {
//                            found = true;
//                            break;
//                        }
//                    }
//                }
//
//                // Gán giá trị cho ô C: dấu `.` nếu tìm thấy, hoặc giá trị của ô B nếu không
//                if (found) {
//                    cellC.setCellValue(".");
//                } else {
//                    cellC.setCellValue(valueB);
//                }
//            }
//        }

        // Thêm vòng lặp từ ô B2 để kiểm tra và tìm mức
        for (int i = 2; i <= sheet.getLastRowNum(); i++) {
            Row currentRow = sheet.getRow(i);
            Cell cellB = currentRow.getCell(1);

            if (cellB != null && cellB.getCellType() == CellType.STRING) {
                String valueB = cellB.getStringCellValue();

                // Truy cập trang web và dán giá trị vào textarea
                driver.get("https://xs.olawin.com/");
                List<String> rowValues = new ArrayList<>();
                for (int j = 4; j < currentRow.getLastCellNum(); j++) {
                    rowValues.add(currentRow.getCell(j).getStringCellValue());
                }

                // Ghép dữ liệu thành chuỗi và dán vào textarea
                String joinedData = String.join(",", rowValues);
                WebElement inputArea = driver.findElement(By.xpath("//textarea[@id='tms_txt_input']"));
                inputArea.clear();
                inputArea.sendKeys(joinedData);
                driver.findElement(By.xpath("//input[@id='tms_btn_tms_3d']")).click();
                sleepInSecond(2);

                WebElement outputArea = driver.findElement(By.xpath("//textarea[@id='tms_txt_output']"));

                String outputText = outputArea.getAttribute("value");

                String[] outputLines = outputText.split("\\R"); // Tách output theo dòng
                int level = -1;
                boolean found = false;

                for (String line : outputLines) {
                    line = line.trim(); // Loại bỏ khoảng trắng hai đầu

                    // Nếu dòng bắt đầu với "Mức:", cập nhật level hiện tại
                    if (line.startsWith("Mức:")) {
                        // Tách phần trước dấu "(" và loại bỏ khoảng trắng
                        String levelString = line.split(":")[1].split("\\(")[0].replaceAll("\\s+", "");
                        level = Integer.parseInt(levelString); // Chuyển đổi thành số nguyên
                    }
                    // Nếu dòng không phải là "Mức:", kiểm tra xem có chứa `valueB` không
                    else if (level != -1 && line.contains(valueB)) {
                        currentRow.createCell(3).setCellValue(level);
                        found = true;
                        break;
                    }
                }


                // Nếu không tìm thấy, in ra thông báo
                if (!found) {
                    System.out.println(valueB + " not found in any level");
                }

            }
        }

        // Ghi workbook vào file
        try (FileOutputStream fileOut = new FileOutputStream("data.xlsx")) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo đóng workbook để giải phóng file
            workbook.close();
            driver.quit();
        }
    }

    @Test
    public void TC_04_Editable() {
        driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");

        enterAndSelectItemInCombo("input.search", "div[role='listbox']", "Bangladesh");

    }

    public void sleepInSecond(long timeInSecond) {
        try {
            Thread.sleep(1000 * timeInSecond); // Ngủ 1 giây
        } catch (InterruptedException e) {
            // Xử lý hoặc log exception ở đây
            Thread.currentThread().interrupt(); // Bảo vệ lại trạng thái interrupt
        }
    }

    public void selectItemInDropdown(String parentCss, String allItemsCss, String expectItemValue) {
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

    public void enterAndSelectItemInCombo(String comboCss, String allItemsCss, String expectItemValue) {
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

package webdriver;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.Color;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class Topic_10_Button_Radio_Checkbox {
    WebDriver driver;
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

        if (!driver.findElement(By.id("engine3")).isSelected()){
            driver.findElement(By.id("engine3")).click();
            System.out.println("có phải chọn lại");
        }


    }

    @Test
    public void TC_03_Button() {
        driver.get("https://material.angular.io/components/radio/examples");

        driver.findElement(By.xpath("//input[@id='mat-radio-4-input']")).click();

        if (!driver.findElement(By.xpath("//input[@id='mat-radio-4-input']")).isSelected()){
            driver.findElement(By.xpath("//input[@id='mat-radio-4-input']")).click();
            System.out.println("có phải chọn lại");
        }

        driver.get("https://material.angular.io/components/checkbox/examples");

        driver.findElement(By.xpath("//input[@id='mat-mdc-checkbox-1-input']")).click();
        driver.findElement(By.xpath("//input[@id='mat-mdc-checkbox-2-input']")).click();

        if (driver.findElement(By.xpath("//input[@id='mat-mdc-checkbox-1-input']")).isSelected()&&driver.findElement(By.xpath("//input[@id='mat-mdc-checkbox-2-input']")).isSelected()){
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

        for (WebElement checkbox : allCheckboxes){
            if (!checkbox.isSelected())
            checkbox.click();
        }

        //verify tất cả đc chọn

        for (WebElement checkbox : allCheckboxes){
            Assert.assertTrue(checkbox.isSelected());
        }

        driver.manage().deleteAllCookies();
        driver.navigate().refresh();

        // chọn checkbox có text là Heart Attack

        allCheckboxes = driver.findElements(By.cssSelector("div.form-single-column input[type='checkbox']"));

        for (WebElement checkbox : allCheckboxes){
            if (checkbox.getAttribute("value").equals("Heart Attack")&&!checkbox.isSelected()){
                checkbox.click();
            }
        }

        for (WebElement checkbox : allCheckboxes){
            if (checkbox.getAttribute("value").equals("Heart Attack")){
                Assert.assertTrue(checkbox.isSelected());
            } else {
                Assert.assertFalse(checkbox.isSelected());
            }
        }


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
        String username = "tinhcop97";
        String password = "18111997";
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
        String[][] daiVaSheet = {
                {"VIET_NAM_22H_00", "22H_00"},
                {"VIET_NAM_24H_00", "24H_00"},
                {"VIET_NAM_01H30", "01H30"},
                {"VIET_NAM_3H_00", "3H_00"},
                {"VIET_NAM_04H30", "04H30"},
                {"VIET_NAM_06H00", "06H00"},
                {"VIET_NAM_7H_30", "7H_30"},
                {"VIET_NAM_9H_00", "9H_00"},
                {"VIET_NAM_10H50", "10H50"},
                {"VIET_NAM_12H_00", "12H_00"},
                {"VIET_NAM_14H_00", "14H_00"},
                {"VIET_NAM_16H_30", "16H_30"},
                {"VIET_NAM_17H_30", "17H_30"},
                {"VIET_NAM_19H_30", "19H_30"},
                {"THANG_LONG_VIP_19H15", "TLVIP"},
                {"HA_NOI_20H_15", "HN"},
                {"MIEN_BAC", "MB"}
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
                String giai3D = giaiDB.substring(2,4);
                String giai4D = giaiDB.substring(1,3);
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
    public void TC_checkTanSuat4D(){
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

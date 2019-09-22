package mvnpackoch;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;

public class DoAm {

    WebDriver demo;
    XSSFWorkbook xwb;
    XSSFSheet sheet;
    ExtentReports xr;
    ExtentTest logs;

    @BeforeTest
    public void tree(){
        String extentrep=System.getProperty("user.dir")+"\\src\\treeing.html";

        xr = new ExtentReports(extentrep);
        logs = xr.startTest("Starting the test");

        System.setProperty("webdriver.chrome.driver","C:\\Users\\Proxes\\Downloads\\JAVABuildTools\\chromedriver_win32\\chromedriver_win32\\chromedriver.exe");
        demo = new ChromeDriver();
        demo.manage().window().maximize();
        logs.log(LogStatus.INFO,"Chrome browser launched");
        demo.get("http://newtours.demoaut.com/mercurywelcome.php");
        logs.log(LogStatus.INFO,"Navigated to demoaut site");
        demo.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        System.out.println("another 30 sec timeout");


    }

    @Test
    public void trees() throws Exception{



        FileInputStream fis = new FileInputStream("C:\\ExcelData\\DDTestData.xlsx");
        xwb = new XSSFWorkbook(fis);
        sheet=xwb.getSheet("Sheet1");

        int rownum=sheet.getLastRowNum();

        for (int i=0;i<rownum;i++){

            XSSFRow currentRow=sheet.getRow(i);

            String one=currentRow.getCell(0).getStringCellValue();
            String two=currentRow.getCell(1).getStringCellValue();

            demo.findElement(By.name("userName")).sendKeys(one);
            demo.findElement(By.name("password")).sendKeys(two);
            demo.findElement(By.name("login")).click();
            Thread.sleep(1500);

            String title = demo.getTitle();

            if (title.equals("Sign-on: Mercury Tours")){
                System.out.println("Invalid credentials");
                logs.log(LogStatus.FAIL,"Invalid credentials");
            } else {
                System.out.println("Successful entry");
                logs.log(LogStatus.PASS, "Successful Entry");
            }

            demo.navigate().back();
            demo.findElement(By.name("userName")).clear();
            demo.findElement(By.name("password")).clear();
            Thread.sleep(1000);
        }
            logs.log(LogStatus.INFO, "End of testing");
            xr.flush();
            xr.endTest(logs);
            xr.close();
            demo.quit();
    }
}

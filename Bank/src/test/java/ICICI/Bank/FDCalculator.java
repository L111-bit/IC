package ICICI.Bank;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FDCalculator {

	WebDriver driver;
	String URL = "https://www.icicibank.com/calculator/fd-calculator.page";
	String tenuredays = "1000";
	String MaturityValue = "";
	String AggregateInterest = "";
	String MaturityValue2 = "";
	String AggregateInterest2 = "";
	String DepositAmount = "60000";
	
	
	@BeforeTest()
	public void login() {
		
		WebDriverManager.chromedriver().setup();
		
		driver = new ChromeDriver();
		
		driver.get(URL);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		
	}
	
	@Test(priority =1)
	public void FDCal() throws InterruptedException {
		
		driver.switchTo().frame(driver.findElement(By.id("paymframe")));

        WebElement TFD= driver.findElement(By.xpath("//*[@id='ddlTypeOfFixedDeposit']"));

        Select drop1 = new Select(TFD);

        drop1.selectByVisibleText("Quarterly Payout");
        
	}
		
        //---------------Setting Deposit Amount-------------
        
        @Test(priority =2)
        public void calculation() throws InterruptedException, IOException {
        
        Thread.sleep(1000);
        WebElement ele1= driver.findElement(By.xpath("//input[@id='loanAmount']"));

        ele1.clear();
        ele1.sendKeys(DepositAmount);
		
        //------------------getting MaturityValue and Aggregate Interest amount-----------------
        
        WebElement ele2= driver.findElement(By.xpath("//*[@id='spnMaturityValue']"));

        ele2.click();
        MaturityValue = ele2.getText();
        System.out.println("Maturity value is: "+MaturityValue);
        
        
        WebElement ele3= driver.findElement(By.xpath("//*[@id='spnAIAmount']"));

        ele3.click();
        AggregateInterest = ele3.getText();
        System.out.println("Aggregate Interest Amount is: "+AggregateInterest);
        
        
        //-----------     Selecting Days only radio button -----------
        Thread.sleep(2000);
        WebElement DaysOnly= driver.findElement(By.xpath("//input[@id='idDays']"));

        DaysOnly.click();
        
        // --------------  Setting tenureDays value ------------
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement ele4= driver.findElement(By.xpath("//input[@id='tenureDay']"));
       
//        ele4.clear();
        js.executeScript("document.getElementById('tenureDay').value='1000'");
        ele4.sendKeys("tenuredays");
        ele4.sendKeys(Keys.ENTER);
        
        // -------------   Getting Maturity & Aggregate Intrest for Tenure Days --------
        
        WebElement ele5= driver.findElement(By.xpath("//*[@id='spnMaturityValue']"));

        ele5.click();
        MaturityValue2 = ele5.getText();
        System.out.println("Maturity value for "+ tenuredays +" is : "+ MaturityValue2);
        
        
        WebElement ele6= driver.findElement(By.xpath("//*[@id='spnAIAmount']"));

        ele6.click();
        AggregateInterest2 = ele6.getText();
        System.out.println("Aggregate Interest Amount for "+ tenuredays +" is : "+ AggregateInterest2);
        
       
        Thread.sleep(2000);
        File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    	FileUtils.copyFile(src, new File("./Screenshot1/Cal.png"));
        
	}
        @Test(priority =3)
        public void takeScreenshot() throws InterruptedException, IOException {
        	//JavascriptExecutor js = (JavascriptExecutor) driver;
        	driver.navigate().refresh();
        	WebElement screen = driver.findElement(By.xpath("//img[@alt='Logo']"));
        	//js.executeScript("arguments[0].scrollIntoView();", screen);
        	//js.executeScript("window.scrollBy(0,-350)", "");
        	screen.click();
        	//Thread.sleep(2000);
        	
        	File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        	FileUtils.copyFile(src, new File("./Screenshot2/Home.png"));
        	
        }
	
	@AfterTest()
	public void quit() throws InterruptedException{
		Thread.sleep(4000);
		System.out.println("====== Execution completed ==========");
		driver.quit();
	}
	
}

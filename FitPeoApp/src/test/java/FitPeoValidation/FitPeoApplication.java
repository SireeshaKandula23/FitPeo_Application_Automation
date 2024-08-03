package FitPeoValidation;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class FitPeoApplication {
    @Test
	public void TestFitPeoApplication() throws InterruptedException {
		ChromeDriver driver=new ChromeDriver();
		
		//Navigating to the FitPeo Homepage
		driver.get("https://www.fitpeo.com/");
		driver.manage().window().maximize();	
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		//Navigating to the Revenue Calculator Page
		WebElement calculator=driver.findElement(By.xpath("//div[contains(text(),'Revenue Calculator')]/parent::a"));
		calculator.click();
        
		//Scrolling Down to the Slider section
		JavascriptExecutor js=(JavascriptExecutor)driver;
		WebElement slidersection=driver.findElement(By.xpath("//h4[text()='Medicare Eligible Patients']"));
		js.executeScript("arguments[0].scrollIntoView(true);", slidersection);
		
		//Adjusting the Slider(Tried to adjust the value of 820 but unable to find the offset value of 820,so proceeding with possible nearest value)
    	Actions builder = new Actions(driver);
 		WebElement sliderinput=driver.findElement(By.xpath("//span[contains(@class,'MuiSlider-thumb')]//input[@type='range']"));
		WebElement Inputbox=driver.findElement(By.xpath("//input[contains(@class,'MuiInputBase-input')]"));
   		builder.clickAndHold(sliderinput).moveByOffset(94,0).release().perform();
		Assert.assertEquals("823", Inputbox.getAttribute("value"));
		
		//Updating the Text Field with 560
		Inputbox.click();
        Inputbox.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);	
		Inputbox.sendKeys("560");
		Thread.sleep(5000);
		
		//Validating the Slider Value
		Assert.assertEquals("560", sliderinput.getAttribute("value"));
		
		//Updating the text field with 820 to get Total Recurring Reimbursement
		Inputbox.click();
        Inputbox.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);	
		Inputbox.sendKeys("820");
		Thread.sleep(5000);
		
		//Selecting the CPT Codes
		WebElement checkbox1=driver.findElement(By.xpath(SelectCheckBox("CPT-99091")));
		js.executeScript("window.scrollTo(0,650);");
		Thread.sleep(3000);
		checkbox1.click();
		WebElement checkbox2=driver.findElement(By.xpath(SelectCheckBox("CPT-99453")));
		checkbox2.click();
		WebElement checkbox3=driver.findElement(By.xpath(SelectCheckBox("CPT-99454")));
		checkbox3.click();
		WebElement checkbox4=driver.findElement(By.xpath(SelectCheckBox("CPT-99474")));
		js.executeScript("window.scrollTo(0,750);");
		Thread.sleep(2000);
		checkbox4.click();
		
		//Validating Total Recurring Reimbursement Heading
		WebElement header=driver.findElement(By.xpath("//p[@class='MuiTypography-root MuiTypography-body2 inter css-1xroguk' and contains(text(),'Total Recurring Reimbursement ')]"));
		Assert.assertTrue(header.isDisplayed());
		
		//Verify that the header displaying Total Recurring Reimbursement for all Patients Per Month: shows the value $110700
		WebElement headervalue=driver.findElement(By.xpath("//p[@class='MuiTypography-root MuiTypography-body2 inter css-1xroguk' and contains(text(),'Total Recurring Reimbursement ')]/p"));
		Assert.assertEquals(headervalue.getText(),"$110700");
		
		driver.quit();
		
	}
	
	//To capture the dynamic xpath of CPT Codes
	public static String SelectCheckBox(String data) {		
		return "//p[text()='"+data+"']/following-sibling::label";	

	}

}

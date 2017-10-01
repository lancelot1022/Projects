package altoroTest1;

import org.testng.asserts.SoftAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class Test2 {
	
	public WebDriver driver;
	
	@BeforeTest
	public void browserSetup() {
		
		String chromePath = "C:\\\\Program Files\\\\chromedriver_win32\\\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", chromePath);
		driver = new ChromeDriver();
		String altoroURL = "http://demo.testfire.net/";
		driver.get(altoroURL);
		
	}
	
	public void altoroLogin(String username, String password)
	{
		WebElement usernameTextbox = driver.findElement(By.xpath(".//*[@id='uid']"));
        WebElement passwordTextbox = driver.findElement(By.xpath(".//*[@id='passw']"));
        WebElement loginButton = driver.findElement(By.xpath(".//*[@id='login']/table/tbody/tr[3]/td[2]/input"));

        driver.findElement(By.xpath(".//*[@id='uid']")).clear();
        driver.findElement(By.xpath(".//*[@id='passw']")).clear();
        usernameTextbox.sendKeys(username);
        passwordTextbox.sendKeys(password);
        loginButton.click();
        
       	
	}
	
	@Test
	public void testLogin1()
	{
		
		driver.findElement(By.xpath(".//*[@id='_ctl0__ctl0_LoginLink']")).click();
	//	System.out.println("[Test Scenario 1]\n[Description]: Testing invalid credentials");
		altoroLogin("invalid123", "1238@ab");
		if(driver.findElement(By.xpath(".//*[@id='_ctl0__ctl0_Content_Main_message']")).isDisplayed())
		{
			System.out.println("PASSED - Unable to login user with invalid credentials\n");
			Assert.assertTrue(true);
		} else {
			System.out.println("FAILED - Invalid credentials allowed user to login\n");
			Assert.assertTrue(false);
		}
		driver.quit();
	}

}

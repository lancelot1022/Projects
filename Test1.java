package altoroTest1;

import org.testng.asserts.SoftAssert;
import org.testng.annotations.BeforeTest;
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

public class Test1 {

	@BeforeTest
	public static void chromeSetup()
	{

		String chromePath = "C:\\\\Program Files\\\\chromedriver_win32\\\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", chromePath);
		
		PrintStream out = null;
		try {
			out = new PrintStream(new FileOutputStream("TestResult.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.setOut(out); 
		
	}
	
	public SoftAssert softAssert = new SoftAssert();
	public WebDriver driver = new ChromeDriver();
	
	@Test
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
	
	public void altoroAddUser(String addFname, String addLname, String addUsername, String addPassword, String confirmPassword)
	{
		WebElement addFNameTextbox = driver.findElement(By.xpath(".//*[@id='wrapper']/table/tbody/tr[2]/td[2]/div/table/tbody/tr[9]/td[1]/input[1]"));
		WebElement addLNameTextbox = driver.findElement(By.xpath(".//*[@id='wrapper']/table/tbody/tr[2]/td[2]/div/table/tbody/tr[9]/td[1]/input[2]"));
		WebElement addUserNameTextbox = driver.findElement(By.xpath(".//*[@id='wrapper']/table/tbody/tr[2]/td[2]/div/table/tbody/tr[9]/td[2]/input"));
		WebElement addPasswordTextbox = driver.findElement(By.xpath(".//*[@id='wrapper']/table/tbody/tr[2]/td[2]/div/table/tbody/tr[9]/td[3]/input[1]"));
		WebElement confirmPasswordTextbox = driver.findElement(By.xpath(".//*[@id='wrapper']/table/tbody/tr[2]/td[2]/div/table/tbody/tr[9]/td[3]/input[2]"));
		WebElement addUserbutton = driver.findElement(By.xpath(".//*[@id='wrapper']/table/tbody/tr[2]/td[2]/div/table/tbody/tr[9]/td[4]/input"));
		
		addFNameTextbox.sendKeys(addFname);
		addLNameTextbox.sendKeys(addLname);
		addUserNameTextbox.sendKeys(addUsername);
		addPasswordTextbox.sendKeys(addPassword);
		confirmPasswordTextbox.sendKeys(confirmPassword);
		addUserbutton.click();
		
	}
	
	public void changePassword(String user, String newPass, String confirmPass)
	{
		WebElement newPassTextbox = driver.findElement(By.xpath(".//*[@id='wrapper']/table/tbody/tr[2]/td[2]/div/table/tbody/tr[6]/td[2]/input"));
		WebElement confirmPassTextbox = driver.findElement(By.xpath(".//*[@id='wrapper']/table/tbody/tr[2]/td[2]/div/table/tbody/tr[6]/td[3]/input"));
		WebElement chgPassbutton = driver.findElement(By.xpath(".//*[@id='wrapper']/table/tbody/tr[2]/td[2]/div/table/tbody/tr[6]/td[4]/input"));
		
		
		Select userDropdown = new Select(driver.findElement(By.xpath(".//*[@id='wrapper']/table/tbody/tr[2]/td[2]/div/table/tbody/tr[6]/td[1]/select")));
		userDropdown.selectByVisibleText(user);	
		newPassTextbox.sendKeys(newPass);
		confirmPassTextbox.sendKeys(confirmPass);
		chgPassbutton.click();
	}
	
	public boolean altoroPopup()
	{
	    try {
	        driver.switchTo().alert();
	        return true;
	    }
	    catch (Exception e) {
	        return false;
	    } 
	}
	
	@Test
	public void testLogin1()
	{
		System.out.println("[Test Scenario 1]\n[Description]: Testing invalid credentials");
		altoroLogin("invalid123", "1238@ab");
		if(driver.findElement(By.xpath(".//*[@id='_ctl0__ctl0_Content_Main_message']")).isDisplayed())
		{
			System.out.println("PASSED - Unable to login user with invalid credentials\n");
		} else {
			System.out.println("FAILED - Invalid credentials allowed user to login\n");
		}
	}
	
	@Test
	public void testLogin2()
	{
		System.out.println("[Test Scenario 2]\n[Description]: Testing valid username with special characters");
		altoroLogin("tuser'--", "tuser");
		
		if(driver.findElement(By.xpath(".//*[@id='wrapper']/table/tbody/tr[2]/td[2]/div/h1")).isDisplayed())
		{
			System.out.println("FAILED - Username with special characters is able to login\n");
			driver.findElement(By.xpath(".//*[@id='_ctl0__ctl0_LoginLink']")).click();
			
		} else if(driver.findElement(By.xpath(".//*[@id='_ctl0__ctl0_Content_Main_message']")).isDisplayed())
		{
			System.out.println("PASSED - Invalid credentials will not login user\n");
		} else {
			System.out.println("FAILED - Unable to login but error message not displayed\n");
		}
	}
	
	@Test
	public void testLogin3() {
		System.out.println("[Test Scenario 3]\n[Description]: Testing valid username and password");
		driver.findElement(By.xpath(".//*[@id='_ctl0__ctl0_LoginLink']")).click();
		altoroLogin("admin", "admin");
		
		if(driver.findElement(By.xpath(".//*[@id='wrapper']/table/tbody/tr[2]/td[2]/div/h1")).isDisplayed())
		{
			System.out.println("PASSED - User with valid credential is able to login\n");		
		}else {
			System.out.println("FAILED - Unable to login user with valid credential\n");
		}
	}
	
	@Test
	public void testAddUser1()
	{
		System.out.println("[Test Scenario 4]\n[Description]: Testing Add User functionality - Valid Credentials");
		driver.findElement(By.xpath(".//*[@id='_ctl0__ctl0_Content_Administration']/ul/li[2]/a")).click();
		altoroAddUser("Peter", "Parker", "spiderman007", "spider123", "spider123");
	
		String[] expectedUser = {"spiderman007"};
		WebElement userDropdown = driver.findElement(By.xpath(".//*[@id='wrapper']/table/tbody/tr[2]/td[2]/div/table/tbody/tr[3]/td[1]/select"));
		Select select = new Select(userDropdown);
		List<WebElement> options = select.getOptions();
		int count = 0;
		for(WebElement we:options)  
		{  
			for (int i=0; i<expectedUser.length; i++){
				if (we.getText().equals(expectedUser[i]))
				{
					count++;
				} else {
				}
		    }
		 }
		if(count == 1)
		{
			System.out.println("PASSED - Created user is displayed in list of users\n");
		} else {
			System.out.println("FAILED - Created user is NOT on the list of users\n");
		}
	}
	
	@Test
	public void testAddUser2()
	{
		System.out.println("[Test Scenario 5]\n[Description]: Testing Add User functionality - Unmatched password");
		altoroAddUser("Peter", "Parker", "spiderman007", "spider123", "man123");
		
		if(altoroPopup())
		{
			System.out.println("PASSED - Alert is displayed");
			Alert PassAlert = driver.switchTo().alert();
			String PassAlertMsg = PassAlert.getText();
			
			if( PassAlertMsg == "Passwords do not match" )
			{
				System.out.println("PASSED - Alert message is correct");
			} else {
				System.out.println("FAILED - Alert message is not correct");
			}
			
			PassAlert.dismiss();
		} else {
			System.out.println("FAILED - No Alert displayed");
		}
		
		String[] expectedUser = {"spiderman007"};
		WebElement userDropdown = driver.findElement(By.xpath(".//*[@id='wrapper']/table/tbody/tr[2]/td[2]/div/table/tbody/tr[3]/td[1]/select"));
		Select select = new Select(userDropdown);
		List<WebElement> options = select.getOptions();
		int count = 0;
		for(WebElement we:options)  
		{  
			for (int i=0; i<expectedUser.length; i++){
				if (we.getText().equals(expectedUser[i]))
				{
					count++;
				} else {
				}
		    }
		 }
		if(count == 1)
		{
			System.out.println("FAILED - User with unmatched password is created\n");
		} else {
			System.out.println("PASSED - User with unmatched password is not created\n");
		}
	}
	
	@Test
	public void testAddUser3()
	{
		System.out.println("[Test Scenario 6]\n[Description]: Testing Add User functionality - Add duplicate user");
		altoroAddUser("Joe", "Smith", "100116013 sjoe", "spider123", "spider123");
		
		String[] expectedUser = {"100116013 sjoe"};
		WebElement userDropdown = driver.findElement(By.xpath(".//*[@id='wrapper']/table/tbody/tr[2]/td[2]/div/table/tbody/tr[3]/td[1]/select"));
		Select select = new Select(userDropdown);
		List<WebElement> options = select.getOptions();
		int count = 0;
		for(WebElement we:options)  
		{  
			for (int i=0; i<expectedUser.length; i++){
				if (we.getText().equals(expectedUser[i]))
				{
					count++;
				} else {
				}
		    }
		 }
		if(count > 1)
		{
			System.out.println("FAILED - Created a duplicate user\n");
		} else {
			System.out.println("PASSED - No duplicate user created\n");
		}
	}
	
	@Test
	public void testChangePassword1() {
		System.out.println("[Test Scenario 7]\n[Description]: Testing Change User Password - Valid Password");
		changePassword("2 tuser", "spidey123", "spidey123");
		System.out.println("PASSED - Successfully changed password");
		driver.findElement(By.xpath(".//*[@id='_ctl0__ctl0_LoginLink']")).click();
		driver.findElement(By.xpath(".//*[@id='_ctl0__ctl0_LoginLink']")).click();
		altoroLogin("2 tuser", "spidey123");
		if(driver.findElement(By.xpath(".//*[@id='_ctl0__ctl0_Content_Main_message']")).isDisplayed())
		{
			System.out.println("FAILED - Unable to login user with changed password\n");
		} else {
			System.out.println("PASSED - User with changed password is logged in\n");
		}
	}
	
	@Test
	public void testChangePassword2() {
		System.out.println("[Test Scenario 8]\n[Description]: Testing Change User Password - Unmatched Password");
		changePassword("2 tuser", "spidey123", "man123");
		
		if(altoroPopup())
		{
			System.out.println("PASSED - Alert is displayed");
			Alert PassAlert = driver.switchTo().alert();
			String PassAlertMsg = PassAlert.getText();
			
			if( PassAlertMsg == "Passwords do not match" )
			{
				System.out.println("PASSED - Alert message is correct");
			} else {
				System.out.println("FAILED - Alert message is not correct");
			}
			
			PassAlert.dismiss();
		} else {
			System.out.println("FAILED - No Alert displayed");
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		
		chromeSetup();
		Test1 alTest = new Test1();
		String altoroURL = "http://demo.testfire.net/";
		alTest.driver.get(altoroURL);
		String expectedTitle = "Altoro Mutual";
		
		//Verify Title of Homepage
		alTest.softAssert.assertTrue(false);
		alTest.softAssert.assertEquals(expectedTitle, alTest.driver.getTitle());
		alTest.driver.findElement(By.xpath(".//*[@id='_ctl0__ctl0_LoginLink']")).click();
		String loginURL = "http://demo.testfire.net/bank/login.aspx";
		String mainURL = "http://demo.testfire.net/bank/main.aspx";
		alTest.softAssert.assertEquals(loginURL, alTest.driver.getCurrentUrl());
		
		//Testing Login function
		alTest.testLogin1();
		alTest.testLogin2();
		alTest.testLogin3();
		
		//Testing administrator accessible functionalities
		alTest.softAssert.assertEquals(mainURL, alTest.driver.getCurrentUrl());
		//Test Add user function
		alTest.testAddUser1();
		alTest.testAddUser2();
		alTest.testAddUser3();
		
		//Test Change password function
		alTest.testChangePassword1();
		alTest.altoroLogin("admin", "admin");
		alTest.driver.findElement(By.xpath(".//*[@id='_ctl0__ctl0_Content_Administration']/ul/li[2]/a")).click();
		alTest.testChangePassword2();
		
		alTest.driver.close();
		alTest.softAssert.assertAll();
		
		
	} 

}

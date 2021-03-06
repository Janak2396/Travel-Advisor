import static org.junit.Assert.*;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class ManageAirports {

	public static ChromeDriver driver;
	public static WebElement email, password, submit;
	
	
	@Before
	public void beforeAll() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "/Users/rajnish/Documents/Codes/test/chromedriver102");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://travel-advisor-self.vercel.app/signin");
		Thread.sleep(3000);

	}
	
	@Test
	// Trying to access manage-source/admin page without login
	public void test1() throws InterruptedException {
		driver.navigate().to("https://travel-advisor-self.vercel.app/manage-sources");
		Thread.sleep(2000);
		String currentUrl = driver.getCurrentUrl();
		Assert.assertEquals(currentUrl, "https://travel-advisor-self.vercel.app/");
	}
	
	@Test
	// Trying to access manage-source/admin page with standard user login  
	public void test2() throws InterruptedException {
		driver.navigate().to("https://travel-advisor-self.vercel.app/signin");
		Thread.sleep(2000);
		email = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[2]/div/div[2]/div[1]/input"));
		password = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[2]/div/div[2]/div[2]/input"));
		submit = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[2]/div/div[2]/button"));

		email.sendKeys("testuser4@gmail.com");
		password.sendKeys("test123");
		submit.click();
		Thread.sleep(2000);
		driver.navigate().to("https://travel-advisor-self.vercel.app/manage-sources");
		Thread.sleep(2000);
		String currentUrl = driver.getCurrentUrl();
		Assert.assertEquals(currentUrl, "https://travel-advisor-self.vercel.app/");
	}
	
	
	@Test
	// Validated if, add new button is visible
	public void test3() throws InterruptedException {
		signIn();
		driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[2]/div[1]/button")).click();
		Thread.sleep(2000);
		WebElement inputBtn = driver.findElement(By.id("code-input"));
		Assert.assertTrue(inputBtn.isDisplayed());
	}
	
	
	@Test
	// Trying to add without entering any input
	public void test4() throws InterruptedException {
		signIn();
		driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[2]/div[1]/button")).click();
		Thread.sleep(2000);
		WebElement checkBtn = driver.findElement(By.id("check-input"));
		checkBtn.click();
		checkMessage("Please enter all required fields!!");
	}
	
	@Test
	// Trying to add without entering any input except code
	public void test5() throws InterruptedException {
		signIn();
		driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[2]/div[1]/button")).click();
		Thread.sleep(2000);
		
		WebElement codeInput = driver.findElement(By.id("code-input"));
		codeInput.sendKeys("YUL");
		driver.findElement(By.id("check-input")).click();
		checkMessage("Please enter all required fields!!");
	}
	
	@Test
	// Trying to add without entering any input except code, name
	public void test6() throws InterruptedException {
		signIn();
		driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[2]/div[1]/button")).click();
		Thread.sleep(2000);
		
		WebElement codeInput = driver.findElement(By.id("code-input"));
		codeInput.sendKeys("YUL");
		WebElement nameInput = driver.findElement(By.id("name-input"));
		nameInput.sendKeys("Trudeau International Airport");
		driver.findElement(By.id("check-input")).click();
		checkMessage("Please enter all required fields!!");
	}
	
	@Test
	// Trying to add without entering country code
	public void test7() throws InterruptedException {
		signIn();
		driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[2]/div[1]/button")).click();
		Thread.sleep(2000);
		
		WebElement codeInput = driver.findElement(By.id("code-input"));
		codeInput.sendKeys("YUL");
		WebElement nameInput = driver.findElement(By.id("name-input"));
		nameInput.sendKeys("Trudeau International Airport");
		WebElement countryInput = driver.findElement(By.id("country-input"));
		countryInput.sendKeys("Canada");
		driver.findElement(By.id("check-input")).click();
		checkMessage("Please enter all required fields!!");
	}
	
	
	@Test
	// Trying to add with same airport-code again
	public void test8() throws InterruptedException {
		signIn();
		driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[2]/div[1]/button")).click();
		Thread.sleep(2000);
		
		WebElement codeInput = driver.findElement(By.id("code-input"));
		codeInput.sendKeys("YUL");
		WebElement nameInput = driver.findElement(By.id("name-input"));
		nameInput.sendKeys("Trudeau International Airport");
		WebElement countryInput = driver.findElement(By.id("country-input"));
		countryInput.sendKeys("Canada");
		WebElement countryCInput = driver.findElement(By.id("countryCode-input"));
		countryCInput.sendKeys("CA");
		driver.findElement(By.id("check-input")).click();
		checkMessage("Airport code is already added before!!");
	}
	
	@Test
	// Trying to add with invalid country-code
	public void test9() throws InterruptedException {
		signIn();
		driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[2]/div[1]/button")).click();
		Thread.sleep(2000);
		
		WebElement codeInput = driver.findElement(By.id("code-input"));
		codeInput.sendKeys("YAB");
		WebElement nameInput = driver.findElement(By.id("name-input"));
		nameInput.sendKeys("Trudeau International Airport");
		WebElement countryInput = driver.findElement(By.id("country-input"));
		countryInput.sendKeys("Canada");
		WebElement countryCInput = driver.findElement(By.id("countryCode-input"));
		countryCInput.sendKeys("CAD");
		driver.findElement(By.id("check-input")).click();
		Thread.sleep(1000);
		checkMessage("Country code should be in only 2 digits!!");
	}

	@Test
	// Success Scenario
	public void test10() throws InterruptedException {
		signIn();
		driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[2]/div[1]/button")).click();
		Thread.sleep(2000);
		
		WebElement codeInput = driver.findElement(By.id("code-input"));
		codeInput.sendKeys("YAB");
		WebElement nameInput = driver.findElement(By.id("name-input"));
		nameInput.sendKeys("Trudeau International Airport");
		WebElement countryInput = driver.findElement(By.id("country-input"));
		countryInput.sendKeys("Canada");
		WebElement countryCInput = driver.findElement(By.id("countryCode-input"));
		countryCInput.sendKeys("CA");
		driver.findElement(By.id("check-input")).click();
		Thread.sleep(1000);
		Assert.assertEquals(driver.findElements(By.xpath("//*[@id=\"alert-message-fm\"]")).size(), 0);
	}
	
	
	@Test
	// Is Edit button clickable
	public void test11() throws InterruptedException {
		signIn();
		Thread.sleep(2000);
		WebElement editBtn = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[2]/div[2]/div[2]/div[5]/div[1]"));
		editBtn.click();
		Assert.assertEquals(driver.findElements(By.xpath("//*[@id=\"alert-message-fm\"]")).size(), 0);
	}
	
	@Test
	// Trying to update airport-code with same value as any other already added row 
	public void test12() throws InterruptedException {
		signIn();
		Thread.sleep(2000);
		WebElement editBtn = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[2]/div[2]/div[2]/div[5]/div[1]"));
		editBtn.click();
		WebElement codeInput = driver.findElement(By.id("code-input"));
		codeInput.clear();
		codeInput.sendKeys("");
		codeInput.sendKeys("AMD");
		driver.findElement(By.id("check-input")).click();
		checkMessage("Airport code is already added before!!");
	}
	
	@Test
	// Success edit scenario
	public void test13() throws InterruptedException {
		signIn();
		Thread.sleep(5000);
		WebElement editBtn = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[2]/div[2]/div[2]/div[5]/div[1]"));
		editBtn.click();
		driver.findElement(By.id("close-input")).click();
		Assert.assertEquals(driver.findElements(By.xpath("//*[@id=\"alert-message-fm\"]")).size(), 0);
	}
	
//	@Test
	// delete button click
//	public void test14() throws InterruptedException {
//		signIn();
//		Thread.sleep(5000);
//		WebElement delete = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[2]/div[2]/div[2]/div[5]/div[2]"));
//		delete.click();
//		Assert.assertEquals(driver.findElements(By.xpath("//*[@id=\"alert-message-fm\"]")).size(), 0);
//	}
	
	
	public void signIn() throws InterruptedException {
		email = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[2]/div/div[2]/div[1]/input"));
		password = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[2]/div/div[2]/div[2]/input"));
		submit = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[2]/div/div[2]/button"));

		email.sendKeys("rajnishkatharotiya.rk.rk@gmail.com");
		password.sendKeys("test123");
		submit.click();

		Thread.sleep(1000);
		driver.navigate().to("https://travel-advisor-self.vercel.app/manage-sources");
		Thread.sleep(3000);
	}
	
	
	public void checkMessage(String message) throws InterruptedException {
		Thread.sleep(2000);
		WebElement msgEle = driver.findElement(By.xpath("//*[@id=\"alert-message-fm\"]"));
		String msgText = msgEle.getText();
		System.out.print(msgText);
		Assert.assertEquals(message, msgText);
	}
	
	@After
	public void afterClass() {
		driver.close();
	}

}

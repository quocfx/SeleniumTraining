package seleniumtraining.pages.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

public class Lab01Test {
	
	@Test
	public void testGoogle() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver",
				"./src/test/resources/driver/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		WebDriver driver = new ChromeDriver(options);
		driver.get("https://www.google.com/");
		Thread.sleep(3000);
	}

}

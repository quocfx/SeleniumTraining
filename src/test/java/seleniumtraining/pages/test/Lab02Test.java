package seleniumtraining.pages.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

import seleniumtraining.pages.ITJobHomePage;
import seleniumtraining.pages.ITZoneHomePage;

public class Lab02Test {
	private WebDriver driver;
	
	private String testMode = "localhost";
	private String browserType = "chrome";
	private String startURL = "https://itzone.com.vn/vi/";
	private String remoteURL = "http://10.4.1.38:4444/wd/hub";
	
	@BeforeClass
	public void initializeTest() throws IOException {
		// For local driver
		if (testMode.equals("localhost")) {
			if (browserType.equals("chrome")) {
				System.setProperty("webdriver.chrome.driver",
						"./src/test/resources/driver/chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				driver = new ChromeDriver(options);
				driver.manage().window().maximize();
			} else if (browserType.equals("firefox")) {
				System.setProperty("webdriver.gecko.driver","./src/test/resources/driver/geckodriver.exe");
				FirefoxOptions options = new FirefoxOptions();				
				driver = new FirefoxDriver(options);
				driver.manage().window().maximize();
			} else if (browserType.equals("ie")) {
				System.setProperty("webdriver.ie.driver","./src/test/resources/driver/IEDriverServer.exe");
				InternetExplorerOptions options = new InternetExplorerOptions();
				driver = new InternetExplorerDriver(options);
				driver.manage().window().maximize();
			} else if (browserType.equals("edge")) {
				System.setProperty("webdriver.edge.driver","./src/test/resources/driver/MicrosoftWebDriver.exe");
				EdgeOptions options = new EdgeOptions();
				driver = new EdgeDriver(options);
				driver.manage().window().maximize();
			}
		} else {
			// For remote driver
			if (browserType.equals("chrome")) {
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--start-maximized");
				driver = new RemoteWebDriver(new URL(remoteURL), options);
			} else if (browserType.equals("firefox")) {
				FirefoxOptions options = new FirefoxOptions();
				driver = new RemoteWebDriver(new URL(remoteURL), options);
			}
		}
		//Set the conditions
//		ChromeDriver myDriver = (ChromeDriver) driver;
//		CommandExecutor executor = myDriver.getCommandExecutor();
//		Map map = new HashMap();
//		map.put("offline", false);
//		map.put("latency", 5);
//		map.put("download_throughput", 5000);
//		map.put("upload_throughput", 5000);
//
//		Response response = executor.
//				execute(new Command(myDriver.getSessionId(),"setNetworkConditions", ImmutableMap.of("network_conditions", ImmutableMap.copyOf(map))));
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
//		@SuppressWarnings("deprecation")
//		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)							
//				.withTimeout(30, TimeUnit.SECONDS) 			
//				.pollingEvery(5, TimeUnit.SECONDS) 			
//				.ignoring(NoSuchElementException.class);	
	}
	
	@Test
	public void testWaitUntil() throws InterruptedException {
		System.out.println("Step 01: Start with the URL: https://itzone.com.vn/vi/");
		driver.get(startURL);
//		Thread.sleep(5000);
		
		System.out.println("Step 02: Click on 'IT JOBS' link on the top to go itjobs site");
		ITZoneHomePage itZoneHomePage = new ITZoneHomePage(driver);
		itZoneHomePage.gotoITJobsPage();
		
		System.out.println("Step 03: Click on 'English' link/icon to switch site language to English");
		ITJobHomePage itJobHomePage = new ITJobHomePage(driver);
		itJobHomePage.switchENLanguage();
		
		System.out.println("Step 04: Input 'Selenium' on search text, select 'Experienced' on 'level' drop down, 'Ho Chi Minh' on 'city' drop down."
				+ " Then, click on 'Search' button");
		
//		Input 'Selenium' on search text
		itJobHomePage.inputSearchText("Selenium");
		
//		Select 'Experienced' on 'level' drop down
		itJobHomePage.selectLevelDropDown("Experienced");
		
//		Select 'Ho Chi Minh' on 'city' drop down.
		itJobHomePage.selectCitySelect("Ho Chi Minh");
		
//		Click on 'Search' button"
		itJobHomePage.clickSearchButton();
		
		System.out.println("Step 05: Verify the search result");
		List<WebElement> searchList = itJobHomePage.getSearchResults();
		assertTrue(searchList.size() > 0, "Search has no result");
		
//		Verify the first result
		WebElement searchResult = searchList.get(0);
//		Check to see if job title contains 'Selenium' keyword?
		WebElement titleElement = itJobHomePage.getTitleElementOnSearchResult(searchResult);
		boolean isTitleContainsKeyword = titleElement.getAttribute("innerHTML").indexOf("Selenium") >= 0;
		
//		Check to see if skills section contains 'Selenium' keyword?
		WebElement skillElement = itJobHomePage.getSkillElementOnSearchResult(searchResult);
		String skillInnerHTML = skillElement.getAttribute("innerHTML");
		boolean isSkillContainsKeyword = skillInnerHTML.indexOf("Selenium") >= 0;
		assertTrue(isSkillContainsKeyword || isTitleContainsKeyword, "Title or Skill section does not have 'Selenium' keyword");
		
//		Verify the search level
		WebElement levelValueElement = itJobHomePage.getLevelElementOnSearchResult(searchResult);
		String levelValue = levelValueElement.getAttribute("innerHTML");
		assertEquals(levelValue, "Experienced (Non-Manager)", "Level search does not match with level criteria");
		
//		Verify the location
		WebElement locationValueElement = itJobHomePage.getLocationElementOnSearchResult(searchResult);
		String locationValue = locationValueElement.getAttribute("innerHTML");
		System.out.println(locationValue);
		boolean isLocationContainKeyword = locationValue.indexOf("Ho Chi Minh") >= 0;
		assertTrue(isLocationContainKeyword, "Location does not contain keyword 'Ho Chi Minh', actual value is: " + locationValue);
	}
	
	@AfterClass
	public void closePage() throws IOException {
		Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
	}
}

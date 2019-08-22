package seleniumtraining.pages.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class ITZoneTest {
	private WebDriver driver;
	private String startURL = "https://itzone.com.vn/vi/";

	
	@Test
	public void testOpen() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver",
				"./src/test/resources/driver/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		driver = new ChromeDriver(options);
		System.out.println("Step 01: Start with the URL: https://itzone.com.vn/vi/");
		driver.get(startURL);
		Thread.sleep(3000);
		
		System.out.println("Step 02: Click on 'IT JOBS' link on the top to go itjobs site");
		WebElement itJobsLink = driver.findElement(By.linkText("IT JOBS"));
		itJobsLink.click();
		Thread.sleep(3000);
		
		System.out.println("Step 03: Click on 'English' link/icon to switch site language to English");
		WebElement enIcon = driver.findElement(By.xpath("//a[@href=\"/en\"]"));
		
		System.out.println("Step 04: Input 'Selenium' on search text, select 'Experienced' on 'level' drop down, 'Ho Chi Minh' on 'city' drop down."
				+ " Then, click on 'Search' button");
		enIcon.click();
//		Input 'Selenium' on search text
		WebElement searchText = driver.findElement(By.cssSelector(".form-control.searchJobKeyword"));
		searchText.sendKeys("Selenium");
		Thread.sleep(1000);
		
//		Select 'Experienced' on 'level' drop down
		WebElement levelDropdown = driver.findElement(By.id("FunctionalLevelKey"));
		levelDropdown.sendKeys("Experienced");
		Thread.sleep(1000);
		
//		Select 'Ho Chi Minh' on 'city' drop down.		
		Select citySelect = new Select(driver.findElement(By.id("CityId")));
		citySelect.selectByVisibleText("Ho Chi Minh");
		Thread.sleep(1000);
		
//		Click on 'Search' button"
		WebElement searchBtn = driver.findElement(By.xpath("//button[contains(text(),'Search')]"));
		searchBtn.click();
		Thread.sleep(3000);
		
		System.out.println("Step 05: Verify the search result");
		List<WebElement> searchList = driver.findElements(By.xpath("//a[@class='jp_job_post_link']"));
		assertTrue(searchList.size() > 0, "Search has no result");
		
//		Verify the first result
		WebElement searchResult = searchList.get(0);
//		Check to see if job title contains 'Selenium' keyword?
		WebElement titleElement = searchResult.findElement(By.xpath("//div[@class='jp_job_post_detail_cont jp_latest_job']//h3"));		
		boolean isTitleContainsKeyword = titleElement.getAttribute("innerHTML").indexOf("Selenium") >= 0;
		
//		Check to see if skills section contains 'Selenium' keyword?
		WebElement skillElement = searchResult.findElement(By.xpath("//div[@class='jp_job_post_keyword_wrapper']//div"));
		String skillText = skillElement.getAttribute("innerHTML");
		boolean isSkillContainsKeyword = skillText.indexOf("Selenium") >= 0;
		assertTrue(isSkillContainsKeyword || isTitleContainsKeyword, "Title or Skill section does not have 'Selenium' keyword");
		
//		Verify the search level
		WebElement levelIconElement = searchResult.findElement(By.xpath("//i[@class='fa fa-suitcase j-suitcase']"));
		WebElement levelValueElement = levelIconElement.findElement(By.xpath(".././/span"));
		String levelValue = levelValueElement.getAttribute("innerHTML");
		assertEquals(levelValue, "Experienced (Non-Manager)", "Level search does not match with level criteria");
		
//		Verify the location
		WebElement locationIconElement = searchResult.findElement(By.xpath("//i[@class='fa fa-map-marker icon-style']"));
		WebElement locationValueElement = locationIconElement.findElement(By.xpath(".././/span"));
		String locationValue = locationValueElement.getAttribute("innerHTML");
		boolean isLocationContainKeyword = locationValue.indexOf("Ho Chi Minh") >= 0;
		assertTrue(isLocationContainKeyword, "Location does not contain keyword 'Ho Chi Minh', actual value is: " + locationValue);
	}
	
	@AfterClass
	public void closePage() {
		if (driver != null) {
			driver.quit();
		}
	}
}

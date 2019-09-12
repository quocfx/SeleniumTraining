package seleniumtraining.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ITJobHomePage {
	WebDriver driver;
	WebDriverWait wait;
	
	WebElement enIcon;
	
	WebElement searchText;
	WebElement levelDropdown;
	Select citySelect;
	WebElement searchBtn;
	
	public ITJobHomePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 20);
		initControls();
	}
	
	private void initControls() {
		getEnIcon();
//		getSearchText();
//		getLevelDropdown();
//		getCitySelect();
//		getSearchBtn();
	}
	
	public void switchENLanguage() throws InterruptedException {
		if (enIcon == null) {
			getEnIcon();
		}
		enIcon.click();
//		Thread.sleep(3000);
	}

	/**
	 * @return the enIcon
	 */
	public WebElement getEnIcon() {
		if (enIcon == null) {
			enIcon = driver.findElement(By.xpath("//a[@href=\"/en\"]"));
		}
		return enIcon;
	}

	/**
	 * @return the searchText
	 */
	public WebElement getSearchText() {
		if (searchText == null) {
			searchText = driver.findElement(By.cssSelector(".form-control.searchJobKeyword"));
		}
		return this.searchText;
	}

	/**
	 * @return the levelDropdown
	 */
	public WebElement getLevelDropdown() {
		if (levelDropdown == null) {
			levelDropdown = driver.findElement(By.id("FunctionalLevelKey"));
		}
		return levelDropdown;
	}

	/**
	 * @return the citySelect
	 */
	public Select getCitySelect() {
		if (citySelect == null) {
			citySelect = new Select(driver.findElement(By.id("CityId")));
		}
		return citySelect;
	}

	/**
	 * @return the searchBtn
	 */
	public WebElement getSearchBtn() {
		if (searchBtn == null) {
			searchBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Search')]"))); 			
//			searchBtn = driver.findElement(By.xpath("//button[contains(text(),'Search')]"));
		}
		return searchBtn;
	}
	
	public void inputSearchText(String text) throws InterruptedException {
		if (searchText == null) {
			getSearchText();
		}
		searchText.sendKeys(text);
//		Thread.sleep(3000);
	}
	
	public void selectLevelDropDown(String value) throws InterruptedException {
		if (levelDropdown == null) {
			getLevelDropdown();
		}
		levelDropdown.sendKeys(value);
//		Thread.sleep(3000);
	}
	
	public void selectCitySelect(String value) throws InterruptedException {
		if (citySelect == null) {
			getCitySelect();
		}
		citySelect.selectByVisibleText(value);
//		Thread.sleep(3000);
	}
	
	public void clickSearchButton() throws InterruptedException {
		if (searchBtn == null) {
			getSearchBtn();
		}
		searchBtn.click();
//		Thread.sleep(3000);
	}
	
	public List<WebElement> getSearchResults() {
		return driver.findElements(By.xpath("//a[@class='jp_job_post_link']"));
	}
	
	public WebElement getTitleElementOnSearchResult(WebElement searchResult) {
		return searchResult.findElement(By.xpath("//div[@class='jp_job_post_detail_cont jp_latest_job']//h3"));
		
	}
	
	public WebElement getSkillElementOnSearchResult(WebElement searchResult) {
		return searchResult.findElement(By.xpath("//div[@class='jp_job_post_keyword_wrapper']//div"));
		
	}
	
	public WebElement getLevelElementOnSearchResult(WebElement searchResult) {
		WebElement levelIconElement = searchResult.findElement(By.xpath("//i[@class='fa fa-suitcase j-suitcase']"));
		return levelIconElement.findElement(By.xpath(".././/span"));	
	}
	
	public WebElement getLocationElementOnSearchResult(WebElement searchResult) {
		WebElement locationIconElement = searchResult.findElement(By.xpath("//i[@class='fa fa-map-marker icon-style']"));
		return locationIconElement.findElement(By.xpath(".././/span"));
		
	}
}
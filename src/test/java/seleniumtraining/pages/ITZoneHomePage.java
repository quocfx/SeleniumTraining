package seleniumtraining.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class ITZoneHomePage {
	WebDriver driver;
	
	@FindBy(linkText = "IT JOBS")
	private WebElement itJobsLink;
	
	public ITZoneHomePage(WebDriver driver) {
		this.driver = driver;
		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 100);
		PageFactory.initElements(factory, this);	
	}

	/**
	 * @return the itJobsLink
	 */
	public WebElement getItJobsLink() {
		if (itJobsLink == null) {
			itJobsLink = driver.findElement(By.linkText("IT JOBS"));
		}
		return itJobsLink;
	}
	
	public void gotoITJobsPage() throws InterruptedException {
		if (itJobsLink == null) {
			getItJobsLink();
		}
		itJobsLink.click();
		Thread.sleep(5000);
	}

}

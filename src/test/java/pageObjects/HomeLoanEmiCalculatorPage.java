package pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import cucumber.hooks.ScreenshotHook;
import seleniumUtils.ElementUtil;
import utils.PropertiesReader;
import utils.TimePeriod;

public class HomeLoanEmiCalculatorPage {
	@FindBy(xpath = "//input[@id='homeprice']")
	private WebElement homeValueInputElement;

	@FindBy(xpath = "//input[@id='downpayment']")
	private WebElement downPaymentInputElement;

	@FindBy(xpath = "//input[@id='homeloaninterest']")
	private WebElement interestRateInputElement;

	@FindBy(xpath = "//input[@id='homeloanterm']")
	private WebElement loanTenureInputElement;

	@FindBy(xpath = "//div[@class='btn-group btn-group-toggle']//label[text()='Yr ']")
	private WebElement loanTenureYearToggleElement;

	@FindBy(xpath = "//div[@class='btn-group btn-group-toggle']//label[text()='Mo ']")
	private WebElement loanTenureMonthToggleElement;

	@FindAll(@FindBy(xpath = "//tr[contains(@class,'yearlypaymentdetails')] | //table[@class='noextras']/tbody/tr[1]"))
	private List<WebElement> yearOnYearTableRowElements;

	@FindBy(xpath = "//button[@class='navbar-toggler']")
	private WebElement navBarTogglerElement;

	@FindBy(xpath = "//a[contains(@title,'Calculators')]")
	private WebElement calculatorMenuElement;

	@FindBy(xpath = "//a[contains(@title,'Calculators')]/following-sibling::ul//*[contains(text(),'Loan Calculator')]")
	private WebElement loanCalculatorMenuItemElement;

	@FindBy(tagName = "html")
	private WebElement scrollableElement;

	@FindBy(xpath = "//tr[contains(@class,'yearlypaymentdetails')][1]/td[1]/following::tr[1]//tr[1]/td[2]")
	private WebElement firstMonthPrincipalElement;

	@FindBy(xpath = "//tr[contains(@class,'yearlypaymentdetails')][1]/td[1]/following::tr[1]//tr[1]/td[3]")
	private WebElement firstMonthInterestElement;

	private WebDriver driver;
	private String browserName;
	private ElementUtil elementUtil;

	public HomeLoanEmiCalculatorPage(WebDriver driver, String browserName) throws Exception {
		this.driver = driver;
		this.browserName = browserName;
		driver.manage().window().maximize();
		elementUtil = new ElementUtil(driver);
		if (!elementUtil.waitUntillLoadedPage())
			throw new Exception("Site stuck on loading");
		loadElements();
	}

	private void loadElements() {
		PageFactory.initElements(driver, this);
	}

	public Boolean setHomeValue(int amount) {
		if (!elementUtil.scrollToAndVerifyElement(homeValueInputElement, scrollableElement))
			return false;

		elementUtil.highlightElement(homeValueInputElement);
		homeValueInputElement.sendKeys(Keys.chord(Keys.CONTROL, "a"), String.valueOf(amount), Keys.ENTER);
		ScreenshotHook.attachScreenshot(ElementUtil.takeScreenshot(driver, browserName, "setHomeValue"));
		elementUtil.undoHighlightElement(homeValueInputElement);
		return true;
	}

	public Boolean setDownPaymentPercentage(float percent) {
		if (!elementUtil.scrollToAndVerifyElement(downPaymentInputElement, scrollableElement))
			return false;

		elementUtil.highlightElement(downPaymentInputElement);
		downPaymentInputElement.sendKeys(Keys.chord(Keys.CONTROL, "a"), String.valueOf(percent), Keys.ENTER);
		ScreenshotHook.attachScreenshot(ElementUtil.takeScreenshot(driver, browserName, "setDownPaymentPercentage"));
		elementUtil.undoHighlightElement(downPaymentInputElement);
		return true;
	}

	public Boolean setInterestRate(float rate) {
		if (!elementUtil.scrollToAndVerifyElement(interestRateInputElement, scrollableElement))
			return false;

		elementUtil.highlightElement(interestRateInputElement);
		interestRateInputElement.sendKeys(Keys.chord(Keys.CONTROL, "a"), String.valueOf(rate), Keys.ENTER);
		ScreenshotHook.attachScreenshot(ElementUtil.takeScreenshot(driver, browserName, "setHomeLoanInterestRate"));
		elementUtil.undoHighlightElement(interestRateInputElement);
		return true;
	}

	public Boolean setLoanTenure(int duration, TimePeriod timePeriod) {
		if (!elementUtil.scrollToAndVerifyElement(loanTenureInputElement, scrollableElement))
			return false;
		if (!elementUtil.verifyElement(loanTenureMonthToggleElement))
			return false;
		if (!elementUtil.verifyElement(loanTenureYearToggleElement))
			return false;

		// Clicks year or month button based on condition.
		if (timePeriod == TimePeriod.MONTH) {
			elementUtil.highlightElement(loanTenureMonthToggleElement);
			loanTenureMonthToggleElement.click();
			elementUtil.undoHighlightElement(loanTenureMonthToggleElement);
		} else {
			elementUtil.highlightElement(loanTenureYearToggleElement);
			loanTenureYearToggleElement.click();
			elementUtil.undoHighlightElement(loanTenureYearToggleElement);
		}

		elementUtil.highlightElement(loanTenureInputElement);
		loanTenureInputElement.sendKeys(Keys.chord(Keys.CONTROL, "a"), String.valueOf(duration), Keys.ENTER);
		ScreenshotHook.attachScreenshot(ElementUtil.takeScreenshot(driver, browserName, "setHomeLoanTenure"));
		elementUtil.undoHighlightElement(loanTenureInputElement);
		return true;
	}

	// Method to get the year to year payment details from the table.
	public String[][] getYearOnYearTableData() {

		WebElement tableElement = yearOnYearTableRowElements.get(0)
				.findElement(By.xpath("./ancestor::div[@id='paymentschedule']"));
		if (!elementUtil.scrollToAndVerifyElement(tableElement, scrollableElement))
			return null;

		Integer retriesLeft = 10;
		String tableData[][] = null;
		elementUtil.highlightElement(tableElement);

		// Logic to get the table values with retry mechanism if in-case of table or few
		// rows are still getting updated in DOM.
		try {
			while (retriesLeft-- != 0) {
				ElementUtil.sleep(500);
				try {
					// Elements representing each row of the table.
					yearOnYearTableRowElements = elementUtil.findAndVerifyElements(driver, By.xpath(
							"//tr[contains(@class,'yearlypaymentdetails')] | //table[@class='noextras']/tbody/tr[1]"));

					// Getting 2d string array by reading cell data from rows.
					tableData = elementUtil.readTableRows(yearOnYearTableRowElements);
					System.out.println("  Yearly payment details excel file created.");
					break;
				} catch (StaleElementReferenceException staleElementReferenceException) {
					System.out.println("  Caught stale element exception, Retrying attempt #" + (10 - retriesLeft));
					if (retriesLeft == 0)
						throw staleElementReferenceException;
				} catch (NullPointerException nullPointerException) {
					System.out.println("  Caught null element exception, Retrying attempt #" + (10 - retriesLeft));
					if (retriesLeft == 0)
						throw nullPointerException;
				}
			}
		} catch (StaleElementReferenceException staleElementReferenceException) {
			throw staleElementReferenceException;
		}

		ScreenshotHook.attachScreenshot(ElementUtil.takeScreenshot(driver, browserName, "getYearOnYearTableData"));
		elementUtil.undoHighlightElement(tableElement);
		return tableData;
	}

	public Boolean clickLoanCalculatorMenuItem() throws Exception {

		if (!elementUtil.verifyElement(calculatorMenuElement, Duration.ofSeconds(1))) {
			if (!elementUtil.verifyElement(navBarTogglerElement))
				return false;
			else {
				navBarTogglerElement.click();
				if (!elementUtil.verifyElement(calculatorMenuElement))
					return false;
			}
		}

		elementUtil.highlightElement(calculatorMenuElement);
		boolean clicked = elementUtil.clickUntilPresenceOfElement(calculatorMenuElement, loanCalculatorMenuItemElement);
		elementUtil.undoHighlightElement(calculatorMenuElement);
		if (!clicked)
			return false;
		elementUtil.highlightElement(loanCalculatorMenuItemElement);
		ScreenshotHook.attachScreenshot(ElementUtil.takeScreenshot(driver, browserName, "clickLoanCalculator"));
		if (!loanCalculatorMenuItemElement.getAttribute("href")
				.equalsIgnoreCase(PropertiesReader.readProperty("loancalculator.url")))
			throw new Exception("Wrong url exception");
		elementUtil.undoHighlightElement(loanCalculatorMenuItemElement);
		loanCalculatorMenuItemElement.click();
		return true;
	}
}

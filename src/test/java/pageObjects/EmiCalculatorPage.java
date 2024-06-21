package pageObjects;

import java.time.Duration;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import cucumber.hooks.ScreenshotHook;
import seleniumUtils.ElementUtil;
import utils.PropertiesReader;
import utils.TimePeriod;

public class EmiCalculatorPage {

	@FindBy(xpath = "//a[text()='Car Loan']")
	private WebElement carLoanTabElement;

	@FindBy(xpath = "//input[@id='loanamount']")
	private WebElement loanAmountInputElement;

	@FindBy(xpath = "//input[@id='loaninterest']")
	private WebElement loanInterestInputElement;

	@FindBy(xpath = "//input[@id='loanterm']")
	private WebElement loanTenureInputElement;

	@FindBy(xpath = "//div[@class='btn-group btn-group-toggle']//label[text()='Yr ']")
	private WebElement loanTenureYearToggleElement;

	@FindBy(xpath = "//div[@class='btn-group btn-group-toggle']//label[text()='Mo ']")
	private WebElement loanTenureMonthToggleElement;

	@FindBy(xpath = "//div[@id='emiamount']//span")
	private WebElement emiAmountElement;

	@FindBy(xpath = "//div[@id='emitotalinterest']//span")
	private WebElement emiTotalInterestElement;

	@FindBy(xpath = "//div[@id='emitotalamount']//span")
	private WebElement emiTotalAmountElement;

	@FindBy(xpath = "//tr[contains(@class,'yearlypaymentdetails')][1]/td[1]")
	private WebElement firstYearElement;

	@FindBy(xpath = "//tr[contains(@class,'yearlypaymentdetails')][1]/td[1]/following::tr[1]//tr[1]/td[2]")
	private WebElement firstMonthPrincipalElement;

	@FindBy(xpath = "//tr[contains(@class,'yearlypaymentdetails')][1]/td[1]/following::tr[1]//tr[1]/td[3]")
	private WebElement firstMonthInterestElement;

	@FindBy(xpath = "//button[@class='navbar-toggler']")
	private WebElement navBarTogglerElement;

	@FindBy(xpath = "//a[@title='Calculators']")
	private WebElement calculatorMenuElement;

	@FindBy(xpath = "//a[@title='Calculators']/following-sibling::ul//*[contains(text(),'Home Loan EMI Calculator')]")
	private WebElement homeLoanEmiCalculatorMenuItemElement;

	@FindBy(tagName = "html")
	private WebElement scrollableElement;

	private WebDriver driver;
	private String browserName;
	private ElementUtil elementUtil;

	public EmiCalculatorPage(WebDriver driver, String browserName) throws Exception {
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

	public Boolean clickCarLoanTab() {
		if (!elementUtil.scrollToAndVerifyElement(carLoanTabElement, scrollableElement))
			return false;

		elementUtil.highlightElement(carLoanTabElement);
		ScreenshotHook.attachScreenshot(ElementUtil.takeScreenshot(driver, browserName, "clickCarLoanTab"));
		carLoanTabElement.click();
		elementUtil.undoHighlightElement(carLoanTabElement);
		return true;
	}

	public Boolean setCarLoanAmount(int amount) {
		if (!elementUtil.scrollToAndVerifyElement(loanAmountInputElement, scrollableElement))
			return false;

		elementUtil.highlightElement(loanAmountInputElement);
		loanAmountInputElement.sendKeys(Keys.chord(Keys.CONTROL, "a"), String.valueOf(amount), Keys.ENTER);
		ScreenshotHook.attachScreenshot(ElementUtil.takeScreenshot(driver, browserName, "setCarLoanAmount"));
		elementUtil.undoHighlightElement(loanAmountInputElement);

		return true;
	}

	public String getCarLoanAmountAsString() {
		return getValueFromInputAsString(loanAmountInputElement);
	}

	public Boolean setInterestRate(float rate) {
		if (!elementUtil.scrollToAndVerifyElement(loanInterestInputElement, scrollableElement))
			return false;

		elementUtil.highlightElement(loanInterestInputElement);
		loanInterestInputElement.sendKeys(Keys.chord(Keys.CONTROL, "a"), String.valueOf(rate), Keys.ENTER);
		ScreenshotHook.attachScreenshot(ElementUtil.takeScreenshot(driver, browserName, "setCarLoanInterestRate"));
		elementUtil.undoHighlightElement(loanInterestInputElement);
		return true;
	}

	public String getCarLoanInterestAsString() {
		return getValueFromInputAsString(loanInterestInputElement);
	}

	public Boolean setLoanTenure(int duration, TimePeriod timePeriod) {
		if (!elementUtil.scrollToAndVerifyElement(loanTenureInputElement, scrollableElement))
			return false;
		if (!elementUtil.verifyElement(loanTenureMonthToggleElement))
			return false;
		if (!elementUtil.verifyElement(loanTenureYearToggleElement))
			return false;

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
		ScreenshotHook.attachScreenshot(ElementUtil.takeScreenshot(driver, browserName, "setCarLoanTenure"));
		elementUtil.undoHighlightElement(loanTenureInputElement);
		return true;
	}

	public String getLoanTenureInMonthsAsString() {
		elementUtil.highlightElement(loanTenureMonthToggleElement);
		loanTenureMonthToggleElement.click();
		elementUtil.undoHighlightElement(loanTenureMonthToggleElement);

		return getValueFromInputAsString(loanTenureInputElement);
	}

	public String getValueFromInputAsString(WebElement element) {
		elementUtil.highlightElement(element);
		String valueOnInputElement = element.getAttribute("value").replaceAll(",", "");
		elementUtil.undoHighlightElement(element);

		return valueOnInputElement;
	}

	// Method to get various details of the loan.
	public String[] getEmiDetails() {
		if (!elementUtil.scrollToAndVerifyElement(emiAmountElement, scrollableElement))
			return null;
		if (!elementUtil.verifyElement(emiTotalInterestElement))
			return null;
		if (!elementUtil.verifyElement(emiTotalAmountElement))
			return null;

		elementUtil.highlightElement(emiAmountElement);
		elementUtil.highlightElement(emiTotalInterestElement);
		elementUtil.highlightElement(emiTotalAmountElement);
		ScreenshotHook.attachScreenshot(ElementUtil.takeScreenshot(driver, browserName, "getEmiDetails"));
		elementUtil.undoHighlightElement(emiAmountElement);
		elementUtil.undoHighlightElement(emiTotalInterestElement);
		elementUtil.undoHighlightElement(emiTotalAmountElement);

		if (!elementUtil.scrollToAndVerifyElement(firstYearElement, scrollableElement))
			return null;

		elementUtil.highlightElement(firstYearElement);
		boolean clicked = elementUtil.clickUntilPresenceOfElement(firstYearElement, firstMonthPrincipalElement);
		elementUtil.undoHighlightElement(firstYearElement);
		if (!clicked)
			return null;

		if (!elementUtil.verifyElement(firstMonthPrincipalElement))
			return null;
		if (!elementUtil.verifyElement(firstMonthInterestElement))
			return null;

		ScreenshotHook.attachScreenshot(ElementUtil.takeScreenshot(driver, browserName, "getFirstMonthEmiDetails"));
		;

		String[] emiData = { emiAmountElement.getText(), emiTotalInterestElement.getText(),
				emiTotalAmountElement.getText(), firstMonthInterestElement.getText(),
				firstMonthPrincipalElement.getText() };

		return emiData;
	}

	public Boolean clickHomeLoanEmiCalculatorMenuItem() throws Exception {

		if (!elementUtil.verifyElement(calculatorMenuElement, Duration.ofSeconds(1))) {
			if (!elementUtil.verifyElement(navBarTogglerElement))
				return false;
			else {
				elementUtil.clickUntilPresenceOfElement(navBarTogglerElement, calculatorMenuElement);
				if (!elementUtil.verifyElement(calculatorMenuElement))
					return false;
			}
		}

		elementUtil.highlightElement(calculatorMenuElement);
		boolean clicked = elementUtil.clickUntilPresenceOfElement(calculatorMenuElement,
				homeLoanEmiCalculatorMenuItemElement);
		elementUtil.undoHighlightElement(calculatorMenuElement);
		if (!clicked)
			return false;
		elementUtil.highlightElement(homeLoanEmiCalculatorMenuItemElement);
		ScreenshotHook.attachScreenshot(ElementUtil.takeScreenshot(driver, browserName, "clickHomeLoanEmiCalculator"));
		if (!homeLoanEmiCalculatorMenuItemElement.getAttribute("href")
				.equalsIgnoreCase(PropertiesReader.readProperty("homeloanemicalculator.url")))
			throw new Exception("Wrong url exception");
		elementUtil.undoHighlightElement(homeLoanEmiCalculatorMenuItemElement);
		homeLoanEmiCalculatorMenuItemElement.click();
		return true;
	}
}

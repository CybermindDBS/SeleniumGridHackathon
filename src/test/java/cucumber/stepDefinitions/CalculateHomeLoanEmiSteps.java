package cucumber.stepDefinitions;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.WebDriver;

import cucumber.hooks.Log4jHook;
import cucumber.hooks.SeleniumDriverHook;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.HomeLoanEmiCalculatorPage;
import utils.ExcelUtil;
import utils.PropertiesReader;
import utils.TimePeriod;

public class CalculateHomeLoanEmiSteps {

	WebDriver driver;
	HomeLoanEmiCalculatorPage homeLoanEmiCalculatorPage;

	@Given("the user is on the home_loan_calculator page using chrome")
	public void the_user_is_on_the_home_loan_calculator_page_using_chrome() throws Exception {
		Log4jHook.log(); // Enables logging.
		String browserName = "chrome";
		driver = SeleniumDriverHook.getDriver(browserName);
		driver.get(PropertiesReader.readProperty("homeloanemicalculator.url"));
		homeLoanEmiCalculatorPage = new HomeLoanEmiCalculatorPage(driver, browserName);
	}

	@Given("the user is on the home_loan_calculator page using edge")
	public void the_user_is_on_the_home_loan_calculator_page_using_edge() throws Exception {
		Log4jHook.log();
		String browserName = "edge";
		driver = SeleniumDriverHook.getDriver(browserName);
		driver.get(PropertiesReader.readProperty("homeloanemicalculator.url"));
		homeLoanEmiCalculatorPage = new HomeLoanEmiCalculatorPage(driver, browserName);
	}

	@When("the user enters the home value {int}")
	public void the_user_enters_the_home_value(Integer amount) {
		Log4jHook.log();
		assertTrue(homeLoanEmiCalculatorPage.setHomeValue(amount));
	}

	@When("the user enters the down payment {float}")
	public void the_user_enters_the_down_payment(Float percent) {
		Log4jHook.log();
		assertTrue(homeLoanEmiCalculatorPage.setDownPaymentPercentage(percent));
	}

	@When("the user enters the home loan interest {float}")
	public void the_user_enters_the_interest_rate(Float rate) {
		Log4jHook.log();
		assertTrue(homeLoanEmiCalculatorPage.setInterestRate(rate));
	}

	@When("the user enters the home loan tenure {int} year")
	public void the_user_enters_the_loan_tenure_year(Integer duration) {
		Log4jHook.log();
		assertTrue(homeLoanEmiCalculatorPage.setLoanTenure(duration, TimePeriod.YEAR));
	}

	@When("the user enters the home loan tenure {int} month")
	public void the_user_enters_the_loan_tenure_month(Integer duration) {
		Log4jHook.log();
		assertTrue(homeLoanEmiCalculatorPage.setLoanTenure(duration, TimePeriod.MONTH));
	}

	@Then("I verify and store the generated year_on_year loan details in an excel format")
	public void I_store_the_generated_year_on_year_loan_details_in_an_excel_format() {
		Log4jHook.log();

		// Getting all value from all cells of the table and storing it in a 2d array.
		String[][] yearOnYearPaymentDetails = homeLoanEmiCalculatorPage.getYearOnYearTableData();
		assertTrue(yearOnYearPaymentDetails != null);

		// Logic to store it in an excel file.
		ExcelUtil excelUtil = new ExcelUtil();
		excelUtil.create(PropertiesReader.readProperty("paymentDetailsExcelFile.path"), "yearOnYearPayment",
				yearOnYearPaymentDetails);
	}

	@And("the user navigates to the loan_calculator page")
	public void the_user_navigates_to_loan_calculator_page() throws Exception {
		Log4jHook.log();
		assertTrue(homeLoanEmiCalculatorPage.clickLoanCalculatorMenuItem());
	}
}

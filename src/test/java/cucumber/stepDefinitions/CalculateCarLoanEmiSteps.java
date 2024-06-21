package cucumber.stepDefinitions;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.WebDriver;

import cucumber.hooks.Log4jHook;
import cucumber.hooks.SeleniumDriverHook;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.EmiCalculatorPage;
import utils.PropertiesReader;
import utils.TimePeriod;

public class CalculateCarLoanEmiSteps {
	WebDriver driver;
	EmiCalculatorPage emiCalculatorPage;

	@Given("the user is on the emi_calculator page using chrome")
	public void the_user_is_on_the_emi_calculator_page_using_chrome() throws Exception {
		Log4jHook.log(); // Enables logging.
		String browserName = "chrome";
		driver = SeleniumDriverHook.getDriver(browserName);
		driver.get(PropertiesReader.readProperty("emicalculator.url"));
		emiCalculatorPage = new EmiCalculatorPage(driver, browserName);
	}

	@Given("the user is on the emi_calculator page using edge")
	public void the_user_is_on_the_emi_calculator_page_using_edge() throws Exception {
		Log4jHook.log();
		String browserName = "edge";
		driver = SeleniumDriverHook.getDriver(browserName);
		driver.get(PropertiesReader.readProperty("emicalculator.url"));
		emiCalculatorPage = new EmiCalculatorPage(driver, browserName);
	}

	@When("the user clicks on the car_loan tab")
	public void the_user_clicks_on_the_car_loan_tab() {
		Log4jHook.log();
		assertTrue(emiCalculatorPage.clickCarLoanTab());
	}

	@When("the user enters the car loan amount {int}")
	public void the_user_enters_the_car_loan_amount(Integer amount) {
		Log4jHook.log();
		assertTrue(emiCalculatorPage.setCarLoanAmount(amount));
	}

	@When("the user enters the car loan interest {float}")
	public void the_user_enters_the_loan_interest(Float rate) {
		Log4jHook.log();
		assertTrue(emiCalculatorPage.setInterestRate(rate));
	}

	@When("the user enters the car loan tenure {int} year")
	public void the_user_enters_the_loan_tenure_year(Integer duration) {
		Log4jHook.log();
		assertTrue(emiCalculatorPage.setLoanTenure(duration, TimePeriod.YEAR));
	}

	@When("the user enters the car loan tenure {int} month")
	public void the_user_enters_the_loan_tenure_month(Integer duration) {
		Log4jHook.log();
		assertTrue(emiCalculatorPage.setLoanTenure(duration, TimePeriod.MONTH));
	}

	// Method gets the EMI details and validates it with calculated values.
	@Then("I verify and display the car emi details")
	public void I_verify_and_display_the_emi_details() {
		Log4jHook.log();
		String[] emiDetails = emiCalculatorPage.getEmiDetails();
		assertTrue(emiDetails != null);

		Integer monthlyEmiAmount = Integer.parseInt(emiDetails[0].replaceAll(",", ""));

		Integer principalAmount = Integer.parseInt(emiCalculatorPage.getCarLoanAmountAsString());
		Float interest = Float.parseFloat(emiCalculatorPage.getCarLoanInterestAsString());
		Integer loanTenureInMonths = Integer.parseInt(emiCalculatorPage.getLoanTenureInMonthsAsString());

		Float monthlyInterestRate = interest / 12 / 100;

		Integer calculatedMonthlyEmiAmount = (int) (principalAmount * monthlyInterestRate
				* ((Math.pow(1 + monthlyInterestRate, loanTenureInMonths))
						/ (Math.pow(1 + monthlyInterestRate, loanTenureInMonths) - 1)));

		Integer difference = Math.abs(monthlyEmiAmount - calculatedMonthlyEmiAmount);

		Integer deviationPercentage = (difference / monthlyEmiAmount) * 100;

		System.out.println();
		System.out.println("  Car Loan EMI Details: ");
		System.out.println("  Monthly EMI Amount: " + emiDetails[0]);
		System.out.println("  Total Interest Amount: " + emiDetails[1]);
		System.out.println("  Total Principal Amount: " + emiDetails[2]);
		System.out.println("  First Month Interest Amount: " + emiDetails[3]);
		System.out.println("  First Month Principal Amount: " + emiDetails[4]);

		// Throws an exception if the difference between the calculated EMI amount and
		// EMI amount from emi-calculator is significant enough.
		assertTrue(deviationPercentage < 1);
		System.out.println();
		System.out.println("  Calculated Monthly EMI Amount: " + calculatedMonthlyEmiAmount);
		System.out.println("  Car Loan EMI Details Verified and Validated.");
		System.out.println();
	}

	@And("the user navigates to home_loan_calculator page")
	public void the_user_navigates_to_home_loan_calculator_page() throws Exception {
		Log4jHook.log();
		assertTrue(emiCalculatorPage.clickHomeLoanEmiCalculatorMenuItem());
	}
}

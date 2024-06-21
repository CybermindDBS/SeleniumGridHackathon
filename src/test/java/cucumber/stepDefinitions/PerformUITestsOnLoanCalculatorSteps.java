package cucumber.stepDefinitions;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.WebDriver;

import cucumber.hooks.Log4jHook;
import cucumber.hooks.SeleniumDriverHook;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.LoanCalculatorPage;
import utils.PropertiesReader;
import utils.TimePeriod;

public class PerformUITestsOnLoanCalculatorSteps {
	WebDriver driver;
	LoanCalculatorPage loanCalculatorPage;

	@Given("the user is on the loan_calculator page using chrome")
	public void the_user_is_on_the_loan_calculator_page_using_chrome() throws Exception {
		Log4jHook.log(); // Enables logging.
		String browserName = "chrome";
		driver = SeleniumDriverHook.getDriver(browserName);
		driver.get(PropertiesReader.readProperty("loancalculator.url"));
		loanCalculatorPage = new LoanCalculatorPage(driver, browserName);
	}

	@Given("the user is on the loan_calculator page using edge")
	public void the_user_is_on_the_loan_calculator_page_using_edge() throws Exception {
		Log4jHook.log();
		String browserName = "edge";
		driver = SeleniumDriverHook.getDriver(browserName);
		driver.get(PropertiesReader.readProperty("loancalculator.url"));
		loanCalculatorPage = new LoanCalculatorPage(driver, browserName);
	}

	@And("the user clicks on the emi_calculator tab")
	public void the_user_clicks_on_the_emi_calculator_tab() {
		Log4jHook.log();
		assertTrue(loanCalculatorPage.clickEmiCalculatorTab());
	}

	@When("the user on emi_calculator tab enters the loan amount {int}")
	public void the_user_on_emi_calculator_tab_enters_the_loan_amount(Integer amount) {
		Log4jHook.log();
		assertTrue(loanCalculatorPage.setLoanAmountOnInput(amount));
	}

	@When("the user on emi_calculator tab enters the loan interest {float}")
	public void the_user_on_emi_calculator_tab_enters_the_loan_interest(Float rate) {
		Log4jHook.log();
		assertTrue(loanCalculatorPage.setLoanInterestOnInput(rate));
	}

	@When("the user on emi_calculator tab enters the loan tenure {int} year")
	public void the_user_on_emi_calculator_tab_enters_the_loan_tenure_year(Integer duration) {
		Log4jHook.log();
		assertTrue(loanCalculatorPage.setLoanTenureOnInput(duration, TimePeriod.YEAR));
	}

	@When("the user on emi_calculator tab enters the loan tenure {int} month")
	public void the_user_on_emi_calculator_tab_enters_the_loan_tenure_month(Integer duration) {
		Log4jHook.log();
		assertTrue(loanCalculatorPage.setLoanTenureOnInput(duration, TimePeriod.MONTH));
	}

	@When("the user on emi_calculator tab enters the loan fees {int}")
	public void the_user_on_emi_calculator_tab_enters_the_loan_fees(Integer amount) {
		Log4jHook.log();
		assertTrue(loanCalculatorPage.setLoanFeesOnInput(amount));
	}

	@Then("I verify the loan amount ui on emi_calculator tab")
	public void I_verify_the_loan_amount_ui_on_emi_calculator_tab() {
		Log4jHook.log();
		validateUI(loanCalculatorPage.getLoanAmountFromSlider(), loanCalculatorPage.getLoanAmountFromInput());
	}

	@Then("I verify the loan interest ui on emi_calculator tab")
	public void I_verify_the_loan_interest_ui_on_emi_calculator_tab() {
		Log4jHook.log();
		validateUI(loanCalculatorPage.getLoanInterestFromSlider(), loanCalculatorPage.getLoanInterestFromInput());
	}

	@Then("I verify the loan tenure year ui on emi_calculator tab")
	public void I_verify_the_year_loan_tenure_ui_on_emi_calculator_tab() {
		Log4jHook.log();
		validateUI(loanCalculatorPage.getLoanTenureFromSlider(TimePeriod.YEAR),
				loanCalculatorPage.getLoanTenureFromInput(TimePeriod.YEAR));
	}

	@Then("I verify the loan tenure month ui on emi_calculator tab")
	public void I_verify_the_month_loan_tenure_ui_on_emi_calculator_tab() {
		Log4jHook.log();
		validateUI(loanCalculatorPage.getLoanTenureFromSlider(TimePeriod.MONTH),
				loanCalculatorPage.getLoanTenureFromInput(TimePeriod.MONTH));
	}

	@Then("I verify the loan fees ui on emi_calculator tab")
	public void I_verify_the_loan_fees_ui_on_emi_calculator_tab() {
		Log4jHook.log();
		validateUI(loanCalculatorPage.getLoanFeesFromSlider(), loanCalculatorPage.getLoanFeesFromInput());
	}

	@Given("the user clicks on the loan_amount_calculator tab")
	public void the_user_clicks_on_the_loan_amount_calculator_tab() {
		Log4jHook.log();
		assertTrue(loanCalculatorPage.clickLoanAmountCalculatorTab());
	}

	@When("the user on loan_amount_calculator tab enters the emi amount {int}")
	public void the_user_on_loan_amount_calculator_tab_enters_the_emi_amount(Integer amount) {
		Log4jHook.log();
		assertTrue(loanCalculatorPage.setEmiOnInput(amount));
	}

	@When("the user on loan_amount_calculator tab enters the loan interest {float}")
	public void the_user_on_loan_amount_calculator_tab_enters_the_loan_interest(Float rate) {
		Log4jHook.log();
		assertTrue(loanCalculatorPage.setLoanInterestOnInput(rate));
	}

	@When("the user on loan_amount_calculator tab enters the loan tenure {int} year")
	public void the_user_on_loan_amount_calculator_tab_enters_the_loan_tenure_year(Integer duration) {
		Log4jHook.log();
		assertTrue(loanCalculatorPage.setLoanTenureOnInput(duration, TimePeriod.YEAR));
	}

	@When("the user on loan_amount_calculator tab enters the loan tenure {int} month")
	public void the_user_on_loan_amount_calculator_tab_enters_the_loan_tenure_month(Integer duration) {
		Log4jHook.log();
		assertTrue(loanCalculatorPage.setLoanTenureOnInput(duration, TimePeriod.MONTH));
	}

	@When("the user on loan_amount_calculator tab enters the loan fees {int}")
	public void the_user_on_loan_amount_calculator_tab_enters_the_loan_fees(Integer amount) {
		Log4jHook.log();
		assertTrue(loanCalculatorPage.setLoanFeesOnInput(amount));
	}

	@Then("I verify the loan emi ui on loan_amount_calculator tab")
	public void I_verify_the_loan_emi_ui_on_loan_amount_calculator_tab() {
		Log4jHook.log();
		validateUI(loanCalculatorPage.getEmiFromSlider(), loanCalculatorPage.getEmiFromInput());
	}

	@Then("I verify the loan interest ui on loan_amount_calculator tab")
	public void I_verify_the_loan_interest_ui_on_loan_amount_calculator_tab() {
		Log4jHook.log();
		validateUI(loanCalculatorPage.getLoanInterestFromSlider(), loanCalculatorPage.getLoanInterestFromInput());
	}

	@Then("I verify the loan tenure year ui on loan_amount_calculator tab")
	public void I_verify_the_year_loan_tenure_ui_on_loan_amount_calculator_tab() {
		Log4jHook.log();
		validateUI(loanCalculatorPage.getLoanTenureFromSlider(TimePeriod.YEAR),
				loanCalculatorPage.getLoanTenureFromInput(TimePeriod.YEAR));
	}

	@Then("I verify the loan tenure month ui on loan_amount_calculator tab")
	public void I_verify_the_month_loan_tenure_ui_on_loan_amount_calculator_tab() {
		Log4jHook.log();
		validateUI(loanCalculatorPage.getLoanTenureFromSlider(TimePeriod.MONTH),
				loanCalculatorPage.getLoanTenureFromInput(TimePeriod.MONTH));
	}

	@Then("I verify the loan fees ui on loan_amount_calculator tab")
	public void I_verify_the_loan_fees_ui_on_loan_amount_calculator_tab() {
		Log4jHook.log();
		validateUI(loanCalculatorPage.getLoanFeesFromSlider(), loanCalculatorPage.getLoanFeesFromInput());
	}

	@Given("the user clicks on the loan_tenure_calculator tab")
	public void the_user_clicks_on_the_loan_tenure_calculator_tab() {
		Log4jHook.log();
		assertTrue(loanCalculatorPage.clickLoanTenureCalculatorTab());
	}

	@When("the user on loan_tenure_calculator tab enters the loan amount {int}")
	public void the_user_on_loan_tenure_calculator_tab_enters_the_loan_amount(Integer amount) {
		Log4jHook.log();
		assertTrue(loanCalculatorPage.setLoanAmountOnInput(amount));
	}

	@When("the user on loan_tenure_calculator tab enters the emi amount {int}")
	public void the_user_on_loan_tenure_calculator_tab_enters_the_emi_amount(Integer amount) {
		Log4jHook.log();
		assertTrue(loanCalculatorPage.setEmiOnInput(amount));
	}

	@When("the user on loan_tenure_calculator tab enters the loan interest {float}")
	public void the_user_on_loan_tenure_calculator_tab_enters_the_loan_interest(Float rate) {
		Log4jHook.log();
		assertTrue(loanCalculatorPage.setLoanInterestOnInput(rate));
	}

	@When("the user on loan_tenure_calculator tab enters the loan fees {int}")
	public void the_user_on_loan_tenure_calculator_tab_enters_the_loan_fees(Integer amount) {
		Log4jHook.log();
		assertTrue(loanCalculatorPage.setLoanFeesOnInput(amount));
	}

	@Then("I verify the loan amount ui on loan_tenure_calculator tab")
	public void I_verify_the_loan_amount_ui_on_loan_tenure_calculator_tab() {
		Log4jHook.log();
		validateUI(loanCalculatorPage.getLoanAmountFromSlider(), loanCalculatorPage.getLoanAmountFromInput());
	}

	@Then("I verify the loan emi ui on loan_tenure_calculator tab")
	public void I_verify_the_loan_emi_ui_on_loan_tenure_calculator_tab() {
		Log4jHook.log();
		validateUI(loanCalculatorPage.getEmiFromSlider(), loanCalculatorPage.getEmiFromInput());
	}

	@Then("I verify the loan interest ui on loan_tenure_calculator tab")
	public void I_verify_the_loan_interest_ui_on_loan_tenure_calculator_tab() {
		Log4jHook.log();
		validateUI(loanCalculatorPage.getLoanInterestFromSlider(), loanCalculatorPage.getLoanInterestFromInput());
	}

	@Then("I verify the loan fees ui on loan_tenure_calculator tab")
	public void I_verify_the_loan_fees_ui_on_loan_tenure_calculator_tab() {
		Log4jHook.log();
		validateUI(loanCalculatorPage.getLoanFeesFromSlider(), loanCalculatorPage.getLoanFeesFromInput());
	}

	// Logic to check whether the value on the slider element matches with the value
	// on the input element.
	private void validateUI(Float[] sliderDetails, Number valueOnInputElement) throws AssertionError {
		Float rightBound = sliderDetails[0]; // Maximum value the slider can represent.
		Float sliderPosition = sliderDetails[1]; // Represents the position of the slider, for example, if right-bound
													// is 200 and slider position is 50(%) then it represents that the
													// value on the input element is 100.

		Float calculatedVal = ((valueOnInputElement.floatValue() / rightBound) * 100f); // Calculated slider position.
		calculatedVal = calculatedVal > 100f ? 100f : calculatedVal;

		Float difference = Math.abs(calculatedVal - sliderPosition); // difference between the calculated slider
																		// position and the actual slider position.

		Float deviationPercentage;
		if (sliderPosition != 0)
			deviationPercentage = (difference / sliderPosition) * 100f;
		else
			deviationPercentage = 0f;

		// Throws an exception if the difference between the calculated slider position
		// and the actual slider position is significant enough.
		assertTrue(deviationPercentage < 1);
	}
}

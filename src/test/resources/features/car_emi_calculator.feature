#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@smoke
Feature: Calculate Car Loan Emi

  Scenario Outline: Calculating car loan emi with various inputs
    Given the user is on the emi_calculator page using <browser>
    When the user clicks on the car_loan tab
    When the user enters the car loan amount <amount>
    When the user enters the car loan interest <rate>
    When the user enters the car loan tenure <duration> <period>
    Then I verify and display the car emi details
    And the user navigates to home_loan_calculator page

    Examples: 
      | browser  | amount  | rate | duration  | period  |
			| chrome   | 1500000 | 9.5  | 1         | year    |
#			| edge     | 1500000 | 9.5  | 1         | year    |
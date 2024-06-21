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
@regression1 @regression
Feature: Calculate Detailed Home Loan Information Annually

  Scenario Outline: Calculating detailed home loan information with various inputs
    Given the user is on the home_loan_calculator page using <browser>
    When the user enters the home value <amount>
    When the user enters the down payment <percent>
    When the user enters the home loan interest <rate>
    When the user enters the home loan tenure <duration> <period>
    Then I verify and store the generated year_on_year loan details in an excel format
    And the user navigates to the loan_calculator page

    Examples: 
      | browser  | amount   | percent | rate | duration  | period  |
			| chrome   | 15000000 | 0 			| 9    | 20        | year    |
#			| edge     | 7500000  | 0 			| 9.5  | 20        | year    |
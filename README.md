COGNIZANT GENC - SELENIUM HACKATHON

Features:-
1. Selenium / Selenium Grid
2. Hybrid Testing Framework (Cucumber with JUnit).
3. Retesting
4. Smoke and Regression tests
5. Log4j logging
6. Page Object Model
7. Cucumber reports


Requriments:-
Problem Statement : Find the Interest Amount for current year

1. Buying a  car of 15 Lac
2. Interest rate of 9.5%
3. Tenure should be 1 year.
Display the interest amount & principal amount of first month.
(Suggested Site: emicalculator.net  / HDFCbank.com etc. however you are free to use any other legitimate site)
Detailed Description: Hackathon Ideas

1. Find the EMI for Car with price of 15 Lac, Interest rate of 9.5% & Tenure 1 year; Display the interest amount & principal amount for one month
2. From Menu, pick Home Loan EMI Calculator, fill relevant details & extract all the data from  year on year table & store in excel;
3. From Menu, pick Loan Calculator and under EMI calculator, do all UI check for text box & scales; change the Loan tenure for year & month,check the change in scale; Re-use the same validation for Loan Amount Calculator & Loan Tenure Calculator
(Suggested Site: emicalculator.net  however you are free to use any other legitimate site)


Selenium Grid Configuration:-
1. Set 'selenium launch mode' property to 'remote' in /src/test/resources/config.properties.
2. Start the hub and nodes with selenium-server jar (provided in /selenium-grid-jar/)

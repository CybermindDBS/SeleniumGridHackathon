# Selenium Cucumber Framework

A BDD test framework over Selenium — Cucumber for specs, JUnit as the runner, Page Object Model,
and the same suites runnable locally or against a Selenium Grid by changing one property.

The suites test the loan calculators on [emicalculator.net](https://emicalculator.net/): computing
car loan EMI, extracting the year-on-year amortisation table into Excel, and validating the
calculator UI where sliders and text inputs have to stay in sync.

## What is worth a look

**Automatic retest of failures.** `RetestHook` collects the tag of every failed scenario, then in
`@AfterAll` re-invokes the Cucumber CLI programmatically for those tags only. Each attempt gets its
own HTML and JSON report, and each tag has its own retry budget, so a genuinely broken scenario stops
after three tries instead of blocking the run.

**Page loads are waited on explicitly.** Drivers are created with `PAGE_LOAD_STRATEGY=none`, so
Selenium never blocks on a page load. Waiting is handled by `DocumentReadyStateExpectedCondition`,
which polls `document.readyState` directly. This makes waits explicit at the point they matter, and
avoids the driver stalling on third-party resources the tests do not care about.

**Element helpers that handle the awkward cases.** `ElementUtil` covers what plain `WebDriverWait`
does not: scroll a specific element into view inside a scrollable container, click repeatedly until a
target appears, read an entire HTML table into a `String[][]`, and highlight elements during a run so
recordings are easier to follow.

## Layout

```
src/test/java/
  cucumber/hooks/          driver lifecycle, log4j, screenshots, retest, cleanup
  cucumber/runners/        TestRunner (all), SmokeRunner (@smoke), RegressionRunner (@regression)
  cucumber/stepDefinitions/
  pageObjects/             one class per calculator page
  seleniumUtils/           DriverFactory, ElementUtil, custom expected conditions
  utils/                   Excel, properties, logging
src/test/resources/features/
```

## Running it

```
mvn test -Dtest=TestRunner
```

`SmokeRunner` and `RegressionRunner` run the `@smoke` and `@regression` subsets. Reports land in
`target/cucumber-reports`, retries in `target/cucumber-retest-reports`, and the runner opens the HTML
report in a browser when it finishes.

Screenshots are taken per step and attached to the scenario, so failures show the page as it was.
Extracted amortisation data is written to `src/test/resources/yearOnYearPaymentDetails.xlsx`.

### Against a Grid

In `src/test/resources/config.properties`:

```properties
selenium.launch.mode=remote
selenium-grid.hub.url=http://localhost:4444/wd/hub
```

Then start a hub and nodes with the server jar in `selenium-grid-jar/`:

```
java -jar selenium-server-<version>.jar hub
java -jar selenium-server-<version>.jar node --hub http://localhost:4444
```

Chrome and Edge are both wired up; scenario outlines take the browser as a parameter.

## Notes

- Written for the Cognizant GENC Selenium hackathon.
- Locators target a live third-party site, so a redesign there will break selectors.

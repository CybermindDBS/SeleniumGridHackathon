package cucumber.hooks;

import java.util.HashMap;
import java.util.Stack;

import io.cucumber.core.cli.Main;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Scenario;
import utils.Utilities;

public class RetestHook {

	public static HashMap<String, Integer> retryLimit = new HashMap<>(); // HashMap to store the retries left for the
																			// particular tag name.
	public static Stack<String> retestScenariosStack = new Stack<>(); // Stack to contain the tag names of the failed
																		// scenarios.
	public static final int MAX_RETRY_LIMIT = 3;

	// Method to add the tag name of a scenario to the stack if it fails.
	@After
	public void addFailedScenarioTagNameToStack(Scenario scenario) {
		if (scenario.isFailed()) {
			String scenarioTagName = scenario.getSourceTagNames().toArray(new String[0])[0];
			if (!retestScenariosStack.contains(scenarioTagName))
				retestScenariosStack.add(scenarioTagName);
		}
	}

	// Method executes at-last after execution of all the scenarios.
	@AfterAll
	public static void startRetest() {
		// Logic to start the re-test of the failed scenarios until the stack containing
		// failed scenario tag names is empty or retry limit for a specific scenario tag
		// name is reached.
		while (!retestScenariosStack.isEmpty()) {
			String failedScenarioTagName = retestScenariosStack.pop();

			if (retryLimit.containsKey(failedScenarioTagName)) {
				Integer retriesLeft = retryLimit.get(failedScenarioTagName);
				if (retriesLeft == 0) {
					Utilities.enablePrintingInConsole();
					System.out.println("  Retest limit reached, Skipping scenario.");
					Utilities.disablePrintingInConsole();
					return;
				} else {
					retryLimit.put(failedScenarioTagName, retriesLeft - 1);
					retestScenario(failedScenarioTagName);
				}
			} else {
				retryLimit.put(failedScenarioTagName, MAX_RETRY_LIMIT - 1);
				retestScenario(failedScenarioTagName);
			}
		}
	}

	// Method to re-test the failed scenarios by programmatically re-running the
	// scenarios with the cucumber CLI.
	public static void retestScenario(String failedScenarioTagName) {
		Utilities.enablePrintingInConsole();
		System.out.println("  Scenario with tag " + failedScenarioTagName + " failed, Attempting retest #"
				+ (3 - retryLimit.get(failedScenarioTagName)));

		String[] argv = { "--glue", "cucumber", "--tags", failedScenarioTagName, // Tag to rerun
				"--plugin",
				"html:target/cucumber-retest-reports/cucumber-retest-report-"
						+ failedScenarioTagName.replaceAll("@", "") + "-attempt~"
						+ (RetestHook.MAX_RETRY_LIMIT - retryLimit.get(failedScenarioTagName)) + ".html",
				"--plugin",
				"json:target/cucumber-retest-reports/CucumberRetestReport-" + failedScenarioTagName.replaceAll("@", "")
						+ "-attempt~" + (RetestHook.MAX_RETRY_LIMIT - retryLimit.get(failedScenarioTagName)) + ".json",
				"src/test/resources/features" };

		Utilities.disablePrintingInConsole(); // Disable printing in console to promote console readability by avoiding
												// unwanted details.

		byte exitstatus = Main.run(argv, Thread.currentThread().getContextClassLoader()); // Cucumber CLI to
																							// programmatically run the
																							// scenarios with the
																							// mentioned tags (tags of
																							// the failed scenarios).

		Utilities.enablePrintingInConsole();
		if (exitstatus == 0)
			System.out.println("  Retest succeeded for tag " + failedScenarioTagName);
	}
}

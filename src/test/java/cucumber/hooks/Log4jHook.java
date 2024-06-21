package cucumber.hooks;

import java.nio.file.Files;
import java.nio.file.Paths;

import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import utils.LogUtil;
import utils.PropertiesReader;
import utils.Utilities;

public class Log4jHook {

	// Variable to store the currently executing method's name
	private static String currentlyExecutingMethodString = null;

	// Method annotated with @BeforeAll to run once before any scenario starts
	@BeforeAll
	public static void setUp() {
		try {
			Files.createDirectories(Paths.get(PropertiesReader.readProperty("logs.path")));
		} catch (Exception ignored) {
		}
		LogUtil.initializeLogger();
	}

	// Method annotated with @Before to run before each scenario
	@Before
	public void logScenario(Scenario scenario) {
		LogUtil.info("Executing Scenario: " + scenario.getName());
	}

	// Method annotated with @BeforeStep to run before each step
	@BeforeStep
	public static void clear() {
		currentlyExecutingMethodString = null;
	}

	// Method to assign the currently executing method's name to
	// 'currentlyExecutingMethodString' variable
	public static void log() {
		currentlyExecutingMethodString = Utilities.getCurrentMethodName();
	}

	@AfterStep
	public static void logScenarioStep(Scenario scenario) {
		if (currentlyExecutingMethodString != null)
			if (scenario.isFailed())
				LogUtil.error("Error at step: " + currentlyExecutingMethodString + ", " + scenario.getStatus());
			else
				LogUtil.info("Step Success: " + currentlyExecutingMethodString + ", " + scenario.getStatus());
	}
}

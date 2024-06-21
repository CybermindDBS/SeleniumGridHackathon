package cucumber.hooks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;

public class ScreenshotHook {

	private static Scenario scenario;

	@BeforeStep
	public void beforeStep(Scenario scenario) {
		// Store the current scenario in the static variable
		ScreenshotHook.scenario = scenario;
	}

	// Method to attach a screenshot to the scenario
	public static void attachScreenshot(String screenShotPath) {
		try {
			File screenShotFile = new File(screenShotPath);
			byte[] fileBytes = Files.readAllBytes(screenShotFile.toPath());
			scenario.attach(fileBytes, "image/png", screenShotFile.getName().toString());
		} catch (IOException ignored) {
			ignored.printStackTrace();
		}
	}
}

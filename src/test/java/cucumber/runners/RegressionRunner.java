package cucumber.runners;

import java.io.File;
import java.nio.file.Files;

import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import seleniumUtils.DriverFactory;
import utils.PropertiesReader;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = "cucumber", tags = "@regression", plugin = { "pretty",
		"html:target/cucumber-reports/cucumber-report.html", "json:target/cucumber-reports/CucumberTestReport.json" })
public class RegressionRunner {

	// Opens the generated HTML reports on browser after execution of all the
	// scenarios.
	@AfterClass
	public static void openHTMLReports() {

		if (PropertiesReader.readProperty("selenium.launch.mode").equalsIgnoreCase("local"))
			try {
				WebDriver driver = DriverFactory.createDriver("chrome");
				driver.manage().window().maximize();
				JavascriptExecutor jExecutor = (JavascriptExecutor) driver;

				File reportsDirectory = new File(PropertiesReader.readProperty("cucumber.reports.path"));
				File retestReportsDirectory = new File(PropertiesReader.readProperty("cucumber.retest-reports.path"));

				Files.createDirectories(reportsDirectory.toPath());
				Files.createDirectories(retestReportsDirectory.toPath());

				System.out.println();
				System.out.println("Test Report(s): ");

				if (reportsDirectory.isDirectory()) {
					for (File file : reportsDirectory.listFiles()) {
						if (file.isFile() && file.getName().endsWith(".html")) {
							System.out.println(file.getAbsolutePath());
							driver.get("file:///" + file.getAbsolutePath());
						}
					}
				}

				if (retestReportsDirectory.isDirectory()) {
					for (File file : retestReportsDirectory.listFiles()) {
						if (file.isFile() && file.getName().endsWith(".html")) {
							System.out.println(file.getAbsolutePath());
							jExecutor.executeScript("window.open('" + "file:///"
									+ file.getAbsolutePath().replace("\\", "/") + "', '_blank');");
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	}
}

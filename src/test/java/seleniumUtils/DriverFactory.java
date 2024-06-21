package seleniumUtils;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import utils.PropertiesReader;

public class DriverFactory {

	// Returns driver based on specified browser.
	public static WebDriver createDriver(String browser) {
		if (PropertiesReader.readProperty("selenium.launch.mode").equalsIgnoreCase("remote"))
			// For Selenium Grid (RemoteWebDriver).
			return createRemoteDriver(browser);
		else
			return createWebDriver(browser);
	}

	public static WebDriver createWebDriver(String browser) {
		switch (browser.toLowerCase()) {
		case "edge":
			EdgeOptions edgeOptions = new EdgeOptions();
			// Makes selenium to not wait until the page loads since we are manually taking
			// care of the page loads.
			edgeOptions.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "none");
			return new EdgeDriver(edgeOptions);

		case "chrome":
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "none");
			return new ChromeDriver(chromeOptions);

		default:
			return null;
		}
	}

	public static WebDriver createRemoteDriver(String browser) {
		// URL of the Hub
		String hubURL = PropertiesReader.readProperty("selenium-grid.hub.url");

		WebDriver driver = null;
		try {
			driver = new RemoteWebDriver(new URL(hubURL),
					browser.equalsIgnoreCase("chrome") ? new ChromeOptions() : new EdgeOptions());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return driver;
	}
}
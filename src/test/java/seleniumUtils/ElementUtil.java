package seleniumUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.PropertiesReader;

public class ElementUtil {

	WebDriver driver;
	JavascriptExecutor jExecutor;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jExecutor = (JavascriptExecutor) driver;
	}

	// To wait till ready-state of a web-page is complete.
	public boolean waitUntillLoadedPage() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		try {
			// 'until' method executes the logic in apply() method from
			// DocumentReadyStateExpectedCondition till it returns true or timeout duration
			// is
			// exceeded.
			wait.until(new DocumentReadyStateExpectedCondition());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// Wait & verify an element.
	public boolean verifyElement(WebElement element, Duration... timeOut) {
		waitUntillLoadedPage();
		WebDriverWait wait = new WebDriverWait(driver, timeOut.length == 0 ? Duration.ofSeconds(10) : timeOut[0]);
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean verifyElementLocator(By locator) {
		waitUntillLoadedPage();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// Scroll untill an element is found on the scrollable element.
	public boolean scrollToElement(WebElement element, WebElement scrollableElement) {
		waitUntillLoadedPage();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		try {
			wait.until(new ScrollUntillElementFound(element, scrollableElement));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean scrollToAndVerifyElement(WebElement element, WebElement scrollableElement) {
		scrollToElement(element, scrollableElement);
		return verifyElement(element);
	}

	// Click on an element till presence of some other element.
	public boolean clickUntilPresenceOfElement(WebElement clickableElement, WebElement element) {
		int timer = 0;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(1500));
		while (timer++ != 10) {
			try {
				wait.until(ExpectedConditions.elementToBeClickable(clickableElement));
				;
				clickableElement.click();
				wait.until(ExpectedConditions.visibilityOf(element));
				return true;
			} catch (Exception ignored) {
			}
		}
		return false;
	}

	// Clicks the click-able element until a new element is visible on the page.
	public boolean clickUntilPresenceOfElementLocator(WebElement clickableElement, By locator) {
		int timer = 0;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(1500));
		while (timer++ != 10) {
			try {
				wait.until(ExpectedConditions.elementToBeClickable(clickableElement));
				clickableElement.click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
				return true;
			} catch (Exception ignored) {
			}
		}
		return false;
	}

	public WebElement findAndVerifyElement(WebElement element, By locator) {
		waitUntillLoadedPage();
		try {
			return element.findElement(locator);
		} catch (Exception e) {
			return null;
		}
	}

	public WebElement findAndVerifyElement(WebDriver driver, By locator) {
		waitUntillLoadedPage();
		try {
			return driver.findElement(locator);
		} catch (Exception e) {
			return null;
		}
	}

	public List<WebElement> findAndVerifyElements(WebElement element, By locator) {
		waitUntillLoadedPage();
		try {
			return element.findElements(locator);
		} catch (Exception e) {
			return null;
		}
	}

	public List<WebElement> findAndVerifyElements(WebDriver driver, By locator) {
		waitUntillLoadedPage();
		try {
			return driver.findElements(locator);
		} catch (Exception e) {
			return null;
		}
	}

	// Causes the current thread to sleep for specified time.
	public static void sleep(int durationInMillis) {
		try {
			Thread.sleep(durationInMillis);
		} catch (Exception ignored) {
		}
	}

	// Produces a 2d array by reading the cell values of all the row elements.
	public String[][] readTableRows(List<WebElement> tableRows)
			throws StaleElementReferenceException, NullPointerException {

		ElementUtil elementUtil = new ElementUtil(driver);
		WebElement firstRowElement = tableRows.get(0);
		List<WebElement> firstRowCellElements = elementUtil.findAndVerifyElements(firstRowElement,
				By.xpath("./td | ./th"));

		// Creating a 2d string array matching the size of table.
		String[][] tableData = new String[tableRows.size()][firstRowCellElements.size()];

		for (int i = 0; i < tableRows.size(); i++) {
			WebElement tableRowElement = tableRows.get(i);
			List<WebElement> rowCellElements = elementUtil.findAndVerifyElements(tableRowElement,
					By.xpath("./td | ./th"));
			for (int j = 0; j < rowCellElements.size(); j++) {
				tableData[i][j] = rowCellElements.get(j).getText();
			}
		}
		return tableData;
	}

	// Highlights an element in red.
	public void highlightElement(WebElement element) {
		jExecutor.executeScript("arguments[0].style.border='4px solid red'", element);
	}

	public void undoHighlightElement(WebElement element) {
		jExecutor.executeScript("arguments[0].style.border='0px solid red'", element);
	}

	// Takes screenshot and returns the path where it is saved.
	public static String takeScreenshot(WebDriver driver, String browserName, String fileName) {

		Path screenshotSavePath = Paths.get(PropertiesReader.readProperty("screenshots.path") + File.separator
				+ fileName + "_" + browserName + ".png");

		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File screenShot = takesScreenshot.getScreenshotAs(OutputType.FILE);

		try {
			Files.move(screenShot.toPath(), screenshotSavePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return screenshotSavePath.toString();
	}
}

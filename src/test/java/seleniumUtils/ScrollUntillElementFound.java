package seleniumUtils;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ScrollUntillElementFound implements ExpectedCondition<Boolean> {

	static int scrollAmount = 500;
	WebElement element, scrollableElement;

	public ScrollUntillElementFound(WebElement element, WebElement scrollableElement) {
		this.element = element;
		this.scrollableElement = scrollableElement;
	}

	// Logic to keep scrolling untill an element has loaded and locatable by the
	// driver.
	public Boolean apply(WebDriver driver) {
		JavascriptExecutor jExecutor = (JavascriptExecutor) driver;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(50));

		// Scrolls to the top most region of the scrollable element / page.
		jExecutor.executeScript("arguments[0].scrollTop = 0;", scrollableElement);

		Number prevScrollPosition = 0d, scrollPosition = 0d;

		while (true) {
			// Scrolls on the element / page by a certain amount.
			jExecutor.executeScript("arguments[0].scrollTop += arguments[1];", scrollableElement, scrollAmount);
			// Getting the current scroll position.
			scrollPosition = ((Number) jExecutor.executeScript("return arguments[0].scrollTop;", scrollableElement))
					.doubleValue();
			if (prevScrollPosition.equals(scrollPosition))
				// returns true when the scrollPosition hasn't changed since already max scroll
				// value has reached.
				return true;
			try {
				// until method throws an exception when the duration timeout is exceeded.
				wait.until(ExpectedConditions.visibilityOf(element));
				jExecutor.executeScript(
						"arguments[0].scrollIntoView({ behavior: 'auto', block: 'center', inline: 'center' });",
						element);
				return true;
			} catch (Exception ignored) {
			}
			// updating prevScrollPosition value.
			prevScrollPosition = scrollPosition;
		}
	}
}

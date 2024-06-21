package utils;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogUtil {
	private static final Logger logger = Logger.getLogger(LogUtil.class);

	// Initializes and configures the logger.
	public static void initializeLogger() {
		PropertyConfigurator.configure("src/test/resources/log4j.properties");
	}

	public static void info(String message) {
		logger.info(message);
	}

	public static void warn(String message) {
		logger.warn(message);
	}

	public static void error(String message) {
		logger.error(message);
	}
}
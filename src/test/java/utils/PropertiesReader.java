package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

	// Used to read properties from mentioned properties file.
	public static String readProperty(String key) {
		Properties properties = new Properties();

		try (InputStream input = new FileInputStream("src/test/resources/config.properties")) {
			properties.load(input);
			return properties.getProperty(key);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return null;
	}
}
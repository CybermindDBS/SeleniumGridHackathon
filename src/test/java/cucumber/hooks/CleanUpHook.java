package cucumber.hooks;

import java.io.File;

import io.cucumber.java.BeforeAll;
import utils.PropertiesReader;

public class CleanUpHook {

	// Method annotated with @BeforeAll to run before all scenarios
	@BeforeAll
	public static void cleanUp() {

		// Delete contents of specific directories and files specified in the properties
		// file
		deleteDirectoryContents(PropertiesReader.readProperty("screenshots.path"));
		deleteDirectoryContents(PropertiesReader.readProperty("cucumber.reports.path"));
		deleteDirectoryContents(PropertiesReader.readProperty("cucumber.retest-reports.path"));
		deleteDirectoryContents(PropertiesReader.readProperty("logs.path"));
		deleteFile(PropertiesReader.readProperty("paymentDetailsExcelFile.path"));
	}

	// Method to delete contents of a directory
	public static void deleteDirectoryContents(String path) {
		File directory = new File(path);
		if (directory.isDirectory()) {
			for (File file : directory.listFiles()) {
				if (file.isFile()) {
					file.delete();
				}
			}
		}
	}

	// Method to delete a single file
	public static void deleteFile(String path) {
		try {
			new File(path).delete();
		} catch (Exception ignored) {
		}
	}
}

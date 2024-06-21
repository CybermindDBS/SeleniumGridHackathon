package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	private File excelFile;
	private XSSFWorkbook workBook;

	// input parameters, path: path to the excel file, data: 2d array representing
	// the cells of an excel sheet.
	public void create(String path, String sheetName, String[][] data) {
		excelFile = new File(path);
		try (FileOutputStream fos = new FileOutputStream(excelFile)) {

			workBook = new XSSFWorkbook();
			// creating a new sheet named "Test Data"
			XSSFSheet sheet = workBook.createSheet(sheetName);

			for (int i = 0; i < data.length; i++) {
				XSSFRow row = sheet.createRow(i);
				for (int j = 0; j < data[0].length; j++) {
					XSSFCell cell = row.createCell(j);
					// Writes value to the corresponding cell represented by the i & j for loops.
					cell.setCellValue(data[i][j]);
				}
			}
			// Writes the data into the excel file.
			workBook.write(fos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// For initializing the variables for a given excel file.
	public void load(String path) {
		try (FileInputStream fis = new FileInputStream(new File(path))) {

			workBook = new XSSFWorkbook(fis);
			excelFile = new File(path);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Reads a string from the mentioned rowNo, columnNo and sheet.
	public String read(int rowNo, int columnNo, int sheetIndex) {
		XSSFSheet sheet = workBook.getSheetAt(sheetIndex);
		XSSFRow row = sheet.getRow(rowNo);
		if (row == null)
			// return null when row not found.
			return null;
		XSSFCell cell = row.getCell(columnNo);
		if (cell == null)
			// return null when cell not found.
			return null;
		// Converting the cell value to string and returning it.
		return cell.toString();
	}
}
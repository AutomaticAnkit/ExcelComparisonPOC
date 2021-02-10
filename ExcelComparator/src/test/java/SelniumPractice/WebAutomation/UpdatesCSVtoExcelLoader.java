package SelniumPractice.WebAutomation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import antlr.debug.NewLineEvent;

public class UpdatesCSVtoExcelLoader {

	private static void convertSelectedSheetInXLXSFileToCSV(File xlsxFile, int sheetIdx) throws Exception {

		FileInputStream fileInStream = new FileInputStream(xlsxFile);
		FileOutputStream fileOut = new FileOutputStream("C:\\Users\\ankit\\Desktop\\Excel\\Book1CSV.csv");

		// Open the xlsx and get the requested sheet from the workbook
		XSSFWorkbook workBook = new XSSFWorkbook(fileInStream);
		XSSFSheet selSheet = workBook.getSheetAt(sheetIdx);

		// Iterate through all the rows in the selected sheet
		Iterator<Row> rowIterator = selSheet.iterator();
	
		while (rowIterator.hasNext()) {

			Row row = rowIterator.next();

			// Iterate through all the columns in the row and build ","
			// separated string
			Iterator<Cell> cellIterator = row.cellIterator();
			StringBuffer sb = new StringBuffer();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();

				switch (cell.getCellType()) {
				case STRING:
					sb.append(cell.getStringCellValue() + ",");
					break;
				case NUMERIC:
					sb.append(cell.getNumericCellValue() + ",");
					break;
				case BOOLEAN:
					sb.append(cell.getBooleanCellValue() + ",");
					break;

				default:

				}

			}

			System.out.println(sb.toString());
			fileOut.write(sb.toString().getBytes());

		}

		fileOut.close();

	}

	public static void main(String[] args) throws Exception {
		File myFile = new File("C:\\Users\\ankit\\Desktop\\Excel\\Book1.xlsx");
		int sheetIdx = 0; // 0 for first sheet

		convertSelectedSheetInXLXSFileToCSV(myFile, sheetIdx);
	}
}
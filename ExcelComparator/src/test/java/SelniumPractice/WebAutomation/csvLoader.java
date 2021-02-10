package SelniumPractice.WebAutomation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellRange;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.cellwalk.CellWalk;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class csvLoader {

	public static void main(String[] args) throws IOException {

		String configPropertyFilePath = "C:\\Users\\ankit\\Desktop\\selenium\\workspace\\WebAutomation\\src\\test\\java\\SelniumPractice\\WebAutomation\\config.properties";
		String envPropertyFilePath = "C:\\Users\\ankit\\Desktop\\selenium\\workspace\\WebAutomation\\src\\test\\java\\SelniumPractice\\WebAutomation\\env.properties";

		FileInputStream fis = new FileInputStream(configPropertyFilePath);
		Properties prop = new Properties();
		prop.load(fis);

		FileInputStream envPropfile = new FileInputStream(envPropertyFilePath);
		Properties envP = new Properties();
		envP.load(envPropfile);

		int t1NoOfCols = Integer.parseInt(prop.getProperty("noOfColumnsInTable1"));
		int t2NoOfCols = Integer.parseInt(prop.getProperty("noOfColumnsInTable2"));

		int t1NoOfRows = Integer.parseInt(prop.getProperty("noOfRowsInTable1"));
		int t2NoOfRows = Integer.parseInt(prop.getProperty("noOfRowsInTable2"));

		String t1FirstColHeader = prop.getProperty("firstColumnHeaderTable1");
		String t1LastColHeader = prop.getProperty("LastColumnHeaderTable1");

		String t2FirstColHeader = prop.getProperty("firstColumnHeaderTable2");
		String t2LastColHeader = prop.getProperty("LastColumnHeaderTable2");

		String path = envP.getProperty("folderPathforInputExcel");
		String inputExcelFileName = envP.getProperty("inputExcelFileName");
		String ouputCSVFileName = envP.getProperty("ouputCSVFileName");

		StringBuffer table1 = csvGenrator(t1FirstColHeader, t1LastColHeader, t1NoOfCols, t1NoOfRows);
		StringBuffer table2 = csvGenrator(t2FirstColHeader, t2LastColHeader, t2NoOfCols, t2NoOfRows);

		StringBuffer finalData = table1.append(table2);

		FileOutputStream fileOut = new FileOutputStream(path + ouputCSVFileName);
		System.out.println(finalData);
		fileOut.write(finalData.toString().getBytes());
		fileOut.close();

	}

	public static StringBuffer csvGenrator(String firstHeader, String lastHeader, int numberOfCOlumns, int numberOfRows)
			throws IOException {

		FileInputStream fileInStream = new FileInputStream("C:\\Users\\ankit\\Desktop\\Excel\\Book1.xlsx");

		int rowcount = 1;

		// Open the xlsx and get the requested sheet from the workbook
		XSSFWorkbook workBook = new XSSFWorkbook(fileInStream);
		// Get Sheet from WorkBook
		XSSFSheet s1 = workBook.getSheetAt(0);

		// String buffer to be written in CSV file
		StringBuffer sb = new StringBuffer();

		// Get number of rows from sheet 0
		int rc = s1.getLastRowNum();
//		System.out.println("Last Number of Rows" + rc);

		// start iteration in rowise
		for (int i = s1.getFirstRowNum(); i < rc; i++) {
//			System.out.println("Value of i for the row" + i);

			if (s1.getRow(i) != null) {

				// Get Number of cell in the first row i=0
				int cc = s1.getRow(i).getLastCellNum();
//			System.out.println("last of cell" + cc);

				// Start iteration in the first row
				for (int j = 0; j < cc; j++) {

					if (s1.getRow(i).getCell(j) != null) {

						int temp = j;

						if ((s1.getRow(i).getCell(j)) != null
								&& (s1.getRow(i).getCell(j + (numberOfCOlumns - 1))) != null) {
//						System.out.println("Not null validation for j"+s1.getRow(i).getCell(j));

							if ((s1.getRow(i).getCell(j).getCellType() == s1.getRow(i).getCell(j).getCellType().NUMERIC)
									|| (s1.getRow(i).getCell(j + (numberOfCOlumns - 1)).getCellType() == s1.getRow(i)
											.getCell(j + (numberOfCOlumns - 1)).getCellType().NUMERIC)) {
//					System.out.println("value is numeric"+s1.getRow(i).getCell(j));
							} else if (((s1.getRow(i).getCell(j).getStringCellValue()).equals(firstHeader))
									&& ((s1.getRow(i).getCell(j + (numberOfCOlumns - 1)).getStringCellValue())
											.equals(lastHeader))) {

								for (int k = 0; k < numberOfCOlumns;) {
//							System.out.println("j values is--------- " + j);
									Cell c1 = s1.getRow(i).getCell(j);

//							System.out.println(c1);
//							System.out.println("k value is under k loop " + k);
//							System.out.println("i value is under k loop" + i);

									if (c1 != null) {
										switch (c1.getCellType()) {

										case STRING:
//									System.out.println("k value is under switch string" + k);
//									System.out.println("i value is under switch string" + i);

											sb.append(c1.getStringCellValue() + ",");
											break;
										case NUMERIC:
//									System.out.println("k value is under switch numeric" + k);
//									System.out.println("i value is under switch numeric" + i);

											sb.append(c1.getNumericCellValue() + ",");
											break;
										case BOOLEAN:
											sb.append(c1.getBooleanCellValue() + ",");
											break;

										case _NONE:
											break;

										case BLANK:
											break;

										default:
											break;
										}
									}
									k++;
									j++;
//							System.out.println("J value is "+j);
									if (k % numberOfCOlumns == 0 && rowcount != numberOfRows) {
//								System.out.println("in rowcount loop k value" + k);
										rowcount++;
										k = 0;
										j = temp;
										i++;
//								System.out.println("in rowcount loop i value" + i);
									}
								}

								break;

							}

						}

					}

				}
			}

		}

		return sb;
//		System.out.println(sb.toString());
//		fileOut.write(sb.toString().getBytes());
//		fileOut.close();
	}
}

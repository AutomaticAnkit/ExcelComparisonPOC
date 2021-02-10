package SelniumPractice.WebAutomation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opencsv.CSVReaderBuilder;

public class csvUtils {
	static String configPropertyFilePath = "C:\\Users\\ankit\\Desktop\\selenium\\workspace\\ExcelComparator\\src\\test\\java\\SelniumPractice\\WebAutomation\\config.properties";
	static String envPropertyFilePath = "C:\\Users\\ankit\\Desktop\\selenium\\workspace\\ExcelComparator\\src\\test\\java\\SelniumPractice\\WebAutomation\\env.properties";

 public static void masterCSVGenrator(String fileName) throws IOException {
	
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
//			String inputExcelFileName = envP.getProperty("inputExcelFileName");
			String inputExcelFileName = fileName;
			String ouputCSVFileName = envP.getProperty("ouputCSVFileName");
	
			StringBuffer table1 = tabletoStringGenrator(inputExcelFileName,t1FirstColHeader, t1LastColHeader, t1NoOfCols, t1NoOfRows);
			
			System.out.println("PFB, the table data from table 1.");
			System.out.println(table1+"\n");
			
			StringBuffer table2 = tabletoStringGenrator(inputExcelFileName,t2FirstColHeader, t2LastColHeader, t2NoOfCols, t2NoOfRows);
			
			System.out.println("PFB, the table data from table 2.");
			System.out.println(table2+"\n");
	
			StringBuffer finalData = table1.append(table2);
			
			System.out.println("CSV File generated on the Below Location : - ");
			System.out.println(path + inputExcelFileName+"\n");
	
			FileOutputStream fileOut = new FileOutputStream(path + inputExcelFileName+"CSV.csv");
			
			
			fileOut.write(finalData.toString().getBytes());
			fileOut.close();

		 
	 
 }
	
	
	
	public static StringBuffer tabletoStringGenrator( String fileName, String firstHeader, String lastHeader, int numberOfCOlumns, int numberOfRows)
			throws IOException {
		
		FileInputStream fis = new FileInputStream(configPropertyFilePath);
		Properties prop = new Properties();
		prop.load(fis);

		FileInputStream envPropfile = new FileInputStream(envPropertyFilePath);
		Properties envP = new Properties();
		envP.load(envPropfile);

//		String inputExcelFileName = envP.getProperty("inputExcelFileName");
		String path = envP.getProperty("folderPathforInputExcel");
		
		FileInputStream fileInStream = new FileInputStream(path+fileName+".xlsx");
		
		

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



	public static void csvComparison() throws IOException {

		FileInputStream envPropfile = new FileInputStream(envPropertyFilePath);
		Properties envP = new Properties();
		envP.load(envPropfile);

		String path = envP.getProperty("folderPathforInputExcel");
		String file1 = envP.getProperty("inputExcelFileName")+"CSV.csv";
		String file2 = envP.getProperty("inputExcelTemplate")+"CSV.csv";
//		String file3 = envP.getProperty("ResultCSVFileName")+"CSV.csv";
		String file3 ="Book4CSV.csv";
		ArrayList al1 = new ArrayList();
		ArrayList al2 = new ArrayList();

		FileInputStream fis = new FileInputStream(configPropertyFilePath);
		Properties prop = new Properties();
		prop.load(fis);

		int table1ColCount = Integer.parseInt(prop.getProperty("noOfColumnsInTable1"));
		int table2ColCount = Integer.parseInt(prop.getProperty("noOfColumnsInTable2"));

		FileWriter writer = new FileWriter(path  + file3);
		// ArrayList al3=new ArrayList();

		BufferedReader CSVFile1 = new BufferedReader(new FileReader(path  + file1));
		String dataRow1 = CSVFile1.readLine();
		while (dataRow1 != null) {
			String[] dataArray1 = dataRow1.split(",");
			for (String item1 : dataArray1) {
				al1.add(item1);
			}

			dataRow1 = CSVFile1.readLine(); // Read next line of data.
		}

		CSVFile1.close();

		BufferedReader CSVFile2 = new BufferedReader(new FileReader(path  + file2));
		String dataRow2 = CSVFile2.readLine();
		while (dataRow2 != null) {
			String[] dataArray2 = dataRow2.split(",");
			for (String item2 : dataArray2) {
				al2.add(item2);

			}
			dataRow2 = CSVFile2.readLine(); // Read next line of data.
		}
		CSVFile2.close();

		boolean tableFlag = false;
		int t1VarianceCol1 = Integer.parseInt(prop.getProperty("tolranceValueTable1Col1"));
		int t1VarianceCol2 = Integer.parseInt(prop.getProperty("tolranceValueTable1Col2"));
		int t1VarianceCol3 = Integer.parseInt(prop.getProperty("tolranceValueTable1Col3"));
		int t1VarianceCol4 = Integer.parseInt(prop.getProperty("tolranceValueTable1Col4"));
		int t1VarianceCol5 = Integer.parseInt(prop.getProperty("tolranceValueTable1Col5"));

		int t2VarianceCol1 = Integer.parseInt(prop.getProperty("tolranceValueTable2Col1"));
		int t2VarianceCol2 = Integer.parseInt(prop.getProperty("tolranceValueTable2Col2"));
		int t2VarianceCol3 = Integer.parseInt(prop.getProperty("tolranceValueTable2Col3"));
		int t2VarianceCol4 = Integer.parseInt(prop.getProperty("tolranceValueTable2Col4"));

		int var = 0;

		for (int i = 0; i < al1.size();) {

			if ((al1.get(i).equals(prop.getProperty("firstColumnHeaderTable1"))
					&& al1.get(i + table1ColCount - 1).equals(prop.getProperty("LastColumnHeaderTable1")))
					|| (al1.get(i).equals(prop.getProperty("firstColumnHeaderTable2"))
							&& al1.get(i + table2ColCount - 1).equals(prop.getProperty("LastColumnHeaderTable2")))) {

				if (al1.get(i).equals(prop.getProperty("firstColumnHeaderTable1"))
						&& al1.get(i + table1ColCount - 1).equals(prop.getProperty("LastColumnHeaderTable1"))) {
					tableFlag = true;
//					System.out.println("value of i in first table before for loop " + i);
					for (int k = i; k < table1ColCount;) {
						writer.append("" + al1.get(k));
						writer.append(",");
						k++;
						i++;
					}

//					System.out.println("value of i in after first table before for loop " + i);
				} else if (al1.get(i).equals(prop.getProperty("firstColumnHeaderTable2"))
						&& al1.get(i + table2ColCount - 1).equals(prop.getProperty("LastColumnHeaderTable2"))) {
//					System.out.println("value of i in second table before for loop " + i);
					tableFlag = false;
					int counter = i + table2ColCount;
					for (int k = i; k < counter;) {

						writer.append("" + al1.get(k));
						writer.append(",");
						k++;
						i++;

					}
//					System.out.println("value of i in after second table before for loop " + i);

				}

			} else if (al1.get(i).equals(al2.get(i))) {

//				System.out.println(al1.get(i) + " == " + al2.get(i) + "i value " + i);
//				System.out.println();
				writer.append("" + "Pass");
				writer.append(",");
				i++;
			} else {

				if (tableFlag)

				{

//					System.out.println(tableFlag + "value of table flag");
//					System.out.println("i value in variance part and values are not equal" + i);
					if (i % table1ColCount == 0) {
						var = t1VarianceCol1;
//						System.out.println("var is 0" + "i value " + i + " " + table1ColCount);
					} else if (i % table1ColCount == 1) {
						var = t1VarianceCol2;
//						System.out.println("var is 1" + "i value " + i + "  " + table1ColCount);
					} else if (i % table1ColCount == 2) {
						var = t1VarianceCol3;
//						System.out.println("var is 2" + "i value " + i + "  " + table1ColCount);

					} else if (i % table1ColCount == 3) {
						var = t1VarianceCol4;
//						System.out.println("var is 3" + "i value " + i + "  " + table1ColCount);
					} else if (i % table1ColCount == 4) {
						var = t1VarianceCol5;
//						System.out.println("var is 4" + "i value " + i + "  " + table1ColCount);
					}

				} else {
//				{	System.out.println("i value for table 2 and values are not equal"+i);
					if (i % table2ColCount == 3) {
						var = t2VarianceCol1;
//						System.out.println("vart2 is 1" + "i value " + i + "  " + table2ColCount);
					} else if (i % table2ColCount == 0) {
						var = t2VarianceCol2;
//						System.out.println("vart2 is 2" + "i value " + i + "  " + table2ColCount);
					} else if (i % table2ColCount == 1) {
						var = t2VarianceCol3;
//						System.out.println("vart2 is 3" + "i value " + i + "  " + table2ColCount);
					} else if (i % table2ColCount == 2) {
						var = t2VarianceCol4;
//						System.out.println("vart2 is 4" + "i value " + i + "  " + table2ColCount);
					}

				}

//				System.out.println(al1.get(i) + " != " + al2.get(i) + "value of i is " + i);
				if (envP.getProperty("runWithTol").equalsIgnoreCase("Yes")) {
					double diff = 0;
					String elmentt1 = (String) al1.get(i);
//					System.out.println(elmentt1);
					double e1 = Double.parseDouble(elmentt1);

					String element2 = (String) al2.get(i);
//					System.out.println(element2);
					double e2 = Double.parseDouble(element2);

					diff = e1 - e2;
					if(e1<e2) {
						if(e1+var==e2) {
							writer.append("" + "Pass with Variance: "+var);
							writer.append(",");
							i++;
						}else {
							writer.append("" + "Fail");
							writer.append(",");
							i++;
						}
					}else {
						if(e1-var==e2) {
							writer.append("" + "Pass with Variance: "+var);
							writer.append(",");
							i++;
						}else {
							writer.append("" + "Fail");
							writer.append(",");
							i++;
						}
					}
//					System.out.println(diff + "diff between values");

//					if (diff <= var || diff >= var) {
//						System.out.println("diff is more or less  than var");
//						writer.append("" + "Fail");
//						writer.append(",");
//						i++;
//					}
//
//					else {
//						System.out.println("diff is accepatable");
//						writer.append("" + "Pass with Variance: "+var);
//						writer.append(",");
//						i++;
//					}

				}else {
					
					writer.append("" + al1.get(i));
					writer.append(",");
					i++;
				}

				
			}

		}
		writer.flush();
		writer.close();

		System.out.println("File Created Successfully.");
		System.out.println("PLease Check the File on Below Location");
		System.out.println(path + "\\" + file3);

		for (Object bs : al2) {
			al1.remove(bs);
		}

		int size = al1.size();
		System.out.println("Number of Values found diff are  " + size);
		System.out.println(" ");

	}

	public static void csvtoExcelCOnverion() throws IOException {
		// Data from CSV inserted into array

		FileInputStream fis = new FileInputStream(configPropertyFilePath);
		Properties prop = new Properties();
		prop.load(fis);

		FileInputStream envPropfile = new FileInputStream(envPropertyFilePath);
		Properties envP = new Properties();
		envP.load(envPropfile);
		String finalExcelFolderPath = envP.getProperty("finalExcelFolderPath");
		String finalOutputExcelFile = envP.getProperty("finalOutputExcelFile");

		String[] line;

		// Row increment
		int r = 0;

		// No. of columns in tables
//				int table1ColCount = 5;
//				int table2ColCount = 4;

		int table1ColCount = Integer.parseInt(prop.getProperty("noOfColumnsInTable1"));
		int table2ColCount = Integer.parseInt(prop.getProperty("noOfColumnsInTable2"));

		// No of row in table
		int table1RowCount = Integer.parseInt(prop.getProperty("noOfRowsInTable1"));
		int table2RowCount = Integer.parseInt(prop.getProperty("noOfRowsInTable2"));

		int rowCount = 1;

		Workbook wb = new HSSFWorkbook();
		CreationHelper helper = wb.getCreationHelper();
		Sheet sheet = wb.createSheet("new sheet");

		// Border for Cell
		HSSFCellStyle style = (HSSFCellStyle) wb.createCellStyle();
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);

		// CSV file reader
		CSVReaderBuilder reader = new CSVReaderBuilder(
				new FileReader("C:\\Users\\ankit\\Desktop\\Excel\\Book4CSV.csv"));
		Row row = sheet.createRow((short) r++);
		while ((line = reader.build().readNext()) != null) {
			// Creating a new row
			 row = sheet.createRow((short) r++);
			

			for (int i = 0; i < line.length;) {

				// Checking for A1 and A5
				if (line[i].equals(prop.getProperty("firstColumnHeaderTable1"))
						&& line[i + 4].equals(prop.getProperty("LastColumnHeaderTable1"))) {

					// iteration till the no. of columns in table 1
					for (int k = 0; k < table1ColCount;) {
						if (!line[i].equals(prop.getProperty("firstColumnHeaderTable2"))) {

							Cell cell = row.createCell(k);
							cell.setCellStyle(style);
							cell.setCellValue(helper.createRichTextString(line[i]));
							k++;
							i++;

							// Creating row on inserting every 5th Element in 5th Column
							if (i % 5 == 0 && rowCount != table1RowCount) {
								rowCount++;
								k = 0;
								row = sheet.createRow((short) r++);

							}
						}
					}

				}
				// checking the combination of B1 and B4 for second Table
				else if ((line[i].equals(prop.getProperty("firstColumnHeaderTable2")))
						&& (line[i + 3].equals(prop.getProperty("LastColumnHeaderTable2")))) {

					// Creating two rows.--One for Gap b/w 2 tables and another for data insertion
					row = sheet.createRow((short) r++);
					row = sheet.createRow((short) r++);

					int temp = i;
					rowCount = 1;

					// iteration for 2nd table with respect to number of columns
					for (int k = 0; k < table2ColCount;) {
						Cell cell = row.createCell(k);
						cell.setCellStyle(style);
						cell.setCellValue(helper.createRichTextString(line[i]));
						k++;
						i++;

						if ((i == temp + 4) && rowCount != table2RowCount) {
							rowCount++;
							k = 0;
							row = sheet.createRow((short) r++);
							temp = temp + 4;

						}

					}

				} else {

					break;
				}

			}

			// Write the output to a file
			FileOutputStream fileOut = new FileOutputStream(finalExcelFolderPath + finalOutputExcelFile+".xlsx");
			wb.write(fileOut);
			fileOut.close();
		}

		System.out.println("File Created sucessfully.");
	}
}

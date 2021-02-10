package SelniumPractice.WebAutomation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import antlr.debug.NewLineEvent;

public class ConvertXLSXToCSV {

	private static void convertSelectedSheetInXLXSFileToCSV(File xlsxFile, int sheetIdx) throws Exception {

		FileInputStream fileInStream = new FileInputStream(xlsxFile);
		FileOutputStream fileOut = new FileOutputStream("C:\\Users\\ankit\\Desktop\\Excel\\Book1CSV.csv");
int rowcount=1;
		// Open the xlsx and get the requested sheet from the workbook
		XSSFWorkbook workBook = new XSSFWorkbook(fileInStream);
		XSSFSheet s1 = workBook.getSheetAt(sheetIdx);
		StringBuffer sb = new StringBuffer();
		int rc=s1.getPhysicalNumberOfRows();
		System.out.println("Number of Rows"+rc);
		for(int i=0;i<rc;i++) {
			System.out.println("Value of i for the row"+i);
			int cc=s1.getRow(i).getPhysicalNumberOfCells();
			System.out.println("No of cell"+cc);
			for(int j=0;j<cc;j++) {
				Cell c1 = s1.getRow(i).getCell(j);
//				System.out.println(c1.getStringCellValue());
//				System.out.println(s1.getRow(i).getCell(j+4));
//				System.out.println("Why  i am breaking hwere");
				
				if((c1.getStringCellValue().equals("A1")) && (s1.getRow(i).getCell(j+4).getStringCellValue().equals("A5"))) {				
					System.out.println("i am in A and A5");
					for(int k=0;k<=4;k++) {
						System.out.println(s1.getRow(i).getCell(k));
						if(c1.getCellType().STRING.equals(true)) {
							sb.append((s1.getRow(i).getCell(k).getStringCellValue())+",");
						}
						else if(c1.getCellType().NUMERIC.equals(true)){
							sb.append((s1.getRow(i).getCell(k).getNumericCellValue())+",");
						}
						
						System.out.println(sb.toString());
						if(k%5==0 && rowcount != 3) {
							rowcount++;
							i++;
							k=0;
						}
						else {
							System.out.println("break");
							break;
					}
					
					
				}
			}else {
				System.out.println("not found anything");
			}

		}
		// Iterate through all the rows in the selected sheet
//		Iterator<Row> rowIterator = selSheet.iterator();
//		
//		while (rowIterator.hasNext()) {
//			
//			 {
//				for(int k=0;k<selSheet.getRow(i).getPhysicalNumberOfCells();k++) {
//					if((selSheet.getRow(i).getCell(k).equals("A1"))&&selSheet.getRow(i).getCell(k+4).equals("A5")) {
//						for(int j=0;j<=4;j++) {
//							sb.append((selSheet.getRow(i).get(j)),",");
//						}
//					}
//				}
//			}
//
//			Row row = rowIterator.next();

			// Iterate through all the columns in the row and build ","
			// separated string
//			Iterator<Cell> cellIterator = row.cellIterator();
////			StringBuffer sb = new StringBuffer();
//			while (cellIterator.hasNext()) {
//				Cell cell = cellIterator.next();
//
//				switch (cell.getCellType()) {
//				case STRING:
//					sb.append(cell.getStringCellValue() + ",");
//					break;
//				case NUMERIC:
//					sb.append(cell.getNumericCellValue() + ",");
//					break;
//				case BOOLEAN:
//					sb.append(cell.getBooleanCellValue() + ",");
//					break;
//
//				default:
//
//				}
//
//			}

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
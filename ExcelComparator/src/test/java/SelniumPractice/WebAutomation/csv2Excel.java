package SelniumPractice.WebAutomation;
 
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
 
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
 
import com.opencsv.CSVReader;



 
public class csv2Excel {
	
	public static void main(String[] args) {
    final char FILE_DELIMITER = ',';
    final String FILE_EXTN = ".xlsx";
    final String FILE_NAME = "EXCEL_DATA";
 
    Logger logger = Logger.getLogger(csv2Excel.class);
 
//    public void  convertCsvToXls() {
        SXSSFSheet sheet = null;
        CSVReader reader = null;
        Workbook workBook = null;
        String generatedXlsFilePath = "C:\\Users\\ankit\\Desktop\\Excel\\Book3NewConverted.xlsx";
        FileOutputStream fileOutputStream = null;
        
    	
        
 
        try {
 
            /**** Get the CSVReader Instance & Specify The Delimiter To Be Used ****/
            String[] nextLine;
            reader = new CSVReader(new FileReader("C:\\Users\\ankit\\Desktop\\Excel\\Book3CSV.csv"));
 
            workBook = new SXSSFWorkbook();
            CellStyle cs = workBook.createCellStyle();
    		cs.setWrapText(true);
            sheet = (SXSSFSheet) workBook.createSheet("Sheet");
 
            int rowNum = 0;
            logger.info("Creating New .Xls File From The Already Generated .Csv File");
            while((nextLine = reader.readNext()) != null) {
                Row currentRow = sheet.createRow(rowNum++);
                for(int i=0; i < nextLine.length; i++) {
//                	Cell cell= currentRow.createCell(i);
//                	if(nextLine[i].equals("A4")) {
//                		System.out.println("A4 found");
//                		cell.setCellStyle(cs);
//                		currentRow.createCell(i).setCellValue(Integer.parseInt(nextLine[i]));
//                		
//                	}
                    if(NumberUtils.isDigits(nextLine[i])) {
                        currentRow.createCell(i).setCellValue(Integer.parseInt(nextLine[i]));
                    } else if (NumberUtils.isNumber(nextLine[i])) {
                        currentRow.createCell(i).setCellValue(Double.parseDouble(nextLine[i]));
                    } else {
                        currentRow.createCell(i).setCellValue(nextLine[i]);
                    }
                }
            }
 
//            generatedXlsFilePath = xlsFileLocation1 + FILE_NAME + FILE_EXTN;
            logger.info("The File Is Generated At The Following Location?= " + generatedXlsFilePath);
 
            fileOutputStream = new FileOutputStream(generatedXlsFilePath.trim());
            workBook.write(fileOutputStream);
        } catch(Exception exObj) {
            logger.error("Exception In convertCsvToXls() Method?=  " + exObj);
        } finally {         
            try {
 
                /**** Closing The Excel Workbook Object ****/
                workBook.close();
 
                /**** Closing The File-Writer Object ****/
                fileOutputStream.close();
 
                /**** Closing The CSV File-ReaderObject ****/
                reader.close();
            } catch (IOException ioExObj) {
                logger.error("Exception While Closing I/O Objects In convertCsvToXls() Method?=  " + ioExObj);          
            }
        }
 
//        return generatedXlsFilePath;
//    }   

	}
	}

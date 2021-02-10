package SelniumPractice.WebAutomation;

import java.io.File;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.SpreadsheetMLPackage;

public class ConverXLSXtoXML {

	public static void main(String[] args) throws Docx4JException {
		// TODO Auto-generated method stub
		File file = new File("C:\\Users\\ankit\\Desktop\\Excel\\Book1.xlsx");
		SpreadsheetMLPackage excelMLPackage = SpreadsheetMLPackage.load(file);
		excelMLPackage.save(new File("C:\\Users\\ankit\\Desktop\\Excel\\Book1XML.xml"));
	}

}

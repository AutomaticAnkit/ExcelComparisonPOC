package SelniumPractice.WebAutomation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class ExcelcsvComp extends csvUtils{

	public static void main(String[] args) throws Exception  {
		
		//Converts Excel to CSV file as per the parameters provided in Config file
		
		String configPropertyFilePath = "C:\\Users\\ankit\\Desktop\\selenium\\workspace\\ExcelComparator\\src\\test\\java\\SelniumPractice\\WebAutomation\\config.properties";
		String envPropertyFilePath = "C:\\Users\\ankit\\Desktop\\selenium\\workspace\\ExcelComparator\\src\\test\\java\\SelniumPractice\\WebAutomation\\env.properties";

		FileInputStream fis = new FileInputStream(configPropertyFilePath);
		Properties prop = new Properties();
		prop.load(fis);

		FileInputStream envPropfile = new FileInputStream(envPropertyFilePath);
		Properties envP = new Properties();
		envP.load(envPropfile);
		
		masterCSVGenrator(envP.getProperty("inputExcelFileName"));
		masterCSVGenrator(envP.getProperty("inputExcelTemplate"));


		
//		//Compare Actual and Baseline CSV and Generates a 3rd CSV 
		csvComparison();
		System.out.println("Actual and Baseline CSV comparison completed.\n");
		
//		//Converts Back the CSV to Excel
		csvtoExcelCOnverion();
		System.out.println("csv to Excel conversion completed.\n");
		
	
		
		
		
	}

}

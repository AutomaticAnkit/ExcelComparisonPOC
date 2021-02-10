package SelniumPractice.WebAutomation;

import java.io.*;
import java.util.ArrayList;
//This method will keep the diffrernce of the CSV

/* file1 - file2 = file3*/
public class csvComparator {
	public static void main(String args[]) throws FileNotFoundException, IOException {
		String path = "C:\\Users\\ankit\\Desktop\\Excel";
		String file1 = "Book1CSV.csv";
		String file2 = "Book2CSV.csv";
		String file3 = "Book4CSV.csv";
		ArrayList al1 = new ArrayList();
		ArrayList al2 = new ArrayList();
		int table1Colcount = 5;
		int table2Colcount = 4;

		FileWriter writer = new FileWriter(path + "\\" + file3);
		// ArrayList al3=new ArrayList();

		BufferedReader CSVFile1 = new BufferedReader(new FileReader(path + "\\" + file1));
		String dataRow1 = CSVFile1.readLine();
		while (dataRow1 != null) {
			String[] dataArray1 = dataRow1.split(",");
			for (String item1 : dataArray1) {
				al1.add(item1);
			}

			dataRow1 = CSVFile1.readLine(); // Read next line of data.
		}

		CSVFile1.close();

		BufferedReader CSVFile2 = new BufferedReader(new FileReader(path + "\\" + file2));
		String dataRow2 = CSVFile2.readLine();
		while (dataRow2 != null) {
			String[] dataArray2 = dataRow2.split(",");
			for (String item2 : dataArray2) {
				al2.add(item2);

			}
			dataRow2 = CSVFile2.readLine(); // Read next line of data.
		}
		CSVFile2.close();

		for (int i = 0; i < al1.size();) {
		

			if ((al1.get(i).equals("A1") && al1.get(i + table1Colcount - 1).equals("A5"))
					|| (al1.get(i).equals("B1") && al1.get(i + table2Colcount - 1).equals("B4"))) {

				if (al1.get(i).equals("A1") && al1.get(i + table1Colcount - 1).equals("A5")) {

					for (int k = i; k < table1Colcount;) {
						writer.append("" + al1.get(k));
						writer.append(",");
						k++;
						i++;
					}
				} else if (al1.get(i).equals("B1") && al1.get(i + table2Colcount - 1).equals("B4")) {

					int counter = i + table2Colcount;
					for (int k = i; k < counter;) {

						writer.append("" + al1.get(k));
						writer.append(",");
						k++;
						i++;

					}

				}

			}
			else if (al1.get(i).equals(al2.get(i))) {
				
				System.out.println(al1.get(i) + " == " + al2.get(i));
				writer.append("" + "--");
				writer.append(",");
				i++;
			} else {

				System.out.println(al1.get(i) + " != " + al2.get(i));
				writer.append("" + al1.get(i));
				writer.append(",");
				i++;
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

	}
}

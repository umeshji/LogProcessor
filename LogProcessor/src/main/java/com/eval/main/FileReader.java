package com.eval.main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileReader {
	/*File used for generating the test data (json) */
	public static void main(String[] args) throws IOException {
		 int j=1;
		 BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\Practise\\Person.json"));
		 int val = 12345;
		 for(int i=0;i<1300000;i++,j++) {
			StringBuffer str1 = new StringBuffer("");
			StringBuffer str2 = new StringBuffer("");
			StringBuffer str3 = new StringBuffer("");
			StringBuffer str4 = new StringBuffer("");
			val++;
			String valu = "\""+val+"\",";
			str1.append("{\"id\":\"scsmbstgra").append(j).append("\",\"state\":\"STARTED\",\"type\":\"APPLICATION_LOG\",\"host\":").append(valu).append("\"timestamp\":").append("1491377495212}").append("\n");
			str2.append("{\"id\":\"scsmbstgra").append(j).append("\",\"state\":\"FINISHED\",\"type\":\"APPLICATION_LOG\",\"host\":").append(valu).append("\"timestamp\":").append("1491377495217}").append("\n");
			str3.append("{\"id\":\"scsmbstgrc").append(j).append("\",\"state\":\"FINISHED\"").append(",\"timestamp\":").append("1491377495217}").append("\n");
			str4.append("{\"id\":\"scsmbstgrc").append(j).append("\",\"state\":\"STARTED\"").append(",\"timestamp\":").append("1491377495212}").append("\n");
			
			writer.write(str1.toString());
			writer.write(str2.toString());
			writer.write(str3.toString());
			writer.write(str4.toString());
		}
		writer.close();
	}
	
}

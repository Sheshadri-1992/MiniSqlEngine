package org.gibello.zql;

import java.io.*;

public class TruncateTable {

	public String table_name;
	
	TruncateTable(String table_name_arg)
	{
		table_name=table_name_arg;
//		System.out.println("File name is " + table_name_arg);
	}
	
	public void truncateTable()
	{
		
		
		File file = new File(table_name);
		PrintWriter writer;
		try {
			writer = new PrintWriter(file);
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
	
	}
}

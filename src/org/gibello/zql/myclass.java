package org.gibello.zql;

import java.io.*;

public class myclass {

	public String file_name;
	File file_x;
	FileWriter writer_x;
	
	myclass(String filename)
	{
		file_name = filename;
	}
	
	public void createFile() throws IOException
	{
		file_x = new File(file_name);
		file_x.createNewFile();
		writer_x = new FileWriter(file_x);		
	}
	
	public void writeline(String myline) throws IOException
	{
		writer_x.write(myline);
		writer_x.write("\n");
	}
	
	public void closeFile()
	{
		try {
			writer_x.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("Exception in myclass");
		}
	}
}

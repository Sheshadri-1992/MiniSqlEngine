package org.gibello.zql;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class InsertClass {

	public String statement;
	
	InsertClass(String statement_arg)
	{
		statement = statement_arg;
	}
	
	public void insert()
	{
		//insert now
		statement = statement.replace("(", "  ");
		statement = statement.replace(")", "  ");
		statement = statement.replace(",", "  ");
		statement = statement.replace(";", "");
		statement = statement.replaceAll("\\s+", " ");
		
		
		
		String[] array = statement.split(" ");
		
		String filename = array[2];
		filename = filename.concat(".csv");
//		System.out.println("the file is " + filename);
		BufferedWriter bw = null;
		
		FileWriter fr_2=null;
		try {
			fr_2 = new FileWriter(filename,true);
			bw = new BufferedWriter(fr_2);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try
		{
			int i;
			
		for(i=4;i<array.length-2;i++)
		{
//			System.out.println(array[i] + " , ");
			bw.write(array[i]);
			
			bw.write(",");
			
			
		}
		
		
		bw.write(array[i]);
		bw.newLine();
		bw.flush();
//		bw.write("\n");
		bw.close();
		fr_2.close();


		
		}
		catch(IOException e)
		{
			System.out.println("cannot insert values");
		}
		
	}
}

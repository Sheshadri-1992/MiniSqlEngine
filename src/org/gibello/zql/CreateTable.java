package org.gibello.zql;
import java.io.*;

public class CreateTable {

	String statement;
	
	CreateTable(String st_arg)
	{
		statement=st_arg;
	}
	
	public void processCreateTable()
	{
		System.out.println(statement);
    	statement=statement.replaceAll("\\s+", " ");
    	statement=statement.replaceAll(",", "");

    	statement=statement.replace("("," ");
    	statement=statement.replace(")"," ");
    	statement=statement.replace(";","");
    	statement=statement.replaceAll("\\s+", " ");
    	

//    	
    	String[] myArray = statement.split(" ");
    	
//    	System.out.println("The table name is " + myArray[2]);
    	
    	String new_file = myArray[2];
    	new_file=new_file.concat(".csv");
    	File myFile = new File(new_file);
    	try {
			myFile.createNewFile();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			;
			
		}
    	
//    	System.out.println(myArray.toString());
    	boolean wrong_datatype = false;
    	
//    	System.out.println("The length of thr array is "+myArray.length);
    	for(int i=3;i<myArray.length-1;i=i+2)
    	{
//    		System.out.println("index of i is " + i);
//    		System.out.println("Name = " + myArray[i] + " Value = "+ myArray[i+1]);
    		if(!myArray[i+1].equals("int"))// || !myArray[i+1].equals("INT") || !myArray[i+1].equals("integer") )
    		{
    			wrong_datatype=true;
    		}
    	}
    	
    	if(!wrong_datatype)
    	{
    		//create a file
    		BufferedWriter bw;
    		try {
    			FileWriter fr = new FileWriter("metadata.txt",true);
				bw = new BufferedWriter(fr);

				bw.newLine();
				bw.write("<begin_table>");
				bw.newLine();
				bw.write("<");
				bw.write(myArray[2]);
				bw.write(">");
				bw.newLine();
				
				for(int i=3;i<myArray.length-1;i=i+2)
				{
					bw.write("<");
					bw.write(myArray[i]);
					bw.write(">\n");
				}
				
				bw.write("<end_table>\n");
				bw.flush();
				bw.close();
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    	else
    	{
    		System.out.println("Table semantics wrong");
    	}
	}
	
}

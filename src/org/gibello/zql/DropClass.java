package org.gibello.zql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

public class DropClass {
	
	public String tablename;
	public HashMap<String, Vector<String>> table_hashmap;

	
	DropClass(String tablename_arg)
	{
		tablename = tablename_arg;
		table_hashmap = new HashMap<String, Vector<String>>();
	}
	
	public void dropTable()
	{
		File temporary_file = new File("temp_file.txt");
		try {
			FileWriter writer = new FileWriter(temporary_file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			temporary_file.createNewFile();
			FileWriter myWriter = new FileWriter(temporary_file);
		
			for(Object objname:table_hashmap.keySet())
		    {
//		    	System.out.println(objname);
//		    	System.out.println(table_hashmap.get(objname));
		    	if(objname.toString().equals(tablename))
		    	{
//		    		System.out.println("Found it ");
		    		;
		    	}
		    	else
		    	{
		    		
		    		myWriter.write("\n<begin_table>\n");
		    		myWriter.write("<");
		    		myWriter.write(objname.toString());
		    		myWriter.write(">\n");
		    		for(int i=0;i<table_hashmap.get(objname).size();i++)
		    		{
		    			myWriter.write("<");
		    			myWriter.write(table_hashmap.get(objname).elementAt(i));
		    			myWriter.write(">\n");
		    		}
		    		myWriter.write("<end_table>\n");
		    	}
		    }
			myWriter.close();
			if(temporary_file.renameTo(new File("metadata.txt")))
			{
//				System.out.println("Success ");
				;
			}
			else
			{
//				System.out.println("Failed ");
				;
			}
		  }
		catch(IOException e)
		{
			System.out.println("Exception Caught");
		}
	}
	
	public void createHashMap()
	{
	    FileReader fr=null;
		try {
			fr = new FileReader("metadata.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}//changed here now
	    BufferedReader br = new BufferedReader(fr);
	    
	   
	    String start_tag="begin_table";
	    String end_tag="end_table";
	    String s;	    
	    
	    //second part
		 
		FileReader fr_1=null;
		try {
			fr_1 = new FileReader("metadata.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		    BufferedReader br_1 = new BufferedReader(fr_1);
		    
		    try {
				while((s = br_1.readLine()) != null)
				{
					
					s=s.replaceAll("\\s+", "");
					s=s.replaceAll("<", "");
					s=s.replaceAll(">", "");
					
//       	System.out.println("lets see if this works "+s);        	
					if(s.compareTo(start_tag)==0)
					{
						Vector<String> table_structure = new Vector<String>();
						
//						System.out.println(s);
						s=br_1.readLine();
						s=s.replaceAll("\\s+", "");
				    	s=s.replaceAll("<", "");
				    	s=s.replaceAll(">", "");
				    	String key = s;
						
						while((s=br_1.readLine())!= null && s.compareTo("<end_table>")!=0)
						{
							s=s.replaceAll("\\s+", "");
				        	s=s.replaceAll("<", "");
				        	s=s.replaceAll(">", "");
				        	if( s.compareTo(start_tag)!=0 && s.compareTo(end_tag)!=0)
				        	{
//				        		System.out.println("This is added "+s);
				        		table_structure.add(s);
				        	}
//       			System.out.println(s);
						}
						
						table_hashmap.put(key, table_structure);
					}        	
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				try {
					fr_1.close();
					br_1.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				e.printStackTrace();
			}
		    /** at the end of this table hash map has been creted */
//		    System.out.println("This is called succesfully" + table_hashmap.toString());

	}
	

	
}

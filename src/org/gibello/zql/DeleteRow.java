package org.gibello.zql;

import java.io.*;
import java.util.HashMap;
import java.util.Vector;


public class DeleteRow {

	public String op1;
	public String op2;
	public String operator;
	public String tablename;
	public String statement;
	public FileReader reader_x;
	public BufferedReader br_x;
	public FileWriter writer_x;
	public HashMap<String, Vector<String>> table_hashmap;
	public HashMap<String,Integer> att_pos;
	
	public DeleteRow(String statement_arg) {
		// TODO Auto-generated constructor stub
		statement = statement_arg;
		
		//hash map initialized
		table_hashmap = new HashMap<String, Vector<String>>();
		//attribute position initialized
		att_pos = new HashMap<String,Integer>();
	}
	
	public void deleteLine()
	{
		String filename;
		statement = statement.replaceAll("\\s+", " ");
		statement = statement.replaceAll("=", " = ");
		statement = statement.replaceAll(";", "");

		String[] array = statement.split(" ");
		
		filename = array[2];
		tablename = array[2];
		filename = filename.concat(".csv");
		op1=array[4];
		op2=array[6];
		operator=array[5];
		
		System.out.println(op1 +" "  +  op2 + " " + operator + " " + filename);
		
		File temporary_file = new File("temp_file.txt");
		try {
			FileWriter writer = new FileWriter(temporary_file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ConditionCompare myObj = new ConditionCompare();
		
		try {
			temporary_file.createNewFile();
			FileWriter myWriter = new FileWriter(temporary_file);
			
			reader_x = new FileReader(filename);
			br_x = new BufferedReader(reader_x);
			
			String line = br_x.readLine();
			int position = att_pos.get(op1);
//			System.out.println("position is " + position);
			
			while(line!=null)
			{
				String[] myArray = line.split(",");
				String operand1 = op2;
//				System.out.println("position is " + position);
//				System.out.println("Array length is  " + myArray.length);
				String operand2 = myArray[position];
				
				
				myObj.flag=false;
				boolean flag = myObj.performConditional(Integer.parseInt(operand1), Integer.parseInt(operand2), operator);
				
				if(flag)
				{
					//dont write
					;
				}
				else
				{
					myWriter.write(line);
					myWriter.write("\n");
				}
				
				
				line = br_x.readLine();						
			}
			
			reader_x.close();
			br_x.close();
			myWriter.close();
			
			if(temporary_file.renameTo(new File(filename)))
			{
//				System.out.println("Success ");
				;
			}
			else
			{
//				System.out.println("Failed ");
				;
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("Cannot open / write into file");
		}
		
		
		
	}
	
	public void createAttPos()
	{
		  int count_1=0;
		  
		  String statement_2=statement;
			statement_2 = statement_2.replaceAll("\\s+", " ");
			statement_2 = statement_2.replaceAll("=", " = ");
			statement_2 = statement_2.replaceAll(";", "");

			String[] array = statement.split(" ");

		  
			tablename = array[2];
//			System.out.println("Name of the table is " + tablename);
			
		  int att_size=table_hashmap.get(tablename).size();
//		  System.out.println(att_size);
		  for(int j=0;j<att_size;j++)
		  {
			  att_pos.put(table_hashmap.get(tablename).get(j), count_1);
			  count_1++;
		  }
	}
	
	//after this att_pos is ready
	
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
						
						System.out.println(s);
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
					br_1.close();
					fr_1.close();
					
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

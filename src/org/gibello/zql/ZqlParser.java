/*
 * This file is part of Zql.
 *
 * Zql is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Zql is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Zql.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.gibello.zql;

import java.io.*;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Vector;

/**
 * ZqlParser: an SQL parser
 */
public class ZqlParser {
	



	private static final int CREATE = 0; 	
	private static final int DROP = 1;
	private static final int TRUNCATE = 2;
	private static final int DELETE = 3;
	private static final int INSERT = 4;
	
	
    static boolean star_flag=false;
    static boolean cart_prod=false;
    static boolean where_flag=false;
    static boolean and_flag=false;
    static boolean or_flag=false;
    static boolean distinct_flag=false;
    static boolean project_flag=false;
    static boolean aggregate_flag=false;
    static boolean delete_flag=false;
    static boolean create_flag=false;
    static boolean truncate_flag=false;
    static boolean insert_flag=false;
    static boolean update_flag=false;
    static boolean exit_flag=false;
    
    
    char[] InputStatement;
	
	 

  static ZqlJJParser _parser = null;

  /**
   * Test program:
   * Parses SQL statements from stdin or from a text file.<br>
   * If the program receives one argument, it is an SQL text file name;
   * if there's no argument, the program reads from stdin.
 * @throws IOException 
   */
  public static void main(String args[]) throws  IOException {

    ZqlParser p = null;
//    
    
    while(true)
    {
    	
    
           
    if ( args.length < 1  ) {
      System.out.print("mysql>");
      
      
      p = new ZqlParser(System.in);
//      System.out.println("THIS IS SPARTAAAA " + p.toString());

    } else {

      try {
        p = new ZqlParser(new DataInputStream(new FileInputStream(args[0])));
      } catch (FileNotFoundException e) {
        System.out.println("/* File " + args[0] +
         " not found. Readings from stdin */");
        p = new ZqlParser(System.in);
      }
    } // else ends here

    if ( args.length > 0 ) {
      System.out.println("/* Sheshadri from " + args[0] + "*/");
    }

    /**following this order
     * _______________________________
     * create statement
     * drop statement
     * truncate statement
     * delete statement
     * insert statement
     * _______________________________ 
     * ALL select statement variants
     * select *
     * select aggregate (min,max,sum,avg)
     * select col1,col2 (multiple columns)
     * select distinct (distinct statement)
     * select from multiple tables
     * _______________________________
     **/

//    int choice=-1; //-1 choice is for error handling
    
    star_flag=false;
    cart_prod=false;
    where_flag=false;
    and_flag=false;
    or_flag=false;
    distinct_flag=false;
    project_flag=false;
    aggregate_flag=false;
    delete_flag=false;
    create_flag=false;
    truncate_flag=false;
    insert_flag=false;
    update_flag=false;
    exit_flag=false;
    
    ZStatement st = null;
   
   
    //reading of input is happening here
    try {
		if((st = p.readStatement()) != null) {
			
//			System.out.println(st.toString());
//    	/**Create statement (custom parser needed) **/
			if(st.toString().contains("and") || st.toString().contains("AND"))
				and_flag=true;
			
			if(st.toString().contains("or") || st.toString().contains("OR"))
				or_flag=true;
			
			
			if(st.toString().contains("distinct") || st.toString().contains("DISTINCT"))
			{
				distinct_flag=true;
			}
			
			
//			ZDelete myDelObj = (ZDelete) st;
//			if(myDelObj!=null)
//			{
//				delete_flag=true;
//				System.out.println("Delete is present");
//			}
			if(st.toString().contains("delete") || st.toString().contains("DELETE"))
			{
				delete_flag=true;
//				System.out.println("Delete is present");
			}
			
			if(st.toString().contains("insert") || st.toString().contains("INSERT"))
			{
				update_flag=true;
//				System.out.println("Insert is present");
			}
			if(st.toString().contains("exit;") || st.toString().contains("EXIT;"))
			{
				exit_flag=true;
				System.out.println("EXIT");
			}
			
			
			
		
			
//    	System.out.println("Choice is "+choice);
			
			if(update_flag==false && delete_flag==false)
			{
			
			
			
		    ZQuery myQuery = (ZQuery) st;
		    
		    Vector v= myQuery.getSelect();
		    
		    for(int i=0;i<v.size();i++)
		    {
		    	//System.out.println("Control ins't coming here");
		    	/**This is getting me all select items **/
		        ZSelectItem it = (ZSelectItem)v.elementAt(i);
//		        System.out.print("col=" + it.getColumn() + ",agg=" + it.getAggregate());
		        if(it.getColumn().contentEquals("*"))
		        {
		        	star_flag=true;
		        }
		        if(it.getAggregate()!=null)
		        {
//		        	System.out.println("This is getting set");
		        	aggregate_flag=true;
		        }
		        String s = it.getSchema();
		        if(s != null) 
		        {
		        	//System.out.print(",schema=" + s);
		        	;
		        }
		        s = it.getTable();
		        if(s != null)
		        {
		        	//System.out.print(",table=" + s);
		        	;
		        }
//		        System.out.println();
		    }
		    /**This is getting all FROM items**/
		    
		    Vector v1=myQuery.getFrom();
		    Vector<String> myTables = new Vector<String>();
		    
		    for(int i=0;i<v1.size();i++)
		    {
		    	//System.out.println("Control ins't coming here");
		    	/*This is getting me all select items */
		        ZFromItem it = (ZFromItem)v1.elementAt(i);
		        //not needed
		       // System.out.print("col=" + it.getColumn());
		        String s = it.getSchema();
		        if(s != null)
		        {
		        	//System.out.print(",schema=" + s);
		        	;
		        }
		        s = it.getTable();
		        
		        myTables.add(s);//adding my tables here
		        
		        if(s != null)
		        {
		        	//System.out.print(",table=" + s);
		        	;
		        }
		        System.out.println();
		    }
		    
		    /**Checking for multiple tables **/
		    if(v1.size()>1)
		    {
		    	cart_prod=true;
		    	/**this is just for reference **/
		    	
		    	//for(int i=0;i<myTables.size();i++)
		    	//	System.out.println("Printing tables "+ myTables.get(i));
		    }
		    
		    /**This is getting all WHERE items**/
		    
		    ZExpression myExp =(ZExpression) myQuery.getWhere();
		    HashMap<String,Vector<String>> where_args= new HashMap<String,Vector<String>>();
		    
		    Vector<ZExpression> expVector = null;
		    
		    if(myExp == null)
		    {
//		    	System.out.println("nUMBER OF OPerands are 0");
		    	;
		    }
		    else
		    {
		    
		    	int no_of_op = myExp.nbOperands();
//		        System.out.println("nUMBER OF OPerands are "+no_of_op);
		        
		        
		     //   String operator= myExp.getOperator();
		        expVector = myExp.getOperands();//new Vector<ZExpression>(no_of_op);
		        
		
		        
		        if(expVector.size()>0)
		        {
		        	where_flag=true;
		        	for(int i=0;i<expVector.size();i++)
		        	{
//            		System.out.println(expVector.get(i).getOperands()); || expVector.get(i).getOperator().toCharArray().equals("or")
//            		System.out.println(expVector.get(i).getOperator().toString());//.toCharArray()); select max(salary) from employee where age>20;
//            	
		        	}
		        }
		        
//		        System.out.println("This is the size " + expVector.size());
		        
		    }
		    
		    
		    //file reading begins here
		    FileReader fr = new FileReader("metadata.txt");//changed here now
		    BufferedReader br = new BufferedReader(fr);
		    
		    String s;
		    String start_tag="begin_table";
		    String end_tag="end_table";
		    int count=0;
		    while((s = br.readLine()) != null)
		    {
		    	
		    	s=s.replaceAll("\\s+", "");
		    	s=s.replaceAll(">","");
		    	s=s.replaceAll("<", "");
		    	
		    	
		    	
		    	if(s.compareTo(start_tag)==0)
		    	{
//		    		System.out.println("Table is " + s);
		    		count++;
		    	}

		    }
		    
		    fr.close();
		    br.close();
		    
		    FileReader fr_1 = new FileReader("metadata.txt");
		    BufferedReader br_1 = new BufferedReader(fr_1);
		    //completed with the file reading
		    
//        System.out.println("Number of tables are " + count);
		    
		   HashMap<String, Vector<String>> table_hashmap = new HashMap<String, Vector<String>>();
		    
		    while((s = br_1.readLine()) != null)
		    {
		    	
		    	s=s.replaceAll("\\s+", "");
		    	s=s.replaceAll("<", "");
		    	s=s.replaceAll(">", "");
		    	
//        	System.out.println("lets see if this works "+s);        	
		    	if(s.compareTo(start_tag)==0)
		    	{
		    		Vector<String> table_structure = new Vector<String>();
		    		
//		    		System.out.println(s);
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
//		            		System.out.println("This is added "+s);
		            		table_structure.add(s);
		            	}
//        			System.out.println(s);
		    		}
		    		
		    		table_hashmap.put(key, table_structure);
		    	}        	
		    }
		    
		    fr_1.close();//newly added
		    br_1.close();
		    
//		    System.out.println("strings are added " + table_hashmap.size());
		    /**Printing table structure **/
//		    for(Object objname:table_hashmap.keySet())
//		    {
//		    	System.out.println(objname);
//		    	System.out.println(table_hashmap.get(objname));
//		    }
		    
		    
		    
		    /** start from the select **/
		  File file_2 = new File("Output.txt"); //this is the temporary file that we are gonna operate on
		  
		  /**Temporary Files **/
		  File file_3 = new File("temp1.txt");
		  File file_4 = new File("temp2.txt");
		  File file_5 = new File("temp3.txt");
		  
		  file_3.createNewFile();//creates these temp files
		  file_5.createNewFile();
		  
		  FileWriter write_1 = new FileWriter(file_2);
		  FileWriter write_3 = null;
		  FileWriter write_2 = null; //for multi-cross product;
		  file_2.createNewFile();
		  
		  //file writer object
		  
		  FileReader reader_1 = null;
		  FileReader reader_2 = null; //for multi-cross product
		  FileReader reader_3 = null;
		  
		  BufferedReader br_2 = null;  
		  
		  
		  /** This will give me the position of the attributes **/
		  HashMap<String,Integer> att_pos = new HashMap<String,Integer>(); 

		  int count_1=0;
		  for(int i=0;i<myTables.size();i++)
		  {
			  
			  /**these 2 statements are for testing **/
//			  System.out.println(myTables.get(i));
//			  
//			  System.out.println("sfhdsf "+table_hashmap.toString());
			  
			  int att_size=table_hashmap.get(myTables.get(i)).size();
//			  System.out.println(att_size);
			  
			  for(int j=0;j<att_size;j++)
			  {
				  att_pos.put(table_hashmap.get(myTables.elementAt(i)).get(j), count_1);
				  count_1++;
			  }
		  }
		  
//		  System.out.println("Count 1 should be 6 " + count_1);
		  
		  /**printing hashmap **/
//		  System.out.println(att_pos.toString());
		  
		    if(cart_prod==true)
		    {
		    	/**we need to perform multiple Cartesian product **/
//		    	System.out.println("Multiple cartesian needed");
		    	String file_name_csv = myTables.get(0);
		    	file_name_csv=file_name_csv.concat(".csv");
		    	
//		    	System.out.println(file_name_csv);
		    	
		    	reader_1 = new FileReader(file_name_csv);
		    	write_3 = new FileWriter(file_3);
		    	BufferedReader br_x = new BufferedReader(reader_1);
		    	
		    	String temp = br_x.readLine();
		    	
//		    	System.out.println(temp);
		    	while(temp!=null)
		    	{
//        		System.out.println(temp);
		    		write_3.write(temp);
		    		write_3.write("\n");
		    		temp = br_x.readLine();
		    		
		    	}
		    	
//		    	System.out.println("TESTING");
		    	reader_1 = new FileReader("temp1.txt");
		    	br_x = new BufferedReader(reader_1);
		    	
		    	temp = br_x.readLine();
//		    	System.out.println(temp);
		    	while(temp!=null)
		    	{
//        		System.out.println(temp);
		    		write_3.write(temp);
		    		write_3.write("\n");
		    		temp = br_x.readLine();
		    		
		    	}
		    	
		    	br_x.close();
		     	write_3.close();
		    	reader_1.close();
		    	
		    	for(int i=1;i<myTables.size();i++)
		    	{
		    		reader_2 = new FileReader("temp1.txt");
		    		
		    		BufferedReader br_3 = new BufferedReader(reader_2);
		    		String line_outer = br_3.readLine();
//        		System.out.println(line_outer);
		    		
		    		write_2 = new FileWriter(file_5);
		    		line_outer=line_outer.trim();
//		    		System.out.println("The outer line is " + line_outer);
		    		line_outer = line_outer.replaceAll("\"", "");
//		    		line_outer=line_outer.replace("\"", "");
//		    		System.out.println("The outer line is " + line_outer);
		    		while(line_outer!=null)
		    		{
		    			
		    			String file_name = myTables.get(i);
		    			file_name= file_name.concat(".csv");
		    			reader_3 = new FileReader(file_name);
		    			BufferedReader br_4 = new BufferedReader(reader_3);
		    			
		    			String line_inner= br_4.readLine();
//		    			System.out.println("The inner line is " + line_inner);
		    			line_inner=line_inner.trim();
		    			line_inner=line_inner.replaceAll("\"", "");
//		    			System.out.println("The innder line is " + line_inner);
		    			while(line_inner!=null)
		    			{       				
		    				
//        				System.out.println(line_inner);
		    				write_2.write(line_outer);
		    				write_2.write(",");
		    				write_2.write(line_inner);
		    				write_2.write("\n");
//		    				System.out.println("The innder line is " + line_inner);
		    				line_inner= br_4.readLine();
		    				if(line_inner!=null)
		    				{
		    					line_inner=line_inner.trim();
		    					line_inner=line_inner.replaceAll("\"", "");
		    				}
//		    				System.out.println("The innder line is " + line_inner);
		    				
		    			}//end of the inner file 
		    			
		    			line_outer = br_3.readLine();
		    			if(line_outer!=null)
		    			{
		    				line_outer=line_outer.trim();
		    				line_outer = line_outer.replaceAll("\"", "");
		    			}

		    			
		    		}
		    		
		    		reader_2.close();//reader of temp1.txt
		    		br_3.close();
		    		write_2.close();//writer of temp3.txt

		    		
		    		reader_2 = new FileReader("temp3.txt");
		    		br_3 = new BufferedReader(reader_2);

		    		write_2 = new FileWriter(file_3);
		    		
		    		String line_to_temp1 = br_3.readLine();
		    		
		    		while(line_to_temp1!=null)
		    		{
		    			write_2.write(line_to_temp1);
		    			write_2.write("\n");
		    			line_to_temp1 = br_3.readLine();
		    		}
		    		
		    		reader_2.close();
		    		br_3.close();
		    		write_2.close();
		    		
		    		
		    	}
		    	
		    	reader_2 = new FileReader("temp3.txt");
				BufferedReader br_3 = new BufferedReader(reader_2);

				write_2 = new FileWriter(file_2);
				
				String line_to_temp1 = br_3.readLine();
				
				while(line_to_temp1!=null)
				{
					write_2.write(line_to_temp1);
					write_2.write("\n");
					line_to_temp1 = br_3.readLine();
				}
				
				reader_2.close();
				br_3.close();
				write_2.close();
		    	
		    	
		    	
		    }
		    else
		    {
//		    	 System.out.println("Single Cartesian needed");
		    	 String file_name=myTables.get(0);
		    	 
		    	 String extension = ".csv";
		    	 
		    	 file_name=file_name.concat(extension);
		    	 
//		    	 System.out.println("File name is "+ file_name);
		    	 
		    	 
		    	 reader_1 = new FileReader(file_name);
		    	 br_2 = new BufferedReader(reader_1);
		    	 String line = br_2.readLine();
		    	 
		    	 while(line!=null)
		    	 {
		    		 line=line.replace("\"", "");
		    		 write_1.write(line);
		    		 write_1.write("\n");
		    		 line=br_2.readLine();
//		    		 
		    	 }
		    	 reader_1.close();
		    	 br_2.close();
		    	 write_1.close();
		    	
		    } 
		    
		    
		    if(where_flag)
		    {
//		    	System.out.println("Where flag is present");
		    	/**do where conditions **/
		    	
		    	if(star_flag)/**if * is present ignore everything else **/
		        {
//		        	System.out.println("The * flag is present");
		       	 	reader_1 = new FileReader("Output.txt");
		       	 	br_2 = new BufferedReader(reader_1);
		       	 	String line = br_2.readLine();
		       	 
		       	 Vector<String> rows = table_hashmap.get(myTables.get(0));
		    	 for(int i=0;i<rows.size();i++)
		    		 System.out.print(rows.elementAt(i)+" ");
		       	 	
		       	 	while(line!=null)
		       	 	{
//           	 		System.out.println(line);
		           	 	if(and_flag)
		    			{
			            		
			            		//the operands and operator is available
			            		
			            		String operand_1 = expVector.get(0).getOperands().elementAt(0).toString();
			            		String value_op_1 = expVector.get(0).getOperands().elementAt(1).toString();
			            		String operator_op1 = expVector.elementAt(0).getOperator().toString();
			            		
			            		int position = att_pos.get(operand_1);
//		            		System.out.println(operand_1+" position is "+position);
			            		
			            		
			            		String operand_2 = expVector.get(1).getOperands().elementAt(0).toString();
			            		String value_op_2 = expVector.get(1).getOperands().elementAt(1).toString();
			            		String operator_op2 = expVector.elementAt(1).getOperator().toString();
			            		
			            		int position2 = att_pos.get(operand_2);
//		            		System.out.println(operand_2+" position is "+position2);
			            		
			            		String[] array = line.split(",");
			            		
//		            		System.out.println("OP1 The value at position " + array[position] + " OP1 value is " + value_op_1);
//		            		System.out.println("OP2 The value at position " + array[position2] + " OP2 value is " + value_op_2);

			            		//need to apply operator
			            		ConditionCompare cc_1 = new ConditionCompare();
//		            		System.out.println("This class worked");
			            		cc_1.flag=false;
			            		boolean op1_result=cc_1.performConditional(Integer.parseInt(value_op_1), Integer.parseInt(array[position]), operator_op1);
			            		cc_1.flag=false;
			            		boolean op2_result=cc_1.performConditional(Integer.parseInt(value_op_2), Integer.parseInt(array[position2]), operator_op2);

			            		
			            		if(op1_result && op2_result)
			            		{
			            			System.out.println(line);
			            		}
			            		
//		            	}
		    			}
		    			
		    			if(or_flag)
		    			{
//		    				System.out.println("OR flag is on ");
		            		String operand_1 = expVector.get(0).getOperands().elementAt(0).toString();
		            		String value_op_1 = expVector.get(0).getOperands().elementAt(1).toString();
		            		String operator_op1 = expVector.elementAt(0).getOperator().toString();
		            		
		            		int position = att_pos.get(operand_1);
//	            		System.out.println(operand_1+" position is "+position);
		            		
		            		
		            		String operand_2 = expVector.get(1).getOperands().elementAt(0).toString();
		            		String value_op_2 = expVector.get(1).getOperands().elementAt(1).toString();
		            		String operator_op2 = expVector.elementAt(1).getOperator().toString();
		            		
		            		int position2 = att_pos.get(operand_2);
//	            		System.out.println(operand_2+" position is "+position2);
		            		
		            		String[] array = line.split(",");
		            		
//	            		System.out.println("OP1 The value at position " + array[position] + " OP1 value is " + value_op_1);
//	            		System.out.println("OP2 The value at position " + array[position2] + " OP2 value is " + value_op_2);

		            		//need to apply operator
		            		
		            		ConditionCompare cc_1 = new ConditionCompare();
//	            		System.out.println("This class worked");
		            		cc_1.flag=false;
		            		boolean op1_result=cc_1.performConditional(Integer.parseInt(value_op_1), Integer.parseInt(array[position]), operator_op1);
		            		cc_1.flag=false;
		            		boolean op2_result=cc_1.performConditional(Integer.parseInt(value_op_2), Integer.parseInt(array[position2]), operator_op2);
		            		
		            		
		            		if(op1_result || op2_result)
		            		{
		            			System.out.println(line);
		            		}
		    				
		    			}
		    			
		    			if(or_flag==false && and_flag==false)
		    			{
		    				
		    				ZExpression myExp_1= (ZExpression)myQuery.getWhere();
		    				String operand_1 = myExp_1.getOperand(0).toString();
		            		String value_op_1 = myExp_1.getOperand(1).toString();
		            		String operator_op1 = myExp_1.getOperator();
//	            		
		            		int position = att_pos.get(operand_1);
		            		
		            		String[] array = line.split(",");
		            		ConditionCompare cc_1 = new ConditionCompare();
		            		cc_1.flag=false;
		            		boolean op1_result=cc_1.performConditional(Integer.parseInt(value_op_1), Integer.parseInt(array[position]), operator_op1);

		            		if(op1_result)
		            		{
		            			System.out.println(line);
		            		}

		    				

		    			}
		    			
		       	 		line=br_2.readLine();
		       	 	}
		       	 	
		       	 	br_2.close();
		       	 	reader_1.close();
		        	
		        }
		    	else if(aggregate_flag)//check for other types of select
		    	{
		        			/** this means two sets of operators are present 
		           	 		 * need to check for 2 values
		           	 		 * **/
		    				myclass agg_obj = new myclass("aggout.txt");
		    				agg_obj.createFile();
//		        			System.out.println("Aggregate flag is set");
		        			reader_1 = new FileReader("Output.txt");
		               	 	br_2 = new BufferedReader(reader_1);
		               	 	String line = br_2.readLine();
		               	 	          	 	          	 	
		               	 	while(line!=null)
		               	 	{
//	               	 		System.out.println(line);
		    	           	 	if(and_flag)
		    	    			{
		    		            		
		    		            		//the operands and operator is available
		    		            		String operand_1 = expVector.get(0).getOperands().elementAt(0).toString();
		    		            		String value_op_1 = expVector.get(0).getOperands().elementAt(1).toString();
		    		            		String operator_op1 = expVector.elementAt(0).getOperator().toString();
		    		            		
		    		            		int position = att_pos.get(operand_1);
//	    		            		System.out.println(operand_1+" position is "+position);
		    		            		
		    		            		
		    		            		String operand_2 = expVector.get(1).getOperands().elementAt(0).toString();
		    		            		String value_op_2 = expVector.get(1).getOperands().elementAt(1).toString();
		    		            		String operator_op2 = expVector.elementAt(1).getOperator().toString();
		    		            		
		    		            		int position2 = att_pos.get(operand_2);
//	    		            		System.out.println(operand_2+" position is "+position2);
		    		            		
		    		            		String[] array = line.split(",");
		    		            		
				            		
		    		            		ConditionCompare cc_1 = new ConditionCompare();
//	    		            		System.out.println("This class worked");
		    		            		cc_1.flag=false;
		    		            		boolean op1_result=cc_1.performConditional(Integer.parseInt(value_op_1), Integer.parseInt(array[position]), operator_op1);
		    		            		cc_1.flag=false;
		    		            		boolean op2_result=cc_1.performConditional(Integer.parseInt(value_op_2), Integer.parseInt(array[position2]), operator_op2);
		    		            		
//	    		            		System.out.println("bool 1 result is "+op1_result+"bool 2 result is "+op2_result);

		    		            		if(op1_result && op2_result)
		    		            		{    		            			
		    		            			agg_obj.writeline(line);
		    		            		}
		    		            		
		    	    			}
		    	    			
		    	    			if(or_flag)
		    	    			{
//		    	    				System.out.println("OR flag is on ");
		    	            		String operand_1 = expVector.get(0).getOperands().elementAt(0).toString();
		    	            		String value_op_1 = expVector.get(0).getOperands().elementAt(1).toString();
		    	            		String operator_op1 = expVector.elementAt(0).getOperator().toString();
		    	            		
		    	            		int position = att_pos.get(operand_1);
//	    	            		System.out.println(operand_1+" position is "+position);
		    	            		
		    	            		
		    	            		String operand_2 = expVector.get(1).getOperands().elementAt(0).toString();
		    	            		String value_op_2 = expVector.get(1).getOperands().elementAt(1).toString();
		    	            		String operator_op2 = expVector.elementAt(1).getOperator().toString();
		    	            		
		    	            		int position2 = att_pos.get(operand_2);
//	    	            		System.out.println(operand_2+" position is "+position2);
		    	            		
		    	            		String[] array = line.split(",");
		    	            		
//	    	            		System.out.println("OP1 The value at position " + array[position] + " OP1 value is " + value_op_1);
//	    	            		System.out.println("OP2 The value at position " + array[position2] + " OP2 value is " + value_op_2);

		    	            		
		    	            		ConditionCompare cc_1 = new ConditionCompare();
//	    	            		System.out.println("This class worked");
				            		cc_1.flag=false;
		    	            		boolean op1_result=cc_1.performConditional(Integer.parseInt(value_op_1), Integer.parseInt(array[position]), operator_op1);
		    	            		cc_1.flag=false;
		    	            		boolean op2_result=cc_1.performConditional(Integer.parseInt(value_op_2), Integer.parseInt(array[position2]), operator_op2);

		    	            		
		    	            		if(op1_result || op2_result)
		    	            		{
		    	            			agg_obj.writeline(line);
		    	            		}
		    	    				
		    	    			}
		    	    			
		    	    			if(or_flag==false && and_flag==false)
		    	    			{
		    	    				
		    	    				ZExpression myExp_1= (ZExpression)myQuery.getWhere();
		    	    				String operand_1 = myExp_1.getOperand(0).toString();
		    	            		String value_op_1 = myExp_1.getOperand(1).toString();
		    	            		String operator_op1 = myExp_1.getOperator();
//	    	            		
		    	            		int position = att_pos.get(operand_1);
		    	            		
		    	            		String[] array = line.split(",");
		    	            		ConditionCompare cc_1 = new ConditionCompare();
		    	            		cc_1.flag=false;
		    	            		boolean op1_result=cc_1.performConditional(Integer.parseInt(value_op_1), Integer.parseInt(array[position]), operator_op1);

		    	            		if(op1_result)
		    	            		{
		    	            			agg_obj.writeline(line);
		    	            		}
		    	    			}

		               	 		
		               	 		
		               	 		line=br_2.readLine();
		               	 	}
		               	 	agg_obj.closeFile();
		               	 	br_2.close();
		               	 	reader_1.close();
		               	 	
		               	 	//based on the aggregate function perform thy job
		               	 	FileReaderClass my_reader_obj = new FileReaderClass(v,att_pos);
		               	 	FileReaderClass.countLines();
		               	 	my_reader_obj.agg_function();
		               	 	
		    	}
		    	else if(distinct_flag)
		    	{
		    		System.out.println("Distinct With where not yet done");
		    		//
		    		
		    	}
		    	else //project with where
		    	{
//		    		System.out.println("Project flag set");
		    		ProjectClass myObj = new ProjectClass(att_pos, expVector,myQuery,v,and_flag,or_flag);
		    		myObj.projectColumns();
		    		myObj.printMyColumns();
		    	}
		    }
		    else
		    {
//		    	System.out.println("Where flag is not present");
		    	
		    	/**
		    	 * print all the records in the given file 
		    	 * check the name of the file
		    	 * print all the columns from it
		    	 * if the file is not present 
		    	 * print table not found
		    	 * **/
		    	
		    	if(star_flag)/**if * is present ignore everything else **/
		        {
//		        	System.out.println("The * flag is present");
		    		Vector<String> rows = table_hashmap.get(myTables.get(0));
			    	 for(int i=0;i<rows.size();i++)
			    		 System.out.print(rows.elementAt(i)+" ");
			    	 System.out.println();
		        	
		        	reader_1 = new FileReader("Output.txt");
		        	br_2 = new BufferedReader(reader_1);
		        	
		        	String line = br_2.readLine();
		       	 	
		       	 	while(line!=null)
		       	 	{
		       	 		System.out.println(line);
		       	 		line=br_2.readLine();
		       	 	}
		       	 	
		       	 	br_2.close();
		       	 	reader_1.close();
		        }
		    	else if(aggregate_flag)
		    	{
//		    		System.out.println("Aggregate flag set");
		    		myclass agg_obj = new myclass("aggout.txt");
					agg_obj.createFile();
//					System.out.println("Aggregate flag is set");
					reader_1 = new FileReader("Output.txt");
		       	 	br_2 = new BufferedReader(reader_1);
		       	 	String line = br_2.readLine();
		       	 	
		       	 	while(line!=null)
		       	 	{
		       	 		line=br_2.readLine();
		       	 	}
		       	 	agg_obj.closeFile();
		       	 	br_2.close();
		       	 	reader_1.close();
		       	 	
		       	 	//based on the aggregate function perform thy job
		       	 	AggregateWoWhere my_reader_obj = new AggregateWoWhere(v,att_pos);
		       	 	AggregateWoWhere.countLines();
		       	 	my_reader_obj.agg_function();

		       	 	
		    	}
		    	else if(distinct_flag)
		    	{
//		    		System.out.println("Distinct flag without where flag");
		    		DistinctClass myobj = new DistinctClass(att_pos, v);
		    		myobj.printDistinct();
		    	}
		    	else
		    	{
//		    		System.out.println("Project without where clause");
		    		FileReader fr_y = new FileReader("Output.txt");
		    		BufferedReader br_y = new BufferedReader(fr_y);
		    		ZSelectItem it=null;
		    		String line = br_y.readLine();
		    		
		    		for(int i=0;i<v.size();i++)
	    			{
	    				it =(ZSelectItem)v.get(i);	    				
	    				System.out.print(it.getColumn()+" ");
	    			}
		    		System.out.println();
		    		while(line!=null)
		    		{
		    			String[] array = line.split(",");
		    			for(int i=0;i<v.size();i++)
		    			{
		    				it =(ZSelectItem)v.get(i);
		    				int pos = att_pos.get(it.getColumn());
		    				System.out.print(array[pos]+ " ");
		    			}
		    			System.out.println();
		    			line=br_y.readLine();
		    		}
		    		br_y.close();
		    		fr_y.close();

		    	}
		    	
		    }
		}
		else
		{
				if(update_flag)
				{
					
//					
//					System.out.println("Insert is called");
					p.setInputStatement();
					char[] char_array= p.setInputStatement();
					String myString = String.valueOf(char_array);
					System.out.println(myString);
					InsertClass myInsObj = new InsertClass(myString);
					myInsObj.insert();
				}
				if(delete_flag)
				{
					
					p.setInputStatement();
					char[] char_array= p.setInputStatement();
					String myString = String.valueOf(char_array);
//					System.out.println(myString);
//					System.out.println("Delete adaadsd");
					
					DeleteRow myDelObj = new DeleteRow(myString);
					myDelObj.createHashMap();
					myDelObj.createAttPos();
					myDelObj.deleteLine();
				}
				
				
		}//end of update and delete flag
			
			if(exit_flag)
			{
				System.out.println("Good Bye");
				break;
			}
		                    
		    
		     
		}//end of if loop
	}  catch (ParseException e) {
		// TODO Auto-generated catch block
//		System.out.println("Exception caught");
		p.setInputStatement();
//		System.out.println(p.setInputStatement());
		char[] char_array= p.setInputStatement();
		
		String myString = String.valueOf(char_array);
//		System.out.println(myString);

		if(myString.contains("delete") || myString.contains("DELETE"))
		{
			;/**DO nothing **/
		}
		else if(myString.contains("insert") || myString.contains("INSERT"))
		{
			;/**DO nothing **/
			
		}
		else if(myString.contains("truncate") || myString.contains("TRUNCATE"))
		{
//			System.out.println("Before "+myString);
			String str = myString.replace(";", "");
//			System.out.println("After "+ str);
			
			String[] array = str.split(" ");
			String extension = ".csv";
			String str1=array[2];
//			System.out.println(array[2]);
//			System.out.println(array[1]);
//			System.out.println(array[0]);
			String filename = array[2].trim()+extension;
			//String str2 = str1.concat(extension);
			System.out.println(filename);
			//System.out.println("JADALSJD");
			

//			System.out.println("s abt to be truncated is "+filename);
			TruncateTable myTrunc = new TruncateTable(filename);
			myTrunc.truncateTable();
			
		}else if(myString.contains("drop") || myString.contains("DROP"))
		{	
			String str = myString.replace(";", "");
			str= str.replaceAll("\\s+", " ");
			String[] array = str.split(" ");
			str=array[2];
			DropClass myDrop = new DropClass(str);
			myDrop.createHashMap();
			myDrop.dropTable();
			
			//delete means delete and truncate
			String str_1 = myString.replace(";", "");
			str_1= str_1.replaceAll("\\s+", " ");
			String[] array_1 = str_1.split(" ");
			str_1=array[2];
			str_1=str_1.trim();
			str_1=str_1.concat(".csv");
//			System.out.println(str_1);
			TruncateTable myTrunc = new TruncateTable(str_1);
			myTrunc.truncateTable();
			
			File del_file = new File(str_1);
			if(del_file.delete())
			{
				System.out.println("Table Dropped");
			}
			else
			{
				System.out.println("Table not Dropped");
			}
			
		}else if(myString.contains("create")||myString.contains("CREATE"))
		{
			//then its CREATE table
			CreateTable myCreate = new CreateTable(myString);
			myCreate.processCreateTable();
		}
		else if(myString.contains("exit")||myString.contains("EXIT")||myString.contains("exit;")||myString.contains("EXIT;"))
		{
			System.out.println("Good Bye");
			break;
		}
		

//		System.out.println("NEXT IS WHAT :P");
//		e.printStackTrace();
	}
    
	catch(Exception e)
	{
		System.out.println("Generic exception");
		e.printStackTrace();
	}

    }

  } // main ends here


  /**
   * Create a new parser to parse SQL statements from a given input stream.
   * @param in The InputStream from which SQL statements will be read.
   */
  public ZqlParser(InputStream in) {
    initParser(in);
  }

  /**
   * Create a new parser: before use, call initParser(InputStream) to
   * specify an input stream for the parsing.
   */
  public ZqlParser() {};

  /**
   * Initialize (or re-initialize) the input stream for the parser.
   */
  public void initParser(InputStream in) {
	
    if(_parser == null) {
      _parser = new ZqlJJParser(in);
    } else {
      _parser.ReInit(in);
    }

  }
  
  public  char[] setInputStatement()
  {
	  
	  return _parser.jj_input_stream.buffer;
  }

  public void addCustomFunction(String fct, int nparm) {
    ZUtils.addCustomFunction(fct, nparm);
  }

  /**
   * Parse an SQL Statement from the parser's input stream.
   * @return An SQL statement, or null if there's no more statement.
   */
  public ZStatement readStatement() throws ParseException {
	  star_flag=false;
	   cart_prod=false;
	   where_flag=false;
	   and_flag=false;
	   or_flag=false;
	    distinct_flag=false;
	    project_flag=false;
	    aggregate_flag=false;
	    
//	 System.out.println("Control coming here");   
    if(_parser == null)
      throw new ParseException(
       "Parser not initialized: use initParser(InputStream);");
    return _parser.SQLStatement();
  }

  /**
   * Parse a set of SQL Statements from the parser's input stream (all the
   * available statements are parsed and returned).
   * @return A vector of ZStatement objects (SQL statements).
   */
  public Vector readStatements() throws ParseException {
    if(_parser == null)
      throw new ParseException(
       "Parser not initialized: use initParser(InputStream);");
    return _parser.SQLStatements();
  }

  /**
   * Parse an SQL Expression (like the WHERE clause of an SQL query).
   * @return An SQL expression.
   */
  public ZExp readExpression() throws ParseException {
    if(_parser == null)
      throw new ParseException(
       "Parser not initialized: use initParser(InputStream);");
    return _parser.SQLExpression();
  }

};


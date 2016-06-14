package org.gibello.zql;
import java.io.*;
import java.util.*;

public class ProjectClass {

	public myclass agg_obj;
	public FileReader reader_1;
	BufferedReader br_2;
	public boolean and_flag;
	public boolean or_flag;
	public String line;
	public HashMap<String,Integer> att_pos;
	public Vector<ZExpression> expVector;
	public ZQuery myQuery;
	public Vector v;
	public ZSelectItem it;


	
	ProjectClass(HashMap<String,Integer> att_pos_arg,Vector<ZExpression> expVector_arg,ZQuery myQuery_arg,Vector v_arg,boolean and_flag_arg,boolean or_flag_arg) throws IOException
	{
		agg_obj = new myclass("aggout.txt");
		agg_obj.createFile();
		reader_1 = new FileReader("Output.txt");
   	 	br_2 = new BufferedReader(reader_1);
   	 	att_pos=att_pos_arg;
   	 	expVector=expVector_arg;
   	 	myQuery=myQuery_arg;
   	 	v=v_arg;
   	 	and_flag=and_flag_arg;
   	 	or_flag= or_flag_arg;
	}
	
	
	public void projectColumns() throws IOException
	{
		line = br_2.readLine();
		while(line!=null)
   	 	{
//   	 		System.out.println(line);
       	 	if(and_flag)
			{
            		String operand_1 = expVector.get(0).getOperands().elementAt(0).toString();
            		String value_op_1 = expVector.get(0).getOperands().elementAt(1).toString();
            		String operator_op1 = expVector.elementAt(0).getOperator().toString();
            		
            		int position = att_pos.get(operand_1);
//            		System.out.println(operand_1+" position is "+position);
            		
            		
            		String operand_2 = expVector.get(1).getOperands().elementAt(0).toString();
            		String value_op_2 = expVector.get(1).getOperands().elementAt(1).toString();
            		String operator_op2 = expVector.elementAt(1).getOperator().toString();
            		
            		int position2 = att_pos.get(operand_2);
//            		System.out.println(operand_2+" position is "+position2);
            		
            		String[] array = line.split(",");
            		
           		
            		ConditionCompare cc_1 = new ConditionCompare();
//            		System.out.println("This class worked");
            		cc_1.flag=false;
            		boolean op1_result=cc_1.performConditional(Integer.parseInt(value_op_1), Integer.parseInt(array[position]), operator_op1);
            		cc_1.flag=false;
            		boolean op2_result=cc_1.performConditional(Integer.parseInt(value_op_2), Integer.parseInt(array[position2]), operator_op2);
            		
//            		System.out.println("bool 1 result is "+op1_result+"bool 2 result is "+op2_result);

            		if(op1_result && op2_result)
            		{    		            			
            			agg_obj.writeline(line);
            		}
            		
			}
			
			if(or_flag)
			{
//				System.out.println("OR flag is on ");
        		String operand_1 = expVector.get(0).getOperands().elementAt(0).toString();
        		String value_op_1 = expVector.get(0).getOperands().elementAt(1).toString();
        		String operator_op1 = expVector.elementAt(0).getOperator().toString();
        		
        		int position = att_pos.get(operand_1);
//        		System.out.println(operand_1+" position is "+position);
        		
        		
        		String operand_2 = expVector.get(1).getOperands().elementAt(0).toString();
        		String value_op_2 = expVector.get(1).getOperands().elementAt(1).toString();
        		String operator_op2 = expVector.elementAt(1).getOperator().toString();
        		
        		int position2 = att_pos.get(operand_2);
//        		System.out.println(operand_2+" position is "+position2);
        		
        		String[] array = line.split(",");
        		
//        		System.out.println("OP1 The value at position " + array[position] + " OP1 value is " + value_op_1);
//        		System.out.println("OP2 The value at position " + array[position2] + " OP2 value is " + value_op_2);

        		
        		ConditionCompare cc_1 = new ConditionCompare();
//        		System.out.println("This class worked");
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
				
				ZExpression myExp= (ZExpression)myQuery.getWhere();
				String operand_1 = myExp.getOperand(0).toString();
        		String value_op_1 = myExp.getOperand(1).toString();
        		String operator_op1 = myExp.getOperator();
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

   	 	

	}
	
	public void printMyColumns() throws IOException
	{
		FileReader reader_x = new FileReader("aggout.txt");;
		BufferedReader bf_x = new BufferedReader(reader_x);;
		String line = bf_x.readLine();
				
		while(line!=null)
		{
			String[] array = line.split(",");
			for(int i=0;i<v.size();i++)
			{
				it=(ZSelectItem)v.get(i);
				String col_name = it.getColumn();
				int position = att_pos.get(col_name);
//				System.out.println("The position is "+position);
				System.out.print(array[position]+" ");
				
			}
			System.out.println("\n");
			line=bf_x.readLine();
		}

		reader_x.close();
		bf_x.close();
		
	}
	
}

package org.gibello.zql;
import java.io.*;

public class ConditionCompare {

	public boolean flag=false;
	public String operand;
	public int op1;
	public int op2;

	
	public boolean performConditional(Integer operand1, Integer operand2, String op)
	{
		op1=(int)operand1;
		op2=(int)operand2;
		operand=op;
		
		//op2 is array[position], op1 is normal operand 
		if(operand.equals(">"))
		{
//			System.out.println("Comparing "+op1+ " and " + op2);
			if(op2>op1)
			{
				flag=true;
			}
		}
		else if(operand.equals("<"))
		{
			if(op1>op2)
			{
				flag=true;
			}
			
		}
		else if(operand.equals("<="))
		{
			if(op1>=op2)
			{
				flag=true;
			}
		}
		else if(operand.equals(">="))
		{
			if(op1<=op2)
			{
				flag=true;
			}
		}
		else if(operand.equals("="))
		{
//			System.out.println("The control comes here ");
//			System.out.println(op1 + "==" + op2);
			if(op1==op2)
			{
//				System.out.println("Control isnt coming here");
				flag=true;
			}
		}
		
		
		return flag;
	}
	
}

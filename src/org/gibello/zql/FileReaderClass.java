package org.gibello.zql;

import java.io.*;
import java.util.*;

public class FileReaderClass {

	public Vector v;
	public ZSelectItem it;
	public HashMap<String,Integer> my_att_pos;
	public Vector<Vector<Integer>> hold_columns;
	public FileReader reader_x;
	public BufferedReader bf_x;
    public static int count = 0;
    public int min;
    public int max;
    public int sum;
    public int avg;

	
	FileReaderClass(Vector arg_vec,HashMap<String,Integer> att_pos) throws FileNotFoundException
	{
		v=arg_vec;
		my_att_pos=att_pos;
		hold_columns = new Vector<Vector<Integer>>();
//		reader_x = new FileReader("aggout.txt");
//		bf_x = new BufferedReader(reader_x);
//		min=0;max=0;avg=0;sum=0;
//		System.out.println(v.toString());
//		System.out.println(my_att_pos.toString());
	}
	
	public void agg_function() throws IOException
	{
		/**here i have the vector, for each vector based on the
		 * aggregate function i need to print ,max,min,sum,avg
		 * vector v provides me agg function and column		 * 		
		 */
		reader_x = new FileReader("aggout.txt");
		bf_x = new BufferedReader(reader_x);
		String line = bf_x.readLine();
		int my_array[][] = new int[count][my_att_pos.size()];
		int row=0;
//		System.out.println("Number of lines in aggout.txt are " + count);
		
		while(line!=null)
		{
			String[] array = line.split(",");
			for(int i=0;i<my_att_pos.size();i++)
			{
//				System.out.println("row = "+ row + "j is " + i);
				my_array[row][i]=(int)Integer.parseInt(array[i]);
			}
			row++;
			line=bf_x.readLine();
		}
		
/** THIS IS TO PRINT THE ARRAY FOR REFERENCE 
 * 	System.out.println("Printing the MY ARRAY " + my_array.toString());
 
//		for(int i=0;i<count;i++)
//		{
//			for(int j=0;j<my_att_pos.size();j++)
//			{
//				System.out.println(my_array[i][j]+" ");
//			}
//			System.out.println("\n");
//		}
 * 
 * */
		for(int i=0;i<v.size();i++)
		{
			it=(ZSelectItem)v.get(i);
			String agg_func = it.getAggregate();
			String col_name = it.getColumn();
			System.out.print(agg_func+ " "+ col_name+ " ");
		}
		
		System.out.println("\n------------------------------------------------------------------------------");
 
		
		//now we just have to see the aggregate function and print it
		
		for(int i=0;i<v.size();i++)
		{
			it=(ZSelectItem)v.get(i);
			String agg_func = it.getAggregate();
			String col_name = it.getColumn();
			int position = my_att_pos.get(col_name);
//			System.out.println("Position of "+col_name + " is "+position);
			
			if(agg_func.equals("max")||agg_func.equals("MAX"))
			{
				max = my_array[0][position];
				for(int k=0;k<count;k++)
				{
					if(max<my_array[k][position])
					{
						max=my_array[k][position];
					}
				}
				
				System.out.print(max + " ");
			}
			
			/**MIN**/
			if(agg_func.equals("min")||agg_func.equals("MIN"))
			{
//				System.out.println("control should reach here");
				min = my_array[0][position];
//				System.out.println("Initialized val is "+my_array[0][position]);

				for(int k=0;k<count;k++)
				{
//					System.out.println("Min is "+my_array[k][position]);

					if(min>my_array[k][position])
					{
						min=my_array[k][position];
//						System.out.println("Min is "+my_array[k][position]);
					}
				}
				
				System.out.print(min + " ");
			}
			
			/**AVG**/
			if(agg_func.equals("avg")||agg_func.equals("AVG"))
			{
				sum = my_array[0][position];
				for(int k=0;k<count;k++)
				{
						sum+=my_array[k][position];
					
				}
				
				avg=sum/count;
				System.out.print(avg + " ");
			}
			
			
			/**SUM**/
			if(agg_func.equals("sum")||agg_func.equals("SUM"))
			{
				sum = my_array[0][position];
				for(int k=0;k<count;k++)
				{
						sum+=my_array[k][position];
					
				}
				
				System.out.print(sum + " ");
			}
			
			
			
		}
		
		System.out.println();
		reader_x.close();
		bf_x.close();
		
	}
	
	public static int countLines() throws IOException {
		count=0;
	    InputStream is = new BufferedInputStream(new FileInputStream("aggout.txt"));
	    try {
	        byte[] c = new byte[1024];
	        int readChars = 0;
	        boolean empty = true;
	        while ((readChars = is.read(c)) != -1) {
	            empty = false;
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                }
	            }
	        }
	        return (count == 0 && !empty) ? 1 : count;
	    } finally {
	        is.close();
	    }
	}
	
}

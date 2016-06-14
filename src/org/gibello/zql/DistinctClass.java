package org.gibello.zql;

import java.io.*;
import java.util.*;

public class DistinctClass {

	public HashMap<String,Integer> att_pos;
	public Vector v;
	FileReader fr;
	BufferedReader br;
	ZSelectItem it;
	LinkedHashSet hs;
	
	DistinctClass(HashMap<String,Integer> att_pos_arg,Vector v_arg) throws IOException
	{
		att_pos = att_pos_arg;
		v=v_arg;
		fr=new FileReader("Output.txt");
		br = new BufferedReader(fr);
		hs = new LinkedHashSet();
	}
	
	
	public void printDistinct() throws IOException  
	{
		String line = br.readLine();
		it=(ZSelectItem)v.get(0);
		int pos = att_pos.get(it.getColumn());
		
		while(line!=null)
		{
			String[] array = line.split(",");
			hs.add(array[pos]);
			
			line = br.readLine();
		}
		
		fr.close();
		br.close();
		
//        System.out.println(hs);
        
        Iterator itr = hs.iterator();
        System.out.println(it.getColumn());
        System.out.println("-------------------------");

        while (itr.hasNext()){
            System.out.println(itr.next());
          }

		
	}
	
}

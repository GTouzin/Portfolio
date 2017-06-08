import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Scanner;

public class Multiplication_compression {
	
	int a,b,m,bit;
	BigDecimal dict;
	

	public Multiplication_compression() {
		a=100;
		b=359;
		m=177;
		bit=50;
		
		String clef="";
		int temp=303;
		while(clef.length()<bit)
		{
			temp=(a*temp+b)%m;
			clef=clef+Integer.toString(temp);
			if(clef.length()>=bit)
			{
				clef=clef.substring(0, bit);
			}
		}
		
		dict=new BigDecimal(clef);
		//System.out.println(dict);
	}
	
	public void reference(String path_ref,int bits,int nrows)
	{
		try {			
			
			File fichier=new File(path_ref);
			FileWriter file = new FileWriter(fichier);
			
		for(int y =0; y<nrows;y++)
		 {
			 			 
			 for(int x =0; x<bits;x++)
			 {
				 file.write( Long.toString(Math.round(Math.random()*9)));
			 } 
			 
			 file.write( "\n");
		 }
		file.close();
		} 
		catch (IOException e) {
		      System.out.println("Error - " + e.toString());
			}
	}
	
	
	public void compression(String path_ref,String path_comp) throws IOException
	{
		
		String encoding = "UTF-8";
		int maxlines = bit;
		BufferedReader reader = null;
		BufferedWriter writer = null;

		try {
		    reader = new BufferedReader(new InputStreamReader(new FileInputStream(path_ref), encoding));
		    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path_comp), encoding));
		    
		    writer.write(dict.toString());
	        writer.newLine();
		    
		    for (String line; (line = reader.readLine()) != null;) {
		    	
		    	BigDecimal number_read=new BigDecimal(line);
		    	int scale=1;
		    	boolean not_equal=true;
		    	String result="";
		    	while(not_equal&scale<=200)
		    	{
		    		BigDecimal temp=number_read.divide(dict, scale, RoundingMode.CEILING);
		    		
		    				    		
		    		if(scale==199||temp.multiply(dict).setScale(0, RoundingMode.FLOOR).compareTo(number_read)==0)
		    		{
		    			result=temp.toString();
		    			not_equal=false;
		    			System.out.println(String.valueOf(scale));
		    			
		    		}
		    		else
		    		{
		    			scale++;
		    		}
		    	}
	    	
		        writer.write(result);
		        writer.newLine();		        
		    }
		} finally {
			if(writer!=null)
			{
				writer.close();
			}
			if(reader!=null)
			{
				reader.close();
			}

		}
		
	}
	
	public void decompression(String path_ref,String path_decomp) throws IOException
	{
		
		String encoding = "UTF-8";
		int maxlines = bit;
		BufferedReader reader = null;
		BufferedWriter writer = null;

		try {
		    reader = new BufferedReader(new InputStreamReader(new FileInputStream(path_ref), encoding));
		    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path_decomp), encoding));
		    
		   		    
		    for (String line; (line = reader.readLine()) != null;) {
		    	
		    	BigDecimal number_read=new BigDecimal(line);
		    	
		    	BigDecimal result=number_read.multiply(dict).setScale(0, RoundingMode.FLOOR);
		    		    	
		        writer.write(result.toString());
		        writer.newLine();		        
		    }
		} finally {
			if(writer!=null)
			{
				writer.close();
			}
			if(reader!=null)
			{
				reader.close();
			}

		}
		
	}
	
	public BigDecimal findClef(int periode, int maxIteration)
	{
		long timeNow = System.currentTimeMillis();
		BigDecimal clef;
		String ref="1";
		while(ref.length()<bit-1)
		{
			ref=ref+Integer.toString(0);
		}
		ref=ref+Integer.toString(1);
		clef= new BigDecimal(ref);
		System.out.println(ref.length());
		
		int count=0;
		int min=bit;
		BigDecimal unit= new BigDecimal(1);
		BigDecimal two= new BigDecimal(2);
		BigDecimal five= new BigDecimal(5);
		BigDecimal ten = new BigDecimal(10);
		BigDecimal iteration=clef;
		while(count<maxIteration&min>periode)
		{
			boolean not_equal=true;
			int scale=0;
			int k=1;
			
			while(not_equal&scale<=bit)
	    	{
	    		BigDecimal temp=unit.divide(iteration, scale, RoundingMode.CEILING);
	    			    				    		
	    		if(scale==bit-1||temp.multiply(iteration).setScale(0, RoundingMode.FLOOR).compareTo(unit)==0)
	    		{
	    			not_equal=false;
	    		}
	    		else
	    		{
	    			scale++;
	    		}
	    		//System.out.println(iteration);
	    	}
			if(min>scale)
			{
				min=scale;
				clef=iteration;
				System.out.println("clef: "+clef);
				System.out.println("min: "+min);
			}
			count++;
			iteration=iteration.add(unit);
			iteration=iteration.add(unit);
			//System.out.println(iteration);
		}
		System.out.println("clef: "+clef);
		System.out.println("min: "+min);
		System.out.println((System.currentTimeMillis()-timeNow)/1000);
		return clef;
		
	}

}

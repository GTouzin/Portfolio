//import java.lang.reflect.Array;
import java.util.Random;


public class Splice {
	
	public int[] array1;
	public int[] array2;
	public int bit;
	public Splice(Chromosome x, Chromosome y, int Bit)

	{
		array1=new int[x.array.length];
		array2=new int[y.array.length];
		bit=Bit;
				
		String parentx="";
		String parenty="";
		
		for(int i=0;i<x.array.length;i++)
		{
			String temp=Integer.toBinaryString(x.array[i]);
			if(temp.length()<bit)
			{
				while(temp.length()<bit)
				{
					temp="0"+temp;
				}
			}
			parentx= parentx+temp;
		}
		
		for(int i=0;i<y.array.length;i++)
		{
			String temp=Integer.toBinaryString(y.array[i]);
			if(temp.length()<bit)
			{
				while(temp.length()<bit)
				{
					temp="0"+temp;
				}
			}
			parenty= parenty+temp;
		}
		
		//Ont génère le point de séparation		
		
		Random randomGenerator = new Random();		
		
		int randomInt = randomGenerator.nextInt(parentx.length());
		
		char[] tetex=new char[randomInt];
		parentx.getChars(0, randomInt, tetex, 0);
		String Tetex=new String(tetex);
		
		char[] tetey=new char[randomInt];
		parenty.getChars(0, randomInt, tetey, 0);
		String Tetey=new String(tetey);
		
		char[] queuex=new char[parentx.length()-randomInt];
		parentx.getChars(randomInt,parentx.length(),queuex, 0);
		String Queuex=new String(queuex);
		
		char[] queuey=new char[parenty.length()-randomInt];
		parenty.getChars(randomInt,parenty.length(), queuey, 0);
		String Queuey=new String(queuey);
		
		String enfantx="";
		String enfanty="";
		
		enfantx=enfantx+Tetex;
		enfantx=enfantx+Queuey;
		
		enfanty=enfanty+Tetey;
		enfanty=enfanty+Queuex;
		
		for(int i=0;i<x.array.length;i++)
		{
			char[] tempx=new char[bit];
			char[] tempy=new char[bit];
			
			enfantx.getChars(i*bit, (i+1)*bit, tempx, 0);
			enfanty.getChars(i*bit, (i+1)*bit, tempy, 0);
			
			Tetex=new String(tempx);
			Tetey=new String(tempy);
			
			array1[i]=Integer.parseInt(Tetex, 2);
			array2[i]=Integer.parseInt(Tetey, 2);			
		}
		
	}

	public Chromosome getEnfantx()
	{
		Chromosome temp=new Chromosome(array1[0],array1[1],array1[2],array1[3],array1[4],array1[5],array1[6]);
		return temp;
	}
	
	public Chromosome getEnfanty()
	{
		Chromosome temp=new Chromosome(array2[0],array2[1],array2[2],array2[3],array2[4],array2[5],array2[6]);
		return temp;
	}
	
	
}

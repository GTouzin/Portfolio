import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//Class that use a linear congruential generator to generate a list of pseudo-random number generator

public class Generator {
	
	public int max;									//Biggest seed used
	public int longueur;							//Number of element in the list
	
	public int a= 16807;							//Coefficient of the generator
	public int m= (int) Math.pow(2, 31)-1;			//Modulo of the generator

	//Constructor
	public Generator(int max, int longueur)
	{
		this.max=max;
		this.longueur=longueur;
	}
	
	//Method that generate the list of random number and add write them to file
	public void Generation()
	{
		try {			
			
			File fichier=new File("E:/Compression/Générateur.txt");
			FileWriter file = new FileWriter(fichier);
			
		for(int x0 =1; x0<=max;x0++)
		 {
			file.write( "*"+Integer.toString(x0)+"*");
			 int terme=(a*x0)%m; 
			 file.write(Integer.toString(terme));
			 
			 for(int y =1; y<longueur;y++)
			 {
				 terme=(a*terme)%m;
				 terme= Math.abs(terme);
				 file.write(Integer.toString(terme));
			 } 
			 
			 file.write( "\n");
		 }
		
		file.close();
		} 
		catch (IOException e) {
		      System.out.println("Error - " + e.toString());
			}
	}
	
	//Method that generate the list of random number of a maximun size and add write them to file
	public void GenerationVentille(int long_num)
	{
		try {			
			
			File fichier=new File("E:/Compression/Générateur.txt");
			FileWriter file = new FileWriter(fichier);
			
			
			
		for(int x0 =1; x0<max;x0++)
		 {
			int count =long_num;
			
			file.write( "*"+Integer.toString(x0)+"*"+"\n");
			 int terme=(a*x0)%m; 
			 
			String temp=Integer.toString(terme);
			
			 			 
			 for(int y =1; y<longueur;y++)
			 {
				 terme=(a*terme)%m;
				 terme= Math.abs(terme);
				 
				 temp+=Integer.toString(terme);
				 
				if(temp.length()>=count)
				{
					while(temp.length()>=count)
					{
						for(int x=0; x<count;x++)
						{
							file.write(temp.charAt(x));
						}
						temp=temp.substring(1);
						file.write("\n");
					}
				}
				else
				{
					file.write(Integer.toString(terme));
					file.write("\n");
				}			 
				 
				 
			 } 
			 
		 }
		
		file.close();
		} 
		catch (IOException e) {
		      System.out.println("Error - " + e.toString());
			}
	}
	
	

}

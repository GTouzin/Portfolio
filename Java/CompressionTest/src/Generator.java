import java.io.File;
import java.io.FileWriter;
import java.io.IOException;



public class Generator {
	
	public int max;
	public int longueur;
	
	public int a= 16807;
	public int m= (int) Math.pow(2, 31)-1;

	
	public Generator(int max, int longueur)
	{
		this.max=max;
		this.longueur=longueur;
	}
	
	
	public void Generation()
	{
		try {			
			
			File fichier=new File("G:/Générateur.txt");
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
	
	public void GenerationVentille(int long_num)
	{
		try {			
			
			File fichier=new File("G:/Générateur.txt");
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

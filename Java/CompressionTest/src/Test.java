import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*Class used to estimate the number of time we can find a random number in a serie of random number. 
To do so, we load a serie of random number as reference, generate a random number and look for
that value in the reference. If we find the number, it's a success, if that's not the case, it's an echec.
At the end we return the number of success and the ratio success/echec */

public class Test {
	
	public int min, max;
	
	
	public Test( int min, int max)
	{
		this.min=min;
		this.max=max;
		
	}
		
	//Method who save a list of random number
	public void Enregistrer(int essais)
	{
		try {			
		
			File fichier=new File("E:/Compression/Test.txt");
			FileWriter file = new FileWriter(fichier);
			
		for(int y =0; y<essais;y++)
		 {
			 			 
			 file.write((int) Math.round(Math.random()*(max-min)+min ));
		 }
		file.close();
		} 
		catch (IOException e) {
		      System.out.println("Error - " + e.toString());
			}
	}
	
	//Method who try to find some random number in a list of number and count the number of success. 
	public void test_find(String ref, int longueur, int bit )
	{
				
		double succes=0;
		double echec=0;
		
try {			
			
			File fichierGen=new File("E:/Compression/"+ref+".txt");
			FileReader file = new FileReader(fichierGen);

			BufferedReader br = new BufferedReader(file);
			
			
			//System.out.println(mot);	
			
			for(int x=0;x<longueur;x++)
			{
				String mot="";
				for(int y=mot.length();y<bit;y++)
				{
					mot=mot+Integer.toString((int)Math.round((max-min)*Math.random()+min));
				}
								
				String ligne=null;
				boolean test=false;
				br = new BufferedReader(new FileReader(fichierGen));
				
				while( !test && (ligne = br.readLine()) != null) 
				{
					//System.out.println( br.readLine().length());
					
			        if(ligne.equalsIgnoreCase(mot))
			        {
			        	test=true;
			        }
			       
			    }
				
				if(!test)
				{
					echec++;	
				}
				else
				{
		        	succes++;
				}
								
			}
			    
			    br.close();
			    System.out.println("Succes:"+succes);
			    System.out.println("Échec:"+echec);
			    System.out.println("Total:"+longueur);
			    double temp=succes/(succes+echec)*100;
			    System.out.println("taux de succes:"+temp);
			
		} 
		catch (IOException e) {
		      System.out.println("Error - " + e.toString());
			}
	}
	
	
	
	
	

}

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Test {
	
	public int min, max;
	
	
	public Test( int min, int max)
	{
		this.min=min;
		this.max=max;
		
	}
	
	
	
	public void Enregistrer(int essais)
	{
		try {			
		
			File fichier=new File("G:/Test.txt");
			FileWriter file = new FileWriter(fichier);
			
		for(int y =0; y<essais;y++)
		 {
			 			 
			 file.write((int) Math.floor(Math.random()*(max-min)+min ));
		 }
		file.close();
		} 
		catch (IOException e) {
		      System.out.println("Error - " + e.toString());
			}
	}
	
	public void test(String ref, int longueur, int bit )
	{
				
		double succes=0;
		double echec=0;
		
try {			
			
			File fichierGen=new File("G:/"+ref+".txt");
			FileReader file = new FileReader(fichierGen);

			BufferedReader br = new BufferedReader(file);
			
			String mot="";
			for(int x=mot.length();x<bit;x++)
			{
				mot=mot+Integer.toString((int)Math.round((max-min)*Math.random()+min));
			}
			//System.out.println(mot);	
			
			for(int x=0;x<longueur;x++)
			{
								
				String ligne=null;
				boolean test=false;
				br = new BufferedReader(new FileReader(fichierGen));
				while( !test && (ligne = br.readLine()) != null) 
				{
					
			        if(ligne.equalsIgnoreCase(mot))
			        {
			        	test=true;
			        }
			    }
				
				if(!test)
				{
					//System.out.println("mot:"+mot);
					//System.out.println("ligne:"+ligne);
					mot=mot.substring(1);
					mot+=Integer.toString((int)Math.round((max-min)*Math.random()+min));
					echec++;
					
				}
				else
				{
					
		        	for(int y=0;y<bit;y++)
					{
		        		if(x<longueur)
		        		{
			        		mot=mot.substring(1);
							mot=mot+Integer.toString((int)Math.round((max-min)*Math.random()+min));
							succes++;
							x++;
		        		}
					}
		        	x--;
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

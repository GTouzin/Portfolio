import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class Reference {
	
	public int[] objet;
	public int[][] tableau_reference;
	public Reference(int[] a){
	
		objet= new int[a.length];
		
		if(Math.pow(objet.length, objet.length+1)<Math.pow(2, 31))
		for(int x=0;x<a.length;x++)
		{
			objet[x]=a[x];
			
			tableau_reference=new int[(int) Math.pow(objet.length, objet.length)][objet.length];
			
			Generate();
		}
		else
			System.out.println("Trop d'éléments dans le tableau");
			
	}
	
	
	public void Generate()
	{
		 int[] temp = new int[objet.length];
		 
		 for(int x =0; x<temp.length;x++)
		 {
			 temp[x]=0;
		 }
		 
		 for(int y =0; y<Math.pow(objet.length, objet.length);y++)
		 {
			 			 
			 for(int x =0; x<objet.length;x++)
			 {
				 tableau_reference[y][x]=objet[temp[x]];
			 }
			 
			 temp[objet.length-1]++;
			 
			 for(int x=objet.length-1; x>0;x--)
			 {
				 if(temp[x]>=objet.length)
				 {
					 temp[x]=0;
					 temp[x-1]++;
				 }
				
			 } 
		 }
		 
	}
	
	public void Affichage()
	{
		for(int y =0; y<Math.pow(objet.length, objet.length);y++)
		 {
			 			 
			 for(int x =0; x<objet.length;x++)
			 {
				 System.out.print(tableau_reference[y][x]);
			 } 
			 
			 System.out.println(" ");
		 }
		
		
	}

	public void Enregistrer()
	{
		try {			
		
			File fichier=new File("G:/Référence.txt");
			FileWriter file = new FileWriter(fichier);
			
		for(int y =0; y<Math.pow(objet.length, objet.length);y++)
		 {
			 			 
			 for(int x =0; x<objet.length;x++)
			 {
				 file.write(Integer.toString(tableau_reference[y][x]));
			 } 
			 
			 file.write( "\n");
		 }
		file.close();
		} 
		catch (IOException e) {
		      System.out.println("Error - " + e.toString());
			}
	}
	
	
	public void Lire()
	{

		try {			
			
			File fichier=new File("G:/Référence.txt");
			FileReader file = new FileReader(fichier);

			BufferedReader br = new BufferedReader(file);
			for(String ligne; (ligne = br.readLine()) != null; ) {
		        System.out.print(ligne);
		    }
			    
			    br.close();
			
		} 
		catch (IOException e) {
		      System.out.println("Error - " + e.toString());
			}

		
		
	}
	
	
}

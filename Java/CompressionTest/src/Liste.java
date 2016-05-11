import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;



public class Liste {
	
	public File référence;
	public File générateur;
	
	
	public Liste(File r, File g)
	{
		référence=r;
		générateur=g;
	}
	
	
	public void Comparaison()
	{
		double succes=0;
		double echec=0;
		try {			
			
			FileReader fileRef = new FileReader(référence);
			FileReader fileGen = new FileReader(générateur);
			
			File fichier=new File("G:/Liste.txt");
			FileWriter fileListe = new FileWriter(fichier);

			BufferedReader brRef = new BufferedReader(fileRef);
			BufferedReader brGen = new BufferedReader(fileGen);
			
			
			for(String ligneRef; (ligneRef = brRef.readLine() ) != null; ) 
			{
				boolean test=false;			

				brGen = new BufferedReader(new FileReader(générateur));
				//count=0;
				String  thisLine=null;
				String index="";
				while ((thisLine = brGen.readLine()) != null) 
				{
					if(thisLine.charAt(0)=="*".charAt(0))
						index=thisLine;
				    
				    if (thisLine.equalsIgnoreCase(ligneRef.toString()))
				    {
				    	if(test)
				    	{
				    		fileListe.write(index);
					    	succes++;
				    	}
				    	else
				    	{
				    		fileListe.write(ligneRef.toString()+": "+index);
					    	test=true;
					    	succes++;
				    	}
				    }
				    		    
				}
				
				if(!test)
				{
					fileListe.write(ligneRef.toString()+": "+"FFFFFFFFFFUUUUUUUUUUUUUUUUUUUUUCCCCCCCCCCCCCCCKKKKKKKKKKKK\n");
					echec++;
				}
				else
				{
					fileListe.write("\n");
				}
				
		    }
 
			fileListe.close();
			brRef.close();
			brGen.close();
			double resultat=(succes/(succes+echec))*100;
			System.out.println("Taux de succes:"+resultat);
			System.out.println("Succes:"+succes);
			System.out.println("Echec:"+echec);
			
		} 
		catch (IOException e) {
		      System.out.println("Error - " + e.toString());
			}

		//return count;
		
	}
	
	public void Trier()
	{
		try {			
			
			FileReader file = new FileReader("G:/Liste.txt");
			
			File fichier=new File("G:/ListeTrier.txt");
			FileWriter fileListeTrier = new FileWriter(fichier);

			BufferedReader brRef = new BufferedReader(file);
			
			for(String ligneRef; (ligneRef = brRef.readLine() ) != null; ) 
			{
				String mot="";
				boolean entete=ligneRef.contains(":");
				int index=0;
				
				if(entete)
				{
					while(ligneRef.charAt(index)!=":".charAt(0) && index<ligneRef.length())
					{
						mot+=ligneRef.charAt(index);
						index++;
					}
					
					fileListeTrier.write(mot+"\n");
					index=index+2;
					
					
					while(index<ligneRef.length())
					{
						mot="";
						
												
						while(index<ligneRef.length()&& ligneRef.charAt(index)=="*".charAt(0))
						{
							index++;
						}
						
						while(index<ligneRef.length()&& ligneRef.charAt(index)!="*".charAt(0))
						{
							mot+=ligneRef.charAt(index);
							index++;
						}
						fileListeTrier.write(mot+",");
					}
					
				}
				else
				{
					while(index<ligneRef.length())
					{
						mot="";
						
												
						while(index<ligneRef.length()&& ligneRef.charAt(index)=="*".charAt(0))
						{
							index++;
						}
						
						while(index<ligneRef.length()&& ligneRef.charAt(index)!="*".charAt(0))
						{
							mot+=ligneRef.charAt(index);
							index++;
						}
						fileListeTrier.write(mot+",");
					}					
				}
				
				fileListeTrier.write(mot+"\n");
				
		    }
 
			fileListeTrier.close();
			brRef.close();
			
		} 
		catch (IOException e) {
		      System.out.println("Error - " + e.toString());
			}

		//return count;
		
	}
	
	public void Dictionnaire()
	{
		try {			
			
			FileReader file1 = new FileReader("G:/ListeTrier2.txt");
			FileReader file2 = new FileReader("G:/ListeTrier2.txt");
			
			File fichier=new File("G:/Dictionnaire.txt");
			FileWriter fileListe = new FileWriter(fichier);

			BufferedReader brRef1 = new BufferedReader(file1);
			BufferedReader brRef2 = new BufferedReader(file2);
			
			String n1="";
			String[][] tableau1=new String[2][100];
			
			for(String ligneRef1; (ligneRef1 = brRef1.readLine() ) != null; ) 
			{
								
				if(!ligneRef1.contains(","))
				{
					tableau1[0][0]=ligneRef1;
				}
				else
				{
					
					int index=1;
					for(int i=0;i<ligneRef1.length();i++)
					{
						if(ligneRef1.charAt(i)==",".charAt(0))
						{
							if(n1!="")
							{
								tableau1[0][index]=n1;
								index++;
							}
							n1="";
						}
						else
						{
							n1+=ligneRef1.charAt(i);
						}							
					}
					
					brRef2 = new BufferedReader(new FileReader("G:/ListeTrier2.txt"));
					
					for(String ligneRef2; (ligneRef2 = brRef2.readLine() ) != null; ) 
					{

						if(!ligneRef2.contains(","))
						{
							//Créer le dictionnaire
							
							
							if(tableau1[1][0]!=null)
							{
								for(int i=1;i<tableau1[0].length;i++)
								{
									for(int j=1;j<tableau1[1].length;j++)
									{
										
										if(tableau1[0][i]!=null && tableau1[1][j]!=null)
										{
											fileListe.write("*"+tableau1[0][i]+","+tableau1[1][j]+"\n");
										}
									}
								}
							}
											
							tableau1[1][0]=ligneRef2;
							
							fileListe.write("-"+tableau1[0][0]+","+tableau1[1][0]+"\n");
							
							
						}
						else
						{
							n1="";
							index=1;
							for(int i=0;i<ligneRef2.length();i++)
							{
								if(ligneRef2.charAt(i)==",".charAt(0))
								{
									if(n1!="")
									{
										tableau1[1][index]=n1;
										index++;
									}
									
									n1="";
								}
								else
									n1+=ligneRef2.charAt(i);
							}
						}
						
						
				    }
				}
				
				
		    }
 
			fileListe.close();
			brRef1.close();
			brRef2.close();
			file1.close();
			file2.close();
			
		} 
		
		catch (IOException e) {
		      System.out.println("Error - " + e.toString());
			}

		//return count;
		
	}
	
	
	
	
	
	

}

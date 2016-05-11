import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_highgui.CV_WINDOW_AUTOSIZE;
import static com.googlecode.javacv.cpp.opencv_highgui.cvDestroyWindow;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvNamedWindow;
import static com.googlecode.javacv.cpp.opencv_highgui.cvShowImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvWaitKey;

import com.googlecode.javacv.cpp.opencv_core.IplImage;

import static com.googlecode.javacv.cpp.opencv_highgui.*;

import com.googlecode.javacv.cpp.opencv_core.CvScalar;

public class ArtificialSelection {
	public Chromosome[] ensemble;
	public IplImage img;
	public int n;
	public int max;
	public ArtificialSelection(int nombre_rectangle, IplImage image)
	{
		img=image;
		int maximum=Math.max(img.width(), img.height());
		max=Integer.toBinaryString(maximum).length();
		
		n=nombre_rectangle;
		
		ensemble=new Chromosome[n];
		
		Random randomGenerator = new Random();			
				
		for(int i=0;i<n;i++)
		{
			int x1 = randomGenerator.nextInt(img.width());
			int y1 = randomGenerator.nextInt(img.height());
			int x2 = randomGenerator.nextInt(img.width());
			int y2 = randomGenerator.nextInt(img.height());
			if(x1==x2)
			{
				while(x1==x2)
				{
					x2 = randomGenerator.nextInt(img.width());
				}
			}
			
			if(y1==y2)
			{
				while(y1==y2)
				{
					y2 = randomGenerator.nextInt(img.height());
				}
			}
						
			int r1 = randomGenerator.nextInt(256);
			int g1 = randomGenerator.nextInt(256);
			int b1 = randomGenerator.nextInt(256);
			
			ensemble[i]=new Chromosome(x1,y1,x2,y2,b1,g1,r1);
			
			long temp=System.currentTimeMillis();
			if(System.currentTimeMillis()-temp<10)
			{
				while(System.currentTimeMillis()-temp<5)
				{
					
				}
			}
		}
		System.out.println("1");
	}
	
	public ArtificialSelection(int nombre_rectangle, IplImage image, boolean aleatoire) //Aléatoire détermine si les couleurs sont choissies au hasard
	{																					//Ajouter une variable pour fixer les rectangles sur une grille
		img=image;
		int maximum=Math.max(img.width(), img.height());
		max=Integer.toBinaryString(maximum).length();
		
		n=nombre_rectangle;
		
		if(n<3)
		{
			throw new IllegalArgumentException("INVALID"); // Il faut au moins 3 rectangles
		}
		
		ensemble=new Chromosome[n];
		if(aleatoire)
		{
			Random randomGenerator = new Random();			
					
			for(int i=0;i<n;i++)
			{
				int x1 = randomGenerator.nextInt(img.width());
				int y1 = randomGenerator.nextInt(img.height());
				int x2 = randomGenerator.nextInt(img.width());
				int y2 = randomGenerator.nextInt(img.height());
				if(x1==x2)
				{
					while(x1==x2)
					{
						x2 = randomGenerator.nextInt(img.width());
					}
				}
				
				if(y1==y2)
				{
					while(y1==y2)
					{
						y2 = randomGenerator.nextInt(img.height());
					}
				}
							
				int r1 = randomGenerator.nextInt(256);
				int g1 = randomGenerator.nextInt(256);
				int b1 = randomGenerator.nextInt(256);
				
				ensemble[i]=new Chromosome(x1,y1,x2,y2,b1,g1,r1);
				
				long temp=System.currentTimeMillis();
				if(System.currentTimeMillis()-temp<10)
				{
					while(System.currentTimeMillis()-temp<5)
					{
												
					}
				}
			
			}
		}
		else
		{	
			int facteur1=2;
			while(n%facteur1!=0&&facteur1<n/2+1)
			{
				facteur1++;
			}
			if(facteur1>=n/2)
			{
				facteur1=1;
			}
			
			int facteur2=n/facteur1;
			
			int[] abscisse=new int[facteur1+1];
			abscisse[0]=0;
			abscisse[facteur1]=img.width()-1;
			int[] ordonne;
			if(facteur2!=1)
			{
				ordonne =new int[facteur2+1];
			}
			else
			{
				ordonne =new int[facteur2+2];
			}
			ordonne[0]=0;
			ordonne[ordonne.length-1]=img.height()-1;
			
			int[] point=new int[abscisse.length-2];
			boolean reechantillonner=true;
			while(reechantillonner)
			{
				reechantillonner=false;
				for(int i=0;i<point.length;i++)
				{
					Random randomGenerator = new Random();
					point[i]=randomGenerator.nextInt(img.width());
					long temp=System.currentTimeMillis();
					if(System.currentTimeMillis()-temp<10)
					{
						while(System.currentTimeMillis()-temp<5)
						{
													
						}
					}
				}
				for(int i=0;i<point.length-1;i++)
				{
					if(point[i]==point[i+1])
					{
						reechantillonner=true;
					}
				}
			}
			
			Arrays.sort(point);
			for(int i=1;i<point.length+1;i++)
			{
				abscisse[i]=point[i-1];
				//System.out.println("abscisse("+i+")"+abscisse[i]);
			}
						
			point=new int[ordonne.length-2];
			reechantillonner=true;
			while(reechantillonner)
			{
				reechantillonner=false;
				for(int i=0;i<point.length;i++)
				{
					Random randomGenerator = new Random();
					point[i]=randomGenerator.nextInt(img.height());
					long temp=System.currentTimeMillis();
					if(System.currentTimeMillis()-temp<10)
					{
						while(System.currentTimeMillis()-temp<5)
						{
													
						}
					}
					
				}
				for(int i=0;i<point.length-1;i++)
				{
					if(point[i]==point[i+1])
					{
						reechantillonner=true;
					}
				}
			}
			Arrays.sort(point);
			
			for(int i=1;i<point.length+1;i++)
			{
				ordonne[i]=point[i-1];
				//System.out.println("origine("+i+")"+ordonne[i]);
			}
						
			//System.out.println("facteur1:"+facteur1);
			//System.out.println("facteur2:"+facteur2);
			int compteur=0;
			for(int i=0;i<abscisse.length-1;i++)
			{
				for(int j=0;j<ordonne.length-1;j++)
				{
					int x1;
					int x2;
					int y1;
					int y2;
					if(abscisse[i]<=abscisse[i+1])//Effacer
					{
						x1 = abscisse[i];
						x2 = abscisse[i+1];
					}
					else
						{
							x2 = abscisse[i];
							x1 = abscisse[i+1];
						}
					if(ordonne[i]<=ordonne[i+1])
					{
						y1 = ordonne[j];
						y2 = ordonne[j+1];
					}
					else
						{
							y2 = ordonne[j];
							y1 = ordonne[j+1];
						}
									
					//System.out.println("largeur:"+img.height());
					//System.out.println("y2-y1:"+(y2-y1));
					//System.out.println("largeur:"+img.width());
					//System.out.println("x2-x1:"+(x2-x1));
					CvScalar s=cvGet2D(img,y2-y1,x2-x1);
					int r1 = (int)s.val(2);
					int g1 = (int)s.val(1);
					int b1 = (int)s.val(0);
					ensemble[compteur]=new Chromosome(x1,y1,x2,y2,b1,g1,r1);
					compteur++;
					
				}
				
			} System.out.println("compteur"+compteur);
		}
		System.out.println("2");
	}
	public double Comparaison(Chromosome x)
	{
		
		int x1=GrayCode.grayToBinary(x.array[0]);
		int y1=GrayCode.grayToBinary(x.array[1]);
		int x2=GrayCode.grayToBinary(x.array[2]);
		int y2=GrayCode.grayToBinary(x.array[3]);
		int b1=GrayCode.grayToBinary(x.array[4]);
		int g1=GrayCode.grayToBinary(x.array[5]);
		int r1=GrayCode.grayToBinary(x.array[6]);
		
		int haut=Math.min(y1,y2);
		int bas=Math.max(y1,y2);
		int droite=Math.max(x1,x2);
		int gauche=Math.min(x1,x2);
		
		double[] mse=new double[3];
		mse[0]=0;
		mse[1]=0;
		mse[2]=0;
		
		
						
		for(int i=haut;i<bas;i++)
		{
			for(int j=gauche;j<droite;j++)
			{
				CvScalar s=cvGet2D(img,i,j);
				mse[0]=mse[0]+(Math.abs(s.val(0)-b1));
				mse[1]=mse[1]+(Math.abs(s.val(1)-g1));
				mse[2]=mse[2]+(Math.abs(s.val(2)-r1));
				
								
			}
		}
		
		double compte=(bas-haut)*(droite-gauche);
		mse[0]=mse[0]/compte;
		mse[1]=mse[1]/compte;
		mse[2]=mse[2]/compte;
				
		System.out.println("3");
		return mse[0]+mse[1]+mse[2];
	}
	
	public void Selection() 
	{
		double erreur1=0;
		double erreur2=0;
		int 	index1=0;
		int 	index2=0;
		double erreurmin1=100000;
		int 	indexmin1=0;
		int 	indexmin2=0;
		
		
		for(int i=0;i<n;i++)
		{
			double temp=Comparaison(ensemble[i]);
			if(temp>erreur1)
			{
				erreur1=temp;
				index1=i;
			}
			else
			{
				if(temp>erreur2)
				{
					erreur2=temp;
					index2=i;
				}
			}
			if(temp<erreurmin1)
			{
				erreurmin1=temp;
				indexmin1=i;
			}
			
		}
		Random randomGenerator = new Random();		
		
		int randomInt = randomGenerator.nextInt(ensemble.length);
		if(randomInt==index1||randomInt==index2||randomInt==indexmin1)
		{
			while(randomInt==index1||randomInt==index2||randomInt==indexmin1)
			{
				randomInt = randomGenerator.nextInt(ensemble.length);
			}			
		}
		indexmin2=randomInt;
		Splice sex=new Splice(ensemble[indexmin1],ensemble[indexmin2],max);
		
		Chromosome enfant1=sex.getEnfantx();
		int temp=GrayCode.grayToBinary(enfant1.array[0]);
		if(temp<0)
		{
			enfant1.array[0]=GrayCode.binaryToGray(0);
		}
		else
			if(temp>img.width())
			{
				enfant1.array[0]=GrayCode.binaryToGray(img.width());
			}
		temp=GrayCode.grayToBinary(enfant1.array[2]);
		if(temp<0)
		{
			enfant1.array[2]=GrayCode.binaryToGray(0);
		}
		else
			if(temp>img.width())
			{
				enfant1.array[2]=GrayCode.binaryToGray(img.width());
			}
		temp=GrayCode.grayToBinary(enfant1.array[1]);
		if(temp<0)
		{
			enfant1.array[1]=GrayCode.binaryToGray(0);
		}
		else
			if(temp>img.height())
			{
				enfant1.array[1]=GrayCode.binaryToGray(img.height());
			}
		temp=GrayCode.grayToBinary(enfant1.array[3]);
		if(temp<0)
		{
			enfant1.array[3]=GrayCode.binaryToGray(0);
		}
		else
			if(temp>img.height())
			{
				enfant1.array[3]=GrayCode.binaryToGray(img.height());
			}	
		ensemble[index1]=enfant1;
		
		
		
		Chromosome enfant2=sex.getEnfanty();
		temp=GrayCode.grayToBinary(enfant2.array[0]);
		if(temp<0)
		{
			enfant2.array[0]=GrayCode.binaryToGray(0);
		}
		else
			if(temp>img.width())
			{
				enfant2.array[0]=GrayCode.binaryToGray(img.width());
			}
		temp=GrayCode.grayToBinary(enfant2.array[2]);
		if(temp<0)
		{
			enfant2.array[2]=GrayCode.binaryToGray(0);
		}
		else
			if(temp>img.width())
			{
				enfant2.array[2]=GrayCode.binaryToGray(img.width());
			}
		temp=GrayCode.grayToBinary(enfant2.array[1]);
		if(temp<0)
		{
			enfant2.array[1]=GrayCode.binaryToGray(0);
		}
		else
			if(temp>img.height())
			{
				enfant2.array[1]=GrayCode.binaryToGray(img.height());
			}
		temp=GrayCode.grayToBinary(enfant2.array[3]);
		if(temp<0)
		{
			enfant2.array[3]=GrayCode.binaryToGray(0);
		}
		else
			if(temp>img.height())
			{
				enfant2.array[3]=GrayCode.binaryToGray(img.height());
			}	
		ensemble[index2]=enfant2;
		System.out.println("4");
	}
	public void Progres(int iteration)
	{
		System.out.println("6");
	
			boolean Continue=true;
			while(Continue)
			{
				for(int i=1;i<=iteration;i++)
				{
					Selection();
				}
				System.out.println("7");
				Composition();
				
							
				System.out.println("Voulez-vous continuer:o/n ");
				Scanner scan = new Scanner(System.in);
				String reponse = scan.nextLine(); 
				         
				if(reponse.equals("n"))
				{
					Continue=false;
				}
				
				
			}
		
			System.out.println("5");
	}
	public void Composition()
	{
		
		IplImage img2=cvCreateImage(cvSize(img.width(),img.height()),img.depth(),img.nChannels());
		
		
		for(int i=0;i<ensemble.length;i++)
		{
			int x1=GrayCode.grayToBinary(ensemble[i].array[0]);		
			int y1=GrayCode.grayToBinary(ensemble[i].array[1]);
			int x2=GrayCode.grayToBinary(ensemble[i].array[2]);
			int y2=GrayCode.grayToBinary(ensemble[i].array[3]);

			
			int haut=Math.min(y1,y2);
			int bas=Math.max(y1,y2);
			int droite=Math.max(x1,x2);
			int gauche=Math.min(x1,x2);
		
			
			for(int j=haut;j<bas;j++)
			{
				for(int k=gauche;k<droite;k++)
				{
					CvScalar s = cvScalar(GrayCode.grayToBinary(ensemble[i].array[4]), GrayCode.grayToBinary(ensemble[i].array[5]), GrayCode.grayToBinary(ensemble[i].array[6]),0);
					CvScalar t= cvGet2D(img2,j,k);
					CvScalar u = cvScalar(t.val(0)+s.val(0),t.val(1)+s.val(1),t.val(2)+s.val(2),0);
						cvSet2D(img2,j,k,u);
				}
			}
			
		}
		
		cvNamedWindow("win1", CV_WINDOW_AUTOSIZE);
		
		cvShowImage("win1",img2);
		 
		cvWaitKey(0);
		
		cvDestroyWindow("win1");
		
		cvReleaseImage(img2 );
		
		System.out.println("8");
	}
	
	
	
}

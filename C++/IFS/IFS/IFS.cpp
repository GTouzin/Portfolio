#include "stdafx.h"

#include <cv.h>
#include <cxcore.h>
#include <highgui.h>

int _tmain(int argc, _TCHAR* argv[])
{
	int Iteration =100;

	//variable booléenne controlant quel méthode est utilisée
	bool Deterministe = false;
	bool Aleatoire =false;
	bool Croissance =false;
	bool Death = false;

	//Initialisation de la graine, de la durée de la période de chauffe et les probabilités pour l'algorithme aléatoire
	int x0=17;
	int y0=23;
	int Chauffe=10;
	double p1=1/3;
	double p2=1/3;
	double p3=1/3;

	//Déclaration de la fonction
	double a1[2];
	a1[0]=0.5;
	a1[1]=0;
	double b1[2];
	b1[0]= 0;
	b1[1]=0.5;
	double c1[2];
	c1[0]= 0;
	c1[1]=0;

	double a2[2];
	a2[0]=0.5;
	a2[1]=0;
	double b2[2];
	b2[0]= 0;
	b2[1]=0.5;
	double c2[2];
	c2[0]= 0;
	c2[1]=0.5;

	double a3[2];
	a3[0]=0.5;
	a3[1]=0;
	double b3[2];
	b3[0]= 0;
	b3[1]=0.5;
	double c3[2];
	c3[0]= 0.5;
	c3[1]=0.5;
	
	//Déclaration du tableau contenant l'image 
	int image[1024][1280];

	//Dessin d'une croix dans le tableau
	for(int x=0;x<1024;x++)
	{
		for(int y=0;y<1280;y++)
		{
			if(x==512)
			{
				image[x][y]=1;
			}
			else 
				if(y==640)
				{
					image[x][y]=1;
				}
				else
				image[x][y]=0;
		}
	}

	//Initialisation de l'image temporaire
	int copie[1024][1280];
		for(int x=0;x<1024;x++)
		{
			for(int y=0;y<1280;y++)
			{
				copie[x][y]=0;
			}
		}

	if(Deterministe)
	{
		for( int i=0;i<Iteration;i++)
		{
			for(int x=0;x<1024;x++)
			{
				for(int y=0;y<1280;y++)
				{
					if(image[x][y]==1)
					{
						int x1 = a1[0]*x+b1[0]*y+c1[0]*x;
						int y1 = a1[1]*x+b1[1]*y+c1[1]*y;
						copie[x1][y1]=1;

						int x2 = a2[0]*x+b2[0]*y+c2[0]*x;
						int y2 = a2[1]*x+b2[1]*y+c2[1]*y;
						copie[x2][y2]=1;

						int x3 = a3[0]*x+b3[0]*y+c3[0]*x;
						int y3 = a3[1]*x+b3[1]*y+c3[1]*y;
						copie[x3][y3]=1;
					}
				}
			}

			for(int x=0;x<1024;x++)
			{
				for(int y=0;y<1280;y++)
				{
					image[x][y]=copie[x][y];
					copie[x][y]=0;
				}
			}
		}
	}

	if(Aleatoire)
	{
		if(p1+p2+p3!=1)
		{
			
		}
		else
		{
			int pointeurx=x0;
			int pointeury=y0;

			for(int i=0;i<Chauffe;i++)
			{
				int temp= rand()%100+1;
				if(temp<=p1*100)
				{
					pointeurx=a1[0]*pointeurx+b1[0]*pointeury+c1[0]*pointeurx;
					pointeury=a1[1]*pointeurx+b1[1]*pointeury+c1[1]*pointeury;
				}
				else
					if(p1*100<temp<=p2*100)
					{
						pointeurx=a2[0]*pointeurx+b2[0]*pointeury+c2[0]*pointeurx;
						pointeury=a2[1]*pointeurx+b2[1]*pointeury+c2[1]*pointeury;
					}
					else
					{
						pointeurx=a3[0]*pointeurx+b3[0]*pointeury+c3[0]*pointeurx;
						pointeury=a3[1]*pointeurx+b3[1]*pointeury+c3[1]*pointeury;
					}
				 
			}

			for(int i=0;i<Iteration;i++)
			{
				int temp= rand()%100+1;
				if(temp<=p1*100)
				{
					pointeurx=a1[0]*pointeurx+b1[0]*pointeury+c1[0]*pointeurx;
					pointeury=a1[1]*pointeurx+b1[1]*pointeury+c1[1]*pointeury;
				}
				else
					if(p1*100<temp<=p2*100)
					{
						pointeurx=a2[0]*pointeurx+b2[0]*pointeury+c2[0]*pointeurx;
						pointeury=a2[1]*pointeurx+b2[1]*pointeury+c2[1]*pointeury;
					}
					else
					{
						pointeurx=a3[0]*pointeurx+b3[0]*pointeury+c3[0]*pointeurx;
						pointeury=a3[1]*pointeurx+b3[1]*pointeury+c3[1]*pointeury;
					}

				 copie[pointeurx][pointeury]=1;
			}

		}
			for(int x=0;x<1024;x++)
			{
				for(int y=0;y<1280;y++)
				{
					image[x][y]=copie[x][y];
				}
			}

	
	}

	if(Croissance)
	{
		if(p1+p2+p3!=1)
		{
			
		}
		else
		{
			int pointeurx=x0;
			int pointeury=y0;

			for(int i=0;i<Chauffe;i++)
			{
				int temp= rand()%100+1;
				if(temp<=p1*100)
				{
					pointeurx=a1[0]*pointeurx+b1[0]*pointeury+c1[0]*pointeurx;
					pointeury=a1[1]*pointeurx+b1[1]*pointeury+c1[1]*pointeury;
				}
				else
					if(p1*100<temp<=p2*100)
					{
						pointeurx=a2[0]*pointeurx+b2[0]*pointeury+c2[0]*pointeurx;
						pointeury=a2[1]*pointeurx+b2[1]*pointeury+c2[1]*pointeury;
					}
					else
					{
						pointeurx=a3[0]*pointeurx+b3[0]*pointeury+c3[0]*pointeurx;
						pointeury=a3[1]*pointeurx+b3[1]*pointeury+c3[1]*pointeury;
					}
				 
			}
			
			int** registre;
			int n;
			int** tempRegistre;
			
			for(int i=0;i<Iteration;i++)
			{
				n=0;
				registre=new int*[n];
				for ( int i = 0 ; i < n ; i++ ) registre[i] = new int[n] ;

				tempRegistre=new int*[n];
				for ( int i = 0 ; i < n ; i++ ) tempRegistre[i] = new int[n] ;

				if(copie[pointeurx][pointeury]==1)
				{
					int temp=0;
					while(registre[0][temp]!=pointeurx||registre[1][temp]!=pointeury)
					{
						temp++;
					}

					for(int i=0;i<temp;i++)
					{
						tempRegistre[0][i]=registre[0][i];
						tempRegistre[1][i]=registre[1][i];
					}
					for(int i=temp;i<n;i++)
					{
						tempRegistre[0][i]=registre[0][i];
						tempRegistre[1][i]=registre[1][i];
					}
					for ( int i = 0 ; i < n ; i++ ) registre[i] = new int[n] ;
				}
				else
				{
					tempRegistre=new int*[n+3];
					for ( int i = 0 ; i < n+3 ; i++ ) tempRegistre[i] = new int[n+3] ;
					for(int i=0;i<n;i++)
					{
						tempRegistre[0][i]=registre[0][i];
						tempRegistre[1][i]=registre[1][i];
					}
				
					int tempx=a1[0]*pointeurx+b1[0]*pointeury+c1[0]*pointeurx;
					int tempy=a1[1]*pointeurx+b1[1]*pointeury+c1[1]*pointeury;

					int count=0;
					int temp=0;
					while(registre[0][temp]!=tempx||registre[1][temp]!=tempy)
					{
						temp++;
					}
					
					if(temp==n)
					{
						tempRegistre[0][n+count]=tempx;
						tempRegistre[1][n+count]=tempy;
						count++;
					}

					tempx=a2[0]*pointeurx+b2[0]*pointeury+c2[0]*pointeurx;
					tempy=a2[1]*pointeurx+b2[1]*pointeury+c2[1]*pointeury;

					temp=0;
					while(registre[0][temp]!=tempx||registre[1][temp]!=tempy)
					{
						temp++;
					}
					
					if(temp==n)
					{
						tempRegistre[0][n+count]=tempx;
						tempRegistre[1][n+count]=tempy;
						count++;
					}

					tempx=a3[0]*pointeurx+b3[0]*pointeury+c3[0]*pointeurx;
					tempy=a3[1]*pointeurx+b3[1]*pointeury+c3[1]*pointeury;

					temp=0;
					while(registre[0][temp]!=tempx||registre[1][temp]!=tempy)
					{
						temp++;
					}
					
					if(temp==n)
					{
						tempRegistre[0][n+count]=tempx;
						tempRegistre[1][n+count]=tempy;
						count++;
					}

					n=n+count;
					for ( int i = 0 ; i < n ; i++ ) registre[i] = new int[n] ;
					for(int i=0;i<n;i++)
					{
						registre[0][i]=tempRegistre[0][i];
						registre[1][i]=tempRegistre[1][i];
					}

				}
				
				
			}
		}
	}
	if(Death)
	{
		//Initialisation de la surface de départ
		for(int x=0;x<1024;x++)
		{
			for(int y=0;y<1280;y++)
			{
				image[x][y]=1;
			}
		}

		for(int i=0;i<Iteration;i++)
		{
			for(int x=0;x<1024;x++)
			{
				for(int y=0;y<1280;y++)
				{
					int tempx=x=a1[0]*x+b1[0]*y+c1[0]*x;
					int tempy=y=a1[1]*x+b1[1]*y+c1[1]*y;
					copie[tempx][tempy]=1;
				}
			}
			for(int x=0;x<1024;x++)
			{
				for(int y=0;y<1280;y++)
				{
					int tempx=x=a2[0]*x+b2[0]*y+c2[0]*x;
					int tempy=y=a2[1]*x+b2[1]*y+c2[1]*y;
					copie[tempx][tempy]=1;
				}
			}
			for(int x=0;x<1024;x++)
			{
				for(int y=0;y<1280;y++)
				{
					int tempx=x=a3[0]*x+b3[0]*y+c3[0]*x;
					int tempy=y=a3[1]*x+b3[1]*y+c3[1]*y;
					copie[tempx][tempy]=1;
				}
			}
			//Copie l'image temporaire dans l'image et efface l'image temporaire
			for(int x=0;x<1024;x++)
			{
				for(int y=0;y<1280;y++)
				{
					image[x][y]=copie[x][y];
					copie[x][y]=0;
				}
			}
		}

	}

       
        


return 0;
}
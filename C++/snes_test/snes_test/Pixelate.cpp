#include "stdafx.h"
#include "Pixelate.h"


#include <math.h>
#include<opencv2/highgui/highgui.hpp>
using namespace cv;


Pixelate::Pixelate(void)
{
}

Pixelate::Pixelate(Mat img)
{
	 
		pixalate = img.clone();
		double largeurpixel=10;
		double hauteurpixel=10;

		int nlpixel = floor((pixalate.cols )/largeurpixel);
		int nhpixel = floor((pixalate.rows )/hauteurpixel);
		double aire =largeurpixel*hauteurpixel;

		for(int countl=0;countl<=nlpixel;countl++)
		{
			for(int counth=0;counth<=nhpixel;counth++)
			{
				int templ=countl*largeurpixel;
				int temph=counth*hauteurpixel;

				double sommerouge=0;
				double sommevert=0;
				double sommebleu=0;

				 unsigned char *input = (unsigned char*)(img.data);
				for(int y=temph;y<(img.rows)&&y<temph+hauteurpixel;y++)
				{
					for(int x=templ;x<(img.cols)&&x<templ+largeurpixel;x++) 
					{
						sommebleu=sommebleu+input[img.step[0]*y+img.step[1]*x+0];
						sommevert=sommevert+input[img.step[0]*y+img.step[1]*x+1];
						sommerouge=sommerouge+input[img.step[0]*y+img.step[1]*x+2];
					}
				}
				
				
				int moyennerouge=floor(sommerouge/(aire));
				int moyennevert=floor(sommevert/(aire));
				int moyennebleu=floor(sommebleu/(aire));

				
				for(int y=temph;y<(pixalate.rows)&&y<temph+hauteurpixel;y++) 
				{
					for(int x=templ;x<(pixalate.cols)&&x<templ+largeurpixel;x++)
					{
						pixalate.data[pixalate.step[0]*y+pixalate.step[1]*x+0]=moyennebleu;
						pixalate.data[pixalate.step[0]*y+pixalate.step[1]*x+1]=moyennevert;
						pixalate.data[pixalate.step[0]*y+pixalate.step[1]*x+2]=moyennerouge;
					}
				}
			}	
		}

		
}


Pixelate::Pixelate(Mat img, Palette p)
{	 
		pixalate = img.clone();
		double largeurpixel=10;
		double hauteurpixel=10;

		int nlpixel = floor((pixalate.cols )/largeurpixel);
		int nhpixel = floor((pixalate.rows )/hauteurpixel);
		double aire =largeurpixel*hauteurpixel;

		for(int countl=0;countl<=nlpixel;countl++)
		{
			for(int counth=0;counth<=nhpixel;counth++)
			{
				int templ=countl*largeurpixel;
				int temph=counth*hauteurpixel;

				double sommerouge=0;
				double sommevert=0;
				double sommebleu=0;

				 unsigned char *input = (unsigned char*)(img.data);
				for(int y=temph;y<(img.rows)&&y<temph+hauteurpixel;y++)
				{
					for(int x=templ;x<(img.cols)&&x<templ+largeurpixel;x++) 
					{
						sommebleu=sommebleu+input[img.step[0]*y+img.step[1]*x+0];
						sommevert=sommevert+input[img.step[0]*y+img.step[1]*x+1];
						sommerouge=sommerouge+input[img.step[0]*y+img.step[1]*x+2];
					}
				}
				
				
				int moyennerouge=floor(sommerouge/(aire));
				int moyennevert=floor(sommevert/(aire));
				int moyennebleu=floor(sommebleu/(aire));

				int pointeurligne=0;
				int distance=sqrt(3*pow(255,2.0));

				for(int y=0;y<p.getLigneTableau();y++) 
				{
					int temp=sqrt(pow(moyennerouge-p.tableau[y][0],2.0)+pow(moyennevert-p.tableau[y][1],2.0)+pow(moyennebleu-p.tableau[y][2],2.0));
					if(distance >temp)
					{
						pointeurligne=y;
						distance=temp;
					}
					
				}


				
				for(int y=temph;y<(pixalate.rows)&&y<temph+hauteurpixel;y++) 
				{
					for(int x=templ;x<(pixalate.cols)&&x<templ+largeurpixel;x++)
					{
						pixalate.data[pixalate.step[0]*y+pixalate.step[1]*x+0]=p.tableau[pointeurligne][2];
						pixalate.data[pixalate.step[0]*y+pixalate.step[1]*x+1]=p.tableau[pointeurligne][1];
						pixalate.data[pixalate.step[0]*y+pixalate.step[1]*x+2]=p.tableau[pointeurligne][0];
					}
				}
			}	
		}

		
}

Pixelate::~Pixelate(void)
{
}

Mat Pixelate::GetImage()
{
	return pixalate;
}

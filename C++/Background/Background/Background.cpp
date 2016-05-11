// Background.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"

#include <cv.h>
#include <cxcore.h>
#include <highgui.h>


int _tmain(int argc, _TCHAR* argv[])
{
	srand ( time(NULL) );
	int largeur=1280*1;
	int hauteur=1024;

	double ratiohorizon=0.333;
	int yhorizon=floor(hauteur*(1-ratiohorizon));
	CvPoint hor1=cvPoint(0,yhorizon);
	CvPoint hor2=cvPoint(largeur,yhorizon);
	CvScalar ligne=cvScalar(0,0,0);

	

	double hauteuretage=300;
	double largeuretage=500;
	int nombremaxapplarge=3;
	int nombapphautmax=floor(yhorizon/hauteuretage);

	double hauteurfenetre=130;
	double largeurfenetre=90;
	int h1=hauteuretage/3;
	int h2=(2*hauteuretage)/3;
	int l1=largeuretage/4;
	int l2=3*largeuretage/2;

	double maxhauteur=hauteur*(1-ratiohorizon);
	double maxlargeur=300;

	IplImage*img=cvCreateImage( cvSize(largeur,hauteur), IPL_DEPTH_8U, 3 );

	CvScalar tempcolor=cvScalar(255,255,255);

	for(int x=0;x<(img->width);x++)
	{
		for(int y=0;y<(img->height);y++)
		{
			cvSet2D(img, y, x,tempcolor);						
		}
	}

	//dessine le croisement entre le sol et les edifices
	cvDrawLine(img,hor1,hor2,ligne,1,8,0);

	//colore le ciel bleu
	CvScalar cielcolor=cvScalar(255,126,77);
	for(int x=0;x<img->width;x++)
	{
		for(int y=0;y<yhorizon;y++)
		{
			cvSet2D(img, y, x,cielcolor);
		}
	}

	int pointeurx=0;
	int pointeury=0;

	for(int x=0;x<img->width;x=pointeurx)
	{
		//Position et taille des immeubles
		int nombapplarg= rand()%nombremaxapplarge; 
		int nombapphaut= rand()%nombapphautmax+1;

		int largimm = nombapplarg*largeuretage;
		int hautimm = nombapphaut*hauteuretage;

		CvPoint x1=cvPoint(x,yhorizon-hautimm);
		CvPoint x2=cvPoint(pointeurx+largimm,yhorizon);

		cvRectangle(img,x1,x2,ligne,2,8,0);
		//texture des immeubles
		 IplImage *fond = cvLoadImage("brique.jpg");

		for(int pointerx=x;pointerx<x+largimm;pointerx+=fond->width)
		{
			for(int pointery=yhorizon;pointery>yhorizon-hautimm;pointery-=fond->height)
			{
				for(int a=0;pointerx+a<img->width&&pointerx+a<pointeurx+largimm&&a<fond->width;a++)
				{
					for(int b=0;pointery-b>0&&pointery-b>yhorizon-hautimm&&b<fond->height;b++)
					{
						cvSet2D(img, pointery-b, pointerx+a,cvGet2D(fond,b,a));
					}
				}
				
			}
		}

		//fenêtres
		//texture des fenêtres
		 IplImage *fenetre = cvLoadImage("fenetre.jpg");
		for(int a=0;a<nombapplarg;a++)
		{
			int refx=x+a*largeuretage;
	
			for(int b=0;b<nombapphaut;b++)
			{
				int refy=yhorizon-b*hauteuretage;

				for(int c=0;refx+c<img->width&&c<fenetre->width;c++)
				{
					for(int d=0;refy-h2+d<img->height&&d<fenetre->height;d++)
					{
						cvSet2D(img,refy-h2+d,refx+c,cvGet2D(fenetre,d,c));
					}

				}

			}

		}

		pointeurx=pointeurx+largimm;
	}







	// Display the image.
    cvNamedWindow("Image:", CV_WINDOW_AUTOSIZE);
    cvShowImage("Image:", img);

    // Wait for the user to press a key in the GUI window.
    cvWaitKey(0);

	cvSaveImage("TestImage.jpg",img);
	 // Free the resources.
        cvDestroyWindow("Image:");
        cvReleaseImage(&img);


	return 0;
}


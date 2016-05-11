#include "stdafx.h"

#include <cv.h>
#include <cxcore.h>
#include <highgui.h>

int _tmain(int argc, _TCHAR* argv[])
{
        IplImage *img = cvLoadImage("zapper-movies-8-650x863.jpg");
        if (!img) {
                printf("Error: Couldn't open the image file.\n");
                return 1;
        }

        cvNamedWindow("Image:", CV_WINDOW_AUTOSIZE);
        cvShowImage("Image:", img);

        cvWaitKey(0);

		IplImage *pixalate = cvCloneImage(img);
		double largeurpixel=10;
		double hauteurpixel=10;

		int nlpixel = floor((pixalate->width)/largeurpixel);
		int nhpixel = floor((pixalate->height)/hauteurpixel);
		double aire =largeurpixel*hauteurpixel;

		for(int countl=0;countl<=nlpixel;countl++)
		{
			for(int counth=0;counth<=nhpixel;counth++)
			{
				int templ=countl*largeurpixel;
				int temph=counth*hauteurpixel;

				double sommerouge=255;
				double sommevert=255;
				double sommebleu=255;

				for(int x=templ;x<(img->width)&&x<templ+largeurpixel;x++)
				{
					for(int y=temph;y<(img->height)&&y<temph+hauteurpixel;y++)
					{
						CvScalar pixel = cvGet2D(img, y, x);
						sommerouge=sommerouge+pixel.val[2];
						sommevert=sommevert+pixel.val[1];
						sommebleu=sommebleu+pixel.val[0];

					}
				}
				
				
				int moyennerouge=floor(sommerouge/(aire));
				int moyennevert=floor(sommevert/(aire));
				int moyennebleu=floor(sommebleu/(aire));

				CvScalar temp;
				temp.val[0]=moyennebleu;
				temp.val[1]=moyennevert;
				temp.val[2]=moyennerouge;

				for(int x=templ;x<(img->width)&&x<templ+largeurpixel;x++)
				{
					for(int y=temph;y<(img->height)&&y<temph+hauteurpixel;y++)
					{
						cvSet2D(pixalate, y, x,temp);						
					}
				}
			}	
		}

		 // Display the image.
        cvNamedWindow("Image2:", CV_WINDOW_AUTOSIZE);
        cvShowImage("Image2:", pixalate);

        // Wait for the user to press a key in the GUI window.
        cvWaitKey(0);

		cvSaveImage("TestImage.jpg",pixalate);

		// Free the resources.
        cvDestroyWindow("Image2:");
        cvReleaseImage(&pixalate);


        // Free the resources.
        cvDestroyWindow("Image:");
        cvReleaseImage(&img);
        
        return 0;
}


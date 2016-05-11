#include "stdafx.h"

#include <iostream>

#include <cv.h>
#include <cxcore.h>
#include <highgui.h>

#include "Palette.h"
#include "Pixelate.h"
#include "Edge.h"
using namespace cv;
using namespace std;

int _tmain(int argc, _TCHAR* argv[])
{

       // Open the file.
        Mat img = imread("zapper-movies-8-650x863.jpg",CV_LOAD_IMAGE_COLOR);
	
		if(!img.data)
		{
			cout <<"La photo ne peut pas être lue."<<std::endl;
			return -1;
		}
        
		namedWindow( "Display window", WINDOW_AUTOSIZE );// Create a window for display.
		imshow( "Display window", img );                   // Show our image inside it.
		waitKey(0);                                          // Wait for a keystroke in the window


		//Edge image(img);

		Palette test;
		Pixelate temp(img,test);

		namedWindow( "Display window", WINDOW_AUTOSIZE );
		imshow("Display window",temp.GetImage());
		waitKey(0);     

		
}


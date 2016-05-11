#pragma once
#include "opencv2/highgui/highgui.hpp"
#include "opencv2/imgproc/imgproc.hpp"

#include <stdlib.h>
#include <stdio.h>

using namespace cv;
class Edge
{
public:

	
	Edge(void);
	Edge(Mat image);

	~Edge(void);
};


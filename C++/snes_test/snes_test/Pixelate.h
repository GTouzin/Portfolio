#include <cv.h>
#include <cxcore.h>
#include <highgui.h>
#include "Palette.h"
using namespace cv;
class Pixelate
{
private:
	int Vrai;

public:

	Mat pixalate;

	Pixelate(void);
	Pixelate(Mat img);
	Pixelate(Mat img, Palette p);
	~Pixelate(void);

	Mat GetImage();
};

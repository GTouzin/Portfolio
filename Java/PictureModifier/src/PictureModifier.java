import static org.bytedeco.javacpp.opencv_core.*;
//import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import static org.bytedeco.javacpp.opencv_highgui.*;

//import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.IplImage;


public class PictureModifier {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		
		IplImage image = cvLoadImage("Grumpy-Cat-Tadar-Sauce2.jpg");
				
		cvNamedWindow("win1", CV_WINDOW_AUTOSIZE);

		cvShowImage("win1",image);
		 
		cvWaitKey(0);
		
		cvDestroyWindow("win1");
				
		ArtificialSelection test=new ArtificialSelection(30,image);
		
		
		test.Progres(1);
		
	
		cvReleaseImage(image );
		
		
		
		//IplImage image = cvLoadImage("Sans titre.jpg");
		//for(int j=0; j<image.height();j++)
		//{
		//	for(int i=0;i<image.width();i++)
		//	{
		//	CvScalar t= cvGet2D(image,j,i);
		//	
		//	int b =(int) t.val(0);
		//	int btemp= GrayCode.binaryToGray(b);
		//	b=GrayCode.grayToBinary(btemp);
		//	
		//	int g =(int)t.val(1);
		//	int gtemp= GrayCode.binaryToGray(g);
		//	g=GrayCode.grayToBinary(gtemp);
		////	
		//	int r =(int)t.val(2);
		//	int rtemp= GrayCode.binaryToGray(r);
		//	r=GrayCode.grayToBinary(rtemp);
		//	CvScalar u = cvScalar(GrayCode.grayToBinary(btemp),GrayCode.grayToBinary(gtemp),GrayCode.grayToBinary(rtemp),0);
		//	cvSet2D(image,j,i,u);
		//}
		//}
		
		//cvNamedWindow("win1", CV_WINDOW_AUTOSIZE);

		//cvShowImage("win1",image);
				 
		//cvWaitKey(0);
				
		//cvDestroyWindow("win1");
			
		//cvReleaseImage(image );
		
		
	}

	
		

}

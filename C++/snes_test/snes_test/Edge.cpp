#include "StdAfx.h"
#include "Edge.h"


Mat src, src_gray;
Mat dst, detected_edges;

int edgeThresh = 1;
int lowThreshold;
int const max_lowThreshold = 100;
int ratio = 3;
int kernel_size = 3;
char* window_name = "Edge Map";

void CannyThreshold(int, void*)
{
  /// Réduit le bruit avec un noyau de dimension 3x3
  blur( src_gray, detected_edges, Size(3,3) );

  /// Détecte les bords
  Canny( detected_edges, detected_edges, lowThreshold, lowThreshold*ratio, kernel_size );

  dst = Scalar::all(0);

  src.copyTo( dst, detected_edges);
  imshow( window_name, dst );
 }

Edge::Edge(Mat image)
{
	 /// Ouvre l'image et la convertie en niveau de gris
  src = image; 

   if( !src.data )
  {  }
   else
   {
	  /// Matrice de même dimension que l'image de départ servant de copie
	  dst.create( src.size(), src.type() );

	  /// convertie en niveau de gris
	  cvtColor( src, src_gray, CV_BGR2GRAY );

	  /// Nouvelle fenêtre
	  namedWindow( window_name, CV_WINDOW_AUTOSIZE );

	  /// Controle du seuil
	  createTrackbar( "Min Threshold:", window_name, &lowThreshold, max_lowThreshold, CannyThreshold);

	  /// Affiche l'image
	  CannyThreshold(0, 0);

	  //Ajouter un bouton qui permet de sauvegarder l'image

	  /// Détruit l'image lorsque l'utilisateur presse une touche
	  waitKey(0);
   }
}

Edge::Edge(void)
{
}


Edge::~Edge(void)
{
}




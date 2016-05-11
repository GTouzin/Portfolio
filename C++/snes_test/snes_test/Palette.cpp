#include "StdAfx.h"
#include<iostream>
#include <vector>
#include "Palette.h"


int n=0;
int *k;

std::vector<int> vect;

int Palette::getColTableau()
{
	return b;
}

int Palette::getLigneTableau()
{
	return a;
}
//Ajouter une fonction permettant d'Ajouter des couleurs
//Ajouter un tableau dynamique
//Ajouter une fonction qui sauve la palette
Palette::Palette()
{
	
	k=new int[4];

for(int count=0;count<n;count++)
{
	k[count]=0;
}
tableau[0][0]=188;
tableau[0][1]=188;
tableau[0][2]=188;
tableau[1][0]=0;
tableau[1][1]=120;
tableau[1][2]=248;
tableau[2][0]=0;
tableau[2][1]=88;
tableau[2][2]=248;
tableau[3][0]=104;
tableau[3][1]=68;
tableau[3][2]=252;
tableau[4][0]=216;
tableau[4][1]=0;
tableau[4][2]=204;


vect.push_back(1);
vect.size(); // --> 1
vect.push_back(2);
vect.size(); // --> 2

}


Palette::~Palette(void)
{
}

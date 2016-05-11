#pragma once
class Palette
{
public:
	static const int a=5;
	static const int b=3;
	int tableau[a][b];

	int getLigneTableau();
	int getColTableau();
	std::vector<int> vect;
	Palette();
	~Palette(void);
};


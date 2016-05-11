#include "StdAfx.h"
#include "Partition.h"

#include <iostream>
#include <fstream>
Partition::Partition(void)
{
	double total =0;

    std::ofstream out_file;
    out_file.open("number.txt");

    const int size = 5;
    double num_array[] = {1,2,3,4,5}; 

    for (int count = 0; count < size; count++)
    {
        if (num_array[count] == 0)
        {
            std::cout << "0 digit detected. " << std::endl;
            system("PAUSE");
        }
        else
        {
            out_file<< num_array[count]<<" ";    
        }
    }
    out_file<<std::endl;
    out_file.close();
    std::ifstream in_file;
    in_file.open("number.txt");
    double a;
    if(in_file.fail())
    {
        std::cout << "File opening error" << std::endl;
    }
    else
    {
        for (int count =0; count< size; count++)
        {
            in_file >> a;
            total += a;  // Access the element a currently points to
        }
    }

        std::cout << total/size << std::endl;
}


Partition::~Partition(void)
{
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Tableau
{
    class Program
    {
        static void Main(string[] args)
        {
           // Cat temp = new Cat(150,2,150);
           // temp.TestCycle();
           // temp.Generation();
           //temp.Afficher();

            Generateur temp = new Generateur(11,7);
            temp.Champ();
            Test tableau = new Test(1600);

            temp.Test(tableau.tableau);

            Console.WriteLine(6 % 3);
            Console.ReadLine();


        }
    }
}

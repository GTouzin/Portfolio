using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Tableau
{
    class Cat
    {
        //Largeur de la grille carrée 
        int n;
        //Modulo
        int m;
        //Multiplicateur
        int a;

        List<List<Coordonnee>> Series = new List<List<Coordonnee>>();

        //Constructeur par défeaut
        public Cat(int x)
        {
            n = x;
            m = 1;
            a = 2;
        }

        //Constructeur permettant de fixer le multiplicateur
        public Cat(int x1, int x2)
        {
            n = x1;
            m = 1;
            a = x2;
        }

        //Constructeur permettant de fixer le multiplicateur et le modulo
        public Cat(int x1, int x2, int x3)
        {
            n = x1;
            m = x3;
            a = x2;
        }

        public void Generation()
        {
            int cycle = TestCycle();

            for (int x = 0; x < n; x++)
            {
                for (int y = 0; y < n; y++)
                {
                    List<Coordonnee> Suite = new List<Coordonnee>();
                    Suite.Add(new Coordonnee(x, y));

                    for (int z = 0; z < cycle; z++)
                    {
                        Suite.Add(new Coordonnee((Suite[z].getX()*a+Suite[z].getY())%m,(Suite[z].getX()+Suite[z].getY())%m));
                    }

                    Series.Add(Suite);
                }

            }
        }

        public int TestCycle()
        {
            //Crée la graine p et la met dans la liste
            Random random = new Random();
            Coordonnee p = new Coordonnee(random.Next(1, n),random.Next(1, n));
            List<Coordonnee> liste = new List<Coordonnee>();
            liste.Add(new Coordonnee(p.getX(),p.getY()));

            int pointeur = 0;// Compte le nombre d'itérations
            int nSuite = 0;// variable qui compte le nombre de répétition repérées
            bool encore=true;
            while (pointeur < 3 * n && encore) 
            {
                double tempx = (a * p.getX() + p.getY())%m;
                double tempy = (p.getX() + p.getY())%m;
                p.setX(tempx);
                p.setY(tempy);
                liste.Add(new Coordonnee(p.getX(), p.getY()));

                if (p.getX() == liste[nSuite].getX() && p.getY() == liste[nSuite].getY())
                {
                    nSuite++;
                }
                else
                    nSuite = 0;

                if (nSuite > n / 2)
                {
                    encore = false;
                }

                pointeur++;
               
            }
            Console.WriteLine(pointeur - nSuite);
            Console.ReadLine();
            return pointeur - nSuite;

        }

        public void Afficher()
        {
            foreach (List<Coordonnee> Sublist in Series)
            {
                foreach (Coordonnee a in Sublist)
                {
                    Console.WriteLine(a.getX() + " , " + a.getY());
                }
                Console.WriteLine();
            }
            Console.ReadLine();
        }
    }
}

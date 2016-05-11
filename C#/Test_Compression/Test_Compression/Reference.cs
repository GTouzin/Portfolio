using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Test_Compression
{
    class Reference
    {
        int[,] table;
        string[,] Liste;
        int a,b,x0,m;
        

        public Reference(int a, int b, int x0, int m)
        {
            this.a=a;
            this.b= b;
            this.x0=x0;
            this.m=m;
            table= new int[m,3];
            Liste = new string[m, 3];
            Generer();
        }

        public void Generer()
        {
            int pointeur = 1;

            table[0, 1] = (a * x0 + b) % m;
            table[0, 2] = x0;

            table[1, 1] = (a * table[0, 1] + b) % m;
            table[1, 2] = table[0, 2];

            while (((a * table[pointeur, 1] + b) % m) != table[0, 1]&& pointeur+1<table.GetLength(0))
            {
                table[pointeur + 1, 1] = (a * table[pointeur, 1] + b) % m;
                table[pointeur + 1, 2] = table[pointeur, 1];
                pointeur++;
            }

            for(int i=0; i<=pointeur; i++)
            {
                for (int j = 1; j < 3; j++)
                {
                    Liste[i, j] = table[i, j].ToString();
                }
                Liste[i, 0] = Liste[i, 1][0].ToString();
            }
        }

        public void AfficherListe()
        {
            for (int i = 0; i < table.GetLength(0); i++)
            {
                Console.Out.WriteLine(table[i, 0] + " | " + table[i, 1] + " | " + table[i, 2]);
            }
            Console.Out.WriteLine("Liste");
            for (int i = 0; i < Liste.GetLength(0); i++)
            {
                Console.Out.WriteLine(Liste[i, 0] + " | " + Liste[i, 1] + " | " + Liste[i, 2]);
            }
            Console.ReadLine();
        }

    }
}

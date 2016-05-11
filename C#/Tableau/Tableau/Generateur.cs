using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Tableau
{
    //Classe qui génère des nombrs à l'aide du générateur congruentiel linéaire
    class Generateur 
    {
        int n;                  //Nombre de nombre à générer
        int m;                  //Modulo
        Reference ensemble;     //Liste des nombres générés
        List<List<int>> indexe = new List<List<int>>();  //Liste des paramètres liée à chaque série
        //Constructeur
        public Generateur(int modulo, int nObjet)
        {
            m = modulo;
            n = nObjet;
            ensemble = new Reference();
        }

        public void Champ()
        {
            List<int> templist = new List<int>();

            for (int X0 = 0; X0 < m; X0++)
            {
                for (int a = 0; a < m; a++)
                {
                    for (int c = 0; c < m; c++)
                    {
                        List<int> parametre = new List<int>();
                        parametre.Add(a);
                        parametre.Add(c);
                        parametre.Add(X0);
                        indexe.Add(parametre);

                        List<int> list = new List<int>();
                        list.Add((a*X0 + c) % m);

                        for (int x = 1; x < n; x++)
                        {
                            list.Add((a * list[x - 1] + c) % m);
                        }
                        //Console.WriteLine("X0:" + X0 + "A:" + a + " C:" + c);
                        ensemble.Compare(list);     //Ajoute la templiste à ensemble si elle est unique
                    }
                }
            }
        }

        public void Afficher()
        {
            ensemble.Affiche();
            
        }

        public void Test(List<int> tableau)// donner l'option de le faire plusieurs fois pour faire la moyenne des résultats
        {
            List<int> dict = new List<int>();
            
            int i=0;
            int compte = 0;//À éffacer
            while (i < tableau.Count)
            {
                if (tableau.Count - i > n)
                {
                    List<int> temp = new List<int>();

                    for (int x = i; x - i < n; x++)
                    {
                        temp.Add(tableau[x]);
                       // Console.Write(tableau[x] + ", ");
                    }
                   // Console.WriteLine();
                    int pointeur = -10;                          //indexe de la série identique dans la liste
                    foreach (List<int> sublist in ensemble.list)
                    {

                        if (temp.SequenceEqual(sublist))
                        {
                            pointeur = ensemble.list.IndexOf(sublist);
                        }
                                                                    
                    }
                    bool trouver;
                    if (pointeur != -10)
                        trouver = true;
                    else
                        trouver = false;

                    if (trouver)
                    {
                        dict.Add(999999);
                        for (int x = 0; x < indexe[pointeur].Count; x++)
                        {
                            dict.Add(indexe[pointeur][x]);
                        }
                        dict.Add(999999);
                        i = i + n;
                        compte++;
                    }
                    else
                    { 
                        dict.Add(temp[0]);
                        i++;
                    }
                    
                }
                else
                {
                    dict.Add(tableau[i]);       
                    i++;
                }
                
            }
            //Affiche le nombre d'élement du tableau

            Console.WriteLine("Nombre d'élement du tableau:" + tableau.Count);

            //Afficher le nombre d'élement du dictionnaire

            Console.WriteLine("Nombre d'élement du dictionnaire:" + dict.Count);
            Console.WriteLine("Nombre de succès:" + compte);
           // foreach (int t in dict)
           // {
           //     Console.WriteLine(t);
            // }
            Console.WriteLine(tableau.Count-dict.Count);
            Console.ReadLine();
        }

       
    }

   
}

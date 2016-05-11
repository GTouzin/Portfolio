using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Tableau
{
    class Reference
    {
       public List<List<int>> list = new List<List<int>>();

        public Reference()
        {
            
        }

        //Méthode qui compare une liste avec celle d'une liste de liste et l'ajoute si elle est unique.
        public void Compare(List<int> tableau)
        {
            bool unique = true;
            foreach (List<int> sublist in list)
            {
                if (sublist.SequenceEqual(tableau))
                {
                    unique = false;
                }
            }

            if (unique)
            {
                list.Add(tableau);

            }
        }

        //Méthode qui affiche les élements d'une liste de liste
        public void Affiche()
        {
            foreach (List<int> sublist in list)
            {
                foreach (int a in sublist )
                {
                    Console.WriteLine(a);
                }
                Console.WriteLine("Next");
            }

            Console.WriteLine("Longueur de la liste:"+list.Count);
            Console.ReadLine();
        }


        public void Add(List<int> Tableau)
        {
            list.Add(Tableau);
        }


    }
}

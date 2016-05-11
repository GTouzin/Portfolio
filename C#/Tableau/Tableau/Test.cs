using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Tableau
{
    class Test
    {
        //Quantité de nombre à générer
        int n;

        //Tableau des nombres
        public List<int> tableau = new List<int>();

        //Plus grand nombre à générer
        int m;

        //Constructeur fixant le plus grand nombre à 10
        public Test(int n)
        {
            this.n = n;
            this.m = 10;
            Generation();
        }

        //Constructeur permettant de fixer le nombre de chiffre à générer et le plus grand nombre à générer 
        public Test(int n, int m)
        {
            this.n = n;
            this.m = m;
            Generation();
        }

        //Méthode qui génère les nombres

        public void Generation()
        {
            Random random = new Random(Guid.NewGuid().GetHashCode()); 

            for (int x = 0; x < n; x++)
            {
                tableau.Add(random.Next(0,m-1));
            }
        }

        //Méthode qui encode une série de nombre à l'aide d'une bibliothèque

        public void EncodeSimple()
        {

        }
    }
}

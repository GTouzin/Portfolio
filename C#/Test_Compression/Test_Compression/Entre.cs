using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Test_Compression
{
    class Entre
    {
        string texte = "";
        public Entre()
        {
        }

        public Entre(int a)
        {
            Random random = new Random();
            for (int i = 0; i < a; i++)
            {
                int t= random.Next(0,10);
                texte = texte + t.ToString();
            }
        }

        public string GetTexte()
        {
            return texte;
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using MySql.Data.MySqlClient; 


namespace Test_Compression
{
    class Program
    {
        static void Main(string[] args)
        {
            DBConnect temp = new DBConnect();
            temp.Insert("3","35");
            temp.AfficherTable();
           Console.Out.WriteLine( temp.Count());
           Console.ReadLine();

           
           

            
        }
    }
}

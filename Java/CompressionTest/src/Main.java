import java.io.IOException;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;

import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Deflater;

public class Main {
    public static void main(String[] args) {
    	
    	//int[] a={0,1,2,3,4};
    	//Reference temp= new Reference(a);
    	//temp.Affichage();
    	//temp.Enregistrer();
    	//temp.Lire();
    	
    	//Generator essaie = new Generator(100,1000);
    	//essaie.GenerationVentille(5);
    	
    	File fichierGen=new File("G:/Générateur.txt");
    	File fichierRef=new File("G:/Référence.txt");
    	Liste liste = new Liste(fichierRef,fichierGen);
    	//liste.Comparaison();
    	
    	//liste.Trier();
    	
    	liste.Dictionnaire();
    	
    	//Test etude=new Test(0,4);
    	//etude.test("Générateur",1000,5);
    }
}
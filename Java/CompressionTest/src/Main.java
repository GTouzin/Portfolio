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

//Main class of the project
public class Main {
    public static void main(String[] args) throws IOException {
    	
//Create the 
    	/*
    	int[] a={0,1,2,3,4};
    	Reference temp= new Reference(a);
    	temp.Affichage();
    	temp.Enregistrer();
    	temp.Lire();
    	
    	
    	
    	Generator essaie = new Generator(200,10000);
    	essaie.GenerationVentille(6);
    	
    	
    	
    	
    	File fichierGen=new File("E:/Compression/Générateur.txt");
    	File fichierRef=new File("E:/Compression/Référence.txt");
    	Liste liste = new Liste(fichierRef,fichierGen);
    	
    	liste.Comparaison();
    	
    	liste.Trier();
    	
    	liste.Dictionnaire();
    	*/
    
    	/*
    	Test etude=new Test(0,9);
    	etude.test_find("Générateur",100000,100);
    	*/
    	
    	Multiplication_compression test= new Multiplication_compression();
    	/*
    	test.reference("E:/Compression/Ref.txt", 120, 10000);
    	test.compression("E:/Compression/Ref.txt", "E:/Compression/mult_comp.txt");
    	test.decompression("E:/Compression/mult_comp.txt", "E:/Compression/mult_decomp.txt");
    	*/
    	//1000 000 000 5h ou 20416s
    	test.findClef(47, 1000000);
    	
    	
    	
    }
}
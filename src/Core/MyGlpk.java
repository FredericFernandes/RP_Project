package Core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MyGlpk 
{
	private float valFctObj;
	private float[] solution;

	public MyGlpk(StringBuffer configPl) {
		super();
		valFctObj = -1;
		
		
		start(configPl);
	}

	/******************************/
	/* LECTURE D'UN  ENTIER DANS UN FICHIER */
	private float lit_entier(BufferedReader fe){
		char[] ch=new char[1];
		String s="";
		try{
			fe.read(ch);
			while (ch[0]==' '){
				fe.read(ch);
			}
			while (ch[0]!=' ') {
				s=s+ch[0];
				fe.read(ch);
			}	
		}catch(IOException e){System.err.println("Probleme de lecture dans out.txt");}

		return Float.parseFloat(s);
	}

	/**************************************/
	/* AVANCE LA LECTURE DU FICHIER JUSQU'A UN CHAR */
	private void avance_jusqu_a(BufferedReader fe, char c){
		char[] ch=new char[1];
		try{
			fe.read(ch);
			while (ch[0]!=c){
				fe.read(ch);
			}
			fe.read(ch); //dernier espace apres =
		}catch(IOException e){System.err.println("Probleme de lecture dans out.txt");}
	}

	/************************************/
	/* LIT LE FICHIER DE SORTIE*/
	private void lit_sortie()
	{
		if (valFctObj == -1)
		{
			BufferedReader out=null;
			try{
				out= new BufferedReader(new FileReader(".out"));
			}catch(IOException e)
			{
				System.err.println(e.getMessage());
				System.err.println("Probleme de lecture de out.txt");
			}

			//Récup le nombre de varialbe
			for (int i=0;i<3;i++)
			{
				avance_jusqu_a(out,':');
			}
			float nbVarialbe =lit_entier(out);
			System.out.println("nbVarialbe :"+nbVarialbe);
			solution = new float[(int) nbVarialbe];
			avance_jusqu_a(out,'=');
			valFctObj=lit_entier(out);
			for(int i=0;i<nbVarialbe;i++)
			{
				avance_jusqu_a(out,'*');
				float res = lit_entier(out);
				solution[i]=res;
			}

			try {
				if(out != null)
					out.close();
			} catch (IOException e) {e.printStackTrace();}
		}

	}

	private void start(StringBuffer configPl)
	{
		try {
			BufferedWriter in = new BufferedWriter(new FileWriter(".in.lp"));
			in.write(configPl.toString());			
			in.close();
		}catch(IOException e1){System.err.println("Probleme en ouvrant le fichier in.lp");}
		/* LANCE GLPSOL EN LIGNE DE COMMANDE */

		String args= "./myGlpsol -o .out --lp .in.lp ";
		System.out.println("\t Lancement de MyGlpk");
		try{
			// construction de objet pour lancer application externe
			final Runtime runtime = Runtime.getRuntime();
			// Lancement de la commande dans un processus externe
			final Process process = runtime.exec(args);	

			try {process.waitFor();} catch (InterruptedException e) {e.printStackTrace();}

			System.out.println("\t Fin du calcul par MyGlpk");			
		}
		catch(IOException e){
			System.err.println("Erreur lancement MyGlpk");
		}
	}
	public void printBrutResult()
	{
		try{

			BufferedReader out= new BufferedReader(new FileReader(".out"));
			String ligne;
			while ((ligne=out.readLine())!=null) 
				System.out.println(ligne);

			out.close();
		}catch(IOException e)
		{
			System.err.println(e.getMessage());
			System.err.println("Probleme de lecture de out.txt");
		}
	}
	public Float getResFctObjectif()
	{
		lit_sortie();
		return valFctObj;
	}
	public float[] getSolutions()
	{
		lit_sortie();
		return solution;
	}


}

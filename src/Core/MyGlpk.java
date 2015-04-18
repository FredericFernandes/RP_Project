package Core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MyGlpk 
{
	private int solution;
	private ArrayList<Integer> list_Solutions;


	public MyGlpk(StringBuffer configPl) {
		super();
		solution = -1;
		list_Solutions = new ArrayList<Integer>();
		start(configPl);
	}

	/******************************/
	/* LECTURE D'UN  ENTIER DANS UN FICHIER */
	private int lit_entier(BufferedReader fe){
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

		return Integer.parseInt(s);
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
		if (solution == -1)
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
			int nbVarialbe =lit_entier(out);
			avance_jusqu_a(out,'=');
			solution=lit_entier(out);
			for(int i=0;i<nbVarialbe;i++)
			{
				avance_jusqu_a(out,'*');
				int res = lit_entier(out);
				list_Solutions.add(res);
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
	public int getResFctObjectif()
	{
		lit_sortie();
		return solution;
	}
	public ArrayList<Integer> getSolutions()
	{
		lit_sortie();
		return list_Solutions;
	}


}

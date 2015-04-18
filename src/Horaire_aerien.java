import java.io.*;
import java.util.*;
class Horaire_aerien
{

	static int solution;

	/******************************/
	/* Ecrit sur le disque le programme au format Cplex lp */
	static void ecrit_fichier_lp(BufferedWriter fs, int[] nb_min_employ){

		try{

			fs.write("Minimize ");

			fs.write("170x1+160x2+175x3+180x4+195x5");

			fs.write("\n\nSubject to\n\n");
			fs.write("x1>=" +nb_min_employ[0]+"\n");
			fs.write("x1+x2>="+nb_min_employ[1]+"\n");
			fs.write("x1+x2>="+nb_min_employ[2]+"\n");
			fs.write("x1+x2+x3>="+nb_min_employ[3]+"\n");
			fs.write("x2+x3>="+nb_min_employ[4]+"\n");
			fs.write("x3+x4>="+nb_min_employ[5]+"\n");
			fs.write("x3+x4>="+nb_min_employ[6]+"\n");
			fs.write("x4>="+nb_min_employ[7]+"\n");
			fs.write("x4+x5>="+nb_min_employ[8]+"\n");
			fs.write("x5>="+nb_min_employ[9]+"\n");

			fs.write("\nBounds\n");
			fs.write("x1>=0\n");
			fs.write("x2>=0\n");
			fs.write("x3>=0\n");
			fs.write("x4>=0\n");
			fs.write("x5>=0\n");


			fs.write("Integers\n");
			fs.write("x1 x2 x3 x4 x5\n");

			fs.write("\nEnd\n");

		}catch(IOException e){System.out.println("Pbm ecriture PL");}


	}

	/******************************/
	/* LECTURE D'UN  ENTIER DANS UN FICHIER */
	static int lit_entier(BufferedReader fe){
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
		}catch(IOException e){System.out.println("Probleme de lecture dans out.txt");}

		return Integer.parseInt(s);
	}

	/**************************************/
	/* AVANCE LA LECTURE DU FICHIER JUSQU'A UN CHAR */
	static void avance_jusqu_a(BufferedReader fe, char c){
		char[] ch=new char[1];
		try{
			fe.read(ch);
			while (ch[0]!=c){
				fe.read(ch);
			}
			fe.read(ch); //dernier espace apres =
		}catch(IOException e){System.out.println("Probleme de lecture dans out.txt");}
	}

	/************************************/
	/* LIT LE FICHIER DE SORTIE*/
	static void lit_sortie_Glpk(BufferedReader fe, int[] vecteur_sol){
		char[] ch=new char[1];
		char[] ch2=new char[2];
		String ss="";
		int i;


		avance_jusqu_a(fe,'=');

		solution=lit_entier(fe);
		//	System.out.println(solution);


		for(i=0;i<5;i++){
			avance_jusqu_a(fe,'*');
			vecteur_sol[i]=lit_entier(fe);
			//	    System.out.println(vecteur_sol[i]);
		}


	}



	/**********************************/
	public static void main(String[] arg)
	{


		int[] nb_min_employ;
		int[] vecteur_solution;
		int i;


		nb_min_employ=new  int[10];
		vecteur_solution= new int[5];

		/* Lit le fichier de donnees */
		//try{

		//BufferedReader in= new BufferedReader(new FileReader("nb_min_employ.data"));
		Random randomGenerator = new Random();
		for (i=0;i<10;i++){
			//nb_min_employ[i]= Integer.parseInt(in.readLine());
			nb_min_employ[i]= randomGenerator.nextInt(10);
		}
		for (i=0;i<10;i++)
		{
			System.out.println(nb_min_employ[i]);
		}

		//in.close();
		//}catch(IOException e){System.out.println("Probleme en lisant nb_min_employ.data");}


		/* Ecrit le PLNE correspondant */
		try{

			BufferedWriter fs= new BufferedWriter(new FileWriter(".in.lp"));

			ecrit_fichier_lp(fs, nb_min_employ);

			fs.close();

		}catch(IOException e1){System.out.println("Probleme en ouvrant le fichier PL_horaire_aerien.lp");}




		/* LANCE GLPSOL EN LIGNE DE COMMANDE */

		String args= "./myGlpsol -o .out.txt --lp .in.lp ";
		System.out.println("\t Lancement de Glpk");
		try{
			// construction de objet pour lancer application externe
			final Runtime runtime = Runtime.getRuntime();
			// Lancement de la commande dans un processus externe
			final Process process = runtime.exec(args);
			System.out.println("\t Fin du calcul par GlpK");
		}
		catch(IOException e){
			System.out.println("Erreur lancement Glpk");
		}



		/* RECUPERE LA SOLUTION EN LISANT LE FICHIER SORTIE DE GLPSOL*/


		try{

			BufferedReader in= new BufferedReader(new FileReader(".out.txt"));

			lit_sortie_Glpk(in, vecteur_solution);

			in.close();
		}catch(IOException e){System.out.println("Probleme de lecture de sortie_horaire_aerien.txt");}

		/* Affichage comprehensible de tous */

		System.out.println("La compagnie aérienne doit répartir ainsi ses employés");
		for (i=0;i<5;i++){
			System.out.println("  Nb employés au service N°"+(i+1)+" : "+vecteur_solution[i]);
		}
		System.out.println("Cette solution correspond à un cout de "+ solution + " euros");


	}

}

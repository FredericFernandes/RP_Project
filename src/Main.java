

import java.util.Random;

import Core.MyGlpk;
import Core.Simulation;
import loader.LoaderSimulation;


public class Main {


	public static void main(String[] args) 
	{
		String pathInstance = "inst_63_10000_1_30_0_7.flp";
		boolean chowWeight =false;
		Simulation simul = LoaderSimulation.createSimulation(pathInstance,chowWeight);
		simul.startSimulation();

		
		MyGlpk glpk = new MyGlpk(creteTestPl());
		glpk.printBrutResult(); // affiche tout le fichier out
		System.out.println("Solution : "+glpk.getResFctObjectif());
		System.out.println("Varialbes :"+glpk.getSolutions());
	}

	private static StringBuffer creteTestPl()
	{
		StringBuffer  buffer = new StringBuffer();
		Random randomGenerator = new Random();
		int[] nb_min_employ =new  int[10];
		for (int i=0;i<10;i++){
			//nb_min_employ[i]= Integer.parseInt(in.readLine());
			nb_min_employ[i]= randomGenerator.nextInt(10);
		}
		buffer.append("Minimize ");

		buffer.append("170x1+160x2+175x3+180x4+195x5");
		buffer.append("\n\nSubject to\n\n");

		buffer.append("x1>=" +nb_min_employ[0]+"\n");
		buffer.append("x1+x2>="+nb_min_employ[1]+"\n");
		buffer.append("x1+x2>="+nb_min_employ[2]+"\n");
		buffer.append("x1+x2+x3>="+nb_min_employ[3]+"\n");
		buffer.append("x2+x3>="+nb_min_employ[4]+"\n");
		buffer.append("x3+x4>="+nb_min_employ[5]+"\n");
		buffer.append("x3+x4>="+nb_min_employ[6]+"\n");
		buffer.append("x4>="+nb_min_employ[7]+"\n");
		buffer.append("x4+x5>="+nb_min_employ[8]+"\n");
		buffer.append("x5>="+nb_min_employ[9]+"\n");

		buffer.append("\nBounds\n");
		buffer.append("x1>=0\n");
		buffer.append("x2>=0\n");
		buffer.append("x3>=0\n");
		buffer.append("x4>=0\n");
		buffer.append("x5>=0\n");

		// Liste des varibales
		buffer.append("Integers\n");
		buffer.append("x1 x2 x3 x4 x5\n");

		buffer.append("\nEnd\n");

		return buffer;
	}

}

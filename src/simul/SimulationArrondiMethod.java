package simul;

import java.util.HashMap;

import Core.MyGlpk;
import Core.SimulatedCity;

public class SimulationArrondiMethod extends Simulation{

	private MyGlpk pl;

	public SimulationArrondiMethod(
			HashMap<Integer, SimulatedCity> listCity,float[][] matrice, int p, boolean isUFLP, boolean chowRoad) 
	{
		super(listCity, matrice, p, isUFLP, chowRoad);

	}

	@Override
	public void startSimulation() {
		pl = new MyGlpk(createDual());
		pl.printBrutResult();
		System.out.println("res"+pl.getResFctObjectif());
		
	}

	private StringBuffer createDual() {

		StringBuffer  buffer = new StringBuffer();

		buffer.append("Maximize ");

		// sum Vi
		for (int i = 0 ; i < nbCity ; i++){
			buffer.append("v"+i+"+"); //Vi
		}
		buffer.deleteCharAt(buffer.length() - 1); // remove "+" in too 
		buffer.append("\n\nSubject to\n\n");

		// constraint (4)
		for (int i = 0 ; i < nbCity ; i++){
			String sI = String.valueOf(i);
			for (int j = 0 ; j < nbCity ; j++){
				if(j!=i){
					if(matriceWeight[i][j]!=0){
						String sJ = String.valueOf(j);
						buffer.append("w"+"("+sI+"#"+sJ+")"+"+");  // Wij+
					}

				}
			}
			SimulatedCity city = listSimulatedCity.get(i);
			String w = String.valueOf(city.getWeight());
			buffer.append("v"+sI+"<="+w+"\n");
		}

		// constraint (5)
		for (int i = 0 ; i < nbCity ; i++){
			for (int j = 0 ; j < nbCity ; j++){
				if(j!=i){
					if(matriceWeight[i][j]!=0){
						String sI = String.valueOf(i);
						String sJ = String.valueOf(j);
						String cij = String.valueOf(matriceWeight[i][j]);
						buffer.append("v"+sJ+"-"+"w"+"("+sI+"#"+sJ+")"+"<="+cij+"\n");
						//Vj-Wij<=Cij
					}
				}
			}
		}
		buffer.append("\nBounds\n");
		// Wij >= 0
		for (int i = 0 ; i < nbCity ; i ++){
			for (int j = 0 ; j < nbCity ; j ++){
				if (i!=j){
					if(matriceWeight[i][j]!=0){
						String sI = String.valueOf(i);
						String sJ = String.valueOf(j);
						buffer.append("w"+"("+sI+"#"+sJ+")"+">=0\n");	
					}			
				}
			}
		}
		
		buffer.append("\n");
		buffer.append("\nEnd\n");

		return buffer;
	}

}

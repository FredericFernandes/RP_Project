package simul;

import java.util.HashMap;

import Core.MyGlpk;
import Core.SimulatedCity;

public class SimulationArrondiMethod extends Simulation{

	private MyGlpk pl;

	public SimulationArrondiMethod(
			HashMap<Integer, SimulatedCity> listCity,float[][] matrice, int p, boolean chowRoad) 
	{
		super(listCity, matrice, p, chowRoad);

	}

	@Override
	public void startSimulation() {
		//pl = new MyGlpk(createDual());
		pl = new MyGlpk(createPBis());
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

	private  StringBuffer createPBis()
	{
		StringBuffer  buffer = new StringBuffer();

		buffer.append("Minimize ");
		// sum FiYi
		for (int i = 0 ; i < nbCity ; i++)
		{
			SimulatedCity city = listSimulatedCity.get(i);
			String w = String.valueOf(city.getWeight());
			String id = String.valueOf(city.getIdIntoSimulation());
			buffer.append(w+"y"+id+"+");
		}

		for (int i = 0 ; i < nbCity ; i ++){
			for (int j = 0 ; j < nbCity ; j ++){
				if (i!=j){
					if(matriceWeight[i][j]!=0){
						String cij = String.valueOf(matriceWeight[i][j]);
						String sI = String.valueOf(i);
						String sJ = String.valueOf(j);
						buffer.append(cij+"x"+"("+sI+"#"+sJ+")"+"+");
					}	
				}	
			}
		}
		buffer.deleteCharAt(buffer.length() - 1); // remove "+" in too 

		buffer.append("\n\nSubject to\n\n");

		// constraint (1)
		for (int i = 0 ; i < nbCity ; i ++){
			for (int j = 0 ; j < nbCity ; j ++){
				if (i!=j){
					if(matriceWeight[i][j]!=0){
						String sI = String.valueOf(i);
						String sJ = String.valueOf(j);
						buffer.append("y"+sI+"-"+"x"+"("+sI+"#"+sJ+")"+">=0\n");   // yi - xij >=0
					}	
				}
			}		
		}
		// constraint (2)
		for (int j = 0 ; j < nbCity ; j ++){
			String sJ = String.valueOf(j);
			buffer.append("y"+sJ+"+");
			for (int i = 0 ; i < nbCity ; i ++){
				if (i!=j){
					if(matriceWeight[i][j]!=0){
						String sI = String.valueOf(i);
						buffer.append("x"+"("+sI+"#"+sJ+")"+"+");
					}		
				}
			}
			buffer.deleteCharAt(buffer.length() - 1); // remove "+" in too 
			buffer.append("=1"+"\n");
		}

		//buffer.append("\nBounds\n");
//		// yi >= 0
//		for (int i = 0 ; i < nbCity ; i ++){
//			String sI = String.valueOf(i);
//			buffer.append("y"+sI+">=0\n");
//		}
		// xij >= 0
//		for (int i = 0 ; i < nbCity ; i ++){
//			for (int j = 0 ; j < nbCity ; j ++){
//				if (i!=j){
//					if(matriceWeight[i][j]!=0){
//						String sI = String.valueOf(i);
//						String sJ = String.valueOf(j);
//						buffer.append("x"+"("+sI+"#"+sJ+")"+">=0\n");	
//					}			
//				}
//			}
//		}

//				buffer.append("General\n");
//				// yi entiers
//				for (int i = 0 ; i < nbCity ; i ++){
//					String sI = String.valueOf(i);
//					buffer.append("y"+sI+" ");
//				}
//				// xij entiers
//				for (int i = 0 ; i < nbCity ; i ++){
//					for (int j = 0 ; j < nbCity ; j ++){
//						if (i!=j){
//							if(matriceWeight[i][j]!=0){
//								String sI = String.valueOf(i);
//								String sJ = String.valueOf(j);
//								buffer.append("x"+"("+sI+"#"+sJ+")"+" ");	
//							}	
//						}
//					}
//				}

		buffer.append("\n");

		buffer.append("\nEnd\n");

		return buffer;
	}

	@Override
	protected void construcResult() {
		// TODO Auto-generated method stub
		
	}
}

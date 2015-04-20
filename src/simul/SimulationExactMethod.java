package simul;

import java.util.HashMap;

import Core.MyGlpk;
import Core.SimulatedCity;
public class SimulationExactMethod extends Simulation {

	private MyGlpk pl;
	public SimulationExactMethod(
			HashMap<Integer, SimulatedCity> listCity,float[][] matrice, int p, boolean isUFLP, boolean chowWeight) 
	{
		super(listCity, matrice, p, isUFLP, chowWeight);

	}
	private  StringBuffer createPl()
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

		buffer.append("\nBounds\n");
		// yi >= 0
		for (int i = 0 ; i < nbCity ; i ++){
			String sI = String.valueOf(i);
			buffer.append("y"+sI+">=0\n");
		}
		// xij >= 0
		for (int i = 0 ; i < nbCity ; i ++){
			for (int j = 0 ; j < nbCity ; j ++){
				if (i!=j){
					if(matriceWeight[i][j]!=0){
						String sI = String.valueOf(i);
						String sJ = String.valueOf(j);
						buffer.append("x"+"("+sI+"#"+sJ+")"+">=0\n");	
					}			
				}
			}
		}

		buffer.append("Integers\n");
		// yi entiers
		for (int i = 0 ; i < nbCity ; i ++){
			String sI = String.valueOf(i);
			buffer.append("y"+sI+" ");
		}
		// xij entiers
		for (int i = 0 ; i < nbCity ; i ++){
			for (int j = 0 ; j < nbCity ; j ++){
				if (i!=j){
					if(matriceWeight[i][j]!=0){
						String sI = String.valueOf(i);
						String sJ = String.valueOf(j);
						buffer.append("x"+"("+sI+"#"+sJ+")"+" ");	
					}	
				}
			}
		}
		buffer.append("\n");

		buffer.append("\nEnd\n");

		return buffer;
	}
	
	@Override
	public void startSimulation(){
		pl = new MyGlpk(createPl());
		//pl.printBrutResult();
		construcResult();
	}
	private void construcResult(){
		float resFctObj = pl.getResFctObjectif();
		System.out.println("Solution : "+resFctObj);
		
		float[] res = pl.getSolutions();		
		// get yi
		for (int i = 0 ; i < nbCity ; i++){
			matriceRes[i][i]= res[i];
		}

		int index = nbCity;
		for (int i = 0 ; i < nbCity; i++){
			for (int j = 0 ; j < nbCity; j++){
				if(i!=j){	
					if(matriceWeight[i][j]!=0){
						matriceRes[i][j]=res[index];
						index++;
					}			
				}
			}
		}
		//printMatrice(matriceRes);
		//printMatrice(matriceWeight);
	}

}

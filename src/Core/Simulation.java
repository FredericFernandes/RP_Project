package Core;

import java.util.ArrayList;
import data.SimulatedCity;

public class Simulation {

	private ArrayList<SimulatedCity> listCity;
	float matriceWeight[][];
	int matriceRes[][];

	public Simulation(ArrayList<SimulatedCity> listCity , float [][] matrice) {
		this.listCity = listCity;
		matriceWeight = matrice;
		int nbCity = listCity.size();
		matriceRes = new int[nbCity][nbCity];
	}

}

package Core;

import java.util.ArrayList;
import data.SimulatedCity;

public class Simulation {

	private ArrayList<SimulatedCity> listSimulatedCity;
	private float matriceWeight[][];
	private int matriceRes[][];
	private int p ;
	private boolean isUFLP;

	public Simulation(ArrayList<SimulatedCity> listCity , float [][] matrice, int p, boolean isUFLP) {
		this.listSimulatedCity = listCity;
		matriceWeight = matrice;
		int nbCity = listCity.size();
		matriceRes = new int[nbCity][nbCity];
		this.p = p;
		this.isUFLP = isUFLP;
	}

}

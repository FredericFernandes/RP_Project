package Core;

import java.util.ArrayList;

import data.City;
import data.SimulatedCity;

public class Simulation {

	private ArrayList<SimulatedCity> listCity;
	float matriceWeight[][];

	public Simulation(ArrayList<SimulatedCity> listCity , float [][] matrice) {
		this.listCity = listCity;
		int nbCity = listCity.size();
		matriceWeight = matrice;
	}

}

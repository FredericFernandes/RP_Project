package Core;

import java.util.ArrayList;
import java.util.HashMap;

import data.City;
import data.SimulatedCity;

public class Storage 
{
	static Storage instance=null; // for singleton pattern
	private HashMap<Integer,City> hashVilles;
	float[][] matriceWeight = null;
	int p = 0;
	ArrayList<SimulatedCity> listSimulatedCity = null;
	boolean isUFLP = false;
	
	public float[][] getMatriceWeight() {
		return matriceWeight;
	}

	public void setMatriceWeight(float[][] matriceWeight) {
		this.matriceWeight = matriceWeight;
	}

	public int getP() {
		return p;
	}

	public void setP(int p) {
		this.p = p;
	}

	public ArrayList<SimulatedCity> getListSimulatedCity() {
		return listSimulatedCity;
	}

	public void setListSimulatedCity(ArrayList<SimulatedCity> listSimulatedCity) {
		this.listSimulatedCity = listSimulatedCity;
	}

	public boolean isUFLP() {
		return isUFLP;
	}

	public void setUFLP(boolean isUFLP) {
		this.isUFLP = isUFLP;
	}

	private Storage() {
		super();
		this.hashVilles = new HashMap<Integer,City>();
	}

	public HashMap<Integer, City> getHashVilles() {
		return hashVilles;
	}

	public static Storage getInstance(){
		if (instance== null)
			instance = new Storage();	
		return instance;		
	}
	public City getVille(int idVille){
		assert (hashVilles.containsKey(idVille)); // crash if idVille doesn't existe 
		return hashVilles.get(idVille);

	}
	public void addVille(City newVille){
		hashVilles.put(newVille.getId(),newVille);
	}
	
}

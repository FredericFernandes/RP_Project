package Core;

import java.util.ArrayList;
import java.util.HashMap;

import data.City;

public class Storage 
{
	static Storage instance=null; // for singleton pattern
	private HashMap<Integer,City> hashVilles;

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

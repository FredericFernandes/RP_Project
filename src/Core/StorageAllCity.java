package Core;


import java.util.HashMap;


public class StorageAllCity 
{
	static StorageAllCity instance=null; // for singleton pattern
	private HashMap<Integer,City> hashVilles;

	private StorageAllCity() {
		super();
		this.hashVilles = new HashMap<Integer,City>();
	}

	public HashMap<Integer, City> getHashVilles() {
		return hashVilles;
	}

	public static StorageAllCity getInstance(){
		if (instance== null)
			instance = new StorageAllCity();	
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

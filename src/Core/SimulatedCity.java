package Core;


public class SimulatedCity extends City
{

	int idIntoSimulation;
	float weight;
	public SimulatedCity(int id, String nbDepartement, String nom,
			int population, double longitude, double latitude, int idS, float w) 
	{
		super(id, nbDepartement, nom, population, longitude, latitude);
		this.idIntoSimulation = idS;
		this.weight = w;
		
	}
	public SimulatedCity(City city, int idS, float w) 
	{
		super(city);
		this.idIntoSimulation = idS;
		this.weight = w;
		
	}
	public int getIdIntoSimulation() {
		return idIntoSimulation;
	}
	
	public float getWeight() {
		return weight;
	}
	
	
	
	

	

}

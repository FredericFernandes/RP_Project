package loader;

import java.util.ArrayList;
import java.util.List;

import data.SimulatedCity;

import Core.Simulation;
import Core.Storage;


public class LoaderInstance extends LoaderCSV {

	ArrayList<SimulatedCity> listCity;
	float matriceWeight[][];
	public LoaderInstance(String path) 
	{
		super();
		load(path);
		
	}
	public void load(String path) 
	{
		ArrayList<String[]> listLines = loadSV(path);
		listLines.remove(listLines.size()-1); // remove -1 in end file
		int nbCity = Integer.parseInt(listLines.get(0)[0]);
		matriceWeight = new float[nbCity][nbCity];
		listCity = new ArrayList<SimulatedCity>();
		int mode  = Integer.parseInt(listLines.get(0)[1]);
		listLines.remove(0); // remove firth line  --> "nbVilles mode "
		ArrayList<String[]> listVilleInfos =  new ArrayList<String[]>();
		listVilleInfos.addAll(listLines.subList(0, nbCity));  // get the first nbCity
		listLines.removeAll(listVilleInfos); // get the rest
		for (String [] ville : listVilleInfos)
		{
			int id = Integer.parseInt(ville[0]);
			int idCity = Integer.parseInt(ville[1]);
			float weight = Float.parseFloat(ville[2]);
			SimulatedCity city = new SimulatedCity(Storage.getInstance().getVille(idCity), id, weight);
			listCity.add(city);
		}
		for (String [] couts : listLines)
		{
			int id1 = Integer.parseInt(couts[0]);
			int id2 = Integer.parseInt(couts[1]);
			float weight = Float.parseFloat(couts[2]);
			matriceWeight[id1][id2] = weight;
		}
	}
	public Simulation createSimulation()
	{
		assert (listCity !=null);
		assert (matriceWeight !=null);
		Simulation sim = new Simulation(listCity, matriceWeight);
		System.out.println("The simulation is successfully created. ");
		return sim;
				
	}


}

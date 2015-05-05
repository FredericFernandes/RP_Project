package loader;


import java.util.ArrayList;
import java.util.HashMap;

import simul.Simulation;
import simul.SimulationArrondiMethod;
import simul.SimulationExactMethod;
import simul.SimulationGeneticsMethod;


import Core.City;
import Core.SimulatedCity;
import Core.StorageAllCity;


public class LoaderSimulation extends LoaderFile {

	private static HashMap < Integer,SimulatedCity> listSimulatedCity;
	private static float matriceWeight[][];
	//private static int p;
	//private static boolean isUFLP;
	private static ArrayList<String[]> originalListLines;
	private static void loadInstance(String path) 
	{
		ArrayList<String[]> listLines = loadFile(path);
		originalListLines= (ArrayList<String[]>) listLines.clone();
		listLines.remove(listLines.size()-1); // remove -1 in end file
		int nbCity = Integer.parseInt(listLines.get(0)[0]);

		matriceWeight = new float[nbCity][nbCity];
		listSimulatedCity = new HashMap < Integer,SimulatedCity>();
//		int mode  = Integer.parseInt(listLines.get(0)[1]);
//		if (mode != -1){
//			// mode  p-median
//			System.out.println("\nIt's a p-median problem. ");
//			p = mode;
//			isUFLP = false;
//		}else{
//			p = -1; // not used in UFLP mode
//			System.out.println("\nIt's a UFLP problem. ");
//			isUFLP = true;
//		}
		listLines.remove(0); // remove firth line  --> "nbVilles mode "
		ArrayList<String[]> listVilleInfos =  new ArrayList<String[]>();
		listVilleInfos.addAll(listLines.subList(0, nbCity));  // get the first nbCity
		listLines.removeAll(listVilleInfos); // get the rest
		for (String [] ville : listVilleInfos)
		{
			int idSimulation = Integer.parseInt(ville[0]);
			int idCity = Integer.parseInt(ville[1]);
			float weight = Float.parseFloat(ville[2]);
			SimulatedCity city = new SimulatedCity(StorageAllCity.getInstance().getVille(idCity), idSimulation, weight);
			listSimulatedCity.put(idSimulation, city);
		}
		for (String [] couts : listLines)
		{
			int id1 = Integer.parseInt(couts[0]);
			int id2 = Integer.parseInt(couts[1]);
			float weight = Float.parseFloat(couts[2]);
			matriceWeight[id1][id2] = weight;
		}
	}

	private static void loadVilles() {
		String csvFile = "villes_france.csv";
		ArrayList<String[]> listLines = loadFile(csvFile);	
		for (String [] ville : listLines){
			int id = Integer.parseInt(ville[0]);
			String nbDepartement = ville[1];
			String nom = ville[2];
			int population = Integer.parseInt(ville[3]);
			double longitude = Double.parseDouble(ville[4]);
			double latitude = Double.parseDouble(ville[5]);
			City newVille = new City(id, nbDepartement, nom, population, longitude, latitude);
			StorageAllCity.getInstance().addVille(newVille);
		}

	}
	private static void load(String path)
	{
		loadVilles();
		loadInstance(path);
	}
	public static Simulation createSimulationExactMethod(String path, boolean chowRoad,int uiP){			
		load(path);		
		Simulation sim = new SimulationExactMethod(listSimulatedCity,matriceWeight,uiP,chowRoad);
		assert  sim != null ;
		//System.out.println("The simulation is successfully created. ");
		return sim;

	}
	public static Simulation createSimulationArrondiMethod(String path, boolean chowRoad,int uiP){			
		load(path);
		Simulation sim = new SimulationArrondiMethod(listSimulatedCity,matriceWeight,uiP,chowRoad);
		assert  sim != null ;
		//System.out.println("The simulation is successfully created. ");
		return sim;

	}
	public static Simulation createSimulationGeneticsMethod(String path, boolean chowWeight,int uiP, int nbIter, int nbPop){		
		load(path);
		Simulation sim = new SimulationGeneticsMethod(listSimulatedCity,matriceWeight,uiP,chowWeight,originalListLines,nbIter,nbPop);
		assert  sim != null ;
		//System.out.println("The simulation is successfully created. ");
		return sim;

	}
}

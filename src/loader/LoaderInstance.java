package loader;

import java.util.ArrayList;

import Core.Storage;
import data.City;

public class LoaderInstance extends LoaderCSV {

	public LoaderInstance(String path) {
		super();
		load(path);
	}

	public void load(String path) 
	{
		ArrayList<String[]> listLines = loadSV(path);
		int nbVilles = Integer.parseInt(listLines.get(0)[0]);
		int mode  = Integer.parseInt(listLines.get(0)[1]);
		listLines.remove(0);
		for (String [] ville : listLines)
		{
			int id = Integer.parseInt(ville[0]);
			String nbDepartement = ville[1];
			String nom = ville[2];
			int population = Integer.parseInt(ville[3]);
			double longitude = Double.parseDouble(ville[4]);
			double latitude = Double.parseDouble(ville[5]);
			City newVille = new City(id, nbDepartement, nom, population, longitude, latitude);
			Storage.getInstance().addVille(newVille);
		}

	}


}

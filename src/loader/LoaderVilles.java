package loader;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Core.Storage;

import data.City;

public class LoaderVilles  extends LoaderCSV{


	public LoaderVilles() {
		super();
		load();
	}
	
	public void load() 
	{
		String csvFile = "villes_france.csv";
		ArrayList<String[]> listLines = loadSV(csvFile);	
		for (String [] ville : listLines){
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

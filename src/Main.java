import java.util.ArrayList;

import Core.Simulation;
import Core.Storage;
import loader.LoaderVilles;


public class Main {


	public static void main(String[] args) 
	{
		LoaderVilles obj = new LoaderVilles();
		System.out.println("Les villes ");
		//System.out.println(Storage.getInstance().getHashVilles().get(50));
		Simulation sim = new Simulation(new ArrayList<Integer>());
	}

}

package simul;

import java.util.ArrayList;
import java.util.HashMap;

import loader.LoaderSimulation;

import Core.Couple;
import Core.Population;
import Core.SimulatedCity;

public class SimulationGeneticsMethod extends Simulation {

	private  ArrayList<String[]> listOfLinesForGenetics;
	private Population pop;
	public SimulationGeneticsMethod(HashMap<Integer, SimulatedCity> listCity,
			float[][] matrice, int p, boolean isUFLP, 
			boolean chowRoad,ArrayList<String[]> listOfLines) 
	{
		super(listCity, matrice, p, isUFLP, chowRoad);
		this.listOfLinesForGenetics = listOfLines;
	}


	@Override
	public void startSimulation() 
	{
		long debut = System.currentTimeMillis();
		int initialPopSize = 100;
		int nbIteration = 400;

		//int numOfCities = LoaderSimulation.getNumberOfCities(listOfLinesForGenetics);
		//int numOfCenters = LoaderSimulation.getNumberOfCenters(listOfLinesForGenetics);
		System.out.println("Number of cities : " + nbCity);

		System.out.println("Number of required centers (in the file): " + p);

		//**************************************************************************************************		
		pop = new Population(nbCity, p, initialPopSize, listOfLinesForGenetics);
		//pop.printPop();

		for(int iter = 0; iter<nbIteration; iter++){
			System.out.println("====== Iteration number " + iter + " =====================================================");
			HashMap<Integer, HashMap<Integer, ArrayList<Couple>>> mMAP = pop.affectCitiesInPop();
			ArrayList<Double> fitnessList = pop.getFitnessList(mMAP);
			Population selectedPop = pop.selectionBest(0.6, fitnessList);
			Population offSprings = selectedPop.crossOver(7);
			//			offSprings.printPop();
			Population mutatedPop = offSprings.randomMutation(0.4);
			pop = mutatedPop.getSolution();
			//System.out.println("====== Exiting with a population of " + pop.size()+"========================================");
		}
		System.out.println(System.currentTimeMillis()-debut);

		System.out.println("resFctObj : "+(float)pop.getResult());
	}


	@Override
	protected void construcResult() {
		resFctObj = (float)pop.getResult();
		
		ArrayList<Integer> resCenters = pop.getAffectationCenters();
		for (int i = 0 ; i < nbCity ; i++){
			matriceRes[i][i]= resCenters.get(i);
		}
		
		HashMap<Integer, ArrayList<Couple>> hashAffectation = pop.getOtherAffectation();
		for(Integer i : hashAffectation.keySet()){
			 ArrayList<Couple> list = hashAffectation.get(i);
			 for(Couple c : list){
				 matriceRes[c.getL()][i]=1;
			 }
		}

	}

}

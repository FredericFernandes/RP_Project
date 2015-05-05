package simul;

import java.util.ArrayList;
import java.util.HashMap;

import Core.Couple;
import Core.Population;
import Core.SimulatedCity;

public class SimulationGeneticsMethod extends Simulation {

	private  ArrayList<String[]> listOfLinesForGenetics;
	private Population pop;
	private int initialPopSize;
	private int nbIteration;
	public SimulationGeneticsMethod(HashMap<Integer, SimulatedCity> listCity,
			float[][] matrice, int p, 
			boolean chowRoad,ArrayList<String[]> listOfLines, int nbIter, int nbPop) 
	{
		super(listCity, matrice, p, chowRoad);
		this.listOfLinesForGenetics = listOfLines;
		this.initialPopSize = nbPop;
		this.nbIteration = nbIter;
	}


	@Override
	public void startSimulation() 
	{
		long debut = System.currentTimeMillis();

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
			System.out.println("====== Exiting with a population of " + pop.size()+"========================================");
		}
		System.out.println(System.currentTimeMillis()-debut);
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

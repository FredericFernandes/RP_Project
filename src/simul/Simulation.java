package simul;

import graphics.GraphWindow;

import java.util.HashMap;

import Core.SimulatedCity;


public abstract class Simulation {

	protected HashMap < Integer,SimulatedCity> listSimulatedCity;
	protected float matriceWeight[][];
	protected boolean chowRoad;
	protected float matriceRes[][];
	protected float resFctObj;
	protected int p ; // -1 if not used for UFLP mode
	protected boolean isUFLP;
	protected int nbCity;

	public Simulation(HashMap<Integer, SimulatedCity> listCity , float [][] matrice, int p,boolean chowRoad) {
		//this.addAttribute("ui.stylesheet","url('stylehsheet.css');");
		this.listSimulatedCity = listCity;
		matriceWeight = matrice;
		nbCity = listCity.size();
		matriceRes = new float[nbCity][nbCity];
		this.p = p;
		this.chowRoad = chowRoad;
		if (p != -1){
		// mode  p-median
		System.out.println("\nIt's a p-median problem. ");
		this.isUFLP = false;
	}else{
		System.out.println("\nIt's a UFLP problem. ");
		this.isUFLP = true;
	}


	}

	public abstract void startSimulation();

	protected abstract void construcResult();

	public void showResult(){
		
		construcResult();
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Le résultat est : "+resFctObj);
		System.out.println("Il y a "+nbCenters()+ " centre(s)");
		showWindow();
	}

	private void showWindow(){
		new GraphWindow(listSimulatedCity, matriceWeight, chowRoad,matriceRes);
	}

	protected void printMatrice()
	{
		System.out.println("matriceRes :\n");
		System.out.print("   | ");
		for (int i =0 ; i < nbCity ; i++){
			System.out.print(" V"+i+" | ");}
		System.out.print("\n");
		System.out.print("---+");
		for (int i =0 ; i < nbCity ; i++){
			System.out.print("-----+");}


		System.out.print("\n");
		for (int i = 0 ; i < nbCity ; i++){
			System.out.print("V"+i+" |");
			for (int j = 0 ; j < nbCity ; j++){
				System.out.print(" "+matriceRes[i][j]+" |");}
			System.out.print("\n");
			System.out.print("---+");
			for (int cpt =0 ; cpt < nbCity ; cpt++){
				System.out.print("-----+");}
			System.out.print("\n");
		}
	}
	protected int nbCenters(){
		int nb =0;
		for (int i = 0 ; i < nbCity ; i++){
			if(matriceRes[i][i]!=0)
				nb++;
		}	
		return nb;
	}

}

package simul;

import graphics.Window;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

import org.graphstream.algorithm.AStar;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;
import org.graphstream.ui.swingViewer.util.Camera;

import Core.SimulatedCity;


public abstract class Simulation {

	protected HashMap < Integer,SimulatedCity> listSimulatedCity;
	protected float matriceWeight[][];
	protected boolean chowRoad;
	protected float matriceRes[][];
	protected int p ; // -1 if not used for UFLP mode
	protected boolean isUFLP;
	protected int nbCity;

	public Simulation(HashMap<Integer, SimulatedCity> listCity , float [][] matrice, int p, boolean isUFLP,boolean chowRoad) {
		//this.addAttribute("ui.stylesheet","url('stylehsheet.css');");
		this.listSimulatedCity = listCity;
		matriceWeight = matrice;
		nbCity = listCity.size();
		matriceRes = new float[nbCity][nbCity];
		this.p = p;
		this.isUFLP = isUFLP;
		this.chowRoad = chowRoad;
		
							
	}
	 public abstract void startSimulation();
	 public void showResult(){
		 showWindow();
	 }
	 
	 private void showWindow(){
		new Window(listSimulatedCity, matriceWeight, chowRoad,matriceRes);
	 }
	
	 protected void printMatrice(float[][] matr)
	 {
			System.out.println("matriceRes :\n");
			System.out.print("  |");
			for (int i =0 ; i < nbCity ; i++){
				System.out.print(" V"+i+"|");}
			System.out.print("\n");
			System.out.print("--+");
			for (int i =0 ; i < nbCity ; i++){
				System.out.print("---+");}
			
			
			System.out.print("\n");
			for (int i = 0 ; i < nbCity ; i++){
				System.out.print("V"+i+"|");
				for (int j = 0 ; j < nbCity ; j++){
					System.out.print(" "+matr[i][j]+" |");}
				System.out.print("\n");
				System.out.print("--+");
				for (int cpt =0 ; cpt < nbCity ; cpt++){
					System.out.print("---+");}
				System.out.print("\n");
			}
		}
	 
}

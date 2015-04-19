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
	protected boolean chowWeight;
	protected int matriceRes[][];
	protected int p ; // -1 if not used for UFLP mode
	protected boolean isUFLP;
	protected int nbCity;

	public Simulation(HashMap<Integer, SimulatedCity> listCity , float [][] matrice, int p, boolean isUFLP,boolean chowWeight) {
		//this.addAttribute("ui.stylesheet","url('stylehsheet.css');");
		this.listSimulatedCity = listCity;
		matriceWeight = matrice;
		nbCity = listCity.size();
		matriceRes = new int[nbCity][nbCity];
		this.p = p;
		this.isUFLP = isUFLP;
		this.chowWeight = chowWeight;
		
		
						
	}
	 public void startSimulation(){
		 /*
		  *  ....
		  */
		 
		 
		 showResul();
	 }
	 
	 private void showResul(){
		new Window(listSimulatedCity, matriceWeight, chowWeight);
	 }
	

}

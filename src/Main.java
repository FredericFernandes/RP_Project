

import graphics.MainWindow;

import java.io.File;
import java.util.Random;

import javax.swing.JFileChooser;

import simul.Simulation;

import Core.MyGlpk;
import loader.LoaderSimulation;


public class Main {


	public static void main(String[] args) 
	{
		//String pathInstance = "inst_63_10000_1_30_0_7.flp";  // 10 cities
		//String pathInstance = "p-median_10Cites.flp";  // 10 cities p median
		
		//String pathInstance ="inst_77_5000_1_30_0_7.flp"; // 58 cities
		//String pathInstance ="inst_77_0_1_50_0_7.flp"; // 514 cities
		//String pathInstance ="Instances utilisées/inst_63_10000_1_30_0_7.flp"; 
		String pathInstance= null;
		while(pathInstance==null)
		{
			JFileChooser fileChooser = new JFileChooser("./Instances utilisées/");
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) 
			{
				 File file = fileChooser.getSelectedFile();
				 pathInstance = file.getAbsolutePath();
			}
		}
		
		
		boolean chowRoad =false;
		//Simulation simul = LoaderSimulation.createSimulationExactMethod(pathInstance,chowRoad);
		//Simulation simul = LoaderSimulation.createSimulationArrondiMethod(pathInstance,chowRoad);	
		Simulation simul = LoaderSimulation.createSimulationGeneticsMethod(pathInstance, chowRoad);
		
		simul.startSimulation();
		simul.showResult();

	}



}

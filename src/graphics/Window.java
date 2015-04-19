package graphics;

import java.util.HashMap;

import javax.swing.JFrame;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;


import Core.City;
import Core.SimulatedCity;
import Core.StorageAllCity;


public class Window extends SingleGraph{

	public Window(HashMap<Integer, SimulatedCity> listSimulatedCity, float[][] matriceWeight, boolean chowWeight) {
		super("Simulation");

		addAttribute("ui.quality");
		addAttribute("ui.antialias");
		setAutoCreate(false);
		setStrict(false);		

		Viewer viewer = new Viewer(this, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		//viewer.enableAutoLayout();
		viewer.addDefaultView(false);/**/   // false indicates "no JFrame".
		View defaultView = viewer.getDefaultView();

		JFrame frame = new JFrame(id);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setContentPane(defaultView);
		frame.setVisible(true);	
 
		//drowFrance();
		drowSimulatedCity(listSimulatedCity);
		drowRoad(matriceWeight, listSimulatedCity, chowWeight);
		
		//Camera cam = defaultView.getCamera();
		//cam.setViewCenter(3,45,0);
		//cam.setViewPercent(0.01);

	}
	
	private void drowFrance(){// print The "France" 
		for (City city : StorageAllCity.getInstance().getHashVilles().values()){
			Node newNode = addNode(city.getNom());
			//System.out.println("name : "+city.getNom());
			//newNode.addAttribute("ui.label", city.getNbDepartement());	
			newNode.setAttribute("ui.style", "size: 3px, 3px;");
			double x = city.getCoord().getLong();
			double y = city.getCoord().getLat();
			newNode.setAttribute("xyz", x, y, 0);
		}
	}
	
	private void drowSimulatedCity(HashMap < Integer,SimulatedCity> listSimulatedCity){
		for (SimulatedCity city : listSimulatedCity.values()){
			//System.out.println("ajout de "+id_Case);
			Node newNode = addNode(city.getNom());
			//System.out.println("name : "+city.getNom());
			newNode.addAttribute("ui.label", " id: "+String.valueOf(city.getIdIntoSimulation()+" "+city.getNom()));	
			newNode.setAttribute("ui.style", "fill-mode: plain; size: 7px, 7px;");
			//newNode.setAttribute("ui.style", "fill-mode: plain; size: 5px, 5px;");
			double x = city.getCoord().getLat();
			double y = city.getCoord().getLong();
			newNode.setAttribute("xyz", x, y, 0);
		}
	}
	private void drowRoad(float [][] matriceWeight, HashMap < Integer,SimulatedCity> listSimulatedCity, boolean chowWeight){
		for (int i =0 ; i < matriceWeight.length ; i ++){
			for (int j =0 ; j < matriceWeight.length ; j ++){
				if (matriceWeight[i][j] != 0){
					//System.out.println("\n");
					String name1 = listSimulatedCity.get(i).getNom();
					String name2 = listSimulatedCity.get(j).getNom();
					//System.out.println("name1 : "+name1);
					//System.out.println("name2 : "+name2);
					Edge e = addEdge("c_"+i+"_"+j, name1, name2);
					if (chowWeight){
						if (e != null)
							e.addAttribute("ui.label", "c: "+matriceWeight[i][j]);
					}
				}				
			}
		}
	}

}

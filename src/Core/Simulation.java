package Core;

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

import data.City;
import data.SimulatedCity;

public class Simulation extends SingleGraph{

	private HashMap < Integer,SimulatedCity> listSimulatedCity;
	private float matriceWeight[][];
	private boolean chowWeight;
	private int matriceRes[][];
	private int p ;
	private boolean isUFLP;

	public Simulation(HashMap<Integer, SimulatedCity> listCity , float [][] matrice, int p, boolean isUFLP, boolean chowWeight) {
		super("Simulation");
		//this.addAttribute("ui.stylesheet","url('stylehsheet.css');");
		this.listSimulatedCity = listCity;
		matriceWeight = matrice;
		int nbCity = listCity.size();
		matriceRes = new int[nbCity][nbCity];
		this.p = p;
		this.isUFLP = isUFLP;
		this.chowWeight = chowWeight;
		// For SingleGraph class

		addAttribute("ui.quality");
		addAttribute("ui.antialias");

		setAutoCreate(false);
		setStrict(false);					
	}
	public void startSimulation()
	{
		Viewer viewer = new Viewer(this, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		//viewer.enableAutoLayout();
		viewer.addDefaultView(false);/**/   // false indicates "no JFrame".
		View defaultView = viewer.getDefaultView();

		JFrame frame = new JFrame(id);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setContentPane(defaultView);
		frame.setVisible(true);	
		addAllCity();
		addAllRoad();
		//Camera cam = defaultView.getCamera();
		//cam.setViewCenter(3,45,0);
		//cam.setViewPercent(0.01);



	}
	private void addAllCity(){
		for (SimulatedCity city : listSimulatedCity.values())
		{
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
		//		int nb =  Storage.getInstance().getHashVilles().size() /5;
		//		int cpt =0;
		//		HashMap<Integer, City> hashTmp = new HashMap<Integer, City>();
		//		for (Integer id : Storage.getInstance().getHashVilles().keySet())
		//		{
		//			if (cpt > nb)
		//				break;
		//			else 
		//				hashTmp.put(id, Storage.getInstance().getHashVilles().get(id));
		//			cpt++;
		//		}
		//		for (City city : Storage.getInstance().getHashVilles().values())
		//		{
		//			Node newNode = addNode(city.getNom());
		//			//System.out.println("name : "+city.getNom());
		//			//newNode.addAttribute("ui.label", city.getNbDepartement());	
		//			newNode.setAttribute("ui.style", "size: 3px, 3px;");
		//			double x = city.getCoord().getLong();
		//			double y = city.getCoord().getLat();
		//			newNode.setAttribute("xyz", x, y, 0);
		//		}
	}
	private void addAllRoad()
	{
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

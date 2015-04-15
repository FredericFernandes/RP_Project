
 
import Core.Simulation;
import loader.LoaderInstance;
import loader.LoaderVilles;


public class Main {


	public static void main(String[] args) 
	{
		String pathInstance = "inst_63_10000_1_30_0_7.flp";
		LoaderVilles obj = new LoaderVilles();
		LoaderInstance loadInst = new LoaderInstance(pathInstance);
		Simulation simul = loadInst.createSimulation();
	}

}

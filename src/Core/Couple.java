package Core;


public class Couple {

	int managedCity;
	double cost;
	
	public Couple(int ville, double cout){
		this.managedCity = ville;
		this.cost = cout;
	}
	
	
	public int getL(){
		return managedCity;
	}
	
	public double getR(){
		return cost;
	}
	
	public String toString (){
		return "(" + this.managedCity + ", " + this.cost + ")";
	}
}

package Core;




public class City 
{
  private int id;
  private String nbDepartement;
  private String nom;
  private int population;
  private Coordonnee coord;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getNbDepartement() {
	return nbDepartement;
}
public void setNbDepartement(String nbDepartement) {
	this.nbDepartement = nbDepartement;
}
public String getNom() {
	return nom;
}
public void setNom(String nom) {
	this.nom = nom;
}
public int getPopulation() {
	return population;
}
public void setPopulation(int population) {
	this.population = population;
}
public Coordonnee getCoord() {
	return coord;
}
public void setCoord(Coordonnee coord) {
	this.coord = coord;
}

public City(int id, String nbDepartement, String nom, int population,
		double longitude , double latitude) {
	super();
	this.id = id;
	this.nbDepartement = nbDepartement;
	this.nom = nom;
	this.population = population;
	this.coord = new Coordonnee(longitude, latitude);
}
public City(City city) 
{
	super();
	this.id = city.id;
	this.nbDepartement = city.nbDepartement;
	this.nom = city.nom;
	this.population = city.population;
	this.coord = city.coord;
}
@Override
public String toString() {
	return "Ville [id=" + id + ", nbDepartement=" + nbDepartement + ", nom="
			+ nom + ", population=" + population + ", coord=" + coord + "]";
}
  
 


}

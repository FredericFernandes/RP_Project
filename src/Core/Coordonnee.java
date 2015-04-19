package Core;

public class Coordonnee {

	private double longitude;
	private double latitude;
	
	public double getLong() {
		return longitude;
	}
	public void setLong(double longitude) {
		this.longitude = longitude;
	}
	public double getLat() {
		return latitude;
	}
	public void setLat(double latitude) {
		this.latitude = latitude;
	}
	
	public Coordonnee(double longitude, double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	@Override
	public String toString() {
		return "Coordonnée [longitude=" + longitude + ", latitude=" + latitude
				+ "]";
	}
	

}

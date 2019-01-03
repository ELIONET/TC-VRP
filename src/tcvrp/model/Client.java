package tcvrp.model;
import java.lang.Math;
import java.util.HashMap;


public class Client {
	
	public int posX;
	public int posY;
	
	public int frequency;
	
	//Used for solution initialisation
	public int numberOfTimeVisited;
	
	public double visitTime;
	public HashMap<Client,Float> distances;

	
	
	public Client(){
		this.posX = 5;
		this.posY = 5;
		this.frequency = 5;
		this.visitTime = 5;
		this.distances = new HashMap<Client,Float>();
		this.numberOfTimeVisited = 0;
	}	
	
	public Client(int k ){
		this.posX = k;
		this.posY = k;
		this.frequency = 3;
		this.visitTime = 3;
		this.distances = new HashMap<Client,Float>();
		this.numberOfTimeVisited = 0;
	}	
	
	public Client(int posX, int posY, int frequency, int visitTime){
		this.posX = posX;
		this.posY = posY;
		this.frequency = frequency;
		this.visitTime = visitTime;
		this.distances = new HashMap<Client,Float>();
		this.numberOfTimeVisited = 0;

	}
	
	
	
	public double distance(Client b){
		double dist = Math.sqrt(Math.pow((double)(this.posX - b.posX) , 2) + Math.pow((double)(this.posY - b.posY) , 2) );
		if (distances.containsKey(b)  ) {
			distances.put(b,Float.valueOf((float) dist ));			
		}
		return dist;
	}
	
	public double min_dist(Client a, Client b){
		return Math.min(this.distance( a), this.distance( b));
	}
	
	public String toString(){
		String message = "on est a la position (" + this.posX + ", " + this.posY + ").\n";
		return message;
	}
	
}

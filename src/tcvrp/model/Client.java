package tcvrp.model;
import java.lang.Math;


public class Client {
	
	public int posX;
	public int posY;
	
	public int frequency;
	
	public int visitTime;
	
	public Client(){
		this.posX = 5;
		this.posY = 5;
		this.frequency = 5;
		this.visitTime = 5;
	}	
	
	public Client(int posX, int posY, int frequency, int visitTime){
		this.posX = posX;
		this.posY = posY;
		this.frequency = frequency;
		this.visitTime = visitTime;
	}
	
	
	
	public int distance(Client b){
		
		return 0;
	}
	
	public int min_dist(Client a, Client b){
		return Math.min(this.distance( a), this.distance( b));
	}
	
	public String toString(){
		String message = "on est a la position (" + this.posX + ", " + this.posY + ").\n";
		return message;
	}
	
}

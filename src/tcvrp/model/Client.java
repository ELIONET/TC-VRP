package tcvrp.model;
import java.lang.Math;

public class Client {

	public int posX;
	public int posY;
	
	public Client (int posX, int posY) {
		
		this.posX = posX;
		this.posY = posY;
		
	}
	
	public int getPosX () {
		return this.posX;
	}
	
	
	public int getPosY () {
		return this.posY;
	}
	
	public int distanceManhattan (Client client) {
		return (Math.abs(this.posX-client.getPosX())+Math.abs(this.posY-client.getPosY()));
	}
	
}

package tcvrp.model;

public class Client {
	
	public int posX;
	public int posY;
	
	public int frequency;
	
	public int visitTime;
	
	public Client(int posX, int posY, int frequency, int visitTime){
		this.posX = posX;
		this.posY = posY;
		this.frequency = frequency;
		this.visitTime = visitTime;
	}
	
}

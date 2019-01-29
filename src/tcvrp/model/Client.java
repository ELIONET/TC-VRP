package tcvrp.model;
import java.lang.Math;
import java.util.HashMap;


// import java.util.HashMap;

public class Client {

	public int posX;
	public int posY;
	
	/*public int tempsVisite;
	
	public HashMap<Client,Integer> tempsTrajet;*/
	
	
	public Client (int posX, int posY) {
		
		this.posX = posX;
		this.posY = posY;
		
	}
	
	
	/*public Client (int posX, int posY, int tempsVisite, int nbClients) {
		
		this.posX = posX;
		this.posY = posY;
		
		this.tempsVisite = tempsVisite;
		
		this.tempsTrajet = new HashMap<Client,Integer>();
		this.tempsTrajet.put(this, this.distanceManhattan(this));
		
	}*/
	
	
	public int getPosX () {
		return this.posX;
	}
	
	
	public int getPosY () {
		return this.posY;
	}
	
	
	/*public int getTempsVisite () {
		return this.tempsVisite;
	}
	
	
	public int getTempsTrajet (Client client) {
		return this.tempsTrajet.get(client);
	}
	
	
	public void setTempsTrajet (Client client) {
		if (!this.tempsTrajet.containsKey(client)) {
			this.tempsTrajet.put(client, this.distanceManhattan(client));
		}
	}*/
	
	
	public int distanceManhattan (Client client) {
		return (Math.abs(this.posX-client.getPosX())+Math.abs(this.posY-client.getPosY()));
	}
	
}

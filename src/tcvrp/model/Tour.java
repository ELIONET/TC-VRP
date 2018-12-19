package tcvrp.model;

import java.util.ArrayList;

public class Tour {

	public Client hub; // tc
	public ArrayList<Client> tour; // liste de clients a voir
	
	public Tour(Client hub){
		this.tour = new ArrayList<Client>();
		this.hub = hub;
		this.tour.add(hub);
		this.tour.add(hub);
	}
	
	public Tour(Client h, ArrayList<Client> t){
		this.hub = h;
		this.tour = new ArrayList<Client>(t);
		
	}
		
	public Tour copyTour(){
		ArrayList<Client> tab = new ArrayList<Client>(this.tour);		
		return new Tour(this.hub, tab); 		
	}
	
	public void addAtTheEnd(Client c){
		this.tour.add( this.tour.size()-1, c );
	}
	
	public String toString(){
		String chaine = "notre hub est " + this.hub.toString() + " et notre tour est : \n";
		for(Client t : this.tour){
			chaine = chaine + t.toString();
		}
		return chaine;
	}
	
	
}

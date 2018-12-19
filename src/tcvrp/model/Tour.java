package tcvrp.model;

import java.util.ArrayList;

public class Tour {

	public Client hub; // tc
	public ArrayList<Client> tour; // liste de clients a voir
	public double distanceTour;
	
	
	public Tour(Client hub){
		this.tour = new ArrayList<Client>();
		this.hub = hub;
		this.tour.add(hub);
		this.tour.add(hub);
		this.distanceTour = 0;
	}
	
	public Tour(Client h, ArrayList<Client> t){
		this.hub = h;
		this.tour = new ArrayList<Client>(t);
		this.distanceTour = this.distance_Globale();
		
	}
		
	public Tour copyTour(){
		ArrayList<Client> tab = new ArrayList<Client>(this.tour);		
		return new Tour(this.hub, tab); 		
	}
	
	public void addAtTheEnd(Client c){
		this.tour.add( this.tour.size()-1, c );
		this.distance_Globale();
	}
	
	public String toString(){
		String chaine = "notre hub est " + this.hub.toString()+"\n notre distance de tour est "
				+ this.distance_Globale() + "\n et notre tour est : \n";
		for(Client t : this.tour){
			chaine = chaine + t.toString();
		}
		return chaine;
	}
	
	
	public double distance_Globale(){
		double somme = 0 ;
		for(int i = 0; i < this.tour.size()-1; i++ ){
			somme =  (somme + this.tour.get(i).distance(this.tour.get(i+1)));			
		}
		this.distanceTour = somme;
		return somme;
	}
	
	
}

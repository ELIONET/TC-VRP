package tcvrp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

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
	
	public double getTotalVisitTime(){
		double total = 0;
		
		for(int i = 0; i < this.tour.size()-2; i++){
			total += this.tour.get(i).distance(this.tour.get(i+1)) / Solution.SPEED;
		}
		
		for(int i = 1; i < this.tour.size()-2; i++){
			total += this.tour.get(i).visitTime;
		}
		
		return total;
	}
	
	public boolean isFeasible(){
		return this.getTotalVisitTime() <= Solution.DAY_DURATION;
	}
	
	public double distance_Globale(){
		double somme = 0 ;
		for(int i = 0; i < this.tour.size()-1; i++ ){
			somme =  (somme + this.tour.get(i).distance(this.tour.get(i+1)));			
		}
		this.distanceTour = somme;
		return somme;
	}

	public void intraSwap() {
		
		int pos1 = ThreadLocalRandom.current().nextInt(1, this.tour.size()-1);
		int pos2 = ThreadLocalRandom.current().nextInt(1, this.tour.size()-1);

		Collections.swap(this.tour, pos1, pos2);
		
	}

	public void intraShift() {
	
		int pos1 = ThreadLocalRandom.current().nextInt(1, this.tour.size()-1);
		int pos2 = ThreadLocalRandom.current().nextInt(1, this.tour.size()-1);
		
		if(pos1 < pos2){
			int temp = pos1;
			pos1 = pos2;
			pos2 = temp;
		}
		
		Collections.rotate(this.tour.subList(pos1, pos2+1), -1);
		
	}

	public void intraOpt(int kOpt) {
		
		int numC1 = ThreadLocalRandom.current().nextInt(1, this.tour.size()-1-kOpt);
		
		Collections.reverse(this.tour.subList(numC1, kOpt));
		
	}
	
	
}

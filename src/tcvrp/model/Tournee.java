package tcvrp.model;

import java.util.Collections;
import java.util.LinkedList;
/*import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;*/
import java.util.concurrent.ThreadLocalRandom;

public class Tournee {

	public Donnees donnees;
	
	public int nbDecompositions;
	
	public int hub;
	public LinkedList<Integer> clients;
	public int duree;
	
	
	public Tournee (Donnees donnees, int nbDecompositions, int hub) {
		
		this.donnees = donnees;
		
		this.nbDecompositions = nbDecompositions;

		this.hub = hub;
		this.clients = new LinkedList<Integer>();
		this.duree = 0;
		
	}
	
	public Tournee (Donnees donnees, int nbDecompositions, int hub, LinkedList<Integer> clients){
		
		this.donnees = donnees;
		
		this.nbDecompositions = nbDecompositions;

		this.hub = hub;
		this.clients = clients;
		this.calculerDuree();
		
	}
	
	
	public void ajouterClient (int client) {
		this.clients.add(client);
		this.calculerDuree();
	}
	
	
	public void ajouterClient (int position, int client) {
		this.clients.add(position, client);
		this.calculerDuree();
	}
	
	
	public void enleverClient (int position) {
		this.clients.remove(position);
		this.calculerDuree();
	}
	
	
	public int getDuree () {
		return this.duree;
	}
	
	
	public void calculerDuree () {
		int duree = 0;
		for (int position = 0; position < this.clients.size(); position++) {
			if (position == 0) {
				duree += this.donnees.getTempsTrajet(this.hub, this.clients.get(position));
			} else {
				duree += this.donnees.getTempsTrajet(this.clients.get(position-1), this.clients.get(position));
			}
			if (position == this.clients.size()-1) {
				duree += this.donnees.getTempsTrajet(this.clients.get(position), this.hub);
			}
			duree += this.donnees.getTempsVisite(this.clients.get(position));
		}
		this.duree = duree;
	}
	
	
	public boolean estFaisable () {
		return (this.duree <= this.nbDecompositions);
	}
	
	
	public Tournee copierTournee () {
		LinkedList<Integer> clients = new LinkedList<Integer>(this.clients);		
		return new Tournee(this.donnees, this.nbDecompositions, this.hub, clients);
	}
	
	
	public String toString () {
		String chaine = "Notre hub est " + this.hub + "\n notre durée de tour est " + this.duree + "\n et notre tour est : \n";
		for (int position = 0; position < this.clients.size(); position++) {
			chaine = chaine + this.clients.get(position) + " -> ";
		}
		return chaine;
	}

	
	public void intraSwap() {
		
		int pos1 = ThreadLocalRandom.current().nextInt(1, this.clients.size());
		int pos2 = ThreadLocalRandom.current().nextInt(1, this.clients.size());

		Collections.swap(this.clients, pos1, pos2);
		
	}
	

	public void intraShift() {
		
		this.toString();
		
		int pos1 = ThreadLocalRandom.current().nextInt(1, this.clients.size());
		int pos2 = ThreadLocalRandom.current().nextInt(1, this.clients.size());
		
		if(pos1 > pos2){
			int temp = pos1;
			pos1 = pos2;
			pos2 = temp;
		}
		
		Collections.rotate(this.clients.subList(pos1, pos2+1), -1);
		
	}
	

	public void intraOpt(int kOpt) {
		
		int numC1 = ThreadLocalRandom.current().nextInt(1, this.clients.size()-kOpt);
		
		Collections.reverse(this.clients.subList(numC1, numC1+kOpt));
		
	}
	
}


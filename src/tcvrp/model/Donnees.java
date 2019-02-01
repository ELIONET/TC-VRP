package tcvrp.model;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Donnees {
	
	// nombre de clients dans les données
	private int nbClients;
	
	// durée minimale d'un trajet entre deux clients (pour la génération aléatoire des données)
	private int tempsTrajetMin;
	
	// durée maximale d'un trajet entre deux clients (pour la génération aléatoire des données)
	private int tempsTrajetMax;
	
	// durée minimale d'une visite (pour la génération aléatoire des données)
	private int tempsVisiteMin;
	
	// durée maximale d'une visite (pour la génération aléatoire des données)
	private int tempsVisiteMax;
	
	// ensemble des clients
	public static HashMap<Integer,Client> clients;
	
	// dimensions de la carte sur laquelle sont situés les clients
	private int dimensionsCarte;
	
	// matrice des temps de trajet entre deux clients
	private double[][] tempsTrajet;
	
	// tableau des temps de visite des clients
	private int[] tempsVisite;
	
	//Vitesse en km/h
	public final static double VITESSE = 60;
	
	public Donnees (int nbClients, int tempsTrajetMin, int tempsTrajetMax, int tempsVisiteMin, int tempsVisiteMax) {
		
		this.nbClients = nbClients;
		this.tempsTrajetMin = tempsTrajetMin;
		this.tempsTrajetMax = tempsTrajetMax;
		this.tempsVisiteMin = tempsVisiteMin;
		this.tempsVisiteMax = tempsVisiteMax;
		
		this.genererDonnees1();
		//this.afficherDonnees();
		
	}
	
	
	public Donnees (int nbClients, int dimensionsCarte, int tempsVisiteMin, int tempsVisiteMax) {
		
		this.nbClients = nbClients;
		clients = new HashMap<Integer,Client>();
		this.dimensionsCarte = dimensionsCarte;
		this.tempsTrajetMax = 2*dimensionsCarte-2;
		this.tempsVisiteMin = tempsVisiteMin;
		this.tempsVisiteMax = tempsVisiteMax;
		
		this.genererDonnees2();
		//this.afficherDonnees();
		
	}

	
	private void genererDonnees1 () {
		this.tempsTrajet = new double[this.nbClients][this.nbClients];
		this.tempsVisite = new int[this.nbClients];
		for (int client1 = 1; client1 < this.nbClients; client1++) {
			for (int client2 = 0; client2 < client1; client2++) {
				int tempsTrajetTemp = (int)(Math.random()*(this.tempsTrajetMax-this.tempsTrajetMin+1))+this.tempsTrajetMin;
				this.tempsTrajet[client1][client2] = tempsTrajetTemp;
				this.tempsTrajet[client2][client1] = tempsTrajetTemp;
			}
		}
		for (int client = 0; client < this.nbClients; client++) {
			this.tempsVisite[client] = (int)(Math.random()*(this.tempsVisiteMax-this.tempsVisiteMin+1))+this.tempsVisiteMin;
		}
	}

	
	private void genererDonnees2 () {
		this.tempsTrajet = new double[this.nbClients][this.nbClients];
		this.tempsVisite = new int[this.nbClients];
		for (int client1 = 0; client1 < this.nbClients; client1++) {
			int posX = ThreadLocalRandom.current().nextInt(0, this.dimensionsCarte);
			int posY = ThreadLocalRandom.current().nextInt(0, this.dimensionsCarte);
			clients.put(client1, new Client(posX, posY));
			for (int client2 = 0; client2 < client1; client2++) {
				double tempsTrajetTemp = clients.get(client2).distanceManhattan(clients.get(client1))/(VITESSE/60);
				this.tempsTrajet[client1][client2] = tempsTrajetTemp;
				this.tempsTrajet[client2][client1] = tempsTrajetTemp;
			}
		}
		for (int client = 0; client < this.nbClients; client++) {
			this.tempsVisite[client] = (int)(Math.random()*(this.tempsVisiteMax-this.tempsVisiteMin+1))+this.tempsVisiteMin;
		}
	}
	
	
	private void afficherDonnees () {
		System.out.println("TEMPS DE TRAJET :");
		for (int client1 = 0; client1 < this.nbClients; client1++) {
			System.out.print("[ ");
			for (int client2 = 0; client2 < this.nbClients; client2++) {
				System.out.print(this.tempsTrajet[client1][client2]+" ");
			}
			System.out.println("]");
		}
		System.out.println();
		System.out.println("TEMPS DE VISITE :");
		System.out.print("[ ");
		for (int client = 0; client < this.nbClients; client++) {
				//System.out.print(this.tempsVisite[client]+" ");
		}
		System.out.println("]");
		System.out.println();
	}
	
	
	public int getNbClients () {
		return this.nbClients;
	}
	
	
	public double getTempsTrajet (int client1, int client2) {
		return this.tempsTrajet[client1][client2];
	}
	
	
	public int getTempsVisite (int client) {
		return this.tempsVisite[client];
	}
	
	
	public int getTempsTrajetMax () {
		return this.tempsTrajetMax;
	}
	
}

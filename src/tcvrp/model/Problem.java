package tcvrp.model;

import java.util.LinkedList;

public class Problem {
	
	private Donnees donnees;
	
	public LinkedList<Tournee> solution;
	
	
	public Problem (int nbClients, int nbJours, int nbDecompositions, int tempsTrajetMin, int tempsTrajetMax, int tempsVisiteMin, int tempsVisiteMax) {
		
		this.donnees = new Donnees (nbClients, tempsTrajetMin, tempsTrajetMax, tempsVisiteMin, tempsVisiteMax);
		
		this.dichotomie(nbJours, nbDecompositions);
		
	}
	
	
	public Problem (int nbClients, int nbJours, int nbDecompositions, int dimensionsCarte, int tempsVisiteMin, int tempsVisiteMax) {
		
		this.donnees = new Donnees (nbClients, dimensionsCarte, tempsVisiteMin, tempsVisiteMax);
		
		this.dichotomie(nbJours, nbDecompositions);
		
	}
	
	
	public void dichotomie (int nbJours, int nbDecompositions) {
		
		int borneInf = 1;
		int borneSup = this.donnees.getNbClients();
		
		while (borneInf != borneSup) {

			int nbCommerciaux = (int)((borneInf+borneSup)/2);
			
			Variables variables = new Variables (this.donnees.getNbClients(), nbCommerciaux);
			
			LinkedList<Tournee> solution = new LinkedList<Tournee>();
			
			boolean faisable = true;
			boolean[] clientsVisites = new boolean[this.donnees.getNbClients()];
			for (int commercial = 0; commercial < variables.getNbCommerciaux(); commercial++) {
				// nouveau commercial
				int nbClientsTemp = variables.getNbClients(commercial);
				int jour = -1;
				int hub = variables.getHub(commercial);
				int nbClientsVisites = 0;
				while (nbClientsVisites < nbClientsTemp) {
					// nouveau jour
					jour++;
					solution.add(new Tournee(this.donnees, nbDecompositions, hub));
					int temps = 0;
					int position = hub;
					// recherche du client le plus éloigné
					int clientSuivant = -1;
					double tempsTrajetMax = -1;
					for (int clientTemp = 0; clientTemp < this.donnees.getNbClients(); clientTemp++) {
						if (variables.getVariableY(commercial, clientTemp) == 1 && clientsVisites[clientTemp] == false && this.donnees.getTempsTrajet(position, clientTemp) 
								> tempsTrajetMax) {
							clientSuivant = clientTemp;
							tempsTrajetMax = this.donnees.getTempsTrajet(position, clientTemp);
						}
					}
					// résolution impossible car le temps pour faire un aller retour chez le client le plus éloigné excède la limite de travail en un jour
					if (2*tempsTrajetMax+this.donnees.getTempsVisite(clientSuivant) > nbDecompositions) {
						faisable = false;
						nbClientsVisites = nbClientsTemp;
					} else {
						// visite du client le plus éloigné
						solution.getLast().ajouterClient(clientSuivant);
						temps += this.donnees.getTempsTrajet(position, clientSuivant);
						temps += this.donnees.getTempsVisite(clientSuivant);
						clientsVisites[clientSuivant] = true;
						nbClientsVisites++;
						position = clientSuivant;
						// recherche du client le plus proche encore visitable ce jour
						boolean clientVisitable = true;
						while (nbClientsVisites != nbClientsTemp && clientVisitable) {
							clientSuivant = -1;
							double tempsTrajetMin = this.donnees.getTempsTrajetMax()+1;
							for (int clientTemp = 0; clientTemp < this.donnees.getNbClients(); clientTemp++) {
								if (variables.getVariableY(commercial, clientTemp) == 1 && clientsVisites[clientTemp] == false 
										&& this.donnees.getTempsTrajet(position, clientTemp) < tempsTrajetMin
										&& temps+this.donnees.getTempsTrajet(position, clientTemp)+this.donnees.getTempsVisite(clientTemp)
										+this.donnees.getTempsTrajet(clientTemp, hub) < nbDecompositions+1) {
									clientSuivant = clientTemp;
									tempsTrajetMin = this.donnees.getTempsTrajet(position, clientTemp);
								}
							}
							// visite du client trouvé
							if (clientSuivant != -1) {
								solution.getLast().ajouterClient(clientSuivant);
								temps += this.donnees.getTempsTrajet(position, clientSuivant)+this.donnees.getTempsVisite(clientSuivant);
								clientsVisites[clientSuivant] = true;
								nbClientsVisites++;
								position = clientSuivant;
							} else {
								clientVisitable = false;
							}
						}
					}
				}
				if (jour >= nbJours) {
					faisable = false;
				} 
			}
			if (faisable) {
				this.solution = solution;
				borneSup = nbCommerciaux;
			} 
			else {
				borneInf = nbCommerciaux+1;
			}
			
			if(borneInf == borneSup){
				for(Tournee t : this.solution){
					t.ajouterClient(0,variables.getHub(variables.getTC(t.clients.get(0))));
					t.ajouterClient(t.clients.size(),variables.getHub(variables.getTC(t.clients.get(1))));
				}
			}
		}
		
		//this.afficherSolution();
		
	}
	
	
	public void afficherSolution () {
		System.out.println("Afficher solution");
	}

}

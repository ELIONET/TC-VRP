package tcvrp;

import java.util.ArrayList;

import tcvrp.model.Client;
import tcvrp.model.Problem;
import tcvrp.model.Solution;
import tcvrp.model.Tournee;

public class Main {

	public static void main(String[] args) {
		
		int nbClients = 100;
		int nbCommerciaux = 10;
		int nbJours = 5;
		int nbDecompositions = 48;
		int tempsTrajetMin = 0;
		int tempsTrajetMax = 12;
		int tempsVisiteMin = 3;
		int tempsVisiteMax = 12;
		int dimensionsCarte = 10;
		
		// Algo monAlgo = new Algo (nbClients, nbCommerciaux, nbJours, nbDecompositions, tempsTrajetMin, tempsTrajetMax, tempsVisiteMin, tempsVisiteMax);
		// Problem problem1 = new Problem (nbClients, nbJours, nbDecompositions, tempsTrajetMin, tempsTrajetMax, tempsVisiteMin, tempsVisiteMax);
		Problem problem2 = new Problem (nbClients, nbJours, nbDecompositions, dimensionsCarte, tempsVisiteMin, tempsVisiteMax);

		ArrayList<Tournee> tournees = new ArrayList<Tournee>();
		tournees.addAll(problem2.solution);
		
		Solution s = new Solution(tournees);
		
		for(Tournee t : s.solution){
			for(Integer i : t.clients){
				System.out.print(i + " -> ");
			}
			System.out.println("");
		}
		System.out.println("Coût avant SA : " + s.computeTotalVisitTime());
		s = s.simulatedAnnealing(s);
		
		for(Tournee t : s.solution){
			for(Integer i : t.clients){
				System.out.print(i + " -> ");
			}
			System.out.println("");
		}
		System.out.println("Coût après SA : " + s.computeTotalVisitTime());
		// Etape 1 = algo glouton pour borne sup = k
		
		// Etape 2 = PLNE pour placer k TC
		
		// Etape 3 = Pour chaque TC, réaliser un algo glouton pour essayer de voir rapidement si c'est faisable:
		// - Si faisable : on recommence étape 2 avec k + petit (jusqu'à convergence)
		// - Si non faisable : on recommence étape 2 avec k + grand (jusqu'à convergence)
		
		// Etape 4 = Métaheuristique pour donner la une solution possible la plus proche de l'optimalité
		// pour le nombre k de TC trouvé à la fin de la convergence

	}

}

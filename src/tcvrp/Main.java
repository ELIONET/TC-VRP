package tcvrp;

import java.util.ArrayList;

import tcvrp.model.Problem;
import tcvrp.model.Solution;
import tcvrp.model.Tournee;

public class Main {

	public static void main(String[] args) {
		
		int nbClients = 100;
		int nbJours = 5;
		int nbDecompositions = 48;
		int tempsVisiteMin = 3;
		int tempsVisiteMax = 12;
		int dimensionsCarte = 50;
		
		Problem problem2 = new Problem (nbClients, nbJours, nbDecompositions, dimensionsCarte, tempsVisiteMin, tempsVisiteMax);

		ArrayList<Tournee> tournees = new ArrayList<Tournee>();
		tournees.addAll(problem2.solution);
		
		
		//Solution retourn�e par l'heuristique
		Solution s = new Solution(tournees);
		
		//Affichage solution avant SA
		/*
		for(Tournee t : s.solution){
			for(Integer i : t.clients){
				System.out.print(i + " -> ");
			}
			System.out.println("");
		}
		*/
		System.out.println("Co�t avant SA : " + s.computeTotalVisitTime());
		
		//Execution de l'algorithme
		s = s.simulatedAnnealing(s);
		
		//Affichage solution apr�s SA
		/*
		for(Tournee t : s.solution){
			for(Integer i : t.clients){
				System.out.print(i + " -> ");
			}
			System.out.println("");
		}
		*/
		
		//Affichage du r�sultat du SA
		System.out.println("Co�t apr�s SA : " + s.computeTotalVisitTime());

	}

}

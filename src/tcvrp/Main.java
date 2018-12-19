package tcvrp;

import tcvrp.model.Client;
import tcvrp.model.Tour;

public class Main {

	public static void main(String[] args) {
		Client c = new Client();
		Tour t = new Tour(c);
		Tour t2;
		System.out.println(t.toString());
		t2 = t.copyTour();
		System.out.println("t2 cette fois");
		System.out.println(t.toString());
		// Etape 1 = algo glouton pour borne sup = k
		
		// Etape 2 = PLNE pour placer k TC
		
		// Etape 3 = Pour chaque TC, r�aliser un algo glouton pour essayer de voir rapidement si c'est faisable:
		// - Si faisable : on recommence �tape 2 avec k + petit (jusqu'� convergence)
		// - Si non faisable : on recommence �tape 2 avec k + grand (jusqu'� convergence)
		
		// Etape 4 = M�taheuristique pour donner la une solution possible la plus proche de l'optimalit�
		// pour le nombre k de TC trouv� � la fin de la convergence

	}

}

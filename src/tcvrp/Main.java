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
		
		// Etape 3 = Pour chaque TC, réaliser un algo glouton pour essayer de voir rapidement si c'est faisable:
		// - Si faisable : on recommence étape 2 avec k + petit (jusqu'à convergence)
		// - Si non faisable : on recommence étape 2 avec k + grand (jusqu'à convergence)
		
		// Etape 4 = Métaheuristique pour donner la une solution possible la plus proche de l'optimalité
		// pour le nombre k de TC trouvé à la fin de la convergence

	}

}

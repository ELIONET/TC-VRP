package tcvrp;

import tcvrp.model.Client;
import tcvrp.model.Solution;
import tcvrp.model.Tour;

public class Main {

	public static void main(String[] args) {
		Client c1 = new Client();
		Client c2 = new Client(2);
		Client c3 = new Client(3);
		
		
		Tour t = new Tour(c1);
		Tour t2;
		
		System.out.println(c2.toString());
		t2 = t.copyTour();
		t2.addAtTheEnd(c2);
		
		t.addAtTheEnd(c3);
		System.out.println("t2 cette fois");
		System.out.println(t2.toString());
		
		Solution s = new Solution();
		s.add(t);
		s.add(t2);
		
		
		System.out.println("notre premiere solution");
		System.out.println (s.toString());
		
		// Etape 1 = algo glouton pour borne sup = k
		
		// Etape 2 = PLNE pour placer k TC
		
		// Etape 3 = Pour chaque TC, r�aliser un algo glouton pour essayer de voir rapidement si c'est faisable:
		// - Si faisable : on recommence �tape 2 avec k + petit (jusqu'� convergence)
		// - Si non faisable : on recommence �tape 2 avec k + grand (jusqu'� convergence)
		
		// Etape 4 = M�taheuristique pour donner la une solution possible la plus proche de l'optimalit�
		// pour le nombre k de TC trouv� � la fin de la convergence

	}

}

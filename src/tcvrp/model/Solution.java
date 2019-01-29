package tcvrp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Solution {

	public static final int SPEED = 50; // km/h
	private static final int MAX_ITERATION = 30;
	public static final double DAY_DURATION = 8;
	
	public ArrayList<Tournee> solution;
	
	public Solution(){
		this.solution = new ArrayList<Tournee>();
	}
	
	public Solution(ArrayList<Tournee> sol){
		this.solution = sol;
	}
	
	public void add(Tournee t){
		this.solution.add(t);
	}
	
	public String toString(){
		String c = "la solution a les tours" ;
		for(Tournee t : this.solution){
			c =  c + t.toString() + "\n ";
		}
		c = c + "le temps totalte est donc " + this.computeTotalVisitTime();
		return c ;
	}
	
	public ArrayList<Integer> getHubs(){
		ArrayList<Integer> hubs = new ArrayList<Integer>();
		
		for(Tournee t : this.solution){
			hubs.add(t.hub);
		}
		
		return hubs;
	}
	
	public Solution copySolution(){
		
		ArrayList<Tournee> copy = new ArrayList<Tournee>();
		
		for(Tournee t : this.solution){
			copy.add(t.copierTournee());
		}
		
		Solution copySol = new Solution(copy);
		
		return copySol;
	}
	
	public void displaySolution(){
		for(int ntour = 0; ntour < this.solution.size(); ntour++){
			
		System.out.print("Tournee n°" + ntour + " : ");
			
			for(int nclient = 0; nclient < this.solution.get(ntour).clients.size() -1; nclient++){
				
				System.out.print("("+Donnees.clients.get(this.solution.get(ntour).clients.get(nclient)).posX+","+Donnees.clients.get(this.solution.get(ntour).clients.get(nclient)).posY+") -> ");
				
			}
			
			System.out.println("("+Donnees.clients.get(this.solution.get(ntour).clients.get(0)).posX+","+Donnees.clients.get(this.solution.get(ntour).clients.get(0)).posY+")");
			
		}
	}
	
	public Solution simulatedAnnealing(Solution solInit){
		Solution bestSol = solInit.copySolution();
		Solution currentSol = solInit.copySolution();
		Solution solPrime = solInit.copySolution();
		int runningTime = 1; // En minutes
		
		int tMax = 25000;
		double T0 = initTemperature(solInit);
		double T = T0;
		double Tb = T0;
		double Tmax = 25000;
		int len = 25;
		int kOpt = 3;
		double alpha = 0.9;
		
		long startTime = System.currentTimeMillis();
		
		while(System.currentTimeMillis() - startTime < 1000 * 60 * runningTime){
			for(int k = 1; k <= len; k++){
				//System.out.println(this.solution.size());
				//System.out.flush();
				switch(1 + (int)(Math.random() * 6)){
				case(1): // Intra Swap
					//System.out.println("intra swap");
					solPrime = intraSwap(currentSol,ThreadLocalRandom.current().nextInt(0, this.solution.size()-1));
					break;
				case(2): // Intra Shift
					//System.out.println("intra shift");
					solPrime = intraShift(currentSol,ThreadLocalRandom.current().nextInt(0, this.solution.size()-1));
					break;
				case(3): // Intra Opt
					//System.out.println("intra opt");
					int tour = ThreadLocalRandom.current().nextInt(0, this.solution.size()-1);
					int iter = 0;
					while(currentSol.solution.get(tour).clients.size() < kOpt + 2 && iter < 100){
						tour = ThreadLocalRandom.current().nextInt(0, this.solution.size()-1);
						iter++;
					}
					if(iter >= 100){ // Au cas où il n'y ait pas assez de tournées de taille kOpt
						break;
					}
					solPrime = intraOpt(currentSol, tour, kOpt);
					break;
				case(4): // Inter Swap
					//System.out.println("inter swap");
					int tour1 = ThreadLocalRandom.current().nextInt(0, this.solution.size()-1);
					int tour2 = ThreadLocalRandom.current().nextInt(0, this.solution.size()-1);
					while(tour1 == tour2){
						//System.out.println("1" + tour1);
						tour1 = ThreadLocalRandom.current().nextInt(0, this.solution.size()-1);
					}
					solPrime = interSwap(currentSol, tour1, tour2);
					break;
				case(5): // Inter Shift
					//System.out.println("inter shift");
					int tour1b = ThreadLocalRandom.current().nextInt(0, this.solution.size()-1);
					int tour2b = ThreadLocalRandom.current().nextInt(0, this.solution.size()-1);
					while(tour1b == tour2b){
						//System.out.println(tour1b);
						tour1b = ThreadLocalRandom.current().nextInt(0, this.solution.size()-1);
					}
					solPrime = interShift(currentSol, tour1b, tour2b);
					break;
				case(6): // Inter Opt
					//System.out.println("inter opt");
					int tour1c = ThreadLocalRandom.current().nextInt(0, this.solution.size()-1);
					int tour2c = ThreadLocalRandom.current().nextInt(0, this.solution.size()-1);
					int iter2 = 0;
					while(currentSol.solution.get(tour1c).clients.size() < kOpt + 2 && iter2 < 100){
						tour1c = ThreadLocalRandom.current().nextInt(0, this.solution.size()-1);
						iter2++;
					}
					if(iter2 >= 100){ // Au cas où il n'y ait pas assez de tournées de taille kOpt
						break;
					}
					//System.out.println("62");
					iter2 = 0;
					while(currentSol.solution.get(tour2c).clients.size() < kOpt + 2 || tour2c == tour1c && iter2 < 100){
						tour2c = ThreadLocalRandom.current().nextInt(0, this.solution.size()-1);
						iter2++;
					}
					if(iter2 >= 100){ // Au cas où il n'y ait pas assez de tournées de taille kOpt
						break;
					}
					solPrime = interOpt(currentSol, tour1c, tour2c, kOpt);
					break;
				}
				
				if(solPrime.computeTotalVisitTime() <= currentSol.computeTotalVisitTime()){
					currentSol = solPrime.copySolution();
				}
				else{
					if(Math.exp( -(solPrime.computeTotalVisitTime() - currentSol.computeTotalVisitTime()) / T ) < Math.random()){
						currentSol = solPrime.copySolution();
					}
				}
				if(solPrime.computeTotalVisitTime() <= bestSol.computeTotalVisitTime()){
					bestSol = solPrime.copySolution();
					Tb = T;
				}
				T = T * alpha;
				if(T <= 0.01){
					Tb = 2 * Tb;
					T = Math.min(Tb, Tmax);
				}
			}
		}
		
		//System.out.println("TERMINE");
		
		return bestSol;
	}
	
	private double initTemperature(Solution solInit) {
		double w = 0.3; // Une solution w fois pire aura 50% de chance d'être acceptée
		return -(w*solInit.computeTotalVisitTime())/Math.log(0.5);
	}

	public int computeTotalVisitTime() {
		int total = 0;
		for(Tournee t : this.solution){
			total += t.duree;
		}
		return total;
	}

	public Solution intraSwap(Solution sol, int tourNum){
		
		Solution copy = this.copySolution();
		
		copy.solution.get(tourNum).intraSwap();
		
		return copy;
		
	}
	
	public Solution intraShift(Solution sol, int tourNum){
		
		Solution copy = this.copySolution();
		
		copy.solution.get(tourNum).intraShift();
		
		return copy;
		
	}
	
	public Solution intraOpt(Solution sol, int tourNum, int kOpt){
		
		Solution copy = this.copySolution();
		
		copy.solution.get(tourNum).intraOpt(kOpt);
		
		return copy;
		
	}
	
	public Solution interSwap(Solution sol, int tourNum1, int tourNum2){
		
		Solution copy = this.copySolution();
		
		int iteration = 0;
		boolean possible = false;
		
		while(!possible && iteration <= MAX_ITERATION){
			
			int numC1 = ThreadLocalRandom.current().nextInt(1, copy.solution.get(tourNum1).clients.size());
			int numC2 = ThreadLocalRandom.current().nextInt(1, copy.solution.get(tourNum2).clients.size());
			
			Integer c1 = copy.solution.get(tourNum1).clients.get(numC1);			
			Integer c2 = copy.solution.get(tourNum2).clients.get(numC2);
			
			copy.solution.get(tourNum1).clients.remove(c1);
			copy.solution.get(tourNum1).clients.add(numC1, c2);
			
			copy.solution.get(tourNum2).clients.remove(c2);
			copy.solution.get(tourNum2).clients.add(numC2, c1);
			
			iteration++;
			
			copy.solution.get(tourNum1).calculerDuree();
			copy.solution.get(tourNum2).calculerDuree();
			
			possible = copy.solution.get(tourNum1).estFaisable() && copy.solution.get(tourNum2).estFaisable();
			
			if(!possible){
				copy = this.copySolution();
			}
		}
		
		return copy;
		
	}
	
	public Solution interShift(Solution sol, int tourNum1, int tourNum2){
		
		Solution copy = this.copySolution();
		
		int iteration = 0;
		boolean possible = false;
		
		while(!possible && iteration <= MAX_ITERATION){
			
			int numC1 = ThreadLocalRandom.current().nextInt(1, copy.solution.get(tourNum1).clients.size());
			int numC2 = ThreadLocalRandom.current().nextInt(1, copy.solution.get(tourNum2).clients.size());
			
			Integer c1 = copy.solution.get(tourNum1).clients.get(numC1);
			
			copy.solution.get(tourNum1).clients.remove(c1);
			copy.solution.get(tourNum2).clients.add(numC2, c1);
			
			iteration++;
			
			possible = copy.solution.get(tourNum2).estFaisable() && copy.solution.get(tourNum1).estFaisable();
			
			if(!possible){
				copy = this.copySolution();
			}
		}
		
		return copy;
		
	}
	
	public Solution interOpt(Solution sol, int tourNum1, int tourNum2, int kOpt){
		
		Solution copy = this.copySolution();
		
		if(this.solution.get(tourNum1).clients.size()-1-kOpt <= 0 || this.solution.get(tourNum2).clients.size()-1-kOpt <= 0){
			return this;
		}
		
		int iteration = 0;
		boolean possible = false;
		
		while(!possible && iteration <= MAX_ITERATION){
			
			//System.out.println(iteration);
			int numC1 = ThreadLocalRandom.current().nextInt(1, copy.solution.get(tourNum1).clients.size()-kOpt);
			int numC2 = ThreadLocalRandom.current().nextInt(1, copy.solution.get(tourNum2).clients.size()-kOpt);
			
			ArrayList<Integer> subTour1 = new ArrayList<Integer>();
			ArrayList<Integer> subTour2 = new ArrayList<Integer>();
			
			for(int i = numC1; i < numC1 + kOpt; i++){
				//System.out.println(copy.solution.get(tourNum1));
				subTour1.add(copy.solution.get(tourNum1).clients.remove(numC1));
			}
			
			for(int i = numC2; i < numC2 + kOpt; i++){
				subTour2.add(copy.solution.get(tourNum2).clients.remove(numC2));
			}
			
			Collections.reverse(subTour1);
			Collections.reverse(subTour2);
			
			copy.solution.get(tourNum1).clients.addAll(numC1, subTour2);
			copy.solution.get(tourNum2).clients.addAll(numC2, subTour1);
			
			iteration++;
			
			possible = copy.solution.get(tourNum1).estFaisable() && copy.solution.get(tourNum2).estFaisable();
			
			if(!possible){
				copy = this.copySolution();
			}
		}
		
		return copy;
		
	}
	
}

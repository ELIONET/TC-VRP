package tcvrp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class Solution {

	public static final int SPEED = 50; // km/h
	private static final int MAX_ITERATION = 30;
	public static final double DAY_DURATION = 8;
	
	public ArrayList<Tour> solution;
	
	public Solution(){
		this.solution = new ArrayList<Tour>();
	}
	
	public Solution(ArrayList<Tour> sol){
		this.solution = sol;
	}
	
	public void add(Tour t){
		this.solution.add(t);
	}
	
	public String toString(){
		String c = "la solution a les tours" ;
		for(Tour t : this.solution){
			c =  c + t.toString() + "\n ";
		}
		c = c + "le temps totalte est donc " + this.computeTotalVisitTime();
		return c ;
	}
	
	public ArrayList<Client> getHubs(){
		ArrayList<Client> hubs = new ArrayList<Client>();
		
		for(Tour t : this.solution){
			hubs.add(t.hub);
		}
		
		return hubs;
	}
	
	public Solution copySolution(){
		
		ArrayList<Tour> copy = new ArrayList<Tour>();
		
		for(Tour t : this.solution){
			copy.add(t.copyTour());
		}
		
		Solution copySol = new Solution(copy);
		
		return copySol;
	}
	
	public void displaySolution(){
		for(int ntour = 0; ntour < this.solution.size(); ntour++){
			
			System.out.print("Tour n°" + ntour + " : ");
			
			for(int nclient = 0; nclient < this.solution.get(ntour).tour.size() -1; nclient++){
				
				System.out.print("("+this.solution.get(ntour).tour.get(nclient).posX+","+this.solution.get(ntour).tour.get(nclient).posY+") -> ");
				
			}
			
			System.out.println("("+this.solution.get(ntour).tour.get(0).posX+","+this.solution.get(ntour).tour.get(0).posY+")");
			
		}
	}
	
	public Solution simulatedAnnealing(Solution solInit){
		Solution bestSol = solInit.copySolution();
		Solution currentSol = solInit.copySolution();
		Solution solPrime = solInit.copySolution();
		int runningTime = 5; // En minutes
		
		int tMax = 25000;
		double T0 = initTemperature(solInit);
		double T = T0;
		double Tb = T0;
		double Tmax = 25000;
		int len = 25;
		int kOpt = 3;
		double alpha = 0.9;
		
		long startTime = System.currentTimeMillis();
		
		while(System.currentTimeMillis() * startTime < 1000 * runningTime){
			for(int k = 1; k <= len; k++){
				switch(1 + (int)(Math.random() * 6)){
				case(1): // Intra Swap
					solPrime = intraSwap(currentSol,(int) Math.random() * this.solution.size());
					break;
				case(2): // Intra Shift
					solPrime = intraShift(currentSol,(int) Math.random() * this.solution.size());
					break;
				case(3): // Intra Opt
					int tour = (int) Math.random() * this.solution.size();
					int iter = 0;
					while(currentSol.solution.get(tour).tour.size() < kOpt + 2 && iter < 100){
						tour = (int) Math.random() * this.solution.size();
						iter++;
					}
					if(iter >= 100){ // Au cas où il n'y ait pas assez de tournées de taille kOpt
						break;
					}
					solPrime = intraOpt(currentSol, tour, kOpt);
					break;
				case(4): // Inter Swap
					int tour1 = (int) Math.random() * this.solution.size();
					int tour2 = (int) Math.random() * this.solution.size();
					while(tour1 == tour2){
						tour1 = (int) Math.random() * this.solution.size();
					}
					solPrime = interSwap(currentSol, tour1, tour2);
					break;
				case(5): // Inter Shift
					int tour1b = (int) Math.random() * this.solution.size();
					int tour2b = (int) Math.random() * this.solution.size();
					while(tour1b == tour2b){
						tour1b = (int) Math.random() * this.solution.size();
					}
					solPrime = interShift(currentSol, tour1b, tour2b);
					break;
				case(6): // Inter Opt
					int tour1c = (int) Math.random() * this.solution.size();
					int tour2c = (int) Math.random() * this.solution.size();
					int iter2 = 0;
					while(currentSol.solution.get(tour1c).tour.size() < kOpt + 2 && iter2 < 100){
						tour1c = (int) Math.random() * this.solution.size();
					}
					if(iter2 >= 100){ // Au cas où il n'y ait pas assez de tournées de taille kOpt
						break;
					}
					iter2 = 0;
					while(currentSol.solution.get(tour2c).tour.size() < kOpt + 2 || tour2c == tour1c && iter2 < 100){
						tour2c = (int) Math.random() * this.solution.size();
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
		
		System.out.println("");
		
		return bestSol;
	}
	
	private double initTemperature(Solution solInit) {
		double w = 0.3; // Une solution w fois pire aura 50% de chance d'être acceptée
		return -(w*solInit.computeTotalVisitTime())/Math.log(0.5);
	}

	private int computeTotalVisitTime() {
		int total = 0;
		for(Tour t : this.solution){
			total += t.tourDuration;
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
			
			int numC1 = ThreadLocalRandom.current().nextInt(1, copy.solution.get(tourNum1).tour.size()-1);
			int numC2 = ThreadLocalRandom.current().nextInt(1, copy.solution.get(tourNum2).tour.size()-1);
			
			Client c1 = copy.solution.get(tourNum1).tour.get(numC1);			
			Client c2 = copy.solution.get(tourNum2).tour.get(numC2);
			
			copy.solution.get(tourNum1).tour.remove(c1);
			copy.solution.get(tourNum1).tour.add(numC1, c2);
			
			copy.solution.get(tourNum2).tour.remove(c2);
			copy.solution.get(tourNum2).tour.add(numC2, c1);
			
			iteration++;
			
			copy.solution.get(tourNum1).updateTotalVisitTime();
			copy.solution.get(tourNum2).updateTotalVisitTime();
			
			possible = copy.solution.get(tourNum1).isFeasible() && copy.solution.get(tourNum2).isFeasible();
		}
		
		
		
		if(!possible){
			copy = this;
		}
		
		return copy;
		
	}
	
	public Solution interShift(Solution sol, int tourNum1, int tourNum2){
		
		Solution copy = this.copySolution();
		
		int iteration = 0;
		boolean possible = false;
		
		while(!possible && iteration <= MAX_ITERATION){
			
			int numC1 = ThreadLocalRandom.current().nextInt(1, copy.solution.get(tourNum1).tour.size()-1);
			
			Client c1 = copy.solution.get(tourNum1).tour.get(numC1);
			
			copy.solution.get(tourNum1).tour.remove(c1);
			copy.solution.get(tourNum2).tour.add(numC1, c1);
			
			iteration++;
			
			possible = copy.solution.get(tourNum2).isFeasible();
		}
		
		if(!possible){
			copy = this;
		}
		
		return copy;
		
	}
	
	public Solution interOpt(Solution sol, int tourNum1, int tourNum2, int kOpt){
		
		Solution copy = this.copySolution();
		
		if(this.solution.get(tourNum1).tour.size()-1-kOpt <= 0 || this.solution.get(tourNum2).tour.size()-1-kOpt <= 0){
			return this;
		}
		
		int iteration = 0;
		boolean possible = false;
		
		while(!possible && iteration <= MAX_ITERATION){
			
			int numC1 = ThreadLocalRandom.current().nextInt(1, copy.solution.get(tourNum1).tour.size()-1-kOpt);
			int numC2 = ThreadLocalRandom.current().nextInt(1, copy.solution.get(tourNum2).tour.size()-1-kOpt);
			
			ArrayList<Client> subTour1 = new ArrayList<Client>();
			ArrayList<Client> subTour2 = new ArrayList<Client>();
			
			for(int i = numC1; i < numC1 + kOpt; i++){
				subTour1.add(copy.solution.get(tourNum1).tour.remove(i));
			}
			
			for(int i = numC2; i < numC2 + kOpt; i++){
				subTour2.add(copy.solution.get(tourNum2).tour.remove(i));
			}
			
			Collections.reverse(subTour1);
			Collections.reverse(subTour2);
			
			copy.solution.get(tourNum1).tour.addAll(numC1, subTour2);
			copy.solution.get(tourNum2).tour.addAll(numC2, subTour1);
			
			iteration++;
			
			possible = copy.solution.get(tourNum1).isFeasible() && copy.solution.get(tourNum2).isFeasible();
		}
		
		if(!possible){
			copy = this;
		}
		
		return copy;
		
	}
	
}

package tcvrp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class Solution {

	private static final int MAX_ITERATION = 30;
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
		c = c + "la distance totalte est donc " + this.distance();
		return c ;
	}
	
	public ArrayList<Client> getHubs(){
		ArrayList<Client> hubs = new ArrayList<Client>();
		
		for(Tour t : this.solution){
			hubs.add(t.hub);
		}
		
		return hubs;
	}
	
	public double distance(){
		double somme = 0 ;
		for(Tour t : this.solution){
			somme = somme + t.distance_Globale();
		}
		return somme;
	}
	
	public Solution copySolution(){
		
		ArrayList<Tour> copy = new ArrayList<Tour>();
		
		for(Tour t : this.solution){
			copy.add(t.copyTour());
		}
		
		Solution copySol = new Solution(copy);
		
		return copySol;
	}
	
	public Solution intraSwap(Solution sol, int tourNum){
		
		Solution copy = this.copySolution();
		
		//copy.solution.get(tourNum).intraSwap();
		
		return copy;
		
	}
	
	public Solution intraShift(Solution sol, int tourNum){
		
		Solution copy = this.copySolution();
		
		//copy.solution.get(tourNum).intraShift();
		
		return copy;
		
	}
	
	public Solution intraOpt(Solution sol, int tourNum, int kOpt){
		
		Solution copy = this.copySolution();
		
		//copy.solution.get(tourNum).intraOpt(kOpt);
		
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
		}
		
		if(!possible){
			copy = this;
		}
		
		return copy;
		
	}
	
}

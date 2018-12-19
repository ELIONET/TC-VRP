package tcvrp.model;

import java.util.ArrayList;

public class Solution {

	public ArrayList<Tour> solution;
	
	public Solution(){
		this.solution = new ArrayList<Tour>();
	}
	
	public ArrayList<Client> getHubs(){
		ArrayList<Client> hubs = new ArrayList<Client>();
		
		for(Tour t : this.solution){
			hubs.add(t.hub);
		}
		
		return hubs;
	}
	
}

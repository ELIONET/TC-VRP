package tcvrp.model;

import java.util.ArrayList;

public class Tour {

	public Client hub;
	public ArrayList<Client> tour;
	
	public Tour(Client hub){
		this.tour = new ArrayList<Client>();
		this.hub = hub;
		this.tour.add(hub);
		this.tour.add(hub);
	}
	
}

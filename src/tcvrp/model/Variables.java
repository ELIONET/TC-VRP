package tcvrp.model;


public class Variables {
	
	// nombre de clients dans les données
	private int nbClients;
	
	// nombre de commerciaux dans les données
	private int nbCommerciaux;
	
	// matrice représentant la variable Xci du programme linéaire (=1 si le commercial c est positionné au niveau du client i, =0 sinon)
	private int[][] variableX;
	
	// matrice représentant la variable Yci du programme linéaire (=1 si le commercial c est affilié au client i, =0 sinon)
	private int[][] variableY;
	
	
	public Variables (int nbClients, int nbCommerciaux) {
		
		this.nbClients = nbClients;
		this.nbCommerciaux = nbCommerciaux;
		
		this.genererVariables();
		
	}
	
	
	// generation totalement aléatoire des variables
	/*private void genererVariables () {
		this.variableX = new int[this.nbCommerciaux][this.nbClients];
		this.variableY = new int[this.nbCommerciaux][this.nbClients];
		for (int commercial = 0; commercial < this.nbCommerciaux; commercial++) {
			this.variableX[commercial][(int)(Math.random()*this.nbClients)] = 1;
		}
		for (int client = 0; client < this.nbClients; client++) {
			this.variableY[(int)(Math.random()*this.nbCommerciaux)][client] = 1;
		}
	}*/

	
	// generation aleatoire des variables avec une limite de nombre de clients par TC
	/*private void genererVariables () {
		this.variableX = new int[this.nbCommerciaux][this.nbClients];
		this.variableY = new int[this.nbCommerciaux][this.nbClients];
		for (int commercial = 0; commercial < this.nbCommerciaux; commercial++) {
			this.variableX[commercial][(int)(Math.random()*this.nbClients)] = 1;
		}
		for (int client = 0; client < this.nbClients; client++) {
			int commercial = (int)(Math.random()*this.nbCommerciaux);
			while (this.getNbClients(commercial) >= 1.2*(this.nbClients/this.nbCommerciaux)) {
				commercial = (int)(Math.random()*this.nbCommerciaux);
			}
			this.variableY[commercial][client] = 1;
		}
	}*/

	
	// generation aleatoire des variables en lissant le nombre de client par TC
	/*private void genererVariables () {
		this.variableX = new int[this.nbCommerciaux][this.nbClients];
		this.variableY = new int[this.nbCommerciaux][this.nbClients];
		for (int commercial = 0; commercial < this.nbCommerciaux; commercial++) {
			this.variableX[commercial][(int)(Math.random()*this.nbClients)] = 1;
		}
		for (int client = 0; client < this.nbClients; client++) {
			int commercial = (int)(Math.random()*this.nbCommerciaux);
			while (this.getNbClients(commercial) >= 1.2*(this.nbClients/this.nbCommerciaux)) {
				commercial = (int)(Math.random()*this.nbCommerciaux);
			}
			this.variableY[commercial][client] = 1;
		}
	}*/

	
	// generation aleatoire des variables avec aucun TC place au niveau du meme client, et TC assigne au client ou il est place
	private void genererVariables () {
		this.variableX = new int[this.nbCommerciaux][this.nbClients];
		this.variableY = new int[this.nbCommerciaux][this.nbClients];
		boolean[] clientAttribue = new boolean[this.nbClients];
		for (int commercial = 0; commercial < this.nbCommerciaux; commercial++) {
			int client = (int)(Math.random()*this.nbClients);
			while (this.getNbCommerciaux(client) == 1) {
				client = (int)(Math.random()*this.nbClients);
			}
			this.variableX[commercial][client] = 1;
			this.variableY[commercial][client] = 1;
			clientAttribue[client] = true;
		}
		for (int client = 0; client < this.nbClients; client++) {
			if (clientAttribue[client] == false) {
				this.variableY[(int)(Math.random()*this.nbCommerciaux)][client] = 1;
			}
		}
	}
	private int getNbCommerciaux (int client) {
		int nbCommerciaux = 0;
		for (int commercial = 0; commercial < this.nbCommerciaux; commercial++) {
			if (this.variableY[commercial][client] == 1) {
				nbCommerciaux++;
			}
		}
		return nbCommerciaux;
	}
	
	
	private void afficherVariables () {
		//System.out.println("VARIABLE Xci :");
		for (int commercial = 0; commercial < this.nbCommerciaux; commercial++) {
			//System.out.print("[ ");
			for (int client = 0; client < this.nbClients; client++) {
				//System.out.print(this.variableX[commercial][client]+" ");
			}
			//System.out.println("]");
		}
		//System.out.println();
		//System.out.println("VARIABLE Yci :");
		for (int commercial = 0; commercial < this.nbCommerciaux; commercial++) {
			//System.out.print("[ ");
			for (int client = 0; client < this.nbClients; client++) {
				//System.out.print(this.variableY[commercial][client]+" ");
			}
			//System.out.println("]");
		}
		//System.out.println();
	}
	
	
	public int getNbCommerciaux () {
		return this.nbCommerciaux;
	}
	
	
	public int getNbClients (int commercial) {
		int nbClients = 0;
		for (int client = 0; client < this.nbClients; client++) {
			if (this.variableY[commercial][client] == 1) {
				nbClients++;
			}
		}
		return nbClients;
	}
	
	
	public int getHub (int commercial) {
		for (int client = 0; client < this.nbClients; client++) {
			if (this.variableX[commercial][client] == 1) {
				return client;
			}
		}
		return -1;
	}
	
	public int getTC (int client){
		for(int commercial = 0; commercial < this.nbCommerciaux; commercial++){
			if(this.variableY[commercial][client] == 1){
				return commercial;
			}
		}
		return -1;
	}
	
	public int getVariableX (int commercial, int client) {
		return this.variableX[commercial][client];
	}
	
	
	public int getVariableY (int commercial, int client) {
		return this.variableY[commercial][client];
	}

}


package batailleNavale.Model.Bateaux;

import java.io.Serializable;

public class Tire implements Serializable {

	private int tireur;
	private int[] PositionCible;
	private int force;


	public int getTireur() {
		return tireur;
	}

	public void setTireur(int tireur) {
		this.tireur = tireur;
	}

	public int[] getPositionCible() {
		return PositionCible;
	}

	public void setPositionCible(int[] positionCible) {
		PositionCible = positionCible;
	}

	public int getForce() {
		return force;
	}

	public void setForce(int force) {
		this.force = force;
	}

	public Tire(int tireur, int force, int[] PositionCible){
		this.force=force;
		this.tireur=tireur;
		this.PositionCible=PositionCible;
	}


}
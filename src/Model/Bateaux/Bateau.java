package Model.Bateaux;

public abstract class Bateau {

	private int position;
	private int nom;
	private int type;
	private int force;
	private int resistance;
	private int projectiles;
	private char etat; // état de la case du bateau : o -> occupée, t-> touchée, d ->detruite











	public void setResistance(int resistance) {
		this.resistance = resistance;
	}

	public int getResistance() {
		return resistance;
	}

}
package batailleNavale.Model.Bateaux;

import batailleNavale.Ressources;

import java.io.Serializable;

public abstract class Bateau implements Serializable {

	protected int[][] position;
	protected String nom;
	protected String epoque;
	protected int type;
	protected int force;
	protected int resistance;
	protected int projectiles;
	protected int [] cas;

	public Bateau(String nom ,String epoque,int type,int force,int resistance){
      this.nom=nom;
      this.epoque=epoque;
      this.type=type;
      this.force=force;
      this.resistance=resistance;
      this.cas=new int[type];
      this.projectiles=type*Ressources.NBPROJECTILEPARCASE;
      for (int i=0;i<cas.length;i++)cas[i]=resistance;
	}
	public int[][] getPosition() {
		return position;
	}

	public void setPosition(int[][] position) {
		this.position = position;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getEpoque() {
		return epoque;
	}

	public void setEpoque(String epoque) {
		this.epoque = epoque;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getForce() {
		return force;
	}

	public void setForce(int force) {
		this.force = force;
	}

	public int getResistance() {
		return resistance;
	}

	public void setResistance(int resistance) {
		this.resistance = resistance;
	}

	public int getProjectiles() {
		return projectiles;
	}

	public void setProjectiles(int projectiles) {
		this.projectiles = projectiles;
	}
	public abstract boolean peutTirer();

	public abstract Tire tirer(int x , int y);

	public abstract void preandFeu(Tire tire);

	public void afficheBateau(){
		System.out.print("[ ");
		for(int i=0;i<cas.length;i++)System.out.print(" |"+cas[i]+"| ");
		System.out.println("]");

	}

	public int[] getCas() {
		return cas;
	}
// retoutner la resistance d'une case se trouvant dans le tableau cas des cases
	public int get_res_Cas(int x,int y) {
        int[][] pos = this.getPosition();
        int ind = 0;
        if (pos[0][1] == pos[1][1]) {
            int minx = pos[0][0];
            if (pos[1][0] < minx) minx = pos[1][0];
            ind = x - minx;
        }
        if (pos[0][0] == pos[1][0]) {
            int miny = pos[0][1];
            if (pos[1][1] < miny) miny = pos[1][1];
            ind = y - miny;
        }
        return cas[ind];
    }


}
package Model.Joueur;

import Model.Bateaux.Bateau;

import java.awt.*;
import java.util.ArrayList;

public class Plateau {
private static Plateau instance ;
	private int ChampBataille;
	Bateau plateau [][];
	private ArrayList<Bateau> bateauEjecte;

	int x,y;

	private Plateau(){

	    plateau = new Bateau[11][11];
        bateauEjecte = new ArrayList<>();

    }
	
	/**
	 * methode factory de plateau
	 * cette methode crée les instances Plateau pour nous.
	 * @return une instance de Plateau.
	 */
	public static Plateau getInstance() {
		if(instance == null)
            instance = new Plateau();
		return instance;
	}


    /**
     * méthode qui vérifie si le bateau est horizontal ou vertival
     **/

private boolean estHV(Point p1, Point p2){
    if(p1.x != p2.y || p1.y != p2.y) return  false;
    return true ;
}

/**
 * méthode qui vérifie si LES POINTS de dépot du bateau débordent pas sur le plateau
 **/

private boolean estDeborde(Point p1, Point p2){
    if(p1.x+p2.x >10 || p1.x+p2.x<0 || p1.y+p2.y <0 ||  p1.y+p2.y >10 ) return  false;
    return true ;
}

/**
 * méthode qui vérifie si un bateau est détruit
 **/

boolean estDetruit(Bateau b){
    if(b.getResistance()==0) return  true;
    return  false;
}

/**
 * méthode qui applique les dégats sur un bateau
 **/
 public void prendTire(Bateau b){
     b.setResistance(b.getResistance()-1);
     //
 }
    /**
     * méthode qui permet de placer les bateau sur le plateau
     **/
 public  void poserBateau(Bateau b) {
    //ccondition a verifier pour pouvoir poser un bateau
 }

    /**
     * méthode qui permet d'enlever un bateau de plateau
     **/
    public  void ejecterBateau(Bateau b) {
        //condition pour enlever le bateau

        bateauEjecte.add(b);

        //rendre les case de bateau inaccessible
    }

}
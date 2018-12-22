package batailleNavale.Model.Joueur;

import batailleNavale.Model.Bateaux.Bateau;

public interface Tirer {

	/**
	 * Cette methode permet de joueur selon une strategie
	 * @param player
	 * @param boat
	 * @return True c'est le tour est toujours a lui
	 * 		   False asinon.
	 */
	boolean tirer(AbstractJoueur player, Bateau boat);

}
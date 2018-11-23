package Model.Joueur;

public class Plateau {

	private int ChampBataille;
	
	/**
	 * methode factory de plateau
	 * cette methode crée les instances Plateau pour nous.
	 * @return une instance de Plateau.
	 */
	public static Plateau getInstance() {
		return new Plateau();
	}
}
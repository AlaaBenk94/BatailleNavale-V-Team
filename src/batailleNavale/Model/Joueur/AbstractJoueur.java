package batailleNavale.Model.Joueur;

import batailleNavale.Model.Bateaux.Bateau;
import batailleNavale.Model.Bateaux.Tire;

import java.util.ArrayList;

import static batailleNavale.Ressources.epoques;

/**
 * Cette classe definie les attributs et le comportement en commun des joueurs de differents type : Homme et machine.
 */
public abstract class AbstractJoueur {

	protected Plateau myField;
	protected String name;
	protected AbstractJoueur next;
	protected ArrayList<Bateau> myBoats;
	protected boolean myTurn;

	/**
	 * Constructeur par default.
	 */
	public AbstractJoueur() {
		name = "Player";
		myBoats = new ArrayList<>();
		next = null;
		/**
		 * TO EDIT
		 */
		myField = Plateau.getInstance(epoques[0]);
	}

	/**
	 * Surchage de constructeur. un seul parametre.
	 * @param Name le Nom de joueur.
	 */
	public AbstractJoueur(String Name, String epoque) {
		this();
		this.name = Name;
        myField = Plateau.getInstance(epoque);
	}

	/**
	 * cette methode passe le tour au successeur
	 */
	public void next() {
		next.jouer();
	}

	/**
	 * cette methode gère le tour de joueur.
	 */
	public abstract void jouer();

	// Setters & Getters

	/**
	 * Getter Next
	 * @return
	 */
	public AbstractJoueur getNext() {
		return next;
	}

	/**
	 * Setter Next
	 * @param next
	 */
	public void setNext(AbstractJoueur next) {
		this.next = next;
	}

	/**
	 * Getter Nom de joueur.
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter Nom de joueur
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter Bateaux
	 * @return
	 */
	public ArrayList<Bateau> getMyBoats() {
		return myBoats;
	}

	/**
	 * Ajout Bateau dans la liste
	 * @param myBoats
	 */
	public void addMyBoats(Bateau myBoats) {
		this.myBoats.add(myBoats);
	}

	/**
	 * Getter mon plateau
	 * @return
	 */
	public Plateau getMyField() {
		return myField;
	}

	/**
	 * Setter mon plateau
	 * @param myField
	 */
	public void setMyField(Plateau myField) {
		this.myField = myField;
	}

	/**
	 * Getter mon tour ?
	 * @return
	 */
	public boolean isMyTurn() {
		return myTurn;
	}

	/**
	 * Setter c'est mon tour
	 * @param myTurn
	 */
	public void setMyTurn(boolean myTurn) {
		this.myTurn = myTurn;
	}

	/**
	 * recuperer le nombre total des projectiles restants
	 * sur tous les bateaux
	 * @return
	 */
	public int nombreProjectilesTotale(){
		int total = 0;
		for (Bateau b : myBoats)
			total += b.getProjectiles();
		return total;
	}
    public abstract boolean attaquer(int xbateu, int ybateu, int ciblex, int cibley);
}
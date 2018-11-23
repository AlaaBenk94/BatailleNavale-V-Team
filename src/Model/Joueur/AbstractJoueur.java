package Model.Joueur;

import Model.Bateaux.Bateau;
import java.util.ArrayList;

/**
 * Cette classe definie les attributs et le comportement en commin des joueurs de differents type : Homme et machine.
 */
public abstract class AbstractJoueur {

	private Plateau myField;
	private Plateau enemyField;
	private String name;
	private AbstractJoueur next;
	private ArrayList<Bateau> myBoats;
	private boolean myTurn;

	/**
	 * Constructeur par default.
	 */
	public AbstractJoueur() {
		name = "Player";
		myBoats = new ArrayList<>();
		next = null;
		myField = Plateau.getInstance();
		enemyField = Plateau.getInstance();
	}

	/**
	 * Surchage de constructeur. un seul parametre.
	 * @param Name le Nom de joueur.
	 */
	public AbstractJoueur(String Name) {
		this();
		this.name = Name;
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

	public AbstractJoueur getNext() {
		return next;
	}

	public void setNext(AbstractJoueur next) {
		this.next = next;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Bateau> getMyBoats() {
		return myBoats;
	}

	public void setMyBoats(ArrayList<Bateau> myBoats) {
		this.myBoats = myBoats;
	}

	public Plateau getMyField() {
		return myField;
	}

	public void setMyField(Plateau myField) {
		this.myField = myField;
	}

	public Plateau getEnemyField() {
		return enemyField;
	}

	public void setEnemyField(Plateau enemyField) {
		this.enemyField = enemyField;
	}

	public boolean isMyTurn() {
		return myTurn;
	}

	public void setMyTurn(boolean myTurn) {
		this.myTurn = myTurn;
	}
}
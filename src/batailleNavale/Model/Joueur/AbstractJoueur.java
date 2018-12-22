package batailleNavale.Model.Joueur;

import batailleNavale.Model.Bateaux.Bateau;
import batailleNavale.Model.Bateaux.Tire;
import batailleNavale.Model.Epoques.Epoque;

import static batailleNavale.Ressources.TireEtats;

import java.util.ArrayList;

/**
 * Cette classe definie les attributs et le comportement en commun des joueurs de differents type : Homme et machine.
 */
public abstract class AbstractJoueur {

	protected Plateau myField;
	protected String name;
	protected AbstractJoueur next;
	protected ArrayList<Bateau> myBoats;
	protected boolean myTurn;
	protected int remainingBoeatToPlace;

	/**
	 * Constructeur par default.
	 */
	public AbstractJoueur() {
		name = "Player";
		myBoats = new ArrayList<>();
		next = null;
	}

	/**
	 * Surchage de constructeur. un seul parametre.
	 * @param Name le Nom de joueur.
	 */
	public AbstractJoueur(String Name, String epoque) {
		this();
		this.name = Name;
        myField = Plateau.getInstance(epoque);
        remainingBoeatToPlace = Epoque.getEpoque(epoque).getBateauxSize().length;
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

	/**
	 * cette methode permet au joueur d'attaquer
	 * @param xbateu
	 * @param ybateu
	 * @param ciblex
	 * @param cibley
	 * @return
	 */
    public abstract boolean attaquer(int xbateu, int ybateu, int ciblex, int cibley);

	/**
	 * cette methode gere les degats d'un tire d'enemie.
	 * @param tire
	 * @return
	 */
	public TireEtats prendreFeu(Tire tire){

	    TireEtats e = myField.prendTire(tire);
        if(e == TireEtats.TBateau)
            updateBoatsList();
        return e;
	}

    /**
     * Cette methode permet d'acctualiser la liste des bateaux
     * et suprimmer les bateaux detruits.
     */
	public void updateBoatsList(){
        for(Bateau b : myBoats){
            if(myField.estDetruit(b))
                myBoats.remove(myBoats);
        }
    }

    /**
	 * verifier si le joueur a perdu.
	 * @return
	 */
	public boolean gameOver(){
		return (nombreProjectilesTotale() == 0 || myBoats.isEmpty());
	}

	/**
	 * verifier si le joueur a gagné.
	 * @return
	 */
	public boolean gameWon(){
		return next.gameOver();
	}

	/**
	 * verifier s'il reste tjrs des Bateaux a placer.
	 * @return
	 */
	public boolean enPlacement(){
		return this.remainingBoeatToPlace == 0;
	}

}
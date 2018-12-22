package batailleNavale.Model.Joueur;

import batailleNavale.Model.Bateaux.Tire;
import batailleNavale.Model.Epoques.Epoque;
import batailleNavale.Ressources;

import java.awt.*;
import java.util.Random;

/**
 * Cette classe represente la machine (adversaire).
 */
public class Machine extends AbstractJoueur {

    private Tirer strategy;

    /**
     * Constructeur
     */
    public Machine(String epoque) {
        super("Machine", epoque);
    }

    /**
     * Surcharger le constructeur avec une strategie de tire
     * @param epoque
     * @param strategy
     */
    public Machine(String epoque,Tirer strategy) {
        this(epoque);
        this.strategy = strategy;
        initializeBoatPosition(epoque);
    }

    /**
     * methode qui place les bateaux de la machine
     * aléatoirement.
     *
     * ======= TO MODIFY =======
     * ======= TO MODIFY =======
     *
     */
    public void initializeBoatPosition(String epoque) {
        for(int t : Epoque.getEpoque(epoque).getBateauxSize()) {
            Point pos1 = getRandomPosition();
            Point pos2 = new Point(pos1);
            pos2.translate(t, 0);
            myField.poserBateau(pos1, pos2, t);
        }
    }

    /**
     * methode qui genere une position dans le plateau aléatoirement.
     * @return
     */
    private Point getRandomPosition(){
        Random rand = new Random();
        return new Point(rand.nextInt(Ressources.Hauteur-1), rand.nextInt(Ressources.Largeur-1));
    }

    @Override
    public void jouer() {

    }

    @Override
    public boolean attaquer(int xbateu, int ybateu, int ciblex, int cibley) {
        return false;
    }


    int [][]getMatriceOrdi(){
        return myField.getTireMatrice();
    }

    /**
     * Getter strategy
     * @return
     */
    public Tirer getStrategy() {
        return strategy;
    }

    /**
     * Setter strategy
     * @param strategy
     */
    public void setStrategy(Tirer strategy) {
        this.strategy = strategy;
    }
}
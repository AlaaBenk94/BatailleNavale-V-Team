package batailleNavale.Model.Joueur;

import batailleNavale.Model.Bateaux.Bateau;
import batailleNavale.Model.Epoques.Epoque;
import batailleNavale.Ressources;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;

import static batailleNavale.Ressources.Hauteur;
import static batailleNavale.Ressources.Largeur;

/**
 * Cette classe represente la machine (adversaire).
 */
public class Machine extends AbstractJoueur implements Serializable {

    private Tirer strategy;

    /**
     * Constructeur
     */
    public Machine(String epoque) {
        super("Machine", epoque);
        initializeBoatPosition(epoque);
        this.strategy = Aleatoire.getInstance();
    }

    /**
     * Surcharger le constructeur avec une strategie de tire
     * @param epoque
     * @param strategy
     */
    public Machine(String epoque,Tirer strategy) {
        this(epoque);
        this.strategy = strategy;
    }

    /**
     * methode qui permet de creer & placer
     * les bateaux de la machine # aléatoirement #.
     * @Param epoque Epoque choisi.
     */
    public void initializeBoatPosition(String epoque) {

        for(int t : Epoque.getEpoque(epoque).getBateauxSize()) {

            Bateau b = null;
            while(b == null) {
                Point[] pos = getRandomPosition(t);
                b =myField.poserBateau(pos[0], pos[1], t);
            }
            myBoats.add(b);
            remainingBoeatToPlace--;
        }

        /**
         * AFFICHAGE PROVISOIRE DE MATRICE DES BATEAUX
         */
        System.out.println("********************************************");
        System.out.println("REMAINING BOATS : " + remainingBoeatToPlace);
        for(Bateau b : myBoats)
            System.out.println(b.getNom() + " : " +
                    "[(" + b.getPosition()[0][0] + "," + b.getPosition()[0][1] + "),(" +
                    b.getPosition()[1][0] + "," + b.getPosition()[1][1] + ")].");
        System.out.println("********************************************");
        myField.affichePlateu();
        System.out.println("********************************************");

    }


    /**
     * methode qui genere une position dans le plateau aléatoirement.
     * @return
     */
    private Point[] getRandomPosition(int t){
        Random rand = new Random();
        Point posX = new Point(rand.nextInt(Hauteur-t), rand.nextInt(Largeur-t));
        Point posY = new Point(posX);
        int k = t-1;
        if(rand.nextBoolean())
            posY.translate(k,0);
        else
            posY.translate(0, k);

        return new Point[]{posX, posY};
    }

    /**
     * Cette methode permet a la machine de Jouer
     * c-a-d choisir des bateaux qui tirent et les position cible
     */
    @Override
    public void jouer() {

        System.out.println("YAY C'EST MON TOUR XD");

        do {
            // choisir le bateaux
            Bateau b = getRandomBoat();

            /**
             * IF b == NULL ==> NO REMAINING MISSILES.
             * THE MACHINE LOSES.
             */
            if (b == null) {
                return;
            }

            // passer a la fonction tirer
            myTurn = strategy.tirer(next, b);

        } while (myTurn);


    }

    /**
     * Permet de recuperer un bateau qui tire
     * Semi - Aléatoirement
     * @return
     */
    private Bateau getRandomBoat(){

        Random r = new Random();
        int size = myBoats.size()-1;
        int index;

        if((nombreProjectilesTotale() == 0))
            return null;

        do {
           index = r.nextInt(size);
        } while(myBoats.get(index).getProjectiles() < 1);

        return myBoats.get(index);
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
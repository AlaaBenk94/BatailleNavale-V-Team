package batailleNavale.Model.Joueur;

import batailleNavale.Model.Bateaux.Bateau;
import batailleNavale.Model.Bateaux.Tire;
import batailleNavale.Ressources;

import java.awt.*;
import java.io.Serializable;

import static batailleNavale.Ressources.TireEtats.TVide;
import static batailleNavale.Ressources.TireEtats.Timposible;

/**
 * Cette class represente le joueur humain.
 */
public class Joueur extends AbstractJoueur implements Serializable {

    public Joueur(String nom, String ep) {
        super(nom, ep);
    }

    /**
     * Cette methode ne fait rien :D
     */
    @Override
    public void jouer() {
        myTurn = true;
    }

    public int [][] getMatriceBateau(){
        return  myField.getBateauMatrice();
    }

    public int [][] getMatriceOrdi(){
        return ((Machine) next).getMatriceOrdi();
    }

    public Bateau monterBateau(Point p1, Point p2, int type){
            Bateau b = myField.poserBateau(p1,p2, type);
            if(b != null) {
                myBoats.add(b);
                remainingBoeatToPlace--;
            }
            return b;
    }

    /**
     * cette methode permet a un joueur d'attaquer
     * @param xbateu
     * @param ybateu
     * @param ciblex
     * @param cibley
     * @return
     */
    @Override
    public boolean attaquer(int xbateu, int ybateu, int ciblex, int cibley){

        Tire tire = myField.tirer(xbateu,ybateu, ciblex, cibley);

        /**
         * TO CHECK LATER
         */
        Ressources.TireEtats etatdetire = next.prendreFeu(tire);

        if(etatdetire == Timposible)
            return false;
        else {
            Bateau b=myField.getBateu(xbateu,ybateu);
            b.setProjectiles(b.getProjectiles()-1);

            /**
             * IF HE MISSED -> THE MACHINE PLAYS
             */
            if(etatdetire == TVide)
                next();

        }
        return true;
    }

}
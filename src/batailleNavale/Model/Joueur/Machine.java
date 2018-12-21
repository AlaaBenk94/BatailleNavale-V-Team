package batailleNavale.Model.Joueur;

import batailleNavale.Model.Bateaux.Tire;

/**
 * Cette classe represente la machine (adversaire).
 */
public class Machine extends AbstractJoueur {

    /**
     * TO DO
     */
    public Machine() {

    }

    /**
     * TO DO
     */
    public void initializeBoatPosition() {

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
}
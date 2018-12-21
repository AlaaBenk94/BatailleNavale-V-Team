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
    public Tire attaquer(int x, int y){
        return myField.tirer(x,y);
    }

    int [][]getMatriceOrdi(){
        return myField.getTireMatrice();
    }
}
package batailleNavale.Model.Joueur;

import batailleNavale.Model.Bateaux.Tire;

/**
 * Cette classe represente la machine (adversaire).
 */
public class Machine extends AbstractJoueur {

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
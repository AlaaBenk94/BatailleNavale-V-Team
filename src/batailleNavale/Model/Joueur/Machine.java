package batailleNavale.Model.Joueur;

import batailleNavale.Ressources;

public class Machine extends AbstractJoueur {
    Plateau p;
    @Override
    public void jouer() {


    }

    int [][]getMatriceOrdi(){
        int matOrdi [][] = new int[Ressources.Hauteur][Ressources.Largeur];
        return matOrdi=p.getTireMatrice();
    }
}
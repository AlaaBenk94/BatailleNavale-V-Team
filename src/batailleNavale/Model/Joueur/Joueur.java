package batailleNavale.Model.Joueur;

import batailleNavale.Model.Bateaux.Bateau;
import batailleNavale.Model.Bateaux.Tire;
import batailleNavale.Ressources;

import java.awt.*;
import java.io.Serializable;

public class Joueur extends AbstractJoueur implements Serializable {
    Plateau p;
    Machine m;
    @Override
    public void jouer() {

    }

    public int [][] getMatriceBateau(){
        int matBateu [][] = new int[Ressources.Hauteur][Ressources.Largeur];
        return  matBateu=p.getBateauMatrice();
    }

    public int [][]getMatriceOrdi(){
        int matOrdi [][] = new int[Ressources.Hauteur][Ressources.Largeur];
        return matOrdi=m.getMatriceOrdi();
    }

public Bateau monterBateau(Point p1, Point p2, int type){
        return p.poserBateau(p1,p2, type);
}
    public Tire attaquer(int x, int y){

            return p.tirer(x,y);

    }

}
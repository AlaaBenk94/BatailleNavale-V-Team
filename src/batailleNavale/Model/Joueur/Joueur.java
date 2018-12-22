package batailleNavale.Model.Joueur;

import batailleNavale.Model.Bateaux.Bateau;
import batailleNavale.Model.Bateaux.Tire;
import batailleNavale.Ressources;

import java.awt.*;
import java.io.Serializable;

/**
 * Cette class represente le joueur humain.
 */
public class Joueur extends AbstractJoueur implements Serializable {

    public Joueur(String nom, String ep) {
        super(nom, ep);
    }

    @Override
    public void jouer() {

    }

    public int [][] getMatriceBateau(){
        return  myField.getBateauMatrice();
    }

    public int [][] getMatriceOrdi(){
        return next.getMyField().getTireMatrice();
    }

    public boolean monterBateau(Point p1, Point p2, int type){
            Bateau b = myField.poserBateau(p1,p2, type);
            if(b != null) {
                myBoats.add(b);
                return true;
            }
            return false;
    }

    @Override
    public boolean attaquer(int xbateu, int ybateu, int ciblex, int cibley){
           Tire tire=myField.tirer(xbateu,ybateu);
           tire.setPositionCible(new int[]{ciblex,cibley});
           Ressources.TireEtats etatdetire =next.getMyField().prendTire(tire);
               if(etatdetire== Ressources.TireEtats.Timposible)return false;
               else {
                   Bateau b=myField.getBateu(xbateu,ybateu);
                   b.setProjectiles(b.getProjectiles()-1);
                 /// gerer les toure

               }
           return true;
    }

}
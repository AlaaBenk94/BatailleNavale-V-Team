package batailleNavale.Model.Joueur;

import batailleNavale.Model.Bateaux.Bateau;
import batailleNavale.Ressources;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static batailleNavale.Ressources.Hauteur;
import static batailleNavale.Ressources.Largeur;
import static batailleNavale.Ressources.TireEtats.TVide;

public class Croise implements Tirer {

    Random rand;
    ArrayList<Point> pointsList;

    public Croise() {
        rand = new Random();
        pointsList = new ArrayList<Point>();
    }

    @Override
    public boolean tirer(AbstractJoueur player, Bateau boat) {
        int x,y;
        Ressources.TireEtats etat;
        x = rand.nextInt(Hauteur - 3);
        y = rand.nextInt(Largeur - 3);
        etat = player.prendreFeu(boat.tirer(x, y));

        if(etat != TVide)
            return true;

        return false;

    }

    private Point getNextCible(){
        if(pointsList.isEmpty())
            initializeCross();
        return pointsList.get(0);
    }

    private void initializeCross(){

    }

    /**
     * cette methode qui crée
     * @return
     */
    public static Croise getInstance(){
        return new Croise();
    }
}
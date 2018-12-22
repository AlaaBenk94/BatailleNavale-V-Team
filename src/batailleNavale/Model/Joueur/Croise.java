package batailleNavale.Model.Joueur;

import batailleNavale.Model.Bateaux.Bateau;
import batailleNavale.Ressources;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static batailleNavale.Ressources.Hauteur;
import static batailleNavale.Ressources.Largeur;
import static batailleNavale.Ressources.TireEtats.TVide;

public class Croise implements Tirer {

    Random rand;
    ArrayList<Point> PointsList;

    public Croise() {
        rand = new Random();
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

    private void initializeCross(){

    }

    public static Croise getInstance(){
        return new Croise();
    }
}
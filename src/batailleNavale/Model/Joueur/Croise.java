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

    /**
     * Constructeur
     */
    public Croise() {
        rand = new Random();
        pointsList = new ArrayList<Point>();
    }

    @Override
    public boolean tirer(AbstractJoueur player, Bateau boat) {
        Ressources.TireEtats etat;
        Point cible = getNextCible();
        etat = player.prendreFeu(boat.tirer(cible.x, cible.y));

        if(etat != TVide)
            return true;

        return false;

    }

    /**
     * cette methode permet de retourner la case suivante
     * @return
     */
    private Point getNextCible(){
        if(pointsList.isEmpty())
            initializeCross();
        return pointsList.remove(0);
    }

    /**
     * cette methode permet de creer le Cross
     */
    private void initializeCross(){
        Point s = new Point(rand.nextInt(Hauteur - 3), rand.nextInt(Largeur - 3));
        pointsList.add(new Point(s));
        s.translate(1, 1); pointsList.add(new Point(s));
        s.translate(1, 1); pointsList.add(new Point(s));
        s.translate(-2, 0); pointsList.add(new Point(s));
        s.translate(2, -2); pointsList.add(new Point(s));
    }

    /**
     * cette methode qui crée
     * @return
     */
    public static Croise getInstance(){
        return new Croise();
    }
}
package batailleNavale.Model.Joueur;

import java.io.Serializable;
import java.util.Random;

import batailleNavale.Model.Bateaux.Bateau;
import static batailleNavale.Ressources.Hauteur;
import static batailleNavale.Ressources.Largeur;
import static batailleNavale.Ressources.TireEtats;
import static batailleNavale.Ressources.TireEtats.TVide;
import static batailleNavale.Ressources.TireEtats.Timposible;

public class Aleatoire implements Tirer, Serializable {

    Random rand;

    public Aleatoire() {
        rand = new Random();
    }

    @Override
    public boolean tirer(AbstractJoueur player, Bateau boat) {
        int x,y;
        TireEtats etat;
        x = rand.nextInt(Hauteur - 1);
        y = rand.nextInt(Largeur - 1);
        etat = player.prendreFeu(boat.tirer(x, y));

        if(etat != TVide)
            return true;

        return false;

    }

    public static Aleatoire getInstance(){
        return new Aleatoire();
    }
}
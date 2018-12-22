package batailleNavale.Model.Joueur;

import batailleNavale.Model.Bateaux.Bateau;

public class Croise implements Tirer {

    @Override
    public boolean tirer(AbstractJoueur player, Bateau boat) {
        return false;
    }
}
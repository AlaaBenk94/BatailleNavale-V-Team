package batailleNavale;

import batailleNavale.Controleur.Controleur;
import batailleNavale.Model.jeu.Jeu;
import batailleNavale.Vues.FenetreJeu;

public class BatailleNavale {

    public static void main(String[] args){

        Jeu j = new Jeu();
        Controleur c = new Controleur(j);
        FenetreJeu f = new FenetreJeu(j,c);
        f.setVisible(true);

    }

}

package batailleNavale.Controleur;

import batailleNavale.Model.jeu.Jeu;

public class Controleur {
    private Jeu modele;
    public Controleur(Jeu modele){
        this.modele=modele;

    }
    public void nouvellepartie(String nom ,String epoque){
        modele.nouvellepartie(nom, epoque);
    }
    public void chargerpartie(String lien){
        System.out.print(lien);
    }
    public void sauvgarder(String lien){
        System.out.print(lien);
    }
    public void ajouter_le_Bateu_select(int[][] pos,String type_bat){
        modele.ajouter_le_Bateu_select(pos,type_bat);
    }
    public void selecinner_cas_bateu_qui_tire(int x,int y){
        modele.selecinner_cas_bateu_qui_tire(x, y);
    }
    public void tirer_cas(int x ,int y){
        modele.tirer_cas(x, y);
    }
}
package batailleNavale.Model.jeu;


import batailleNavale.Controleur.Controleur;
import batailleNavale.DaoSauvegarde.UsineSaveLoad;
import batailleNavale.Model.Epoques.Epoque;
import batailleNavale.Model.Joueur.Joueur;
import batailleNavale.Model.Joueur.Plateau;
import batailleNavale.Ressources;
import batailleNavale.Vues.FenetreJeu;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;


public class Jeu extends Observable implements Serializable {
    String nompartie;
    Epoque epoque;
    Ressources.Etats etat=Ressources.Etats.Menu;
    Joueur joueur;
    Plateau p;

    public void nouvellepartie(String nom, String epoque){
        System.out.println("new partie"+nom+" epoque "+epoque);
        nompartie=nom;
        this.epoque=Epoque.getEpoque(epoque);
        etat=Ressources.Etats.Placement;
        setChanged();
        notifyObservers();
    }



    public int[][]getBateauMatrice(){

        int mat [][] = new int[Ressources.Hauteur][Ressources.Largeur];
        mat=joueur.getMatriceBateau();
        return mat;
    }
    public int[][]getTireMatrice(){
        int mat [][] = new int[Ressources.Hauteur][Ressources.Largeur];
        mat=joueur.getMatriceBateau();
        return mat;

    }

    public String getinfobateucase(int x,int y){

         return p.getinfobateucase(x,y) ;
    }

    public void ajouter_le_Bateu_select(int[][] pos,String type_bat){
        etat= Ressources.Etats.Selection;
        Point p1 = new Point(pos[0][0],pos[0][1]);
        Point p2 = new Point(pos[1][0],pos[1][1]);
        int typeBateua = Integer.valueOf(type_bat);
        joueur.monterBateau(p1,p2,typeBateua);
        setChanged();
        notifyObservers();
        System.out.println("bateu selectionner monte");
    }
    public void selecinner_cas_bateu_qui_tire(int x, int y){
        System.out.println("select "+x+","+y);
        etat=Ressources.Etats.Tire;
        if(!p.estNoyer(x,y)) {
            setChanged();
            notifyObservers();
            System.out.println("selection reussite !");
        }
        System.out.println("erreur de selection  : case noyee");
    }
    public void tirer_cas(int x, int y){
        System.out.println("tire "+x+","+y);

        if(joueur.attaquer(x,y)!=null) {
            setChanged();
            notifyObservers();
            etat= Ressources.Etats.Selection;
            System.out.println("attaque reussie");
        }
        else {
            etat = Ressources.Etats.Tire;
            System.out.println("attaque pas reussie");
            System.out.println("rester dans l'etat"+etat);
        }

    }

    ///////////////////////////////////////////////////////////

    public Epoque getEpoque() {
        return epoque;
    }

    public Map<String, String> getBateuDescreption() {
        return epoque.getBateuDescreption();
    }


    public String getNompartie() {
        return nompartie;
    }
    public Ressources.Etats getetat(){
        return etat;
    }
    public String[] getEpoqes(){
        return Ressources.epoques;
    }
    public String[] getBateuTypes(){ return epoque.getBateauType(); }
    public void notify_views(){
        setChanged();
        notifyObservers();
    }

    public void chargerpartie(String lien,FenetreJeu fenetre){
        Jeu partiecharger=UsineSaveLoad.getUsineSaveLoad("ser").charger(lien);
        Ressources.Etats etatp = partiecharger.getetat();
        if(partiecharger!=null){
            fenetre.close();
            Controleur c=new Controleur(partiecharger);
            fenetre=new FenetreJeu(partiecharger,c);
            fenetre.setVisible(true);
            partiecharger.setetat(etatp);
            notify_views();
        }

    }
    public void sauvgarder(String lien){
        Ressources.Etats etatr=etat;
        UsineSaveLoad.getUsineSaveLoad("ser").sauvegarder(this,lien);
        etat=etatr;
    }

    public void setetat(Ressources.Etats etat){
        this.etat=etat;
        setChanged();
        notifyObservers();
    }


}
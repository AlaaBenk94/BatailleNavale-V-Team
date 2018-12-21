package batailleNavale.Model.jeu;


import batailleNavale.Controleur.Controleur;
import batailleNavale.DaoSauvegarde.UsineSaveLoad;
import batailleNavale.Model.Epoques.Epoque;
import batailleNavale.Model.Joueur.AbstractJoueur;
import batailleNavale.Model.Joueur.Joueur;
import batailleNavale.Model.Joueur.Machine;
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
    AbstractJoueur[] joueurs;
    private int[]pos_Tireur;
    /**
     * constructeur du jeu.
     */
    public Jeu(){
        joueurs = new AbstractJoueur[2];
        joueurs[0] = new Joueur();
        joueurs[1] = new Machine();
        joueurs[0].setNext(joueurs[1]);
    }

    /**
     * methode qui créé une nouvelle partie
     * @param nom
     * @param epoque
     */
    public void nouvellepartie(String nom, String epoque){
        System.out.println("new partie" + nom + " epoque " + epoque);
        nompartie=nom;
        this.epoque=Epoque.getEpoque(epoque);
        etat=Ressources.Etats.Placement;
        setChanged();
        notifyObservers();
    }


    /**
     * recuperer la matrice des bateaux
     * @return
     */
    public int[][]getBateauMatrice(){
        return ((Joueur) joueurs[0]).getMatriceBateau();
    }

    /**
     * recuperer la matrice des tires
     * @return
     */
    public int[][]getTireMatrice(){
        return ((Joueur) joueurs[0]).getMatriceOrdi();
    }

    /**
     * recuperer les infos de la case (x,y)
     * @param x
     * @param y
     * @return
     */
    public String getinfobateucase(int x,int y){
        return ((Joueur) joueurs[0]).getMyField().getinfobateucase(x,y) ;
    }

    /**
     * placer le bateau choisi sur le plateau a la position choisie.
     * @param pos
     * @param type_bat
     */
    public void ajouter_le_Bateu_select(int[][] pos,String type_bat){
        etat= Ressources.Etats.Selection;
        Point p1 = new Point(pos[0][1],pos[0][0]);
        Point p2 = new Point(pos[1][1],pos[1][0]);
        int typeBateua = epoque.getBateauTaille(type_bat);
        ((Joueur) joueurs[0]).monterBateau(p1,p2,typeBateua);
        setChanged();
        notifyObservers();
        System.out.println("bateu selectionner monte");
    }

    /**
     * selection de bateau qui effectue le tire
     * @param x
     * @param y
     */
    public void selecinner_cas_bateu_qui_tire(int x, int y){
        System.out.println("select "+x+","+y);
        joueurs[0].getMyField().tirer(x,y);
        if(((Joueur) joueurs[0]).getMyField().peutTirer(x,y)){
            etat=Ressources.Etats.Tire;
            pos_Tireur=new int[]{x,y};
            setChanged();
            notifyObservers();
        }
    }

    /**
     * methode de tire
     * @param x coordonnée
     * @param y coordonnée
     */
    public void tirer_cas(int x, int y){
        System.out.println("tire "+x+","+y);

        if(((Joueur) joueurs[0]).attaquer(pos_Tireur[0],pos_Tireur[1],x,y)) {
            etat= Ressources.Etats.Selection;
            setChanged();
            notifyObservers();
            System.out.println("attaque reussie");
        }
        else {
            System.out.println("attaque pas reussie");
            System.out.println("rester dans l'etat"+etat);
        }

    }

    /**
     * Getter Epoque
     * @return
     */
    public Epoque getEpoque() {
        return epoque;
    }

    /**
     * Getter description des bateaux disponibles
     * @return
     */
    public Map<String, String> getBateuDescreption() {
        return epoque.getBateuDescreption();
    }

    /**
     * Getter Nom de la partie
     * @return
     */
    public String getNompartie() {
        return nompartie;
    }

    /**
     * Getter Etat de jeu
     * @return
     */
    public Ressources.Etats getetat(){
        return etat;
    }

    /**
     * recuperer la liste des epoques
     * @return
     */
    public String[] getEpoqes(){
        return Ressources.epoques;
    }

    /**
     * La liste des bateaux disponible
     * @return
     */
    public String[] getBateuTypes(){ return epoque.getBateauType(); }
    public void notify_views(){
        setChanged();
        notifyObservers();
    }

    /**
     * methode de chargement de la partie
     * @param lien chemin de fichier de sauvegarde
     * @param fenetre la fenetre graphique
     */
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

    /**
     * methode de sauvegarde de partie.
     * @param lien repertoire de sauvegarde
     */
    public void sauvgarder(String lien){
        Ressources.Etats etatr=etat;
        UsineSaveLoad.getUsineSaveLoad("ser").sauvegarder(this,lien);
        etat=etatr;
    }

    /**
     * Setter d'etat de jeu.
     * @param etat
     */
    public void setetat(Ressources.Etats etat){
        this.etat=etat;
        setChanged();
        notifyObservers();
    }


}
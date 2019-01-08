package batailleNavale.Model.jeu;


import batailleNavale.Controleur.Controleur;
import batailleNavale.DaoSauvegarde.UsineSaveLoad;
import batailleNavale.Model.Epoques.Epoque;
import batailleNavale.Model.Joueur.AbstractJoueur;
import batailleNavale.Model.Joueur.Joueur;
import batailleNavale.Model.Joueur.Machine;
import batailleNavale.Ressources;
import batailleNavale.Vues.FenetreJeu;

import java.awt.*;
import java.io.Serializable;
import java.util.*;


public class Jeu extends Observable implements Serializable {
    String nompartie;
    Epoque epoque;
    Ressources.Etats etat=Ressources.Etats.Menu;
    Ressources.Etats etatp;
    AbstractJoueur[] joueurs;
    private int[]pos_Tireur;
    private int[] bateuxplacer;
    /**
     * constructeur du jeu.
     */
    public Jeu(){
        joueurs = new AbstractJoueur[2];
    }

    /**
     * creation des joueurs
     */
    public void creerJoueurs(String nom, String ep){
        joueurs[0] = new Joueur(nom, ep);
        joueurs[1] = new Machine(ep);
        joueurs[0].setNext(joueurs[1]);
        joueurs[1].setNext(joueurs[0]);
    }

    /**
     * methode qui cree une nouvelle partie
     * @param nom
     * @param epoque
     */
    public void nouvellepartie(String nom, String epoque){
        nompartie=nom;
        this.epoque=Epoque.getEpoque(epoque);

        etat=Ressources.Etats.Placement;
        creerJoueurs(nom, epoque);
        initaliserBateuxPlacer();
        notify_views();
    }

    private void initaliserBateuxPlacer(){
        int nbbateu=this.epoque.getBateauType().length;
        bateuxplacer=new int[nbbateu];
        for(int i=0;i<bateuxplacer.length;i++){
            bateuxplacer[i]=Ressources.NBBATEUDECHAQUETYPE;
        }
    }
    private int indBateu(String type){
        int ind=0;
        String[] bat = epoque.getBateauType();
        for(int i=0;i<bat.length;i++){
            if (bat[i].equals(type))ind=i;
        }
        return ind;
    }
    private boolean peutPlacer(String type){
        int ind=indBateu(type);
        return bateuxplacer[ind]>0;
    }
    private boolean toutsPlacer(){
        for(int i=0 ;i<bateuxplacer.length;i++){
            if(bateuxplacer[i]>0)return false;
        }
        return true;
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
        if(peutPlacer(type_bat)) {
            Point p1 = new Point(pos[0][1], pos[0][0]);
            Point p2 = new Point(pos[1][1], pos[1][0]);
            int typeBateua = epoque.getBateauTaille(type_bat);
            boolean bienmonter = ((Joueur) joueurs[0]).monterBateau(p1, p2, typeBateua);
            if (bienmonter) {
                bateuxplacer[indBateu(type_bat)]--;
            }
        }

        if(toutsPlacer()) {

            etat = Ressources.Etats.Selection;
        }
        setChanged();
        notifyObservers();

    }

    /**
     * selection de bateau qui effectue le tire
     * @param x
     * @param y
     */
    public void selecinner_cas_bateu_qui_tire(int x, int y){
        joueurs[0].getMyField().tirer(x,y);
        if(((Joueur) joueurs[0]).getMyField().peutTirer(x,y)){

            etat=Ressources.Etats.Tire;
            pos_Tireur=new int[]{x,y};
            notify_views();
        }
    }

    /**
     * methode de tire
     * @param x coordonnee
     * @param y coordonnee
     */
    public void tirer_cas(int x, int y){

         if(((Joueur) joueurs[0]).attaquer(pos_Tireur[0],pos_Tireur[1],x,y)) {

            etat= Ressources.Etats.Selection;
            System.out.println(etat);

        }
        if(((Joueur) joueurs[0]).gameOver()){

            etat=Ressources.Etats.Gameover;

            System.out.println(etat);
        }
        else if(((Joueur) joueurs[0]).gameWon()){

            etat=Ressources.Etats.Win;

            System.out.println(etat);
        }

        notify_views();
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
    public String[] getBateuTypes(){
        String[]battype= epoque.getBateauType();
        ArrayList<String> batrest=new ArrayList<>();
        for(int i=0;i<battype.length;i++){
            if(bateuxplacer[indBateu(battype[i])]>0)
                batrest.add(battype[i]);
        }
        return batrest.toArray(new String[batrest.size()]);
    }


    /*

    ** notify if game over or won
     */

    public boolean estGameOW(){

        if(((Joueur) joueurs[0]).gameOver()) {
            etat=Ressources.Etats.Gameover;
            return true;
        }
        else if(((Joueur) joueurs[0]).gameWon()){
            etat=Ressources.Etats.Win;
            return true;
        }

        return false;
    }
    /*
    ** methode notifier
     */

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
        Ressources.Etats etatp = partiecharger.getetatp();
        if(partiecharger!=null){
            fenetre.close();
            Controleur c=new Controleur(partiecharger);
            fenetre=new FenetreJeu(partiecharger,c);
            fenetre.setetat(etatp);
            fenetre.setVisible(true);
            partiecharger.setetat(etatp);
            partiecharger.notify_views();
        }

    }

    /**
     * methode de sauvegarde de partie.
     * @param lien repertoire de sauvegarde
     */
    public void sauvgarder(String lien){
        Ressources.Etats etatr=etat;
        etat=etatp;
        UsineSaveLoad.getUsineSaveLoad("ser").sauvegarder(this,lien);
        etat=etatr;
    }

    /**
     * Setter d'etat de jeu.
     * @param etat
     */
    public void setetat(Ressources.Etats etat){
        this.etat=etat;
    }

    public void setetatp(Ressources.Etats etatp) {
        this.etatp=etatp;
    }
    private Ressources.Etats getetatp(){
        return this.etatp;
    }
}
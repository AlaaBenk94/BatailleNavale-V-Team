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
     * methode qui créé une nouvelle partie
     * @param nom
     * @param epoque
     */
    public void nouvellepartie(String nom, String epoque){
        System.out.println("new partie" + nom + " epoque " + epoque);
        nompartie=nom;
        this.epoque=Epoque.getEpoque(epoque);
        etat=Ressources.Etats.Placement;
        creerJoueurs(nom, epoque);
        initaliserBateuxPlacer();
        setChanged();
        notifyObservers();
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

        if(toutsPlacer())
            etat= Ressources.Etats.Selection;
        setChanged();
        notifyObservers();

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
        if(((Joueur) joueurs[0]).attaquer(pos_Tireur[0],pos_Tireur[1],x,y)) {
            etat= Ressources.Etats.Selection;
        }

        if(joueurs[0].gameOver())
            System.out.println("GAME OVER !!");
        else if(joueurs[0].gameWon())
            System.out.println("GAME WON !!");

        System.out.println("**********************************************");
        System.out.println("Machine Boats : ");
        System.out.println("**********************************************");

        setChanged();
        notifyObservers();
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

    public void affm(int[][] t){
        for(int i =0 ; i<t.length;i++) {
            System.out.println();
            for (int j = 0; j < t.length; j++)
                System.out.print(" " + t[i][j] + " ");
        }
    }

}
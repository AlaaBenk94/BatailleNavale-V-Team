package batailleNavale.Model.jeu;


import batailleNavale.Controleur.Controleur;
import batailleNavale.DaoSauvegarde.UsineSaveLoad;
import batailleNavale.Model.Epoques.Epoque;
import batailleNavale.Ressources;
import batailleNavale.Vues.FenetreJeu;

import javax.swing.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;


public class Jeu extends Observable implements Serializable {
    String nompartie;
    Epoque epoque;
    Ressources.Etats etat=Ressources.Etats.Menu;


    public void nouvellepartie(String nom, String epoque){
        System.out.println("new partie"+nom+" epoque "+epoque);
        nompartie=nom;
        this.epoque=Epoque.getEpoque(epoque);
        etat=Ressources.Etats.Placement;
        setChanged();
        notifyObservers();
    }
    public int[][]getBateauMatrice(){
        int[][] mat = new int[10][10];
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                mat[i][j]=0;
                if(j==5){
                    if(i<5){
                        for(int k=0;k<i;k++)mat[i][k]=i;
                    }
                }
            }
        }
        return mat;
    }
    public int[][]getTireMatrice(){
        int[][] mat = new int[10][10];
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                mat[i][j]=0;
                if(j==5){
                    if(i<5){
                        for(int k=0;k<i;k++)mat[i][k]=1;
                    }
                }
            }
        }
        return mat;
    }
    private String sep="<br>";
    public String getinfobateucase(int x,int y){
        if(x>5&&y>5)
            return "<html> bat1nom  "+sep+" rest 20 projectile"+sep+"resistence 50 </html>";
        else return "vide";
    }

    public void ajouter_le_Bateu_select(int[][] pos,String type_bat){
        etat= Ressources.Etats.Selection;
        setChanged();
        notifyObservers();
        System.out.println("placer le bateu selectionner");
    }
    public void selecinner_cas_bateu_qui_tire(int x, int y){
        System.out.println("select "+x+","+y);
        etat=Ressources.Etats.Tire;
        setChanged();
        notifyObservers();
    }
    public void tirer_cas(int x, int y){
        System.out.println("tire "+x+","+y);
        etat= Ressources.Etats.Selection;
        setChanged();
        notifyObservers();
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
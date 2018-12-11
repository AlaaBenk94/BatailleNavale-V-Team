package batailleNavale.Model.jeu;


import batailleNavale.Model.Epoques.Epoque;
import batailleNavale.Ressources;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;


public class Jeu extends Observable {
    Epoque epoque;
    Ressources.Etats etat=Ressources.Etats.Menu;
    LinkedList<Integer[]> cas_selectionner=new LinkedList<>() ;

    public void setetat(Ressources.Etats etat){
        this.etat=etat;
        setChanged();
        notifyObservers();
    }

    public LinkedList<Integer[]> getCas_selectionner() {
        return cas_selectionner;
    }
    public Ressources.Etats getetat(){
        return etat;
    }
    public String[] getEpoqes(){
        return Ressources.epoques;
    }
    public String[] getBateuTypes(){
        // System.out.println("eep null "+epoque==null);
        return epoque.getBateauType();
    }

    public void nouvellepartie(String nom, String epoque){
        System.out.println("new partie"+nom+" epoque "+epoque);
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
    public Map<String, String> getBateuDescreption() {
        return epoque.getBateuDescreption();
    }

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

    public Epoque getEpoque() {
        return epoque;
    }

}
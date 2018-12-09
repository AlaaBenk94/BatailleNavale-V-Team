package batailleNavale.Model.jeu;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;


public class Jeu extends Observable {
    int etat=0;
    LinkedList<Integer[]> cas_selectionner=new LinkedList<>() ;
    public void setetat(int etat){
        this.etat=etat;
        setChanged();
        notifyObservers();
    }

    public LinkedList<Integer[]> getCas_selectionner() {
        return cas_selectionner;
    }
    public int getetat(){
        return etat;
    }
    public String[] getEpoqes(){
        return new String[]{"ep1","ep2","ep3"};
    }
    public String[] getBateuTypes(){
        return new String[]{"bat1","bat2","bat3","bat4","bat5"};
    }


    public void nouvellepartie(String nom, String epoque){
        System.out.println("new partie"+nom+" epoque "+epoque);
        etat=1;
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
        Map p=new HashMap();
        p.put("bat1","<html> bat1nom  "+sep+"  eppoque ep1  "+sep+" nb projectile 20 "+sep+" resistence 50 </html>");
        p.put("bat2","<html> bat2nom"+sep+"eppoque ep2 "+sep+" nb projectile 40  "+sep+" resistence 100 </html>");
        return p;
    }

    public String getinfobateucase(int x,int y){
        if(x>5&&y>5)
            return "<html> bat1nom  "+sep+" rest 20 projectile"+sep+"resistence 50 </html>";
        else return "vide";
    }

    //select la 1ere et la 2eme case d'un bateu a placer
    public void selecinnerunecase(int x, int y, String type_bat){
        if(true){///verification si on peut selecionner la case x,y
            System.out.println("select a placer "+x+","+y);
            Integer[] p=new Integer[2];
            p[0]=x;p[1]=y;
            cas_selectionner.add(p);
        }
        //mat i,j =0 case selectionner
        setChanged();
        notifyObservers();

    }

    public void ajouter_le_Bateu_select(){
        etat=2;
        setChanged();
        notifyObservers();
        System.out.println("placer le bateu selectionner");
    }
    public void selecinner_cas_bateu_qui_tire(int x, int y){
        System.out.println("select "+x+","+y);
        etat=3;
        setChanged();
        notifyObservers();
    }
    public void tirer_cas(int x, int y){
        System.out.println("tire "+x+","+y);
        etat=2;
        setChanged();
        notifyObservers();
    }

}
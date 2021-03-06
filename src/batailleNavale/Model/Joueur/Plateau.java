package batailleNavale.Model.Joueur;




import batailleNavale.Model.Bateaux.Bateau;
import batailleNavale.Model.Bateaux.BateauEP1;
import batailleNavale.Model.Bateaux.Tire;
import batailleNavale.Model.Epoques.Epoque;
import batailleNavale.Model.Epoques.Epoque1;
import batailleNavale.Ressources;

import javax.tools.ToolProvider;
import java.awt.*;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class Plateau implements Serializable {

    private int ChampBataille;
    String epoque;
    Bateau plateau [][];
    boolean plateau2 [][]; // matrice sur laquelle on verifie si la case est noyer ou pas (si elle est completement brulee)

    private boolean tirePossible; // pour savoir si on peu choisir une case pour tirer ou non

    /**
     * Constructeur
     * @param epoque
     */
    public Plateau(String epoque){
        this.epoque =epoque;
        plateau = new Bateau[Ressources.Hauteur][Ressources.Largeur];
        plateau2 = new boolean[Ressources.Hauteur][Ressources.Largeur];


        for(int i=0;i<Ressources.Hauteur;i++)
            for(int j=0;j<Ressources.Largeur;j++) {
                plateau[i][j] = null;
                plateau2[i][j]=true;
            }
        tirePossible = true;
    }

    /**
     * methode factory de plateau
     * cette methode cree les instances Plateau pour nous.
     * @return une instance de Plateau.
     */
    public static Plateau getInstance(String epoque) {
 return new Plateau(epoque);
    }


    /**
     * methode qui verifie si le bateau est horizontal ou vertival
     **/

    private boolean estHV(Point p1, Point p2){
        if(p1.x != p2.x || p1.y != p2.y) return  false;
        return true ;
    }

    /**
     * methode qui verifie si LES POINTS de depot du bateau debordent pas sur le plateau
     **/

    private boolean estDeborde(Point p1, Point p2){
        return !(p1.x<0 || p1.y<0 || p2.x<0 || p2.y<0 || p1.x< Ressources.Hauteur || p1.y<Ressources.Largeur || p2.x< Ressources.Hauteur || p2.y<Ressources.Largeur  );
    }



    /**
     * methode qui verifie si un bateau est detruit
     **/

    boolean estDetruit(Bateau b){
        for(int i =0;i<b.getType();i++)
            if(b.getCas()[i] > 0)
                return false;
        return  true;
    }

    /**
     * methode qui verifie si un bateau est detruit
     **/
    boolean estOccupe(Point p1, Point p2,int type){
        int i,j,mini=p1.x,minj=p1.y;
        if(p1.x>p2.x) mini=p2.x;
        if(p1.y>p2.y) minj=p2.y;
        if(p1.x==p2.x)
            for(j=minj; j<minj+type;j++)
                if(plateau[mini][j]!=null) return true;

                else if(p1.y==p2.y)
                    for(i=mini;i<mini+type;i++)
                        if(plateau[i][minj]!=null) return true;
        return false;
    }

    /**
     ** verifier si la case du bateau est toujours pas noyer
     ** ce qui revien a verifier qu'on peut la choisir pour effectuer un tire
     */
    public boolean estNoyer(int x, int y){
        if((plateau [x][y]==null && plateau2[x][y]==false) || (plateau [x][y]!=null && plateau [x][y].get_res_Cas(x,y)<=0) ) return  true;
        return false;
    }

    /**
    ** fonction qui verifie l'etat de la case sur le plateau (case bateau ou case mer)
    ** etat=-1 lorsque la case et une case mer brulee ou noyee et etat=0 si la case mer est true
     */
    public int getResisteceCase(int x, int y){
        Bateau bateau =plateau[x][y];
        if(bateau==null){
            if(!plateau2[x][y])return Ressources.casedesbateuau[1];
            else return Ressources.casedesbateuau[0];
        }else{
           return Ressources.casedesbateuau[bateau.get_res_Cas(x,y)];
        }
    }

    /**
     ** recuperer les information de bateau
     */
    private String sep="<br>";
    public String getinfobateucase(int x,int y){
        Bateau bateau =plateau[x][y];
        if(bateau==null)return "vide";
        String batNom = bateau.getNom();
        String nbProjectile = String.valueOf(bateau.getProjectiles());
        String resistance = String.valueOf(bateau.get_res_Cas(x,y));

        if(!estNoyer(x,y) && bateau!=null)
            return "<html> batNom  : "+ batNom + sep + sep
                    + " nbProjectile : " + nbProjectile + sep +sep
                    + "resistance : " +resistance + "</html>";
        else return "vide";
    }

    /**
     * Recuperer l'etat de la case (x,y)
     * @param x
     * @param y
     * @return
     */
    public int getEtatCase(int x, int y){
        Bateau bateau =plateau[x][y];
        if(bateau==null){
            if(plateau2[x][y])return Ressources.casedesTire[0];
            else return Ressources.casedesTire[2];
        }else{
            if(bateau.get_res_Cas(x,y)<=0){
                return Ressources.casedesTire[2];
            }
            if(bateau.get_res_Cas(x,y)<bateau.getResistance()){
                return Ressources.casedesTire[1];
            }
            return Ressources.casedesTire[0];
        }
    }

    /**
    ** matrice d'etat des cases du plateau du joueur
     */
    public int[][]getBateauMatrice() {
        int[][] mat = new int[Ressources.Largeur][Ressources.Hauteur];
        for (int i = 0; i < Ressources.Hauteur; i++) {
            for (int j = 0; j < Ressources.Largeur; j++) {
                mat[i][j] = getResisteceCase(j,i);
            }
        }
            return mat;
    }

    /**
    ** matrice d'etat des cases du joueur adversaire
     */
    public int[][]getTireMatrice(){
        int[][] mat = new int[Ressources.Hauteur][Ressources.Largeur];
        for(int i=0;i<Ressources.Hauteur;i++){
            for(int j=0;j<Ressources.Largeur;j++){
                mat[i][j]=getEtatCase(j,i);
            }
        }
        return mat;
    }


    /**
     * methode qui applique les degats sur un bateau
     * dans l'attribut tire on trouvera combien la case de coordonnees x et y lui reste pour prendre des tires
     * dans le cas ou la case une case mere, pour la difirencier de la cae bateau ou la mets a false pour interdir de tirer une autre fois sur cette case
     **/
    public Ressources.TireEtats prendTire(Tire tire){
        int x = tire.getPositionCible()[0];
        int y = tire.getPositionCible()[1];
        // system.out.println(estNoyer(x,y));
        if(!estNoyer(x, y)) {
            if(plateau[x][y]==null){
                plateau2[x][y] = false;
                return Ressources.TireEtats.TVide;
            }else {
                plateau[x][y].preandFeu(tire);
                return Ressources.TireEtats.TBateau;
            }
        }
        return Ressources.TireEtats.Timposible;
    }

    /**
     * fonction qui permet d'effectuer un tire sur un bateau
     **/


    public Tire tirer(int x, int y){
        if(!estNoyer(x,y)&&plateau[x][y]!=null){
         return plateau[x][y].tirer(x,y);
        }
        return null;
    }

    /**
     * Surcharge de la fonction fonction qui permet d'effectuer un tire sur un bateau
     **/
    public Tire tirer(int x, int y, int cibleX, int cibleY){
        if(!estNoyer(x,y)&&plateau[x][y]!=null){
            return plateau[x][y].tirer(cibleX,cibleY);
        }
        return null;
    }

    public boolean peutTirer(int x, int y) {
        if (!estNoyer(x, y) && plateau[x][y] != null) {
            return plateau[x][y].peutTirer();
        }
        return false;
    }
    /**
     * methode qui permet de placer les bateau sur le plateau et de les creer aussi
     **/


    public Bateau poserBateau(Point p1 , Point p2, int type) {
        //ccondition a verifier pour pouvoir poser un bateau
        int i,j,mini,minj;

        mini=p1.x;
        if(p1.x>p2.x) mini=p2.x;

        minj=p1.y;
        if(p1.y>p2.y) minj=p2.y;


        if(!estDeborde(p1,p2) && !estHV(p1,p2)){
            if (!estOccupe(p1,p2,type)) {
                Bateau b = Epoque.getEpoque(this.epoque).getBateau(type);
                b.setPosition(new int[][]{{p1.x,p1.y},{p2.x,p2.y}});
                if(p1.x==p2.x)
                    for(j=minj; j<minj+type;j++) {
                        plateau[mini][j] = b;

                    }

                else if(p1.y==p2.y)
                    for(i=mini;i<mini+type;i++) {
                        plateau[i][minj] = b;

                    }
                return b;
            }
        }

        return null;
    }

    /**
     * affichage de platreau
     */
    public void  affichePlateu(){
        for(int i =0 ;i<plateau.length;i++) {
            // system.out.println();
            for (int j = 0; j < plateau.length; j++) {
             if(plateau[j][i]==null) {
                 if (plateau2[j][i]) System.out.print("  B  ");
                 else System.out.print("  B  ");
             }else {
                 System.out.print("  "+plateau[j][i].get_res_Cas(j,i)+"  ");
             }
            }
        }
    }

    Bateau getBateu(int x, int y){
        return plateau[x][y];
    }
}
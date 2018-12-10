package batailleNavale.Model.Joueur;




import batailleNavale.Model.Bateaux.Bateau;
import batailleNavale.Model.Bateaux.BateauEP1;
import batailleNavale.Model.Bateaux.Tire;
import batailleNavale.Model.Epoques.Epoque;
import batailleNavale.Ressources;

import javax.tools.ToolProvider;
import java.awt.*;
import java.util.ArrayList;


public class Plateau {

    private int ChampBataille;
    Bateau plateau [][];
    boolean plateau2 [][]; // matrice sur laquelle on verifie si la case est noyer ou pas (si elle est completement brulee)

    private boolean tirePossible; // pour savoir si on peu choisir une case pour tirer ou non


    public Plateau(){

        plateau = new Bateau[Ressources.PLATEAU_HEIGHT][Ressources.PLATEAU_WIDTH];
        plateau2 = new boolean[Ressources.PLATEAU_HEIGHT][Ressources.PLATEAU_WIDTH];


        for(int i=0;i<Ressources.PLATEAU_HEIGHT;i++)
            for(int j=0;j<Ressources.PLATEAU_WIDTH;j++) {
                plateau[i][j] = null;
                plateau2[i][j]=true;
            }
        tirePossible = true;
    }

    /**
     * methode factory de plateau
     * cette methode crée les instances Plateau pour nous.
     * @return une instance de Plateau.
     */
    public static Plateau getInstance() {

        return new Plateau();
    }


    /**
     * méthode qui vérifie si le bateau est horizontal ou vertival
     **/

    private boolean estHV(Point p1, Point p2){
        if(p1.x != p2.x || p1.y != p2.y) return  false;
        return true ;
    }

    /**
     * méthode qui vérifie si LES POINTS de dépot du bateau débordent pas sur le plateau
     **/

    private boolean estDeborde(Point p1, Point p2){
        return !(p1.x<0 || p1.y<0 || p2.x<0 || p2.y<0 || p1.x< Ressources.PLATEAU_WIDTH || p1.y<Ressources.PLATEAU_HEIGHT || p2.x< Ressources.PLATEAU_WIDTH || p2.y<Ressources.PLATEAU_HEIGHT  );
    }



    /**
     * méthode qui vérifie si un bateau est détruit
     **/

    boolean estDetruit(Bateau b){
        if(b.getResistance()==0) return  true;
        return  false;
    }

    /**
     * méthode qui vérifie si un bateau est détruit
     **/

    boolean estOccupe(Point p1, Point p2,int type){
        int i,j,mini=p1.x,minj=p1.y;
        if(p1.x>p2.x) mini=p2.x;

        if(p1.y>p2.y)minj= p2.y;
        if(p1.x==p2.x)
            for(j=minj; j<=type;j++)
                if(plateau[mini][j]!=null) return true;

                else if(p1.y==p2.y)
                    for(i=mini;i<=type;i++)
                        if(plateau[i][minj]!=null) return true;
        return false;
    }
    /*
     ** verifier si la case du bateau est toujours pas noyer
     ** ce qui revien a verifier qu'on peut la choisir pour effectuer un tire
     */

    private boolean estNoyer(int x, int y){

        if((plateau [x][y]==null && plateau2[x][y]!=true) || (plateau [x][y]!=null && plateau2[x][y]!=true) ) return  true;
        return false;
    }

    /*
    ** fonction qui verifie l'etat de la case sur le plateau (case bateau ou case mer)
    ** etat=-1 lorsque la case et une case mer brulee ou noyee et etat=0 si la case mer est true
     */

    public int getEtatCase(int x, int y){
        int etat=0;
        for (int i=0;i<Ressources.PLATEAU_HEIGHT;i++)
            for (int j =0;j<Ressources.PLATEAU_WIDTH;j++){
                if(!plateau2[x][y])
                    if(plateau[x][y].getResistance()<plateau[x][y].getType())
                        //case touchee
                        etat=1;
                    else if(plateau[x][y]==null)
                        // case bleu de la mer
                        etat=2;
                    // case brulee
                etat=0;
            }
            return etat;
    }

    /*
    ** matrice d'etat des cases du plateau du joueur
     */

    public int[][]getBateauMatrice(){
        int[][] mat = new int[10][10];
        for(int i=0;i<Ressources.PLATEAU_HEIGHT;i++){
            for(int j=0;j<Ressources.PLATEAU_WIDTH;j++){
                mat[i][j]=getEtatCase(i,j);

            }
        }
        return mat;
    }

    /*
    ** matrice d'etat des cases du joueur adversaire
     */

    public int[][]getTireMatrice(){
        int[][] mat = new int[Ressources.PLATEAU_WIDTH][Ressources.PLATEAU_WIDTH];
        for(int i=0;i<Ressources.PLATEAU_HEIGHT;i++){
            for(int j=0;j<Ressources.PLATEAU_WIDTH;j++){
                if(plateau[i][j].getResistance()==0 || plateau2[i][j]==false)
                    mat[i][j]=0;
                mat[i][j]=plateau[i][j].getResistance();
            }
        }
        return mat;
    }


    /**
     * méthode qui applique les dégats sur un bateau
     * dans l'attribut tire on trouvera combien la case de coordonnees x et y lui reste pour prendre des tires
     * dans le cas ou la case une case mere, pour la difirencier de la cae bateau ou la mets a false pour interdir de tirer une autre fois sur cette case
     **/
    public int  prendTire(Tire tire){
        int t;
        int x = tire.getPositionCible()[0];
        int y = tire.getPositionCible()[1];
        if(!estNoyer(x, y)) {
            plateau[x][y].preandFeu(tire);
            t=1;
        }
        else {
            plateau2[x][y] = false;
            t=-1;
        }
        return t;
    }

    /**
     * fonction qui permet d'effectuer un tire sur un bateau
     **/


    public Tire tirer(int x, int y){
        Tire tire = null;
        if(!estNoyer(x,y))
            tire= new Tire(tire.getTireur(),tire.getForce(),new int[]{x, y});


        tirePossible=false;

        return tire;

    }



    /**
     * méthode qui permet de placer les bateau sur le plateau
     **/


    public  void poserBateau(Bateau b) {
        //ccondition a verifier pour pouvoir poser un bateau
        b.getPosition();
        Point p1 = new Point(b.getPosition()[0][0],b.getPosition()[0][1]);
        Point p2 = new Point(b.getPosition()[1][0],b.getPosition()[1][1]);

        int i,j,mini,minj;

        mini=p1.x;
        if(p1.x>p2.x) mini=p2.x;

        minj=p1.y;
        if(p1.y>p2.y) minj=p2.y;


        if(!estDeborde(p1,p2) && !estHV(p1,p2)){
            if (!estOccupe(p1,p2,b.getType())) {
                if(p1.x==p2.x)
                    for(j=minj; j<=b.getType();j++) {
                        plateau[mini][j] = b;

                    }
                else if(p1.y==p2.y)
                    for(i=p1.y;i<=b.getType();i++) {
                        plateau[i][minj] = b;

                    }
            }

        }

    }



//    public  static  void main(String [] args){
//        Plateau plateau = Plateau.getInstance();
//
//        Bateau b1 = new BateauEP1("Croiseur","EP1",2,2,1);
//        b1.setPosition(new int[][]{
//                {1,1},
//                {1,4}
//        });
//        plateau.poserBateau(b1);
//    }

}
package batailleNavale;

import java.awt.*;
import java.io.Serializable;
import java.net.URL;

/**
 * Cette classe contient tous les constants globales que nous utilisons.
 */
public class Ressources implements Serializable {

    public static final int NBBATEUDECHAQUETYPE=1;
    public static final int NBPROJECTILEPARCASE=3;

    public static final int Largeur=10;
    public static final int Hauteur=10;
    public static final int CasTaille=30;
    public static final int Largeurgrille=500;
    public static final int Hauteurgrille=500;
    public static final int fenetreLargeur=1200;
    public static final int fenetreHeauteur=600;

    public static final URL menu_bg_img= Ressources.class.getResource("res/bgmenu.png");
    public static final URL game_over_bg_img= Ressources.class.getResource("res/gameover.gif");
    public static final URL win_bg_img= Ressources.class.getResource("res/win.gif");
    public static final URL barre_bg_img= Ressources.class.getResource("res/barrebg.png");
    public static final URL jeu_icon= Ressources.class.getResource("res/jeuicon.png");
    public static final URL menu_icon= Ressources.class.getResource("res/menuicon.png");

    public static final Color[] casedesbateuaucolors=  {Color.decode("#1C1C1C"),Color.decode("#2A0A12"),Color.decode("#FFFDE7"),Color.decode("#FFF59D"),Color.decode("#FDD835"),Color.decode("#F57F17"),Color.decode("#FFF59D")};
    public static final Color[] casedestirecolor={Color.decode("#003366"),Color.decode("#00E676"),Color.decode("#212121")};
    public static final Color SelectColor=Color.decode("#1DE9B6");
    public static final Color ChoixColor= Color.decode("#1565C0");

    public static final int[] casedesbateuau={0,1,2,3,4,5,6};
    public static final int[] casedesTire={0,1,2};

    public enum Etats{Menu,Placement,Selection,Tire,Fermer,Gameover, Win};
    public enum TireEtats{TBateau,TVide,Timposible};

    public static final String[] epoques={"XX-siecle","XVI-siecle"};





}

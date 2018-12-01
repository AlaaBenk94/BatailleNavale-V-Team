package Vues;

import Controleur.Controleur;
import Model.jeu.Jeu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.text.View;

/**
 *
 * @author walidone
 */
public class FenetreJeu extends JFrame implements Observer{
    static final int Largeur=10;
    static final int Hauteur=10;
    static final int CasTaille=30;
    static final int Largeurgrille=500;
    static final int Hauteurgrille=500;
    static final int fenetreLargeur=1200;
    static final int fenetreHeauteur=600;
    static final String menu_bg_img= "src/img_ressource/bgmenu.png";
    static final String barre_bg_img= "src/img_ressource/barrebg.png";
    static final String jeu_icon= "src/img_ressource/jeuicon.png";
    static final String menu_icon= "src/img_ressource/menuicon.png";
    
    private Jeu modele;
    private Controleur controleur;

    private int etat=0;
    private JTabbedPane selecteur_fenetre;
    private JPanel jeu_section, plateau_bateu,barre_jeu, plateau_tire, menu_section,menu,menuprincipale,menucontenu;
    private JScrollPane menupanel;
    private JPanel joueurmatrice[][];
    private JPanel advercairematrice[][];
    private JComboBox choix_bateux;
    private JLabel descreptionBateuLabel;
    private JButton ajouter;
    private JTextField nomsauvgarder;
    private Map<String,String> descreptions;
    
    FenetreJeu(Jeu jeu ,Controleur controleur){
        this.modele=jeu;
        this.controleur=controleur;
        modele.addObserver(this);
        this.getContentPane().setBackground(Color.WHITE);
        this.setTitle("BatailleNavale-V-Team");
        ImageIcon img = new ImageIcon(jeu_icon);
        this.setIconImage(img.getImage());
        construction();
    }

    void jeuconstruction(){
        jeu_section = new javax.swing.JPanel();
        plateau_bateu = new javax.swing.JPanel();
        barre_jeu = new javax.swing.JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    g.drawImage(ImageIO.read(new File(barre_bg_img)), 0, 0, null);
                } catch (IOException ex) {
                    Logger.getLogger(FenetreJeu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        choix_bateux = new javax.swing.JComboBox<>();
        descreptionBateuLabel = new javax.swing.JLabel();
        ajouter = new javax.swing.JButton();
        plateau_tire = new javax.swing.JPanel();
        jeu_section.setBackground(new java.awt.Color(254, 254, 254));
        descreptionBateuLabel.setFont(new java.awt.Font("Ubuntu", 1, 18));
        descreptionBateuLabel.setForeground(new java.awt.Color(254, 254, 254));
        choix_bateux.setBackground(new java.awt.Color(254, 254, 254));
        choix_bateux.setFont(new java.awt.Font("Ubuntu", 1, 18));
        choix_bateux.setForeground(new java.awt.Color(254, 254, 254));
        choix_bateux.setModel(new javax.swing.DefaultComboBoxModel<>());

        ajouter.setBackground(new java.awt.Color(0, 140, 186));
        ajouter.setFont(new java.awt.Font("Ubuntu", 1, 18));
        ajouter.setForeground(new java.awt.Color(254, 254, 254));
        ajouter.setText("ajouter");

        Dimension dim=new Dimension(130,343);
        descreptionBateuLabel.setMinimumSize(dim);
        descreptionBateuLabel.setPreferredSize(dim);
        descreptionBateuLabel.setMaximumSize(dim);

        ajouter.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ajouterClick(evt);
            }
        });
        plateau_bateu.setLayout(new java.awt.GridLayout(10, 10));
        plateau_bateu.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                joueurMatriceClick(evt);
            }
        });
        plateau_bateu.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                joueurMatriceFocuse(evt);
            }
        });
        plateau_bateu.setPreferredSize(new Dimension(Largeurgrille,Hauteurgrille));
        plateau_tire.setPreferredSize(new Dimension(Largeurgrille,Hauteurgrille));
        plateau_bateu.setMaximumSize(new Dimension(Largeurgrille,Hauteurgrille));
        plateau_tire.setMaximumSize(new Dimension(Largeurgrille,Hauteurgrille));
        plateau_bateu.setMinimumSize(new Dimension(Largeurgrille,Hauteurgrille));
        plateau_tire.setMinimumSize(new Dimension(Largeurgrille,Hauteurgrille));
        plateau_tire.setLayout(new java.awt.GridLayout(10, 10));
        plateau_tire.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adversaireMatriceClick(evt);
            }
        });

        joueurmatrice=new JPanel[Largeur][Hauteur];
        advercairematrice=new JPanel[Largeur][Hauteur];
        for(int i=0;i<Hauteur;i++){
            for(int j=0;j<Largeur;j++){
                joueurmatrice[i][j]=new JPanel();
                joueurmatrice[i][j].setBackground(Color.BLUE);
                joueurmatrice[i][j].setSize(CasTaille,CasTaille);
                joueurmatrice[i][j].setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
                plateau_bateu.add(joueurmatrice[i][j]);

                advercairematrice[i][j]=new JPanel();
                advercairematrice[i][j].setSize(CasTaille,CasTaille);
                advercairematrice[i][j].setBackground(Color.BLACK);
                advercairematrice[i][j].setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
                plateau_tire.add(advercairematrice[i][j]);
            }
        }

        choix_bateux.setModel(new javax.swing.DefaultComboBoxModel<>());
        choix_bateux.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                String desc =String.valueOf(descreptions.get(choix_bateux.getSelectedItem()));
                descreptionBateuLabel.setText(desc);
            }
        });

        descreptionBateuLabel.setText("");

        ajouter.setText("ajouter");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(barre_jeu);
        barre_jeu.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(choix_bateux, 0, 109, Short.MAX_VALUE)
                                .addComponent(descreptionBateuLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ajouter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(choix_bateux, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(descreptionBateuLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(ajouter)
                        .addContainerGap())
        );


        plateau_tire.setLayout(new java.awt.GridLayout(10, 10));

        javax.swing.GroupLayout jeuLayout = new javax.swing.GroupLayout(jeu_section);
        jeu_section.setLayout(jeuLayout);
        jeuLayout.setHorizontalGroup(
                jeuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jeuLayout.createSequentialGroup()
                                .addComponent(barre_jeu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(plateau_bateu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(plateau_tire, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(27, Short.MAX_VALUE))
        );
        jeuLayout.setVerticalGroup(
                jeuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jeuLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addGroup(jeuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(barre_jeu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(plateau_bateu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(plateau_tire, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }
    void menuconstruction(){
        menu_section = new javax.swing.JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    g.drawImage(ImageIO.read(new File(menu_bg_img)), 0, 0, null);
                } catch (IOException ex) {
                    Logger.getLogger(FenetreJeu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        menupanel = new javax.swing.JScrollPane();
        menu = new javax.swing.JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    g.drawImage(ImageIO.read(new File(menu_bg_img)), 0, 0, null);
                } catch (IOException ex) {
                    Logger.getLogger(FenetreJeu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        menucontenu=new javax.swing.JPanel();
        menucontenu.setBackground(new java.awt.Color(254, 254, 254));
        menupanel.setBackground(new java.awt.Color(254, 254, 254));
        menupanel.setViewportView(menucontenu);

        javax.swing.GroupLayout menuLayout = new javax.swing.GroupLayout(menu);
        menu.setLayout(menuLayout);
        menuLayout.setHorizontalGroup(
                menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(menuLayout.createSequentialGroup()
                                .addGap(368, 368, 368)
                                .addComponent(menupanel, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(356, Short.MAX_VALUE))
        );
        menuLayout.setVerticalGroup(
                menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(menupanel, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(menu_section);
        menu_section.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

    }
    void construction(){
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(fenetreLargeur,fenetreHeauteur));
        setResizable(false);
        selecteur_fenetre = new JTabbedPane();
        selecteur_fenetre.setBackground(new java.awt.Color(243, 252, 253));
        selecteur_fenetre.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                selecteurEtat(evt);
            }
        });

        jeuconstruction();
        selecteur_fenetre.addTab("JEU", jeu_section);
        menuconstruction();
        selecteur_fenetre.addTab("MENU", menu_section);
        selecteur_fenetre.setSelectedIndex(1);
        this.add(selecteur_fenetre);

        selecteur_fenetre.setIconAt(0,new ImageIcon(jeu_icon));
        selecteur_fenetre.setIconAt(1,new ImageIcon(menu_icon));
        selecteur_fenetre.setBackgroundAt(0,Color.WHITE);
        selecteur_fenetre.setBackgroundAt(1,Color.WHITE);
        selecteur_fenetre.setForeground(Color.BLUE);
        selecteur_fenetre.setFont(new java.awt.Font("Ubuntu", 1, 18));


        construction_de_menu_principale();
        menucontenu.add(menuprincipale);
        menucontenu.updateUI();

        pack();


    }

    void construction_de_menu_principale(){
        menuprincipale = new javax.swing.JPanel();
        JButton nouvelle = new javax.swing.JButton();
        JButton charger = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        menuprincipale.setBackground(new java.awt.Color(254, 254, 254));

        nouvelle.setBackground(new java.awt.Color(0, 140, 186));
        nouvelle.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        nouvelle.setForeground(new java.awt.Color(254, 254, 254));
        nouvelle.setText("nouvelle");
        nouvelle.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nouvelleMenuClick(evt);
            }
        });
        charger.setBackground(new java.awt.Color(0, 140, 186));
        charger.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        charger.setForeground(new java.awt.Color(254, 254, 254));
        charger.setText("charger ");
        charger.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chargerMenuClick(evt);
            }
        });

        javax.swing.GroupLayout menuprincipaleLayout = new javax.swing.GroupLayout(menuprincipale);
        menuprincipale.setLayout(menuprincipaleLayout);
        menuprincipaleLayout.setHorizontalGroup(
                menuprincipaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(menuprincipaleLayout.createSequentialGroup()
                                .addGap(106, 106, 106)
                                .addGroup(menuprincipaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(charger, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(nouvelle, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE))
                                .addGap(106, 106, 106))
        );
        menuprincipaleLayout.setVerticalGroup(
                menuprincipaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(menuprincipaleLayout.createSequentialGroup()
                                .addGap(77, 77, 77)
                                .addComponent(nouvelle, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(charger, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(85, Short.MAX_VALUE))
        );
    }
    void aller_menu_newpartie(String[] epoques){
        JPanel mjPanel1 = new javax.swing.JPanel();
        JButton mjButton1 = new javax.swing.JButton();
        JPanel mjPanel2 = new javax.swing.JPanel();
        JTextField mjTextField1 = new javax.swing.JTextField();
        JScrollPane mjScrollPane1 = new javax.swing.JScrollPane();
        JTextPane mjTextPane1 = new javax.swing.JTextPane();
        JPanel mjPanel5 = new javax.swing.JPanel();
        JScrollPane mjScrollPane4 = new javax.swing.JScrollPane();
        JTextPane mjTextPane4 = new javax.swing.JTextPane();
        JComboBox<String> mjComboBox1 = new javax.swing.JComboBox<>();

        mjPanel1.setBackground(new java.awt.Color(254, 254, 254));

        mjButton1.setBackground(new java.awt.Color(0, 140, 186));
        mjButton1.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        mjButton1.setForeground(new java.awt.Color(254, 254, 254));
        mjButton1.setText("demarer");
        mjButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                demarerNouvellePartie(mjTextField1.getText(),mjComboBox1.getItemAt(mjComboBox1.getSelectedIndex()));
            }
        });

        mjPanel2.setBackground(new java.awt.Color(0, 173, 230));

        mjTextField1.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        mjTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        mjTextField1.setText("NOM");

        mjTextPane1.setEditable(false);
        mjTextPane1.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        mjTextPane1.setText("Nom:");
        mjScrollPane1.setViewportView(mjTextPane1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(mjPanel2);
        mjPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(mjScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(mjTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(mjTextField1)
                                        .addComponent(mjScrollPane1))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mjPanel5.setBackground(new java.awt.Color(0, 173, 230));

        mjTextPane4.setEditable(false);
        mjTextPane4.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        mjTextPane4.setText("Epoque:");
        mjScrollPane4.setViewportView(mjTextPane4);

        mjComboBox1.setBackground(new java.awt.Color(254, 254, 254));
        mjComboBox1.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        mjComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(epoques));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(mjPanel5);
        mjPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(mjScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(mjComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(mjComboBox1)
                                        .addComponent(mjScrollPane4))
                                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(mjPanel1);
        mjPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(102, 102, 102)
                                .addComponent(mjButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(110, 110, 110))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(mjPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(mjPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addComponent(mjPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(mjPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(mjButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(64, Short.MAX_VALUE))
        );
        menucontenu.removeAll();
        menucontenu.add(mjPanel1);
        menucontenu.updateUI();
    }

    void aller_menu_sauvgarder(){

        JPanel savepanel = new javax.swing.JPanel();
        JButton save = new javax.swing.JButton();
        JPanel sjPanel3 = new javax.swing.JPanel();
        nomsauvgarder = new javax.swing.JTextField();
        JScrollPane jScrollPane2 = new javax.swing.JScrollPane();
        JTextPane savenom = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        savepanel.setBackground(new java.awt.Color(254, 254, 254));

        save.setBackground(new java.awt.Color(0, 140, 186));
        save.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        save.setForeground(new java.awt.Color(254, 254, 254));
        save.setText("sauvegarder");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sauvgarder();
            }
        });

        sjPanel3.setBackground(new java.awt.Color(0, 173, 230));

        nomsauvgarder.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        nomsauvgarder.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        savenom.setEditable(false);
        savenom.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        savenom.setText("nom de partie");
        jScrollPane2.setViewportView(savenom);

        javax.swing.GroupLayout sjPanel3Layout = new javax.swing.GroupLayout(sjPanel3);
        sjPanel3.setLayout(sjPanel3Layout);
        sjPanel3Layout.setHorizontalGroup(sjPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sjPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nomsauvgarder, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
        );
        sjPanel3Layout.setVerticalGroup(sjPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(sjPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(sjPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(nomsauvgarder)
                                .addComponent(jScrollPane2))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout savepanelLayout = new javax.swing.GroupLayout(savepanel);
        savepanel.setLayout(savepanelLayout);
        savepanelLayout.setHorizontalGroup(
                savepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, savepanelLayout.createSequentialGroup()
                                .addGap(102, 102, 102)
                                .addComponent(save, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                                .addGap(110, 110, 110))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, savepanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(sjPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        savepanelLayout.setVerticalGroup(
                savepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, savepanelLayout.createSequentialGroup()
                                .addContainerGap(106, Short.MAX_VALUE)
                                .addComponent(sjPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(83, 83, 83)
                                .addComponent(save, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33))
        );

        menucontenu.removeAll();
        menucontenu.add(savepanel);
        menucontenu.updateUI();
    }

    private void joueurMatriceClick(java.awt.event.MouseEvent evt){
        if(etat==1){
            int i=evt.getX()/(plateau_bateu.getWidth()/Largeur);
            int j=evt.getY()/(plateau_bateu.getHeight()/Hauteur);
            //System.out.println(i+" "+j);
            //joueurmatrice[j][i].setBackground(Color.red);
            String type=choix_bateux.getSelectedObjects()[0].toString();
            controleur.selecinnerunecase(i, j,type);
        }
        if(etat==2){
            int i=evt.getX()/(plateau_bateu.getWidth()/Largeur);
            int j=evt.getY()/(plateau_bateu.getHeight()/Hauteur);
            //System.out.println(i+" "+j);
            //joueurmatrice[j][i].setBackground(Color.red);
            controleur.selecinner_cas_bateu_qui_tire(i, j);
        }
    }
    private void adversaireMatriceClick(java.awt.event.MouseEvent evt){
        if(etat==3){
            int i=evt.getX()/(plateau_tire.getWidth()/Largeur);
            int j=evt.getY()/(plateau_tire.getHeight()/Hauteur);
            //System.out.println(i+" "+j);
            //advercairematrice[j][i].setBackground(Color.GREEN);
            controleur.tirer_cas(i, j);
        }
    }
    private void joueurMatriceFocuse(java.awt.event.MouseEvent evt){
        if(etat==2){
            int i=evt.getX()/(plateau_tire.getWidth()/Largeur);
            int j=evt.getY()/(plateau_tire.getHeight()/Hauteur);
            String info=modele.getinfobateucase(i,j);
            if(info!=null)
                descreptionBateuLabel.setText(info);
        }
    }


    private void nouvelleMenuClick(MouseEvent evt) {
        String[] epoques=modele.getEpoqes();
        aller_menu_newpartie(epoques);
    }
    private void chargerMenuClick(MouseEvent evt) {
        String lien=selectioner_un_ficher(1);
        controleur.chargerpartie(lien);
    }


    private void selecteurEtat(ChangeEvent evt) {
        if(etat==0) selecteur_fenetre.setSelectedIndex(selecteur_fenetre.getTabCount()-1);
    }

    void demarerNouvellePartie(String nom ,String epoque){
        controleur.nouvellepartie(nom, epoque);
    }

    private void ajouterClick(ActionEvent evt) {
        controleur.ajouter_le_Bateu_select();
    }

    void sauvgarder(){
        String lien=selectioner_un_ficher(0);
        if(lien!=null)lien+="/"+nomsauvgarder.getText();
        controleur.sauvgarder(lien);
    }

    public void ubdateMatrice(int [][] matriceBateau ,int[][] matriceTire ){
        for(int i=0;i<matriceBateau.length;i++)
            for(int j=0;j<matriceBateau.length;j++){
                if(matriceBateau[i][j]==0) joueurmatrice[i][j].setBackground(Color.decode("#003366"));
                if(matriceBateau[i][j]==1) joueurmatrice[i][j].setBackground(Color.RED);
                if(matriceBateau[i][j]==2) joueurmatrice[i][j].setBackground(Color.BLACK);
                if(matriceBateau[i][j]==3) joueurmatrice[i][j].setBackground(Color.YELLOW);
                if(matriceBateau[i][j]==4) joueurmatrice[i][j].setBackground(Color.GREEN);
                if(matriceBateau[i][j]==5) joueurmatrice[i][j].setBackground(Color.GRAY);

            }
        for(int i=0;i<matriceTire.length;i++)
            for(int j=0;j<matriceTire.length;j++){
                if(matriceTire[i][j]==0) advercairematrice[i][j].setBackground(Color.decode("#003366"));
                if(matriceTire[i][j]==1) advercairematrice[i][j].setBackground(Color.red);
            }
    }


    @Override
    public void update(Observable o, Object arg) {
        etat=modele.getetat();
        System.out.println("etat :"+etat);
        if(etat==1){
            ubdateMatrice(modele.getBateauMatrice(),modele.getTireMatrice());
            selecteur_fenetre.setSelectedIndex(0);
            String[] typebat = modele.getBateuTypes();
            descreptions=modele.getBateuDescreption();
            choix_bateux.removeAllItems();choix_bateux.updateUI();
            for(int i=0;i<typebat.length;i++)choix_bateux.addItem(typebat[i]);
            String des =String.valueOf(descreptions.get(choix_bateux.getSelectedItem()));
            descreptionBateuLabel.setText(des);
            choix_bateux.setVisible(true);
            ajouter.setVisible(true);
            aller_menu_sauvgarder();
        }
        if(etat==2||etat==3){
            ubdateMatrice(modele.getBateauMatrice(),modele.getTireMatrice());
            selecteur_fenetre.setSelectedIndex(0);
            String[] typebat = modele.getBateuTypes();
            descreptions=modele.getBateuDescreption();
            choix_bateux.setVisible(false);
            ajouter.setVisible(false);
            aller_menu_sauvgarder();
        }
        if(etat==4){
            setVisible(false);
            dispose();
        }

    }

    String selectioner_un_ficher(int type){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choisir un dossier pour enregistrer la partie");
        if(type==1){
            fileChooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);
        }else{
            fileChooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        }
        fileChooser.setPreferredSize(new Dimension(732,506));

        int userSelection = fileChooser.showSaveDialog(this);
        String lien = null;
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            lien = file.getAbsolutePath();
        }
        return lien;
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Jeu j = new Jeu();
                FenetreJeu b=new FenetreJeu(j,new Controleur(j));
                b.setVisible(true);

            }
        });
    }


}

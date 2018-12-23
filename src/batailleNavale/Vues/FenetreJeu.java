package batailleNavale.Vues;

import batailleNavale.Controleur.Controleur;
import batailleNavale.Model.Epoques.Epoque;
import batailleNavale.Model.jeu.Jeu;
import batailleNavale.Ressources;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;
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
public class FenetreJeu extends JFrame implements Observer {

    private Jeu modele;
    private Controleur controleur;

    private Ressources.Etats etat = Ressources.Etats.Menu;
    Ressources.Etats etatp=Ressources.Etats.Menu;
    private JTabbedPane selecteur_fenetre;
    private JPanel game,jeu_section, plateau_bateu, barre_jeu, plateau_tire, menu_section, menu, menuprincipale, menucontenu;
    private JScrollPane menupanel;
    private JPanel joueurmatrice[][];
    private JPanel advercairematrice[][];
    private JComboBox choix_bateux;
    private JLabel descreptionBateuLabel;
    private JButton ajouter;
    private JTextField nomsauvgarder;
    private Map<String, String> descreptions;
    private LinkedList<int[]> caseselectioner;
    private LinkedList<int[]> caseposibles;

    public FenetreJeu(Jeu jeu, Controleur controleur) {
        this.modele = jeu;
        this.controleur = controleur;
        modele.addObserver(this);
        this.getContentPane().setBackground(Color.WHITE);
        this.setTitle("BatailleNavale-V-Team");
        ImageIcon img = new ImageIcon(Ressources.jeu_icon);
        this.setIconImage(img.getImage());
        construction();

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
        caseposibles = new LinkedList<>();
        caseselectioner = new LinkedList<>();
    }

    private void jeuconstruction() {
        jeu_section = new javax.swing.JPanel();
        plateau_bateu = new javax.swing.JPanel();
        barre_jeu = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    g.drawImage(ImageIO.read(new File(Ressources.barre_bg_img)), 0, 0, null);
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

        Dimension dim = new Dimension(130, 343);
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
        plateau_bateu.setPreferredSize(new Dimension(Ressources.Largeurgrille, Ressources.Hauteurgrille));
        plateau_tire.setPreferredSize(new Dimension(Ressources.Largeurgrille, Ressources.Hauteurgrille));
        plateau_bateu.setMaximumSize(new Dimension(Ressources.Largeurgrille, Ressources.Hauteurgrille));
        plateau_tire.setMaximumSize(new Dimension(Ressources.Largeurgrille, Ressources.Hauteurgrille));
        plateau_bateu.setMinimumSize(new Dimension(Ressources.Largeurgrille, Ressources.Hauteurgrille));
        plateau_tire.setMinimumSize(new Dimension(Ressources.Largeurgrille, Ressources.Hauteurgrille));
        plateau_tire.setLayout(new java.awt.GridLayout(10, 10));
        plateau_tire.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adversaireMatriceClick(evt);
            }
        });

        joueurmatrice = new JPanel[Ressources.Largeur][Ressources.Hauteur];
        advercairematrice = new JPanel[Ressources.Largeur][Ressources.Hauteur];
        for (int i = 0; i < Ressources.Hauteur; i++) {
            for (int j = 0; j < Ressources.Largeur; j++) {
                joueurmatrice[i][j] = new JPanel();
                joueurmatrice[i][j].setBackground(Color.BLUE);
                joueurmatrice[i][j].setSize(Ressources.CasTaille, Ressources.CasTaille);
                joueurmatrice[i][j].setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
                plateau_bateu.add(joueurmatrice[i][j]);

                advercairematrice[i][j] = new JPanel();
                advercairematrice[i][j].setSize(Ressources.CasTaille, Ressources.CasTaille);
                advercairematrice[i][j].setBackground(Color.BLACK);
                advercairematrice[i][j].setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
                plateau_tire.add(advercairematrice[i][j]);
            }
        }

        choix_bateux.setModel(new javax.swing.DefaultComboBoxModel<>());
        choix_bateux.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String desc = String.valueOf(descreptions.get(choix_bateux.getSelectedItem()));
                descreptionBateuLabel.setText(desc);
                caseselectioner.clear();
                upDateMatrice(modele.getBateauMatrice(), modele.getTireMatrice());
                lescaseposibles();
                update_case_desponible();

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

    private void menuconstruction() {
        menu_section = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    g.drawImage(ImageIO.read(new File(Ressources.menu_bg_img)), 0, 0, null);
                } catch (IOException ex) {
                    Logger.getLogger(FenetreJeu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        menupanel = new javax.swing.JScrollPane();
        menu = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    g.drawImage(ImageIO.read(new File(Ressources.menu_bg_img)), 0, 0, null);
                } catch (IOException ex) {
                    Logger.getLogger(FenetreJeu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        menucontenu = new javax.swing.JPanel();
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

    private void construction() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(Ressources.fenetreLargeur, Ressources.fenetreHeauteur));
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
        this.getContentPane().add(selecteur_fenetre);

        selecteur_fenetre.setIconAt(0, new ImageIcon(Ressources.jeu_icon));
        selecteur_fenetre.setIconAt(1, new ImageIcon(Ressources.menu_icon));
        selecteur_fenetre.setBackgroundAt(0, Color.WHITE);
        selecteur_fenetre.setBackgroundAt(1, Color.WHITE);
        selecteur_fenetre.setForeground(Color.BLUE);
        selecteur_fenetre.setFont(new java.awt.Font("Ubuntu", 1, 18));


        construction_de_menu_principale();
        menucontenu.add(menuprincipale);
        menucontenu.updateUI();

        pack();


    }

    private void construction_de_menu_principale() {
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

    private void aller_menu_newpartie(String[] epoques) {
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
                demarerNouvellePartie(mjTextField1.getText(), mjComboBox1.getItemAt(mjComboBox1.getSelectedIndex()));
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

    private void aller_menu_sauvgarder() {

        JPanel savepanel = new javax.swing.JPanel();
        JButton save = new javax.swing.JButton();
        JPanel sjPanel3 = new javax.swing.JPanel();
        nomsauvgarder = new javax.swing.JTextField();
        JScrollPane jScrollPane2 = new javax.swing.JScrollPane();
        JTextPane savenom = new javax.swing.JTextPane();
        if (modele.getNompartie() != null) nomsauvgarder.setText(modele.getNompartie());

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


    private void selecinnerunecase(int x, int y) {
        if (caseselectioner.size() == 2) {
            caseselectioner.remove(0);
            lescaseposibles();
        }
        if (est_desponible(x, y)) {
            caseselectioner.add(new int[]{x, y});
        }
        upDateMatrice(modele.getBateauMatrice(), modele.getTireMatrice());
        lescaseposibles();
        update_case_desponible();
    }

    private void lescaseposibles() {
        if (choix_bateux.getSelectedObjects().length > 0) {
            int[][] matriceBateau = modele.getBateauMatrice();
            String batteaukey = choix_bateux.getSelectedObjects()[0].toString();
            int tailledebat = modele.getEpoque().getBateauTaille(batteaukey) - 1;
            caseposibles.clear();
            if (caseselectioner.size() == 0) {
                for (int i = 0; i < matriceBateau.length; i++)
                    for (int j = 0; j < matriceBateau.length; j++) {
                        if (verification_une_case(matriceBateau, i, j, tailledebat)) {
                            caseposibles.add(new int[]{i, j});
                        }
                    }

            }
            if (caseselectioner.size() == 1) {
                int i = caseselectioner.get(0)[0];
                int j = caseselectioner.get(0)[1];
                if (i - tailledebat >= 0 && !traversse_bateu(matriceBateau, i, j, i - tailledebat, j)) {
                    caseposibles.add(new int[]{i - tailledebat, j});
                }
                if (i + tailledebat < matriceBateau.length && !traversse_bateu(matriceBateau, i, j, i + tailledebat, j)) {
                    caseposibles.add(new int[]{i + tailledebat, j});
                }
                if (j - tailledebat >= 0 && !traversse_bateu(matriceBateau, i, j, i, j - tailledebat)) {
                    caseposibles.add(new int[]{i, j - tailledebat});
                }
                if (j + tailledebat < matriceBateau.length && !traversse_bateu(matriceBateau, i, j, i, j + tailledebat)) {
                    caseposibles.add(new int[]{i, j + tailledebat});
                }
            }
        }
    }

    private boolean verification_une_case(int[][] matrice, int x, int y, int taille) {
        return ((matrice.length > x + taille && !traversse_bateu(matrice, x, y, x + taille, y)) ||
                (x - taille >= 0 && !traversse_bateu(matrice, x - taille, y, x, y)) ||
                (matrice.length > y + taille && !traversse_bateu(matrice, x, y + taille, x, y)) ||
                (y - taille >= 0 && !traversse_bateu(matrice, x, y - taille, x, y)))
                && (matrice[x][y] == Ressources.casedesbateuau[0]);
    }

    private boolean traversse_bateu(int[][] matrice, int x1, int y1, int x2, int y2) {
        if (y1 == y2) {
            int min = x1;
            if (x2 < x1) min = x2;
            int max = x1;
            if (x2 > x1) max = x2;
            for (int i = min; i < max; i++)
                if (matrice[i][y1] != Ressources.casedesbateuau[0])
                    return true;
        }
        if (x1 == x2) {
            int min = y1;
            if (y2 < y1) min = y2;
            int max = y1;
            if (y2 > y1) max = y2;
            for (int i = min; i < max; i++)
                if (matrice[x1][i] != Ressources.casedesbateuau[0])
                    return true;
        }
        return false;
    }

    private boolean est_desponible(int x, int y) {
        if (caseposibles == null) return false;
        for (int i = 0; i < caseposibles.size(); i++) {
            if (caseposibles.get(i)[0] == x && caseposibles.get(i)[1] == y) return true;
        }
        return false;
    }

    private void update_case_desponible() {
        for (int[] p : caseposibles) {
            joueurmatrice[p[0]][p[1]].setBackground(Ressources.ChoixColor);
        }
        for (int[] p : caseselectioner) {
            joueurmatrice[p[0]][p[1]].setBackground(Ressources.SelectColor);
        }

    }


    private void joueurMatriceClick(java.awt.event.MouseEvent evt) {
        if (etat == Ressources.Etats.Placement) {
            int i = evt.getX() / (plateau_bateu.getWidth() / Ressources.Largeur);
            int j = evt.getY() / (plateau_bateu.getHeight() / Ressources.Hauteur);
            //System.out.println(i+" "+j);
            //joueurmatrice[j][i].setBackground(Color.red);
            selecinnerunecase(j, i);
        }
        if (etat == Ressources.Etats.Selection) {
            int i = evt.getX() / (plateau_bateu.getWidth() / Ressources.Largeur);
            int j = evt.getY() / (plateau_bateu.getHeight() / Ressources.Hauteur);
            //System.out.println(i+" "+j);
            //joueurmatrice[j][i].setBackground(Color.red);
            controleur.selecinner_cas_bateu_qui_tire(i, j);
        }
    }

    private void adversaireMatriceClick(java.awt.event.MouseEvent evt) {
        if (etat == Ressources.Etats.Tire) {
            int i = evt.getX() / (plateau_tire.getWidth() / Ressources.Largeur);
            int j = evt.getY() / (plateau_tire.getHeight() / Ressources.Hauteur);
            //System.out.println(i+" "+j);
            //advercairematrice[j][i].setBackground(Color.GREEN);
            controleur.tirer_cas(i, j);
        }
    }

    private void joueurMatriceFocuse(java.awt.event.MouseEvent evt) {
        if (etat == Ressources.Etats.Selection) {
            int i = evt.getX() / (plateau_tire.getWidth() / Ressources.Largeur);
            int j = evt.getY() / (plateau_tire.getHeight() / Ressources.Hauteur);
            String info = modele.getinfobateucase(i, j);
            if (info != null)
                descreptionBateuLabel.setText(info);
        }
    }


    private void nouvelleMenuClick(MouseEvent evt) {
        String[] epoques = modele.getEpoqes();
        aller_menu_newpartie(epoques);
    }

    private void chargerMenuClick(MouseEvent evt) {
        String lien = selectioner_un_ficher(1);
        controleur.chargerpartie(lien, this);
        this.etat = modele.getetat();
    }


    private void selecteurEtat(ChangeEvent evt) {
        if (etat == Ressources.Etats.Menu) selecteur_fenetre.setSelectedIndex(selecteur_fenetre.getTabCount() - 1);
    }

    void demarerNouvellePartie(String nom, String epoque) {
        controleur.nouvellepartie(nom, epoque);
    }

    private void ajouterClick(ActionEvent evt) {
        if (caseselectioner.size() == 2) {
            int[][] pos = new int[][]{caseselectioner.get(0), caseselectioner.get(1)};
            String battype = choix_bateux.getSelectedItem().toString();
            controleur.ajouter_le_Bateu_select(pos, battype);
        }
        caseselectioner.clear();
    }

    void sauvgarder() {
        String lien = selectioner_un_ficher(0);
        if (lien != null) lien += "/" + nomsauvgarder.getText();
        controleur.sauvgarder(lien);
    }

    public void upDateMatrice(int[][] matriceBateau, int[][] matriceTire) {
        for (int i = 0; i < matriceBateau.length; i++)
            for (int j = 0; j < matriceBateau.length; j++) {
                int etat = matriceBateau[i][j];
                joueurmatrice[i][j].setBackground(Ressources.casedesbateuaucolors[etat]);
            }
        for (int i = 0; i < matriceTire.length; i++)
            for (int j = 0; j < matriceTire.length; j++) {
                int etat = matriceTire[i][j];
                advercairematrice[i][j].setBackground(Ressources.casedestirecolor[etat]);
            }
    }
    @Override
    public void update(Observable o, Object arg) {
        etat = modele.getetat();
        if(etat!=Ressources.Etats.Menu){ modele.setetatp(etat);}
         System.out.println("etat :"+etat);
        if (etat == Ressources.Etats.Placement) {
            upDateMatrice(modele.getBateauMatrice(), modele.getTireMatrice());
            selecteur_fenetre.setSelectedIndex(0);
            String[] typebat = modele.getBateuTypes();
            descreptions = modele.getBateuDescreption();
            choix_bateux.removeAllItems();
            choix_bateux.updateUI();
            for (int i = 0; i < typebat.length; i++) choix_bateux.addItem(typebat[i]);
            String des = String.valueOf(descreptions.get(choix_bateux.getSelectedItem()));
            descreptionBateuLabel.setText(des);
            choix_bateux.setVisible(true);
            ajouter.setVisible(true);
            aller_menu_sauvgarder();
            lescaseposibles();
            update_case_desponible();
        }
        if (etat == Ressources.Etats.Selection || etat == Ressources.Etats.Tire) {
            etatp=etat;
            upDateMatrice(modele.getBateauMatrice(), modele.getTireMatrice());
            selecteur_fenetre.setSelectedIndex(0);
            String[] typebat = modele.getBateuTypes();
            descreptions = modele.getBateuDescreption();
            choix_bateux.setVisible(false);
            ajouter.setVisible(false);
            aller_menu_sauvgarder();
        }
        if (etat == Ressources.Etats.Fermer) {
            setVisible(false);
            dispose();
        }
        if (etat == Ressources.Etats.Gameover) {
            anime(Ressources.game_over_bg_img,6000);
        }
        if (etat == Ressources.Etats.Win) {
            anime(Ressources.win_bg_img,6000);
        }

    }
    private Timer timer;
    private void anime(String img,int time){
            ImageIcon ii = new ImageIcon(img);
            JLabel imgl=new JLabel();
            imgl.setIcon(ii);
            changerbg(imgl);
            ActionListener action = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    retoureDepart();
                }
            };
            timer = new Timer(time, action);
            timer.start();
    }
    private void changerbg(JLabel label){
        this.getContentPane().removeAll();
        this.getContentPane().add(label);
        this.setVisible(true);
    }
    private void retoureDepart() {
        timer.stop();
    this.getContentPane().removeAll();
    selecteur_fenetre.setSelectedIndex(0);
    construction();
    caseposibles =new LinkedList<>();
    caseselectioner=new LinkedList<>();
    etatp=modele.getetat();
    modele.setetat(Ressources.Etats.Menu);
    update(null,null);
    }

    private String selectioner_un_ficher(int type){
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

    public void close(){
        setVisible(false);
        dispose();
    }
    public void setetat(Ressources.Etats etat){
        this.etat=etat;
    }
    public Ressources.Etats getetp(){return etatp;}
    public Ressources.Etats getetat(){
        return this.etat;
    }

}

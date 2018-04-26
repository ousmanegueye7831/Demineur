package sn.esp.dic2.projets.game.demineur.view;
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import sn.esp.dic2.projets.game.demineur.model.DemineurModel;
/**
* La classe DemineurMainWindow représente la fenêtre globale du Démineur
* @author group 8
*/
public class DemineurMainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private DemineurView view;
	private DemineurModel model;
	protected static final int DEBUTANT = 0;
	private JPanel grilleJeu;
	InfoScore info; 

	public DemineurMainWindow (DemineurView view, DemineurModel model){
		 super("Démineur : Niveau "+model.getPartieModel().getStrLevel() );
		 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 ImageIcon img = new ImageIcon(DemineurMainWindow.class.getResource("/sn/esp/dic2/projets/game/demineur/data/mine.png"));
		 setIconImage(img.getImage());
		 
		 this.view = view;
		 this.model = model;

		 info = new InfoScore(this);
		 initMainWindow();
		 
		 setVisible(true);
		 setSize(model.getPartieModel().getGrille().getNbRow()*60,model.getPartieModel().getGrille().getNbCol()*60);
		 setResizable(false);
		 setLocationRelativeTo(null);
		 model.addModelChangeListener(view);
		 view.addControllerChangeListener(model);
	}
	
	private void initMainWindow(){
		 int niveauCourant = this.model.getPartieModel().getLevel();
		 JMenuBar menuBar = new JMenuBar();
		 JMenu menuJeu = new JMenu("Jeu");
		 JMenu menuNiveau = new JMenu("Niveau");
		 menuJeu.setToolTipText("Menu du jeu");
		 menuJeu.setMnemonic('J');
		 menuNiveau.setMnemonic('N');
		 menuNiveau.setToolTipText("Changer le niveau du jeu");
		 
		 MesActionsAbstract actionRejouer = new MesActionsAbstract("Rejouer la partie",this,niveauCourant);
		 MesActionsAbstract actionReculer = new MesActionsAbstract("Reculer",this,niveauCourant);
		 MesActionsAbstract actionRepartir = new MesActionsAbstract("Repartir",this,niveauCourant);
		 MesActionsAbstract actionRecharger = new MesActionsAbstract("Reprendre Partie",this,niveauCourant);
		 MesActionsAbstract actionRecord = new MesActionsAbstract("Enregistré Partie",this,niveauCourant);
		 MesActionsAbstract actionAPropos = new MesActionsAbstract("A Propos",this,niveauCourant);
		 MesActionsAbstract actionQuitter = new MesActionsAbstract("Quitter",this,niveauCourant);
		 
		 MesActionsAbstract actionDebutant = new MesActionsAbstract("Débutant", this,niveauCourant) ;
		 MesActionsAbstract actionIntermediaire = new MesActionsAbstract("Intermediaire", this,niveauCourant) ;
		 MesActionsAbstract actionExpert = new MesActionsAbstract("Expert", this,niveauCourant) ;
		 
		 menuJeu.addSeparator();
		 menuJeu.add(actionRejouer).setIcon(new ImageIcon(DemineurMainWindow.class.getResource("/sn/esp/dic2/projets/game/demineur/data/rejouer.png")));		 		 
		 menuJeu.add(actionReculer).setIcon(new ImageIcon(DemineurMainWindow.class.getResource("/sn/esp/dic2/projets/game/demineur/data/undo.png")));	 
		 menuJeu.add(actionRepartir).setIcon(new ImageIcon(DemineurMainWindow.class.getResource("/sn/esp/dic2/projets/game/demineur/data/redo.png")));
		 menuJeu.addSeparator();
		 menuJeu.add(actionRecharger).setIcon(new ImageIcon(DemineurMainWindow.class.getResource("/sn/esp/dic2/projets/game/demineur/data/open.png")));		 
		 menuJeu.add(actionRecord).setIcon(new ImageIcon(DemineurMainWindow.class.getResource("/sn/esp/dic2/projets/game/demineur/data/save.png")));
		 menuJeu.addSeparator();
		 menuJeu.add(actionAPropos).setIcon(new ImageIcon(DemineurMainWindow.class.getResource("/sn/esp/dic2/projets/game/demineur/data/apropos.png")));
		 menuJeu.add(actionQuitter).setIcon(new ImageIcon(DemineurMainWindow.class.getResource("/sn/esp/dic2/projets/game/demineur/data/quitter.png")));
		 
		 
		 menuNiveau.add(actionDebutant).setIcon(new ImageIcon(DemineurMainWindow.class.getResource("/sn/esp/dic2/projets/game/demineur/data/Flechebas.png")));
		 menuNiveau.addSeparator();
		 menuNiveau.add(actionIntermediaire).setIcon(new ImageIcon(DemineurMainWindow.class.getResource("/sn/esp/dic2/projets/game/demineur/data/Flechedroite.png")));
		 menuNiveau.addSeparator();
		 menuNiveau.add(actionExpert).setIcon(new ImageIcon(DemineurMainWindow.class.getResource("/sn/esp/dic2/projets/game/demineur/data/Fleche_haut_vert.png")));
		 
		 menuBar.add(menuJeu);
		 menuBar.add(menuNiveau);
		 
		 this.setJMenuBar(menuBar);
		 grilleJeu = new JPanel();
		 grilleJeu.setLayout(new BorderLayout());
		 grilleJeu.add(view, BorderLayout.CENTER);
		 grilleJeu.add(info, BorderLayout.NORTH);
		
		 this.setContentPane(grilleJeu);
	}
	
	public static void main(String[] args) {
		try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DemineurModel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		DemineurModel model = new DemineurModel(DEBUTANT);
		DemineurView view = new DemineurView(model.getPartieModel().getGrille().getNbRow(),model.getPartieModel().getGrille().getNbCol(),model);
		new DemineurMainWindow(view,model);
	}
	
	//*************Tout ce qui suit concerne les getteurs et les setteurs************
	public DemineurView getView() {
		return view;
	}
	public void setView(DemineurView view) {
		this.view = view;
	}
	public JPanel getGrilleJeu() {
		return grilleJeu;
	}
	public void setGrilleJeu(JPanel grilleJeu) {
		this.grilleJeu = grilleJeu;
	}
	public DemineurModel getModel() {
		return model;
	}
	public void setModel(DemineurModel model) {
		this.model = model;
	}

	public InfoScore getInfo() {
		return info;
	}

	public void setInfo(InfoScore info) {
		this.info = info;
	}
	
}


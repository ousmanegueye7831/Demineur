package sn.esp.dic2.projets.game.demineur.view;

import java.applet.Applet;

import java.applet.AudioClip;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import sn.esp.dic2.projets.game.demineur.controller.ControllerChangeEvent;
import sn.esp.dic2.projets.game.demineur.controller.ControllerChangeListener;
import sn.esp.dic2.projets.game.demineur.controller.ModelChangeEvent;
import sn.esp.dic2.projets.game.demineur.controller.ModelChangeListener;
import sn.esp.dic2.projets.game.demineur.model.Case;
import sn.esp.dic2.projets.game.demineur.model.CaseMinee;
import sn.esp.dic2.projets.game.demineur.model.CaseNumerotee;
import sn.esp.dic2.projets.game.demineur.model.CaseVide;
import sn.esp.dic2.projets.game.demineur.model.DemineurModel;
import sn.esp.dic2.projets.game.demineur.view.InfoScore;

/**
 * La classe DemineurView représente la grille complete du demineur
 * @author groupe 8
 *
 */
public class DemineurView extends JPanel implements ModelChangeListener{
	private static final long serialVersionUID = 1L;
	private CaseView matriceCase[][];
	private DemineurModel model ;
	private Image bg;
	
	//La liste de tous les écouteurs d'évenement
	private ArrayList<ControllerChangeListener> listeners = new ArrayList<ControllerChangeListener>();
	
	
	/**
	 * Constructeur par défaut utiliser pour la sérialisation
	 */
	public DemineurView(){}
	
	
	/**
	 * Constructeur de DemineurView represantant la vue du Démienur
	 * @param model
	 */
	public DemineurView(int nbRow, int nbCol,DemineurModel model) {
		grilleDemineur(nbRow,nbCol);
		this.model = model;
		bg = new ImageIcon(DemineurView.class.getResource("/sn/esp/dic2/projets/game/demineur/data/bgView.jpg" )).getImage();
		
	}
	
	/**
	 * Initialisation de la grille du Demineur
	 * @param nbRow
	 * @param nbCol
	 */
	private void grilleDemineur(int nbRow, int nbCol) {
	 setLayout(new GridLayout(nbRow,nbCol));
	 setPreferredSize(new Dimension(nbRow*40,nbCol*40));
	 matriceCase = new CaseView[nbRow][nbCol];
	 CaseView caseView;
	 Case caseModel;
	 for (int i = 0; i < nbRow; i++) {
		 for (int j = 0; j < nbCol; j++) {
			 caseModel = new Case(i,j);
			caseView = new CaseView(i,j,this,caseModel);
			caseView.setIcon(new ImageIcon(DemineurView.class.getResource("/sn/esp/dic2/projets/game/demineur/data/couvert.png")));
		   add(caseView);
		   matriceCase[i][j] = caseView;
		 }
	 }
	}
	
	

	/**
     * Notifie aux différents écouteurs d'un changement dans la vue
     * @param controllerChangeEvent l'événement à envoyer
     */
    protected void fireControllerChange(ControllerChangeEvent controllerChangeEvent) {
    	Iterator<ControllerChangeListener> iterator = listeners.iterator();
		while(iterator.hasNext()){
				iterator.next().controllerChanged(controllerChangeEvent);
			}
    }
    /**
     * permet au "écouteurs" de s'enregistrer auprès du controleur de la vue.
     * @param controllerChangeEvent
     */
    public void addControllerChangeListener(ControllerChangeListener controllerChangeEvent){
    	listeners.add(controllerChangeEvent);
    }
    /**
     * permet au "écouteurs" de se désenregistrer auprès du controleur de la vue
     * @param controllerChangeEvent
     */
    public void removeControllerChangeListener(ControllerChangeListener controllerChangeEvent){
    	listeners.remove(controllerChangeEvent);
    }

	@Override
	public void modelChanged(ModelChangeEvent modelEvent) {
		if(modelEvent.getEtatPartie().equals("encours")){	
				Case c = modelEvent.getCase();
				if(modelEvent.getEtat() == 0){
						if(c instanceof CaseVide){
							paintCaseVide();
						}else{
							if(c instanceof CaseNumerotee){
							this.matriceCase[c.getRow()][c.getCol()].setIcon(new ImageIcon(""));
							this.matriceCase[c.getRow()][c.getCol()].setFont(new Font("Timesroman",Font.BOLD,30));
							this.matriceCase[c.getRow()][c.getCol()].setText(""+((CaseNumerotee)this.model.getPartieModel().getGrille().getCase(c.getRow(),c.getCol())).getNbCaseMineeVoisines());
							}
						}
				}
				if(modelEvent.getEtat() == 1){
						InfoScore.boutonNbMine.setText(""+modelEvent.getNb());
						matriceCase[modelEvent.getRow()][modelEvent.getCol()].setText("");				
						if(c.isMarked()){
							matriceCase[modelEvent.getRow()][modelEvent.getCol()].setIcon(new ImageIcon(DemineurView.class.getResource("/sn/esp/dic2/projets/game/demineur/data/drapeau.png")));				
						}else{
							if(c.isIndecis()){
								matriceCase[modelEvent.getRow()][modelEvent.getCol()].setIcon(new ImageIcon(DemineurView.class.getResource("/sn/esp/dic2/projets/game/demineur/data/indeci.png")));
							}else{
								matriceCase[modelEvent.getRow()][modelEvent.getCol()].setIcon(new ImageIcon(DemineurView.class.getResource("/sn/esp/dic2/projets/game/demineur/data/couvert.png")));;					
							}
						}
					}
				}else{
					if(modelEvent.getEtatPartie().equals("perdue")){
					InfoScore.emoticon.setIcon(new ImageIcon(DemineurView.class.getResource("/sn/esp/dic2/projets/game/demineur/data/perdre.png")));
					for(int i = 0; i < this.model.getPartieModel().getGrille().getNbRow(); i++){
						for(int j = 0; j < this.model.getPartieModel().getGrille().getNbCol(); j++){
							if(!this.model.getPartieModel().getGrille().getCase(i,j).isCaseCouvert() && !this.model.getPartieModel().getGrille().getCase(i,j).isMarked() && !this.model.getPartieModel().getGrille().getCase(i,j).isIndecis() && this.model.getPartieModel().getGrille().getCase(i,j) instanceof CaseMinee ){
								this.matriceCase[i][j].setIcon(new ImageIcon(DemineurView.class.getResource("/sn/esp/dic2/projets/game/demineur/data/mine.png")));
							}
						}
					}
					URL url = DemineurView.class.getResource("/sn/esp/dic2/projets/game/demineur/data/bombe.wav");
					AudioClip ac = Applet.newAudioClip(url);
					ac.play();
					JOptionPane.showMessageDialog(null,"Vous avez perdu", "Over Game",JOptionPane.INFORMATION_MESSAGE);
					ac.stop();
				}
				else{
					if(modelEvent.getEtatPartie().equals("gagne")){
						if(modelEvent.getEtat() == 1){
							InfoScore.boutonNbMine.setText(""+modelEvent.getNb());
							if(modelEvent.getCase().isMarked()){
								matriceCase[modelEvent.getRow()][modelEvent.getCol()].setIcon(new ImageIcon(DemineurView.class.getResource("/sn/esp/dic2/projets/game/demineur/data/drapeau.png")));				
							}else{
								if(modelEvent.getCase().isIndecis()){
									matriceCase[modelEvent.getRow()][modelEvent.getCol()].setIcon(new ImageIcon(DemineurView.class.getResource("/sn/esp/dic2/projets/game/demineur/data/indeci.png")));
								}else{
									matriceCase[modelEvent.getRow()][modelEvent.getCol()].setIcon(new ImageIcon(DemineurView.class.getResource("/sn/esp/dic2/projets/game/demineur/data/couvert.png")));
								}
							}
						}else{
							paintCaseVide();
						}
						URL url = DemineurView.class.getResource("/sn/esp/dic2/projets/game/demineur/data/Bravo1.wav");
						AudioClip ac = Applet.newAudioClip(url);
						ac.play();
						JOptionPane.showMessageDialog(null,"Bravo!! Vous avez gagné votre score est "+modelEvent.getSc(), "Bravo",JOptionPane.INFORMATION_MESSAGE);
						ac.stop();
					}else{
						if(modelEvent.getEtatPartie().equals("finie")){
							JOptionPane.showMessageDialog(null,"Jeu Terminé!! Veillez rejouer", "End",JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}
		}
	
	
	
	/**
	 *Cette méthode permet de reculer la vue à un état antérieur 
	 *@param modelEvent
	 */
	public void reculmodelChanged(ModelChangeEvent modelEvent) {
		if(modelEvent.getEtatPartie().equals("encours")){	
				Case c = modelEvent.getCase();
				if(modelEvent.getEtat() == 0){
						if(c instanceof CaseVide){
							repaintCaseVide();	
						}else{
							if(c instanceof CaseNumerotee){
								this.matriceCase[c.getRow()][c.getCol()].setText("");
								this.matriceCase[c.getRow()][c.getCol()].setIcon(new ImageIcon(DemineurView.class.getResource("/sn/esp/dic2/projets/game/demineur/data/couvert.png")));
							}
						}
				}
				if(modelEvent.getEtat() == 1){
						InfoScore.boutonNbMine.setText(""+modelEvent.getNb());
						matriceCase[modelEvent.getRow()][modelEvent.getCol()].setText("");				
						if(c.isMarked()){
							matriceCase[modelEvent.getRow()][modelEvent.getCol()].setIcon(new ImageIcon(DemineurView.class.getResource("/sn/esp/dic2/projets/game/demineur/data/drapeau.png")));				
						}else{
							if(c.isIndecis()){
								matriceCase[modelEvent.getRow()][modelEvent.getCol()].setIcon(new ImageIcon(DemineurView.class.getResource("/sn/esp/dic2/projets/game/demineur/data/indeci.png")));
							}else{
								matriceCase[modelEvent.getRow()][modelEvent.getCol()].setIcon(new ImageIcon(DemineurView.class.getResource("/sn/esp/dic2/projets/game/demineur/data/couvert.png")));					
							}
						}
					}
				}
		}
	
	
	/**
	 * Cette méthode permet de readapter la vue si on click sur une case vide 
	 * On découvert les cases voisines vide et non minées récursivement
	 */
	protected void paintCaseVide(){
		for(int i = 0; i < this.model.getPartieModel().getGrille().getNbRow(); i++){
			for(int j = 0; j < this.model.getPartieModel().getGrille().getNbCol(); j++){
				if(!this.model.getPartieModel().getGrille().getCase(i,j).isCaseCouvert() && !(this.model.getPartieModel().getGrille().getCase(i,j) instanceof CaseMinee)){//si la case est découverte
					if(this.model.getPartieModel().getGrille().getCase(i,j) instanceof CaseNumerotee){
						this.matriceCase[i][j].setIcon(new ImageIcon(""));
						this.matriceCase[i][j].setFont(new Font("Timesroman",Font.BOLD,30));
						this.matriceCase[i][j].setText(""+((CaseNumerotee)this.model.getPartieModel().getGrille().getCase(i,j)).getNbCaseMineeVoisines());
					}else
					{
						this.matriceCase[i][j].setIcon(new ImageIcon(""));
					}
				}
			}
		}
	}
	
	/**
	 * Cette méthode permet de recouvrir les cases couvertes
	 * Elle sert à reculer le jeu d'un état antérieur
	 */
	protected void repaintCaseVide(){
		for(int i = 0; i < this.model.getPartieModel().getGrille().getNbRow(); i++){
			for(int j = 0; j < this.model.getPartieModel().getGrille().getNbCol(); j++){
				if(this.model.getPartieModel().getGrille().getCase(i,j).isCaseCouvert()){//si la case est découverte
					this.matriceCase[i][j].setText("");
					this.matriceCase[i][j].setIcon(new ImageIcon(DemineurView.class.getResource("/sn/esp/dic2/projets/game/demineur/data/couvert.png")));
				}
			}
		}
	}
	/**
	 * Changement de l'arrier plan du jeu
	 * @param g
	 */
	@Override
    public void paintComponent(Graphics g) {
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
    }
	
	//********** Tout ce qui suit concerne les getteurs et les setteurs *******
	
	public void setCase(Case c,int i,int j){
		matriceCase[i][j].setCaseModel(c); 
	}
	
	public CaseView getCase(int i,int j){
		return matriceCase[i][j];
	}
	public DemineurModel getModel() {
		return model;
	}

	public void setModel(DemineurModel model) {
		this.model = model;
	}

	public CaseView[][] getMatriceCase() {
		return matriceCase;
	}

	public void setMatriceCase(CaseView[][] matriceCase) {
		this.matriceCase = matriceCase;
	}

	public ArrayList<ControllerChangeListener> getListeners() {
		return listeners;
	}

	public void setListeners(ArrayList<ControllerChangeListener> listeners) {
		this.listeners = listeners;
	}
	public Image getBg() {
		return bg;
	}
	public void setBg(Image bg) {
		this.bg = bg;
	}
	
}
package sn.esp.dic2.projets.game.demineur.view;

import java.awt.event.ActionEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import sn.esp.dic2.projets.game.demineur.controller.ControllerChangeEvent;
import sn.esp.dic2.projets.game.demineur.controller.ModelChangeListener;
import sn.esp.dic2.projets.game.demineur.model.DemineurModel;
import sn.esp.dic2.projets.game.demineur.model.XMLTools;

public class MesActionsAbstract extends AbstractAction{ 
	private static final long serialVersionUID = 1L;
	private String nom = null;
	private DemineurMainWindow parent;
	private int niveauCourant;
	protected static final int DEBUTANT = 0;
	protected static final int INTERMEDIARE = 1;
	private static final int EXPERT = 2; 
	
	/**
	 * Constructeur d'une Action Abstraite
	 * @param nom
	 * @param parent
	 * @param niveauCourant
	 */
    public MesActionsAbstract (String nom,DemineurMainWindow parent,int niveauCourant){ 
        super (nom) ;
        this.nom = nom;
        this.parent = parent;
        this.niveauCourant = niveauCourant;
    }
    
    @Override
    public void actionPerformed (ActionEvent e){ 
    	if(this.nom.equals("Rejouer la partie")){
    		DemineurModel model = new DemineurModel(parent.getModel().getPartieModel().getLevel());
    		DemineurView view = new DemineurView(model.getPartieModel().getGrille().getNbRow(),model.getPartieModel().getGrille().getNbCol(),model);
    		parent.setVisible(false);
    		parent.dispose();
    		System.gc();
    		parent = new DemineurMainWindow(view, model);
    	}
    	if(this.nom.equals("Reculer")){
    		if(!parent.getModel().reculer()){
        		JOptionPane.showMessageDialog(null, "Aucun etat antérieur");
    		}
    	}
    	if(this.nom.equals("Repartir")){
    		if(!parent.getModel().repartir()){
        		JOptionPane.showMessageDialog(null, "Aucun etat postérieur");
    		}
    	}
    	if(this.nom.equals("Reprendre Partie")){
    		DemineurModel  modelCourant = this.parent.getModel();
    		DemineurModel modelcharge = null;
    		JFileChooser fileChooser = new JFileChooser();
    		int interv = fileChooser.showOpenDialog(fileChooser);
    		if(interv == JFileChooser.APPROVE_OPTION ){
				String nameFile = fileChooser.getSelectedFile().getName();
				try {
					modelcharge = XMLTools.decodeFromFile(nameFile);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//modelcharge = (DemineurModel)BeanSerial.deserialiser(nameFile);
				modelcharge.setListeners(modelCourant.getListeners());
				modelcharge.setNext(modelCourant.getNext());
				modelcharge.setPrevious(modelCourant.getPrevious());
				DemineurView viewcharger = new DemineurView(modelcharge.getPartieModel().getGrille().getNbRow(),modelcharge.getPartieModel().getGrille().getNbCol(),modelcharge);
			    modelcharge.addModelChangeListener(viewcharger);
			    viewcharger.addControllerChangeListener(modelcharge);
			    viewcharger.paintCaseVide();
				this.parent.dispose();
				this.parent = null;
	    		System.gc();
				this.parent = new DemineurMainWindow(viewcharger, modelcharge);
				this.parent.getView().paintCaseVide();
    		}else{
    			;
    		}
    	}
    	if(this.nom.equals("Enregistré Partie")){
    		DemineurModel model = this.parent.getModel();
    		ArrayList<ModelChangeListener> modelListeners = null;
    		Stack<ControllerChangeEvent> next = null;
    		Stack<ControllerChangeEvent> previous = null;
    		File fileScore = null;
    		String nameFile = JOptionPane.showInputDialog("Nom de la partie");
    		if(nameFile != null){
				modelListeners = model.getListeners();
				fileScore = model.getScoreFile();
				next = model.getNext();
				previous = model.getPrevious();
				// on enlève les listeners,les stack et les fichiers
				model.setListeners(null);
				model.setScoreFile(null);
				model.setNext(null);
				model.setPrevious(null);
				try {
					XMLTools.encodeToFile(model, nameFile);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				//on remet les attributs enlevés
				model.setListeners(modelListeners);
				model.setScoreFile(fileScore);
				model.setNext(next);
				model.setPrevious(previous);
    		}else{
    			JOptionPane.showMessageDialog(null, "impossible de sauvegarder","Erreur",JOptionPane.ERROR_MESSAGE);
    		}
    	}
    	
    	if(this.nom.equals("A Propos")){
    		JOptionPane.showMessageDialog(null, "Equipe développeur : "
    				+ "\n Ndeye Saynabou Ba"
    				+ "\n Ass Malick Diop"
    				+ "\n Ousmane Gueye"
    				+ "\n Ngagne Thiam"
    				+ "\n    vs 1.0");
    	}
    	if(this.nom.equals("Quitter")){
    		int rest = JOptionPane.showConfirmDialog(null, "Voullez-vous vraiment quitter ?","Confirmation",JOptionPane.YES_NO_OPTION);
    		if(rest == JOptionPane.OK_OPTION){
    			parent.setVisible(false);
    			parent.dispose();
        		System.gc();
    		}
    	}
    	if(this.nom.equals("Débutant") && this.niveauCourant != DEBUTANT){
    		DemineurModel model = new DemineurModel(DEBUTANT);
    		DemineurView view = new DemineurView(model.getPartieModel().getGrille().getNbRow(),model.getPartieModel().getGrille().getNbCol(),model);
    		parent.setVisible(false);
    		parent.dispose();
    		System.gc();
    		parent = new DemineurMainWindow(view, model);
    	}
    	if(this.nom.equals("Intermediaire") && this.niveauCourant != INTERMEDIARE){
	    		DemineurModel model = new DemineurModel(INTERMEDIARE);
	    		DemineurView view = new DemineurView(model.getPartieModel().getGrille().getNbRow(),model.getPartieModel().getGrille().getNbCol(),model);
	    		parent.setVisible(false);
	    		parent.dispose();
	    		System.gc();
	    		parent = new DemineurMainWindow(view, model);
	    }
	    if(this.nom.equals("Expert") && this.niveauCourant != EXPERT){
	    		DemineurModel model = new DemineurModel(EXPERT);
	    		DemineurView view = new DemineurView(model.getPartieModel().getGrille().getNbRow(),model.getPartieModel().getGrille().getNbCol(),model);
	    		parent.setVisible(false);
	    		parent.dispose();
	    		System.gc();
	    		parent = new DemineurMainWindow(view, model);
	   	}
     }
}
package sn.esp.dic2.projets.game.demineur.view;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import sn.esp.dic2.projets.game.demineur.controller.ControllerChangeEvent;
import sn.esp.dic2.projets.game.demineur.model.Case;
import sn.esp.dic2.projets.game.demineur.model.CaseMinee;
import sn.esp.dic2.projets.game.demineur.model.CaseNumerotee;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * La classe CaseView représente une case de la grille
 * @author groupe 8
 *
 */
public class CaseView extends JButton {
	/*  ligne de la case sur la grille */
	private int row ;
	/*colone de la case sur la grille*/
	private int col ;
	/*reference à la vue du Demineur global*/
	private DemineurView view;
	/*representation de la case dans le model*/
	private Case caseModel ;
	//etats de la case
	private final int REVEALED = 0;
	private final int MARKED = 1;
	
	public CaseView(){}
	/**
	 * Création d'une Case
	 * @param col la colonne de la case
	 * @param row la ligne de la case
	 * @param view la vue du Démineur
	 * @param caseModel le modèle de la case
	 */
	public CaseView(int row, int col, DemineurView view, Case caseModel){
		this.view = view;
		this.col = col;
		this.row = row;
		this.caseModel = caseModel;
		this.addMouseListener(new CaseController());
	}
	
	
	/**
	 * Classe interne CaseController dans la classe CaseView réagissant aux
	*	événements de la souris.
	*/
	private class CaseController implements MouseListener{
		
		public CaseController() {
		}
		@Override
		public void mouseClicked(java.awt.event.MouseEvent e) {
			ControllerChangeEvent cce ;
			if(e.getButton() == MouseEvent.BUTTON1){
				//boutton gauche
				cce = new ControllerChangeEvent(CaseView.this,row,col,REVEALED);
				view.fireControllerChange(cce);
			}
			
			if(e.getButton() == MouseEvent.BUTTON3){
				cce = new ControllerChangeEvent(CaseView.this,row,col,MARKED);
				view.fireControllerChange(cce);
			}

		}

		@Override
		public void mouseEntered(java.awt.event.MouseEvent e) {
			
		}

		@Override
		public void mouseExited(java.awt.event.MouseEvent e) {
			
		}

		@Override
		public void mousePressed(java.awt.event.MouseEvent e) {
			InfoScore.emoticon.setIcon(new ImageIcon(CaseView.class.getResource("/sn/esp/dic2/projets/game/demineur/data/click1.png"))); 
		}

		@Override
		public void mouseReleased(java.awt.event.MouseEvent e) {
			InfoScore.emoticon.setIcon(new ImageIcon(CaseView.class.getResource("/sn/esp/dic2/projets/game/demineur/data/gagne.png"))); 
		}
		
	}
	
	//*********** getteurs/setteurs  ********
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public DemineurView getView() {
		return view;
	}

	public void setView(DemineurView view) {
		this.view = view;
	}

	public Case getCaseModel() {
		return caseModel;
	}

	public void setCaseModel(Case caseModel) {
		this.caseModel = caseModel;
	}

	public int getREVEALED() {
		return REVEALED;
	}

	public int getMARKED() {
		return MARKED;
	}
	
	

}

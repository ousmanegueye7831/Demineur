package sn.esp.dic2.projets.game.demineur.controller;

import javax.swing.event.ChangeEvent;
import sn.esp.dic2.projets.game.demineur.view.CaseView;

public class ControllerChangeEvent extends ChangeEvent {
	private static final long serialVersionUID = 1L;
	private int row;
	private int col;
	private int etat;
	private CaseView caseView;
	
	public ControllerChangeEvent(CaseView cv,int row,int col,int etat) {
		super(cv);
		this.caseView = cv;
		this.row = row;
		this.col = col;
		this.etat = etat;
	}
	public CaseView getCaseView() {
		return caseView;
	}

	public void setCaseView(CaseView caseView) {
		this.caseView = caseView;
	}

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
	public int getEtat() {
		return etat;
	}

	public void setEtat(int etat) {
		this.etat = etat;
	}
	@Override
	public String toString() {
		return "ControllerChangeEvent [row=" + row + ", col=" + col + ", etat="
				+ etat + "]";
	}
	
	
}

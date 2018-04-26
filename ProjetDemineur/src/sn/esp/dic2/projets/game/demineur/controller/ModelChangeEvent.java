package sn.esp.dic2.projets.game.demineur.controller;

import javax.swing.event.ChangeEvent;
import sn.esp.dic2.projets.game.demineur.model.Case;

public class ModelChangeEvent extends ChangeEvent{
	private static final long serialVersionUID = 1L;
	private int row;
	private int col;
	private int etat;
	private int nb;
	private int sc;
	private Case c;
	private String etatPartie;
	
	public ModelChangeEvent(Case c,int row,int col,int etat,int nb,int sc,String etatPartie) {
		super(c);
		this.row = row;
		this.col = col;
		this.etat = etat;
		this.c = c; 
		this.nb= nb;
		this.sc = sc;
		this.etatPartie = etatPartie;
	}
	
	//************* getteurs/setteurs  **************
	public int getSc() {
		return sc;
	}
	public void setSc(int sc) {
		this.sc = sc;
	}
	public int getNb() {
		return nb;
	}
	public void setNb(int nb) {
		this.nb= nb;
	}
	public Case getC() {
		return c;
	}
	public void setC(Case c) {
		this.c = c;
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
	public Case getCase() {
		return c;
	}
	public void setCase(Case c) {
		this.c = c;
	}
	public String getEtatPartie() {
		return etatPartie;
	}
	public void setEtatPartie(String etatPartie) {
		this.etatPartie = etatPartie;
	}
	
	

}

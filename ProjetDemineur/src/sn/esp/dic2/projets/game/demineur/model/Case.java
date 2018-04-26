package sn.esp.dic2.projets.game.demineur.model;

import javax.swing.JButton;

public class Case extends JButton{
	private static final long serialVersionUID = 1L;
	private int row;
	private int col;
	private boolean caseCouvert = true;//toutes case est couverte par défaut
	private boolean marked = false;  //par défaut toutes les cases sont non marquées
	private boolean indecis = false; //et sont non indécis
	
	/**
	 * Constructeur de case par défaut
	 */
	public Case(){}
	/**
	 * Constructeur de case de coordonnées row et col
	 * @param row
	 * @param col
	 */
	public Case(int row,int col){
		this.row = row;
		this.col = col;
	}
	
	//*********** getteurs/setteurs ************
	public boolean isIndecis() {
		return indecis;
	}
	
	public Case getCase(int row,int col){
		Case cas = new Case(row,col);
		return cas;
	}
	public void setIndecis(boolean indecis) {
		this.indecis = indecis;
	}
	public boolean isMarked() {
		return marked;
	}
	public void setMarked(boolean marked) {
		this.marked = marked;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public boolean isCaseCouvert() {
		return caseCouvert;
	}
	public void setCaseCouvert(boolean caseCouvert) {
		this.caseCouvert = caseCouvert;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}

package sn.esp.dic2.projets.game.demineur.model;

import javax.swing.JButton;

public class Case extends JButton{
	private static final long serialVersionUID = 1L;
	private int row;
	private int col;
	private boolean caseCouvert = true;//toutes case est couverte par d�faut
	private boolean marked = false;  //par d�faut toutes les cases sont non marqu�es
	private boolean indecis = false; //et sont non ind�cis
	
	/**
	 * Constructeur de case par d�faut
	 */
	public Case(){}
	/**
	 * Constructeur de case de coordonn�es row et col
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

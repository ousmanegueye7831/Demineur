package sn.esp.dic2.projets.game.demineur.model;


public class Grille{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int nbCaseInit ;
	private int nbCaseMinees;
	private int nbCaseDecouvert = 0;
	private int nbCol ;
	private int nbRow ;
	private Case matriceCase[][]  ;
	private int nbMineeMarket = 0;//ajouter pour savoir si toutes les mines sont marquées
	
	/**
	 * Constructeur par défaut
	 */
	public Grille(){}
	/**
	 * Constructeur d'un grille qui est un ensemble de cases
	 * @param nbCaseMinee
	 * @param nbRow
	 * @param nbCol
	 */
	public Grille(int nbCaseMinee,int nbRow, int nbCol){
		this.nbCaseMinees = nbCaseMinee;
		this.nbCol = nbCol;
		this.nbRow = nbRow;
		this.nbCaseInit = nbRow*nbCol ;
		this.matriceCase = new Case[nbRow][nbCol];
	}
	
	//****** getteurs/setteurs ************
	public Case[][] getMatriceCase() {
		return matriceCase;
	}
	
	public void setMatriceCase(Case[][] matriceCase) {
		this.matriceCase = matriceCase;
	}
	
	public int getNbCol() {
		return nbCol;
	}

	public void setNbCol(int nbCol) {
		this.nbCol = nbCol;
	}

	public int getNbRow() {
		return nbRow;
	}

	public void setNbRow(int nbRow) {
		this.nbRow = nbRow;
	}	
	
	public int getNbCaseInit() {
		return nbCaseInit;
	}

	public void setNbCaseInit(int nbCaseInit) {
		this.nbCaseInit = nbCaseInit;
	}

	public int getNbCaseMinees() {
		return nbCaseMinees;
	}

	public void setNbCaseMinees(int nbCaseMinees) {
		this.nbCaseMinees = nbCaseMinees;
	}

	public int getNbCaseDecouvert() {
		return nbCaseDecouvert;
	}

	public void setNbCaseDecouvert(int nbCaseDecouvert) {
		this.nbCaseDecouvert = nbCaseDecouvert;
	}
	
	public Case getCase(int i,int j){
		return matriceCase[i][j];
	}
	
	public void setCase(Case c,int i, int j){
		matriceCase[i][j] = c;
	}

	public int getNbMineeMarket() {
		return nbMineeMarket;
	}

	public void setNbMineeMarket(int nbMineeMarket) {
		this.nbMineeMarket = nbMineeMarket;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}

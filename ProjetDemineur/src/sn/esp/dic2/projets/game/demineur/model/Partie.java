package sn.esp.dic2.projets.game.demineur.model;

import javax.swing.JLabel;

public class Partie{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int nbCaseMineesNonMark; 
	private int score ;
	private String etatPartie ;
	protected static final int DEBUTANT = 0;
	protected static final int INTERMEDIARE = 1;
	private static final int EXPERT = 2; 
	private String strLevel = null;
	private int level;
	private Grille grille;
	private Chronometre time;
	private int nombreDeCoup = 0; 
	private JLabel labelScore,labelNbCoup ;
	
	/**
	 * Constructeur de partie par défaut utilisé pour la sérialisation
	 */
	public Partie(){}
	/**
	 * Constructeur de Partie suivant un niveau 
	 * @param level
	 */
	public Partie(int level){
		this.etatPartie = "encours";
		this.level = level;
		this.score = 0;
		labelNbCoup = new JLabel(""+nombreDeCoup);
		//Grille(int nbCaseInit,int nbCaseMinee,int nbCaseDecouvert,int nbCol, int nbRow){
		switch (level) {
		case DEBUTANT:
			this.grille = new Grille(10,9,9);
			this.nbCaseMineesNonMark = 10;
			this.strLevel = "Débutant";
			break;
		
		case INTERMEDIARE:
			this.grille = new Grille(20,10,10);
			this.nbCaseMineesNonMark = 20;
			this.strLevel = "Intermediaire";
			break;
			
		case EXPERT:
			this.grille = new Grille(50,14,14);
			this.nbCaseMineesNonMark = 50;
			this.strLevel = "Expert";
			break;
			
		default:
			this.grille = new Grille(10,9,9);
			this.strLevel = "Débutant";
			this.nbCaseMineesNonMark = 10;
			break;
		}
		this.time = new Chronometre(0);
		labelScore = new JLabel(""+nbCaseMineesNonMark);
	}
	
	//**************** getteurs / setteurs *************
	
	public JLabel getLabelNbCoup() {
		return labelNbCoup;
	}
	public void setLabelNbCoup(JLabel labelNbCoup) {
		this.labelNbCoup = labelNbCoup;
	}
	
	public int getNbCaseMineesNonMark() {
		return nbCaseMineesNonMark;
	}
	public void setNbCaseMineesNonMark(int nbCaseMineesNonMark) {
		this.nbCaseMineesNonMark = nbCaseMineesNonMark;
	}
	public String getEtatPartie() {
		return etatPartie;
	}
	public void setEtatPartie(String etatPartie) {
		this.etatPartie = etatPartie;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	public Grille getGrille() {
		return grille;
	}

	public void setGrille(Grille grille) {
		this.grille = grille;
	}

	public Chronometre getTime() {
		return time;
	}

	public void setTime(Chronometre time) {
		this.time = time;
	}
	
	public JLabel getLabelScore() {
		return labelScore;
	}
	public void setLabelScore(JLabel labelScore) {
		this.labelScore = labelScore;
	}
	public int getNombreDeCoup() {
		return nombreDeCoup;
	}
	public void setNombreDeCoup(int nombreDeCoup) {
		this.nombreDeCoup = nombreDeCoup;
	}
	public static int getDebutant() {
		return DEBUTANT;
	}
	public static int getIntermediare() {
		return INTERMEDIARE;
	}
	public static int getExpert() {
		return EXPERT;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getStrLevel() {
		return strLevel;
	}
	public void setStrLevel(String strLevel) {
		this.strLevel = strLevel;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}	
}

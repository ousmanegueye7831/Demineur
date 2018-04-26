package sn.esp.dic2.projets.game.demineur.model;

public class CaseNumerotee extends Case{
	private static final long serialVersionUID = 1L;
	private int nbCaseMineeVoisines;		

	public CaseNumerotee(){}
	
	public CaseNumerotee(int i,int j){
		super(i,j);
	}
	
	public int getNbCaseMineeVoisines() {
		return nbCaseMineeVoisines;
	}

	public void setNbCaseMineeVoisines(int nbCaseMineeVoisines) {
		this.nbCaseMineeVoisines = nbCaseMineeVoisines;
	}
	
}

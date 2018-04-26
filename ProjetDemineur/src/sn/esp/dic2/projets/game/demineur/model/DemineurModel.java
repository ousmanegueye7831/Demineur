package sn.esp.dic2.projets.game.demineur.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import javax.swing.event.ChangeEvent;

import sn.esp.dic2.projets.game.demineur.controller.ControllerChangeEvent;
import sn.esp.dic2.projets.game.demineur.controller.ControllerChangeListener;
import sn.esp.dic2.projets.game.demineur.controller.ModelChangeEvent;
import sn.esp.dic2.projets.game.demineur.controller.ModelChangeListener;
/**
 * La classe DemineurModel represente le modele du démineur
 * @author groupe 8
 *
 */
public class DemineurModel implements ControllerChangeListener{
	private static final long serialVersionUID = 1L;
	private static Partie partieModel ;
	
	private ArrayList<ModelChangeListener> listeners = new ArrayList<ModelChangeListener>();
	
	private ArrayList<Point> listeCaseVide = new ArrayList<Point>();
	private ArrayList<Point> listCases = new ArrayList<Point>();
	private ArrayList<Point> listeCaseNumerotees = new ArrayList<Point>();
	private Stack<ControllerChangeEvent> next = new Stack<ControllerChangeEvent>() ;
	private Stack<ControllerChangeEvent>  previous = new Stack<ControllerChangeEvent>() ;
	
	private File scoreFile = null;
	private int nbCaseMinee ;
	

	private final int REVEALED = 0;
	private final int MARKED = 1;
	
	/**
	 * Constructeur par défaut utiliser pour la sérialisation
	 */
	public DemineurModel(){
	}
	/**
	 * Constructeur de DemineurModel representant le model du démineur
	 * @param levelPartie
	 */
	public DemineurModel(int levelPartie){
		partieModel = new Partie(levelPartie);
		this.nbCaseMinee  = this.partieModel.getGrille().getNbCaseMinees();
		genererListeIndicesCase();
		placerCases();
	}

	/**
	 * Cette méthode permet de placer les différentes types de cases dans la grille 
	 * On utilisera le concepte de polymorphisme pour cela.
	 * En effet les cases minées, numérotées et vides sont toutes des cases.
	 */
	public void placerCases(){
		
		//on place d'abord les cases minées
		Iterator listCasesIt = listCases.iterator();
		while(listCasesIt.hasNext()){
			Point p = (Point) listCasesIt.next();
			CaseMinee caseMinee = new CaseMinee(p.getI(), p.getJ());
			this.partieModel.getGrille().setCase(caseMinee, p.getI(), p.getJ());
		}
		
		 // on place les case numérotées
		Iterator listCasesNumIt = listeCaseNumerotees.iterator() ;
		while(listCasesNumIt.hasNext()){
			Point p = (Point) listCasesNumIt.next();
			CaseNumerotee caseNum = new CaseNumerotee(p.getI(), p.getJ());
			caseNum.setNbCaseMineeVoisines(getNombreCaseMinees(p.getI(), p.getJ()));
			this.partieModel.getGrille().setCase(caseNum, p.getI(), p.getJ());
		}
		
		
		 // En fin les cases vides
		Iterator listeCaseVideIt = listeCaseVide.iterator() ;
		while(listeCaseVideIt.hasNext()){
			Point p = (Point) listeCaseVideIt.next();
			CaseVide caseVide = new CaseVide(p.getI(), p.getJ());
			this.partieModel.getGrille().setCase(caseVide, p.getI(), p.getJ());
		}
	}	
	
	/**
	 * Cette méthode permet d'avoir, pour une case numérotée de coordonnées row et col, le nombre de case minées adjacentess
	 * @param row
	 * @param col
	 * @return
	 */
	public int getNombreCaseMinees(int row,int col){
		int nb = 0;
		for(int i = row -1; i <= row + 1; i++){
			for(int j = col - 1; j <= col + 1; j++){
				Point p = new Point(i,j);
				if(indicesCaseValable(i, j) 
					&& estDans(listCases, p)){
					nb++;
				}
			}
		}
		return nb;
	}
	
	
	/**
	 * Cette méthode va nous permetre de generer les différentes positions des cases minées,numérotées et vides dans la grille
	 * @return
	 */
	public void genererListeIndicesCase(){
		//int i = (int)((max - min)*Math.random() + min) formulle mathématique permettant d'avoir un
		//nombres aléatoir comprit ent min et max
		
		//La fonction estDans() est définie en bas. Elle vérifie si un point est dans un ArrayList
		int k = 0;
		while( k < nbCaseMinee){
			int i = (int)((partieModel.getGrille().getNbCol() - 0)*Math.random());
			int j = (int)((partieModel.getGrille().getNbRow() - 0)*Math.random());
			Point p = new Point(i,j);
			if(!estDans(listCases, p)){
				listCases.add(p);
				k++;
			}
		}
		int m = 0; //compteur des cases numérotées
			Iterator it = listCases.iterator() ;
			while(it.hasNext()){
				Point p ;
				Point point = (Point)it.next() ;
				if(indicesCaseValable(point.getI()-1, point.getJ()-1)){
					p = new Point(point.getI()-1, point.getJ()-1) ;
					if(!estDans(listeCaseNumerotees, p)
						&& !estDans(listCases, p)	){
						listeCaseNumerotees.add(p);
						m++;
					}
					p = new Point(point.getI()-1, point.getJ()) ;
					if(!estDans(listeCaseNumerotees, p)
							&& !estDans(listCases, p)){
						listeCaseNumerotees.add(p);
						m++;
					}
					 p = new Point(point.getI(), point.getJ()-1) ;
					if(!estDans(listeCaseNumerotees,p)
							&& !estDans(listCases, p)){
						m++;
						listeCaseNumerotees.add(p);
					}
				}
				p = new Point(point.getI()+1, point.getJ()-1) ;
				if(indicesCaseValable(point.getI()+1, point.getJ()-1)){
					if(!estDans(listeCaseNumerotees,p)
							&& !estDans(listCases, p)){
						m++;
						listeCaseNumerotees.add(p);
					}
					p = new Point(point.getI()+1, point.getJ()) ;
					if(!estDans(listeCaseNumerotees,p)
							&& !estDans(listCases, p)){
						m++;
						listeCaseNumerotees.add(p);
					}
					p = new Point(point.getI(), point.getJ()-1) ; 
					if(!estDans(listeCaseNumerotees,p)
							&& !estDans(listCases, p)){
						m++;
						listeCaseNumerotees.add(p);
					}
				}
				p = new Point(point.getI()-1, point.getJ()+1) ;
				if(indicesCaseValable(point.getI()-1, point.getJ()+1)){
					if(!estDans(listeCaseNumerotees,p)
							&& !estDans(listCases, p)){
						listeCaseNumerotees.add(p);
						m++;
					}
					p = new Point(point.getI()-1, point.getJ());
					if(!estDans(listeCaseNumerotees,p)
							&& !estDans(listCases, p)){
						listeCaseNumerotees.add(p);
						m++;
					}
					p = new Point(point.getI(), point.getJ()+1) ;
					if(!estDans(listeCaseNumerotees,p)
							&& !estDans(listCases, p)){
						listeCaseNumerotees.add(p);
						m++;
					}
				}
				p = new Point(point.getI()+1, point.getJ()+1); 
				if(indicesCaseValable(point.getI()+1, point.getJ()+1)){
					if(!estDans(listeCaseNumerotees,p)
							&& !estDans(listCases, p)){
						listeCaseNumerotees.add(p);
						m++;
					}
					p = new Point(point.getI()+1, point.getJ()) ;
					if(!estDans(listeCaseNumerotees,p)
							&& !estDans(listCases, p)){
						listeCaseNumerotees.add(p);
						m++;
					}
					p = new Point(point.getI(), point.getJ()+1) ; 
					if(!estDans(listeCaseNumerotees,p)
							&& !estDans(listCases, p)){
						listeCaseNumerotees.add(p);
						m++;
					}
				}
			}
		
		//les poistions restantes non encore occupées sont occupées par des cases vides
		for(int i = 0; i < this.partieModel.getGrille().getNbRow(); i++){
			for(int j = 0; j < this.partieModel.getGrille().getNbCol(); j++){
				Point p = new Point(i,j);
				if(		   !estDans(listCases,p) 
						&& !estDans(listeCaseNumerotees, p)
						&& !estDans(listeCaseVide, p))
				{
					listeCaseVide.add(p);				
				}
			}
		}		
	}
	
	
	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void controllerChanged(ControllerChangeEvent controllerChangeEvent){
		if(this.partieModel.getEtatPartie() == "encours")
			next.add(controllerChangeEvent);
		
		/*
		 * On comptera les cases découvertes. Une case marquée ou indécis n'est pas découverte.
		 * Une case numéroté et découverte est comptée tout comme une case vide
		 * On comptera aussi les cases minées et marquées. 
		 * Si on marque toutes les mines ou découvrert toutes autres cases le jeu termine et on gagne.
		 */
		Case c = partieModel.getGrille().getCase(controllerChangeEvent.getRow(), controllerChangeEvent.getCol());
		if(this.partieModel.getNbCaseMineesNonMark() <= 0 ){
			this.partieModel.setNbCaseMineesNonMark(0);
			this.partieModel.setEtatPartie("finie");
			ModelChangeEvent modelChangeEvent = new ModelChangeEvent(c, controllerChangeEvent.getRow(), controllerChangeEvent.getCol(),controllerChangeEvent.getEtat(),this.partieModel.getNbCaseMineesNonMark(),this.partieModel.getScore(),this.partieModel.getEtatPartie());
			this.fireModelChange(modelChangeEvent);
		}
		else{
			if (controllerChangeEvent.getEtat() == REVEALED) {
				this.partieModel.setNombreDeCoup(this.partieModel.getNombreDeCoup()+1);
				if(c instanceof CaseMinee){
					this.partieModel.setEtatPartie("perdue");
					this.partieModel.getTime().cancel();
					decouvertCaseMinees();
				}else{
					if(c instanceof CaseVide){
						if(c.isCaseCouvert())
							c.setMarked(false);
							c.setIndecis(false);
							decouvertCasesVoisines(c.getRow(),c.getCol()) ;
					}
					else{
						//Case numeroté
						if(c.isCaseCouvert()){
							c.setCaseCouvert(false);
							c.setMarked(false);
							c.setIndecis(false);
							this.partieModel.getGrille().setNbCaseDecouvert(this.partieModel.getGrille().getNbCaseDecouvert()+1);
						}
					}
				}
			}
			else{
				this.partieModel.setNombreDeCoup(this.partieModel.getNombreDeCoup()+1);
				if(c.isMarked()){ 
					c.setMarked(false);
					c.setIndecis(true);				
				}
				else{
					if(c.isIndecis()){
						c.setIndecis(false);
						c.setMarked(false);
						this.partieModel.setNbCaseMineesNonMark(this.partieModel.getNbCaseMineesNonMark()+1);
						if(c instanceof CaseMinee){
							this.partieModel.getGrille().setNbMineeMarket(this.partieModel.getGrille().getNbMineeMarket()-1);
						}
					}else{
						//premier click droit sur une case
						this.partieModel.setNbCaseMineesNonMark(this.partieModel.getNbCaseMineesNonMark()-1);
						c.setMarked(true);
						if(c instanceof CaseMinee){
							this.partieModel.getGrille().setNbMineeMarket(this.partieModel.getGrille().getNbMineeMarket()+1);
						}
					}
				}
				
			}
			if(this.partieModel.getGrille().getNbMineeMarket() == this.partieModel.getGrille().getNbCaseMinees() || ((this.partieModel.getGrille().getNbCaseInit()-this.partieModel.getGrille().getNbCaseDecouvert()) == this.partieModel.getGrille().getNbCaseMinees())){
				this.partieModel.getGrille().getCase(c.getRow(), c.getCol()).setCaseCouvert(false);
				this.partieModel.setEtatPartie("gagne");
				calculScore() ;
			}
			ModelChangeEvent modelChangeEvent = new ModelChangeEvent(c, controllerChangeEvent.getRow(), controllerChangeEvent.getCol(),controllerChangeEvent.getEtat(),this.partieModel.getNbCaseMineesNonMark(),this.partieModel.getScore(),this.partieModel.getEtatPartie());
			this.fireModelChange(modelChangeEvent);
		}
	}
	
	/**
	 * Cett méthode permet d'annuler la dernière action faite sur le jeu.
	 *  Elle retourne true si l'operatioon s'est bien déroulée
	 * @return
	 */
	public boolean reculer(){
		if(!this.next.empty() && this.partieModel.getEtatPartie() != "perdue"){
			ControllerChangeEvent cevt =  next.pop();
			this.reculerModel(cevt);
			previous.add(cevt);
			return true;
		}
		return false;
	}
	
	/**
	 * Cette méthode permet de rejouer le dernier coup sauvegarder avec reculer
	 * Elle retourne true si l'operatioon s'est bien déroulée
	 * @return
	 */
	public boolean repartir(){
		if(!this.previous.empty()){
			ControllerChangeEvent cevt = previous.pop();
			this.controllerChanged(cevt);
			next.add(cevt);
			return true;
		}
		return false;
	}
	
	/**
	 * Cette méthode fait la meme chose que controllerChanger mais dans un sens inverse
	 * Elle permet de recouvrir les cases présendément couvert et ainsi reculer l'etat du jeu
	 * @param controllerChangeEvent
	 */
	public void reculerModel(ControllerChangeEvent controllerChangeEvent){
		Case c = partieModel.getGrille().getCase(controllerChangeEvent.getRow(), controllerChangeEvent.getCol());
		if(this.partieModel.getNbCaseMineesNonMark() == 0 ){
			this.partieModel.setNbCaseMineesNonMark(1);
			this.partieModel.setEtatPartie("encours");
			c.setMarked(false);
			c.setIndecis(false);
			c.setCaseCouvert(true);
		}
		else{
			if (controllerChangeEvent.getEtat() == 0) {
				this.partieModel.setNombreDeCoup(this.partieModel.getNombreDeCoup()-1);
				if(c instanceof CaseMinee){
					couvertCaseMinees();
					this.partieModel.setEtatPartie("encours");
				}else{
					if(c instanceof CaseVide){
							couvertCasesVoisines(c.getRow(),c.getCol()) ;
					}
					else{
						//Case numeroté
						c.setCaseCouvert(true);
						this.partieModel.getGrille().setNbCaseDecouvert(this.partieModel.getGrille().getNbCaseDecouvert()-1);
					}
				}
			}
			else{
				this.partieModel.setNombreDeCoup(this.partieModel.getNombreDeCoup()+1);
				if(c.isMarked()){ 
					c.setMarked(false);
					c.setIndecis(false);
					c.setCaseCouvert(true);
					this.partieModel.setNbCaseMineesNonMark(this.partieModel.getNbCaseMineesNonMark()+1);				
				}
				else{
					if(c.isIndecis()){
						c.setIndecis(false);
						c.setMarked(true);
						if(c instanceof CaseMinee){
							this.partieModel.getGrille().setNbMineeMarket(this.partieModel.getGrille().getNbMineeMarket()-1);
						}
					}
				}
				
			}
			
			ModelChangeEvent modelChangeEvent = new ModelChangeEvent(c, controllerChangeEvent.getRow(), controllerChangeEvent.getCol(),controllerChangeEvent.getEtat(),this.partieModel.getNbCaseMineesNonMark(),this.partieModel.getScore(),this.partieModel.getEtatPartie());
			this.reculfireModelChange(modelChangeEvent);
		}
	}
	
	/**
	 * Cette méthode permet de calculer le score.
	 * Il prend en compte le nombre de cases découvertes, le nombre de case minées et le temps écoulé
	 * @param i
	 * @return
	 */
	protected void calculScore(){

		FileWriter fwt;
		PrintWriter pwt;
		
		int temps = Integer.parseInt(this.getPartieModel().getTime().getTimerLb().getText());
		int score = (this.partieModel.getGrille().getNbCaseDecouvert()*this.partieModel.getGrille().getNbCaseMinees()*10) / temps;
		this.partieModel.setScore(score);
		this.getPartieModel().getTime().getTimer().cancel();
		
		try {
			fwt = new FileWriter("/sn/esp/dic2/projets/game/demineur/data/score.txt");
			pwt = new PrintWriter(new BufferedWriter(fwt));
			
			pwt.println(score);
			pwt.flush();
			pwt.close();
			
		} catch (FileNotFoundException exp) {
				exp.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Cette méthode permet de calculer le meilleur score
	 * @return 
	 */
	public int calculBestScore(){
		int max = 0;
		return max;
	}
	
	/**
     * Notifie les écouteurs d'un changement dans du model
     * @param event l'événement à envoyer
     */
    protected void fireModelChange(ModelChangeEvent modelChangeEvent) {
	    Iterator<ModelChangeListener> iterator = listeners.iterator();
		while(iterator.hasNext()){
				iterator.next().modelChanged(modelChangeEvent);
			}
    }
    
    /**
     * notifie les écouteur d'un changement du modele mais dans son état antérieur
     * @param modelChangeEvent
     */
    protected void reculfireModelChange(ModelChangeEvent modelChangeEvent) {
	    Iterator<ModelChangeListener> iterator = listeners.iterator();
		while(iterator.hasNext()){
				iterator.next().reculmodelChanged(modelChangeEvent);
			}
    }
    /**
     * permet au "écouteurs" de s'enregistrer auprès du controlleur du model.
     * @param cce
     */
    public void addModelChangeListener(ModelChangeListener modelChangeListener){
    	listeners.add(modelChangeListener);
    }
    /**
     * permet au "écouteurs" de se désenregistrer auprès du controlleur du model
     * @param cce
     */
    public void removeModelChangeListener(ModelChangeListener modelChangeListener){
    	listeners.remove(modelChangeListener);
    }
	
	/**
	 * Cette méthode nous permet de texter si une case de coordonnées i,j est dans un arraylist.
	 * On va l'utilisé dans la génération automatique des cases.
	 * @param listPoint
	 * @param p 
	 * @return
	 */
	public boolean estDans(ArrayList<Point> listPoint,Point p){	
		boolean ok = false;
		Iterator<Point> iterator = listPoint.iterator();
		while(iterator.hasNext() && !ok){
			Point point = (Point)iterator.next();
			ok = point.equals(p);
		}
		return ok;
	}
	
	/**
	 * Cette méthode permet de reveler toutes les cases minées de la grille.
	 * Elle est appelée quand l'utilisateur clique sur une mine
	 */
	public void decouvertCaseMinees(){
		for(int i = 0; i < this.partieModel.getGrille().getNbRow(); i++){
			for(int j = 0; j < this.partieModel.getGrille().getNbCol(); j++){
				if(this.partieModel.getGrille().getMatriceCase()[i][j] instanceof CaseMinee && this.partieModel.getGrille().getMatriceCase()[i][j].isCaseCouvert()){
					this.partieModel.getGrille().getMatriceCase()[i][j].setCaseCouvert(false);
					this.partieModel.getGrille().setNbCaseDecouvert(this.partieModel.getGrille().getNbCaseDecouvert()+1);
				}
			}
		}
	}
	
	/**
	 * Cette méthode permet de recouvrir toutes les cases minées de la grille.
	 * utilisée pour reculer/repartir
	 */
	public void couvertCaseMinees(){
		for(int i = 0; i < this.partieModel.getGrille().getNbRow(); i++){
			for(int j = 0; j < this.partieModel.getGrille().getNbCol(); j++){
				if(this.partieModel.getGrille().getMatriceCase()[i][j] instanceof CaseMinee){
					this.partieModel.getGrille().getMatriceCase()[i][j].setCaseCouvert(true);
					this.partieModel.getGrille().setNbCaseDecouvert(this.partieModel.getGrille().getNbCaseDecouvert()-1);
				}
			}
		}
	} 
	
	
	/**
	 * Cette méthode teste si un indice donnée est dans la grille
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean indicesCaseValable(int i,int j){
		return ((i >= 0 && i < this.partieModel.getGrille().getNbRow()) && (j >= 0 && j < this.partieModel.getGrille().getNbCol()) ) ;
	}
	
	
	/**
	 * Cette fonction permet de découvrir une case selon certaines conditions
	 * @param row
	 * @param col
	 */
	public boolean decouvert(int i,int j){
		if(		indicesCaseValable(i,j) 
				&& !(this.partieModel.getGrille().getMatriceCase()[i][j] instanceof CaseMinee) 
				&& !(this.partieModel.getGrille().getMatriceCase()[i][j].isMarked()) 
				&& !(this.partieModel.getGrille().getMatriceCase()[i][j].isIndecis())
				&&   this.partieModel.getGrille().getMatriceCase()[i][j].isCaseCouvert()
			){
			this.partieModel.getGrille().getMatriceCase()[i][j].setCaseCouvert(false) ;
			this.partieModel.getGrille().setNbCaseDecouvert(this.partieModel.getGrille().getNbCaseDecouvert()+1);
			return true;
		}	
		return false;
	}
	
	
	/**
	 * Cette méthode sera utilisée quand l'utilisateur click sur une case vide.
	 * On devra alors découvert toutes les cases voisines non minées
	 * @param int row, int col
	 */
	public void decouvertCasesVoisines(int row, int col){
		if(decouvert(row,col) && this.partieModel.getGrille().getMatriceCase()[row][col] instanceof CaseVide){
			if(decouvert(row-1,col-1) && this.partieModel.getGrille().getMatriceCase()[row-1][col-1] instanceof CaseVide)
				decouvertCasesVoisines(row-1,col-1);
			}
			if(indicesCaseValable(row-1,col) ){
				if(decouvert(row-1,col) && this.partieModel.getGrille().getMatriceCase()[row-1][col] instanceof CaseVide)
					decouvertCasesVoisines(row-1,col);
			}
			if(indicesCaseValable(row-1,col+1)){
				if(decouvert(row-1, col+1) && this.partieModel.getGrille().getMatriceCase()[row-1][col+1] instanceof CaseVide)
					decouvertCasesVoisines(row-1,col+1);
			}
			if(indicesCaseValable(row,col-1)){
				if(decouvert(row, col-1) && this.partieModel.getGrille().getMatriceCase()[row][col-1] instanceof CaseVide)
					decouvertCasesVoisines(row,col-1);
			}
			if(indicesCaseValable(row,col+1)){
				if(decouvert(row, col+1) && this.partieModel.getGrille().getMatriceCase()[row][col+1] instanceof CaseVide)
					decouvertCasesVoisines(row,col+1);
				
			}
			if(indicesCaseValable(row+1,col-1)){
				if(decouvert(row+1, col-1) && this.partieModel.getGrille().getMatriceCase()[row+1][col-1] instanceof CaseVide)
					decouvertCasesVoisines(row+1,col-1);
			}
			if(indicesCaseValable(row+1,col)){
				if(decouvert(row+1, col) && this.partieModel.getGrille().getMatriceCase()[row+1][col] instanceof CaseVide)
					decouvertCasesVoisines(row+1,col);
			}
			if(indicesCaseValable(row+1,col+1)){
				if(decouvert(row+1, col+1) && this.partieModel.getGrille().getMatriceCase()[row+1][col+1] instanceof CaseVide)
					decouvertCasesVoisines(row+1,col+1);
			}
		}
	
	/**
	 * Classe utilisée pour recouvrir une case découverte dans certaines conditions.
	 * méthode utilisée pour reculer/avancé
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean couvert(int i,int j){
		if(		indicesCaseValable(i,j) 
				&& !(this.partieModel.getGrille().getMatriceCase()[i][j] instanceof CaseMinee) 
				&& !(this.partieModel.getGrille().getMatriceCase()[i][j].isMarked()) 
				&& !(this.partieModel.getGrille().getMatriceCase()[i][j].isIndecis())
				&&  !this.partieModel.getGrille().getMatriceCase()[i][j].isCaseCouvert()
			){
			this.partieModel.getGrille().getMatriceCase()[i][j].setCaseCouvert(true) ;
			this.partieModel.getGrille().setNbCaseDecouvert(this.partieModel.getGrille().getNbCaseDecouvert()-1);
			return true;
		}	
		return false;
	}
	
	/**
	 * Cette méthode permet de recouvir toutes les cases couvertes suite à un click sur une case vide
	 * Elle est utiliser quand la dernière action était un click sur une case vides
	 * @param int row, int col
	 */
	public void couvertCasesVoisines(int row, int col){
		if(couvert(row,col) && this.partieModel.getGrille().getMatriceCase()[row][col] instanceof CaseVide){
			if(couvert(row-1,col-1) && this.partieModel.getGrille().getMatriceCase()[row-1][col-1] instanceof CaseVide)
				couvertCasesVoisines(row-1,col-1);
			}
			if(indicesCaseValable(row-1,col) ){
				if(couvert(row-1,col) && this.partieModel.getGrille().getMatriceCase()[row-1][col] instanceof CaseVide)
					couvertCasesVoisines(row-1,col);
			}
			if(indicesCaseValable(row-1,col+1)){
				if(couvert(row-1, col+1) && this.partieModel.getGrille().getMatriceCase()[row-1][col+1] instanceof CaseVide)
					couvertCasesVoisines(row-1,col+1);
			}
			if(indicesCaseValable(row,col-1)){
				if(couvert(row, col-1) && this.partieModel.getGrille().getMatriceCase()[row][col-1] instanceof CaseVide)
					couvertCasesVoisines(row,col-1);
			}
			if(indicesCaseValable(row,col+1)){
				if(couvert(row, col+1) && this.partieModel.getGrille().getMatriceCase()[row][col+1] instanceof CaseVide)
					couvertCasesVoisines(row,col+1);
			}
			if(indicesCaseValable(row+1,col-1)){
				if(couvert(row+1, col-1) && this.partieModel.getGrille().getMatriceCase()[row+1][col-1] instanceof CaseVide)
					couvertCasesVoisines(row+1,col-1);
			}
			if(indicesCaseValable(row+1,col)){
				if(couvert(row+1, col) && this.partieModel.getGrille().getMatriceCase()[row+1][col] instanceof CaseVide)
					couvertCasesVoisines(row+1,col);
			}
			if(indicesCaseValable(row+1,col+1)){
				if(couvert(row+1, col+1) && this.partieModel.getGrille().getMatriceCase()[row+1][col+1] instanceof CaseVide)
					couvertCasesVoisines(row+1,col+1);
			}
		}	
	
	
	//********************* Tout ce qui suit concerne les getteurs et les setteurs********************
	public Partie getPartieModel() {
		return partieModel;
	}
	public void setPartieModel(Partie partieModel) {
		this.partieModel = partieModel;
	}

	public ArrayList<ModelChangeListener> getListeners() {
		return listeners;
	}

	public void setListeners(ArrayList<ModelChangeListener> listeners) {
		this.listeners = listeners;
	}
	public int getNbCaseMinee() {
		return nbCaseMinee;
	}

	public void setNbCaseMinee(int nbCaseMinee) {
		this.nbCaseMinee = nbCaseMinee;
	}

	public ArrayList<Point> getListCases() {
		return listCases;
	}

	public void setListCases(ArrayList<Point> listCases) {
		this.listCases = listCases;
	}

	public ArrayList<Point> getListeCaseVide() {
		return listeCaseVide;
	}

	public void setListeCaseVide(ArrayList<Point> listeCaseVide) {
		this.listeCaseVide = listeCaseVide;
	}

	public ArrayList<Point> getListeCaseNumerotees() {
		return listeCaseNumerotees;
	}

	public void setListeCaseNumerotees(ArrayList<Point> listeCaseNumerotees) {
		this.listeCaseNumerotees = listeCaseNumerotees;
	}

	public Stack<ControllerChangeEvent> getNext() {
		return next;
	}
	public void setNext(Stack<ControllerChangeEvent> next) {
		this.next = next;
	}
	public Stack<ControllerChangeEvent> getPrevious() {
		return previous;
	}
	public void setPrevious(Stack<ControllerChangeEvent> previous) {
		this.previous = previous;
	}
	public File getScoreFile() {
		return scoreFile;
	}
	public void setScoreFile(File scoreFile) {
		this.scoreFile = scoreFile;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public int getREVEALED() {
		return REVEALED;
	}
	public int getMARKED() {
		return MARKED;
	}
	
	@Override
	public String toString() {
		return "DemineurModel [listeners=" + listeners + ", listeCaseVide="
				+ listeCaseVide + ", listCases=" + listCases
				+ ", listeCaseNumerotees=" + listeCaseNumerotees
				+ ", nbCaseMinee=" + nbCaseMinee + "]";
	}		
}

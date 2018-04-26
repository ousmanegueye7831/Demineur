/**
 * 
 */
package sn.esp.dic2.projets.game.demineur.controller;

/**
 * l'interface ModelChangeListener permet � la vue de s'enregistrer en tant qu'�couteur aupr�s du model via le controleur
* @author groupe8
 *
 */
public interface ModelChangeListener {
	/**
	 * cette methode permet de changer la vue suivant les �v�nements envoy�s par le mod�le
	 * @param event
	 */
	public void modelChanged(ModelChangeEvent event);
	
	/**
	 * 
	 * @param modelChangeEvent
	 */
	public void reculmodelChanged(ModelChangeEvent modelChangeEvent);

}	

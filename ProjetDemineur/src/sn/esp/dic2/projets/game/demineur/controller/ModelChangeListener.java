/**
 * 
 */
package sn.esp.dic2.projets.game.demineur.controller;

/**
 * l'interface ModelChangeListener permet à la vue de s'enregistrer en tant qu'écouteur auprès du model via le controleur
* @author groupe8
 *
 */
public interface ModelChangeListener {
	/**
	 * cette methode permet de changer la vue suivant les événements envoyés par le modèle
	 * @param event
	 */
	public void modelChanged(ModelChangeEvent event);
	
	/**
	 * 
	 * @param modelChangeEvent
	 */
	public void reculmodelChanged(ModelChangeEvent modelChangeEvent);

}	

package sn.esp.dic2.projets.game.demineur.controller;

import javax.swing.event.ChangeListener;

/**
 * l'interface ControllerChangeListener permet au model de s'enregistrer en tant qu'écouteur auprès de la vue via le controleur
 * @author groupe8
 *
 */
public interface ControllerChangeListener extends ChangeListener {
	
	/**
	 * 
	 * @param cce
	 */
	void controllerChanged(ControllerChangeEvent cce);

}

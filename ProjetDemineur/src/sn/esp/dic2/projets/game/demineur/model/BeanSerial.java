package sn.esp.dic2.projets.game.demineur.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BeanSerial {
	
	public static void serialiser(Object object,String nameFile){
		try{
			// ouverture d'un flux de sortie vers le fichier "personne.serial"
			FileOutputStream fos = new FileOutputStream(nameFile+".partie");
			ObjectOutputStream oos= new ObjectOutputStream(fos);
			try {
				// sérialisation : écriture de l'objet dans le flux de sortie
				oos.writeObject(object); 
				// on vide le tampon
				oos.flush();
				System.out.println(object + " a ete serialise");
			} finally {
				//fermeture des flux
				try {
					oos.close();
				} finally {
					fos.close();
				}
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static Object deserialiser(String nameFile){
		Object object = null;
		try {
			// ouverture d'un flux d'entrée depuis le fichier "personne.serial"
			FileInputStream fis = new FileInputStream(nameFile);
			// création d'un "flux objet" avec le flux fichier
			ObjectInputStream ois= new ObjectInputStream(fis);
			
			try {	
				// désérialisation : lecture de l'objet depuis le flux d'entrée
				object =  ois.readObject(); 
			} finally {
				// on ferme les flux
				try {
					ois.close();
				} finally {
					fis.close();
				}
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		return object;
	}

}

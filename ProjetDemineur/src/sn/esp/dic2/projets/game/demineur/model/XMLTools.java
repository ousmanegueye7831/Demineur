package sn.esp.dic2.projets.game.demineur.model;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class XMLTools {

    private XMLTools() {}
	
    /**
     * Serialisation d'un objet dans un fichier
     * @param object l'objet à serialiser
     * @param filename chemin du fichier
     */
    public static void encodeToFile(DemineurModel model, String fileName) throws FileNotFoundException, IOException {
        // ouverture de l'encodeur vers le fichier
        XMLEncoder encoder = null;
        try {
            // serialisation de l'objet
            encoder = new XMLEncoder(new FileOutputStream(fileName+".xml"));
        	encoder.writeObject(model);
            encoder.flush();
        } finally {
            // fermeture de l'encodeur
            encoder.close();
        }
    }
    
    /**
     * Deserialisation d'un objet depuis un fichier
     * @param filename chemin du fichier
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static DemineurModel decodeFromFile(String fileName) throws FileNotFoundException, IOException {
        DemineurModel model = null;
        XMLDecoder decoder =null;
        // ouverture de decodeur
        
        try {
            // deserialisation de l'objet
        	decoder = new XMLDecoder(new FileInputStream(fileName));
        	model = (DemineurModel)decoder.readObject();
        } finally {
            // fermeture du decodeur
            decoder.close();
        }
        return model;
    }
}
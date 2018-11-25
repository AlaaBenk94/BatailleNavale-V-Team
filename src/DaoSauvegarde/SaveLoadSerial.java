package DaoSauvegarde;

import Model.jeu.*;

import java.io.*;

public class SaveLoadSerial extends DAOSaveLoad {
    private static SaveLoadSerial saveLoadSerial;
    private String nom="jeu.ser";

    private SaveLoadSerial(){}
    public static DAOSaveLoad getInstentce(){
      if(saveLoadSerial==null)saveLoadSerial=new SaveLoadSerial();
      return saveLoadSerial;
    }

    @Override
    public void sauvegarder(Jeu jeu, String chemin) {
        ObjectOutputStream os = null ;
        FileOutputStream file= null;
        try {
            file = new FileOutputStream(chemin+nom);
            os=new ObjectOutputStream(file);
            os.writeObject(jeu);
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Jeu charger(String chemin) {
        FileInputStream fichier = null;
        Jeu jeu=null;
        try {
            fichier = new FileInputStream(chemin+nom);
            ObjectInputStream ois = new ObjectInputStream(fichier);
            jeu = (Jeu) ois.readObject();
            fichier.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return jeu;
    }
}

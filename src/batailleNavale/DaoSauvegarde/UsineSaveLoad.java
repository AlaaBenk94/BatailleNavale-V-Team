package batailleNavale.DaoSauvegarde;

public abstract class UsineSaveLoad {

	public static DAOSaveLoad getUsineSaveLoad(String type){
		if(type.equals("xml"))return SaveLoadXML.getInstence();
		if(type.equals("ser"))return SaveLoadSerial.getInstentce();
		return null;
	}

	public abstract DAOSaveLoad getDAOSaveLoad();

}
package batailleNavale.DaoSauvegarde;

public class UsineSaveLoadXML extends UsineSaveLoad {
    private static UsineSaveLoad usineSaveLoad;
    private UsineSaveLoadXML(){};
    public static UsineSaveLoad getInstence(){
        if(usineSaveLoad==null)usineSaveLoad=new UsineSaveLoadXML();
        return usineSaveLoad;
    }
    @Override
    public DAOSaveLoad getDAOSaveLoad() {
        return SaveLoadXML.getInstence();
    }
}
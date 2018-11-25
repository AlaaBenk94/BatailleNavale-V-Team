package DaoSauvegarde;

public class UsineSaveLoadSerial extends UsineSaveLoad {
    private static UsineSaveLoad usineSaveLoad;
    private UsineSaveLoadSerial(){};
    public static UsineSaveLoad getInstence(){
        if(usineSaveLoad==null)usineSaveLoad=new UsineSaveLoadSerial();
        return usineSaveLoad;
    }
    @Override
    public DAOSaveLoad getDAOSaveLoad() {
        return SaveLoadSerial.getInstentce();
    }
}

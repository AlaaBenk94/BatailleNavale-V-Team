package batailleNavale.Model.Epoques;

import batailleNavale.Model.Bateaux.Bateau;
import batailleNavale.Model.Bateaux.BateauEP1;
import batailleNavale.Ressources;

import java.util.HashMap;
import java.util.Map;

public class Epoque1 extends Epoque {
    public  static final String NOMEPOQUE= Ressources.epoques[0];
    public  static final String[] NOMTYPES={"TYPE1","TYPE2","TYPE3"};
    public  static final int[] TYPES={2,3,5};
    public  static final int[] FORCES={2,3,5};
    public  static final int[] RESISTANCES={2,3,3};


    private static Epoque epoque1;
    private Epoque1(){};
    public static Epoque getInstance() {
        if(epoque1==null)epoque1=new Epoque1();
        return epoque1;
    }

    @Override
    public Bateau getBateau(int type) {
        if(type== TYPES[0])return new BateauEP1(NOMTYPES[0],NOMEPOQUE,TYPES[0],FORCES[0],RESISTANCES[0]);
        if(type== TYPES[1])return new BateauEP1(NOMTYPES[1],NOMEPOQUE,TYPES[1],FORCES[1],RESISTANCES[1]);
        if(type== TYPES[2])return new BateauEP1(NOMTYPES[2],NOMEPOQUE,TYPES[2],FORCES[2],RESISTANCES[2]);
        return null;
    }

    private String sep="<br>";
    @Override
    public Map<String, String> getBateuDescreption() {
        Map p=new HashMap();
        for (int i=0 ;i<NOMTYPES.length;i++){
            String key =NOMTYPES[i];
            String descreption="<html>";
            descreption+="nom :"+sep+NOMTYPES[i]+sep+sep;
            descreption+="epoque :"+sep+NOMEPOQUE+sep+sep;
            descreption+="nbr projectile :"+sep+FORCES[i]+sep+sep;
            descreption+="resistence :"+sep+RESISTANCES[i]+sep+sep;
            descreption+="taille :"+sep+TYPES[i]+sep+sep;
            descreption+="</html>";
            p.put(key,descreption);

        }
        return p;
    }

    @Override
    public String[] getBateauType() {
        return Epoque1.NOMTYPES;
    }
    @Override
    public int getBateauTaille(String nom){
        for (int i=0;i<NOMTYPES.length;i++)
            if(nom.equals(NOMTYPES[i]))return TYPES[i];
        return 0;
    }

    @Override
    public String getNom() {
        return NOMEPOQUE;
    }
}
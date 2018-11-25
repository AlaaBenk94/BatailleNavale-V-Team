package Model.Epoques;

import Model.Bateaux.Bateau;
import Model.Bateaux.BateauEP1;

public class Epoque1 extends Epoque {
    public  static final String NOMEPOQUE="EP1";
    public  static final String NOMTYPE1="TYPE1";
    public  static final String NOMTYPE2="TYPE2";
    public  static final String NOMTYPE3="TYPE3";
    public  static final int TYPE1=2;
    public  static final int TYPE2=3;
    public  static final int TYPE3=5;
    public  static final int FORCE1=2;
    public  static final int FORCE2=3;
    public  static final int FORCE3=3;
    public  static final int RESISTANCE1=2;
    public  static final int RESISTANCE2=3;
    public  static final int RESISTANCE3=3;


    private static Epoque epoque1;
    private Epoque1(){};
    public static Epoque getInstance() {
        if(epoque1==null)epoque1=new Epoque1();
        return epoque1;
    }

    @Override
    public Bateau getBateau(int type) {
       switch (type){
           case TYPE1: return new BateauEP1(NOMTYPE1,NOMEPOQUE,TYPE1,FORCE1,RESISTANCE1);
           case TYPE2: return new BateauEP1(NOMTYPE2,NOMEPOQUE,TYPE2,FORCE2,RESISTANCE2);
           case TYPE3: return new BateauEP1(NOMTYPE3,NOMEPOQUE,TYPE3,FORCE3,RESISTANCE3);
       }
       return null;
    }
}
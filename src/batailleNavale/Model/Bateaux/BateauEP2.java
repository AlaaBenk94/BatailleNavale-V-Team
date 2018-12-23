package batailleNavale.Model.Bateaux;

import java.io.Serializable;

public class BateauEP2 extends Bateau implements Serializable {
    protected BateauEP2(String nom, String epoque, int type, int force, int resistance) {
        super(nom, epoque, type, force, resistance);
    }

    public  boolean peutTirer(){
        return projectiles>0 || resistance<=0;
    }

    public  int calculeCas(int[] pos){
        int cas=-1;
        if(pos[0]==position[0][0]&&pos[0]==position[1][0]
                &&position[0][1]<=pos[1]&&pos[1]<=position[1][1]){
            return pos[1]-position[0][1];
        }
        if(pos[1]==position[0][1]&&pos[1]==position[1][1]
                &&position[0][0]<=pos[0]&&pos[0]<=position[1][0]){
            return pos[0]-position[0][0];
        }
        return cas;
    }

    public Tire tirer(int x , int y){
        Tire tire=null;
        if (peutTirer()){
            tire= new Tire(type,force,new int[]{x, y});
        }
        return tire;
    }

    public void preandFeu(Tire tire){
        int ciblecas=calculeCas(tire.getPositionCible());
        if(ciblecas>-1)cas[ciblecas]-=tire.getForce();
    }

}
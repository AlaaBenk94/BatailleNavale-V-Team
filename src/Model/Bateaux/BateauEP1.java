package Model.Bateaux;

public class BateauEP1 extends Bateau {

    public BateauEP1(String nom, String epoque, int type, int force, int resistance) {
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
        if(tire!=null) {
            this.resistance -= tire.getForce();
            int ciblecas = calculeCas(tire.getPositionCible());
            System.out.println(ciblecas);
            if (ciblecas > -1 && ciblecas < cas.length) cas[ciblecas] -= tire.getForce();
        }
    }
}
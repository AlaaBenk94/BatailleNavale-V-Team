package batailleNavale.Model.Epoques;

import batailleNavale.Model.Bateaux.Bateau;

import java.util.HashMap;
import java.util.Map;

public abstract class Epoque {
	private String siecle;

	public  static Epoque getEpoque(String siecle){
		if(siecle.equals(Epoque1.NOMEPOQUE))return Epoque1.getInstance();
		if(siecle.equals(Epoque2.NOMEPOQUE))return Epoque2.getInstance();

		return null;
	}

	public abstract Bateau getBateau(int type);
	public abstract Map<String, String> getBateuDescreption();
	public abstract String[] getBateauType();
	public abstract int getBateauTaille(String nom);
	public abstract String getNom();

}
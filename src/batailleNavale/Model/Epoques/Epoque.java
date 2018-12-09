package batailleNavale.Model.Epoques;

import batailleNavale.Model.Bateaux.Bateau;

public abstract class Epoque {
	public  static final String EP1="EP1";
	private String siecle;

	public  static Epoque getEpoque(String siecle){
		if(siecle=="1")return Epoque1.getInstance();
		if(siecle=="2")return Epoque2.getInstance();
		return null;
	}

	public abstract Bateau getBateau(int type);

}
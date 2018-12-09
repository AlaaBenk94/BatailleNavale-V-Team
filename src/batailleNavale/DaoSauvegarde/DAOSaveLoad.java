package batailleNavale.DaoSauvegarde;

import batailleNavale.Model.jeu.*;

public abstract class DAOSaveLoad {

	private String chemin;

	public abstract void sauvegarder(Jeu jeu ,String chemin);

	public abstract Jeu charger(String chemin);

}
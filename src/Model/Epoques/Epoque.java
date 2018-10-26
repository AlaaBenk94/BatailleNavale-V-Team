package NavaleBattle.Model.Epoques;

public abstract class Epoque {

	private string siecle;

	public abstract void getInstance();

	public void getBateau() {
		throw new UnsupportedOperationException();
	}

}
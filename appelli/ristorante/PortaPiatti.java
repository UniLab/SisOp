package appelli.ristorante;

public abstract class PortaPiatti {
	protected final int numPosti;
	protected int numPiatti = 0;
	public enum Tipo {Contenitore, Scolapiatti};
	protected Tipo tipo;

	public PortaPiatti(int numPosti, Tipo tipo) {
		this.numPosti = numPosti;
		this.tipo = tipo;
	}

	public abstract void put(int n);

	public abstract void get();
}

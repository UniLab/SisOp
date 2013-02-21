package appelli.formicaio;

public abstract class Formicaio {

	protected int capacitaIngresso, numLarve;

	public Formicaio(int z, int y) {
		capacitaIngresso = z;
		numLarve = y;
	}

	public abstract void occupaIngresso() throws InterruptedException;
	public abstract void liberaIngresso() throws InterruptedException;
	public abstract void occupaLarva() throws InterruptedException;
	public abstract void liberaLarva() throws InterruptedException;

}

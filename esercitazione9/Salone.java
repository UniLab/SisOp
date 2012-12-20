package esercitazione9;

public abstract class Salone {
	protected int capienza;
	protected int numClientiInAttesa = 0;
	protected boolean barbiereAddormentato = true;
	
	public Salone(int c) { capienza = c; }
	
	protected abstract boolean entra() throws InterruptedException;

	protected abstract void getCliente() throws InterruptedException;

	protected abstract void congedaCliente() throws InterruptedException;
}

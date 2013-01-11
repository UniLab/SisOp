package esercitazione10;

public abstract class Tavolo {

	public static final int NUM_INGREDIENTI = 3;
	
	protected int[] ingredienti = new int[NUM_INGREDIENTI - 1];
	
	public abstract void prendi(int ingrediente) throws InterruptedException;
	
	public abstract void metti(int[] ingredienti) throws InterruptedException;

}

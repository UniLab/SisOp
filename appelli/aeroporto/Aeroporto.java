package appelli.aeroporto;

public abstract class Aeroporto {
	
	protected int numPiste, numNavette, numAerei;
	protected boolean[] navetteLibere;
	protected int numPisteLibere;
	
	public Aeroporto(int p, int q, int r) {
		numPisteLibere = numPiste = p;
		numNavette = q;
		numAerei = r;
		navetteLibere = new boolean[q];
		for (int i = 0; i < q; i++) navetteLibere[i] = true;
	}

	protected int scegliNavetta() {
		// Pre: esiste almeno una navetta libera
		int i = 0;
		while (!navetteLibere[i]) i++;
		return i;
	}

	public abstract void richiediAtterraggio() throws InterruptedException;
	public abstract void attendiDecollo() throws InterruptedException;
	public abstract void rilasciaPista() throws InterruptedException;

	public abstract void attendiAereo(int n) throws InterruptedException;
	public abstract void liberaAereo(int n);

}

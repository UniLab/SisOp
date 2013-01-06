package appelli.aereoporto;

public abstract class Aereoporto {
	
	protected int numPiste, numNavette, numAerei;
	protected boolean[] pisteLibere, navetteLibere;
	protected int richiesteAtterraggi = 0, richiesteDecolli = 0, numPisteLibere;
	
	public Aereoporto(int p, int q, int r) {
		numPisteLibere = numPiste = p;
		numNavette = q;
		numAerei = r;
		pisteLibere = new boolean[p];
		navetteLibere = new boolean[q];
		for (int i = 0; i < p; i++) pisteLibere[i] = true;
		for (int i = 0; i < q; i++) navetteLibere[i] = true;
	}

	protected int scegliPista() {
		// Pre: esiste almeno una pista libera
		int i = 0;
		while (!pisteLibere[i]) i++;
		return i;
	}

	protected int scegliNavetta() {
		// Pre: esiste almeno una navetta libera
		int i = 0;
		while (!navetteLibere[i]) i++;
		return i;
	}

	public abstract int richiediAtterraggio() throws InterruptedException;
	public abstract int attendiDecollo() throws InterruptedException;
	public abstract void rilasciaPista(int p) throws InterruptedException;

	public abstract void attendiAereo(int n) throws InterruptedException;
	public abstract void liberaAereo(int n);

}

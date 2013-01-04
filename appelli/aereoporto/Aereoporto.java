package appelli.aereoporto;

public abstract class Aereoporto {
	
	protected int numPiste, numNavette, numAerei;
	protected int[] richiesteAtterraggi, richiesteDecolli;
	protected boolean[] pisteLibere, navetteLibere;
	
	public Aereoporto(int p, int q, int r) {
		numPiste = p;
		numNavette = q;
		numAerei = r;
		richiesteAtterraggi = new int[p];
		richiesteDecolli = new int[p];
		pisteLibere = new boolean[p];
		navetteLibere = new boolean[q];
		for (int i = 0; i < p; i++) {
			richiesteAtterraggi[i] = 0;
			richiesteDecolli[i] = 0;
			pisteLibere[i] = true;
		}
		for (int i = 0; i < q; i++) navetteLibere[i] = true;
	}

	protected int scegliPista() {
		for (int i = 0; i < numPiste; i++)
			if (pisteLibere[i]) return i;
		int iMin = 0, min = richiesteAtterraggi[iMin] + richiesteDecolli[iMin], n;
		for (int i = 1; i < numPiste; i++) {
			if (min == 0) return iMin;
			n = richiesteAtterraggi[i] + richiesteDecolli[i];
			if (min > n) {
				min = n;
				iMin = i;
			}
		}
		return iMin;
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

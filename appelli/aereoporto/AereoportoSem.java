package appelli.aereoporto;

import java.util.concurrent.Semaphore;

public class AereoportoSem extends Aereoporto {
	
	private Semaphore mutex = new Semaphore(1), navetta = new Semaphore(0);
	private Semaphore[] atterraggi, decolli, navette, imbarchi;

	public AereoportoSem(int p, int q, int r) {
		super(p, q, r);
		atterraggi = new Semaphore[p];
		decolli = new Semaphore[p];
		navette = new Semaphore[q];
		imbarchi = new Semaphore[q];
		for (int i = 0; i < p; i++) {
			atterraggi[i] = new Semaphore(0, true);
			decolli[i] = new Semaphore(0, true);
		}
		for (int i = 0; i < q; i++) {
			navette[i] = new Semaphore(0);
			imbarchi[i] = new Semaphore(0);
		}
	}

	public int richiediAtterraggio() throws InterruptedException {
		mutex.acquire();
		System.out.println("Aereo #" + Thread.currentThread().getId() + " richiede atterraggio");
	 	int pista = scegliPista();
		if (pisteLibere[pista]) pisteLibere[pista] = false;
		else {
			richiesteAtterraggi[pista]++;
			mutex.release();
			atterraggi[pista].acquire();
			mutex.acquire();
			richiesteAtterraggi[pista]--;
		}
		mutex.release();
		return pista;
	}
	
	public int attendiDecollo() throws InterruptedException {
		System.out.println("Aereo #" + Thread.currentThread().getId() + " attende navetta");
		navetta.acquire();
		mutex.acquire();
		int n = scegliNavetta();
		navetteLibere[n] = false;
		mutex.release();
		navette[n].release();
		imbarchi[n].acquire();
		mutex.acquire();
		System.out.println("Aereo #" + Thread.currentThread().getId() + " richiede decollo");
		int pista = scegliPista();
		if (pisteLibere[pista]) pisteLibere[pista] = false;
		else {
			richiesteDecolli[pista]++;
			mutex.release();
			decolli[pista].acquire();
			mutex.acquire();
			richiesteDecolli[pista]--;
		}
		mutex.release();
		return pista;
	}

	public void rilasciaPista(int p) throws InterruptedException {
		mutex.acquire();
		if (richiesteAtterraggi[p] > 0) atterraggi[p].release();
		else if (richiesteDecolli[p] > 0) decolli[p].release();
		else pisteLibere[p] = true;
		mutex.release();
	}

	public void attendiAereo(int n) throws InterruptedException {
		mutex.acquire();
		navetta.release();
		navetteLibere[n] = true;
		mutex.release();
		navette[n].acquire();
	}

	public void liberaAereo(int n) {
		imbarchi[n].release();
	}

}

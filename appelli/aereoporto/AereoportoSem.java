package appelli.aereoporto;

import java.util.concurrent.Semaphore;

public class AereoportoSem extends Aereoporto {
	
	private Semaphore mutex = new Semaphore(1), navetta = new Semaphore(0),
		pista, pistaDecollo;
	private Semaphore[] navette, imbarchi;

	public AereoportoSem(int p, int q, int r) {
		super(p, q, r);
		pista = new Semaphore(0, true);
		pistaDecollo = new Semaphore(0, true);
		navette = new Semaphore[q];
		imbarchi = new Semaphore[q];
		for (int i = 0; i < q; i++) {
			navette[i] = new Semaphore(0);
			imbarchi[i] = new Semaphore(0);
		}
	}

	public int richiediAtterraggio() throws InterruptedException {
		mutex.acquire();
		System.out.println("Aereo #" + Thread.currentThread().getId() + " richiede atterraggio");
		if (numPisteLibere <= richiesteAtterraggi) {
			richiesteAtterraggi++;
			mutex.release();
			pista.acquire();
			mutex.acquire();
			richiesteAtterraggi--;
		}
		int pista = scegliPista();
		pisteLibere[pista] = false;
		numPisteLibere--;
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
		if (numPisteLibere <= richiesteAtterraggi || richiesteDecolli > 0) {
			richiesteDecolli++;
			mutex.release();
			pistaDecollo.acquire();
			mutex.acquire();
			richiesteDecolli--;
		}
		int pista = scegliPista();
		pisteLibere[pista] = false;
		numPisteLibere--;
		mutex.release();
		return pista;
	}

	public void rilasciaPista(int p) throws InterruptedException {
		mutex.acquire();
		pisteLibere[p] = true;
		numPisteLibere++;
		if (richiesteAtterraggi > 0) pista.release();
		else if (richiesteDecolli > 0) pistaDecollo.release();
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

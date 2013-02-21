package appelli.aeroporto;

import java.util.concurrent.Semaphore;

public class AeroportoSem extends Aeroporto {
	
	private Semaphore mutexPiste = new Semaphore(1),
		mutexNavette = new Semaphore(1),
		navetta = new Semaphore(0),
		pistaAtterraggio, pistaDecollo;
	private Semaphore[] navette, imbarchi;
	private int richiesteAtterraggi = 0, richiesteDecolli = 0;

	public AeroportoSem(int p, int q, int r) {
		super(p, q, r);
		pistaAtterraggio = new Semaphore(0, true);
		pistaDecollo = new Semaphore(0, true);
		navette = new Semaphore[q];
		imbarchi = new Semaphore[q];
		for (int i = 0; i < q; i++) {
			navette[i] = new Semaphore(0);
			imbarchi[i] = new Semaphore(0);
		}
	}

	public void richiediAtterraggio() throws InterruptedException {
		mutexPiste.acquire();
		if (numPisteLibere <= richiesteAtterraggi) {
			richiesteAtterraggi++;
			mutexPiste.release();
			pistaAtterraggio.acquire();
			richiesteAtterraggi--;
		}
		numPisteLibere--;
		mutexPiste.release();
	}
	
	public void attendiDecollo() throws InterruptedException {
		navetta.acquire();
		mutexNavette.acquire();
		int n = scegliNavetta();
		navetteLibere[n] = false;
		mutexNavette.release();
		navette[n].release();
		imbarchi[n].acquire();
		mutexPiste.acquire();
		if (numPisteLibere <= richiesteAtterraggi || richiesteDecolli > 0) {
			richiesteDecolli++;
			mutexPiste.release();
			pistaDecollo.acquire();
			richiesteDecolli--;
		}
		numPisteLibere--;
		mutexPiste.release();
	}

	public void rilasciaPista() throws InterruptedException {
		mutexPiste.acquire();
		numPisteLibere++;
		if (richiesteAtterraggi > 0) pistaAtterraggio.release();
		else if (richiesteDecolli > 0) pistaDecollo.release();
		else mutexPiste.release();
	}

	public void attendiAereo(int n) throws InterruptedException {
		mutexNavette.acquire();
		navetta.release();
		navetteLibere[n] = true;
		mutexNavette.release();
		navette[n].acquire();
	}

	public void liberaAereo(int n) {
		imbarchi[n].release();
	}

}

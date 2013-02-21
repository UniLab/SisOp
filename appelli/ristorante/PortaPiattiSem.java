package appelli.ristorante;

import java.util.concurrent.Semaphore;

public class PortaPiattiSem extends PortaPiatti {

	private Semaphore mutex = new Semaphore(1);
	private Semaphore ciSonoPiatti = new Semaphore(0);
	private Semaphore ciSonoPostiVuoti;

	public PortaPiattiSem(int numPosti, Tipo tipo) {
		super(numPosti, tipo);
		ciSonoPostiVuoti = new Semaphore(numPosti);
	}

	public void put(int n) throws InterruptedException {
		ciSonoPostiVuoti.acquire(n);
		mutex.acquire();
		numPiatti += n;
		ciSonoPiatti.release(n);
		mutex.release();
	}

	public void get() throws InterruptedException {
		ciSonoPiatti.acquire();
		mutex.acquire();
		numPiatti--;
		ciSonoPostiVuoti.release();
		mutex.release();
	}
}

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

	public void put(int n) {
		try {
			ciSonoPostiVuoti.acquire(n);
			mutex.acquire();
			numPiatti += n;
			System.out.println(Thread.currentThread().toString() +
				" ha posato " + n + " piatt" + (n > 1 ? "i" : "o") + ". " + tipo + " ne contiene " + numPiatti);
			ciSonoPiatti.release(n);
			mutex.release();
		} catch (InterruptedException e) {}
	}

	public void get() {
		try {
			ciSonoPiatti.acquire();
			mutex.acquire();
			numPiatti--;
			System.out.println(Thread.currentThread().toString() +
				" ha prelevato un piatto. " + tipo + " ne contiene " + numPiatti);
			ciSonoPostiVuoti.release();
			mutex.release();
		} catch (InterruptedException e) {}
	}
}

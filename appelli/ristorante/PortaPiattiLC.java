package appelli.ristorante;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class PortaPiattiLC extends PortaPiatti {
	private Lock l = new ReentrantLock();
	private Condition ciSonoPiatti = l.newCondition();
	private Condition ciSonoPostiVuoti = l.newCondition();

	public PortaPiattiLC(int numPosti, Tipo tipo) {
		super(numPosti, tipo);
	}

	public void put(int n) {
		try {
			l.lock();
			while (numPosti - numPiatti < n) ciSonoPostiVuoti.await();
			numPiatti += n;
			System.out.println(Thread.currentThread().toString() +
				" ha posato " + n + " piatt" + (n > 1 ? "i" : "o") + ". " + tipo + " ne contiene " + numPiatti);
			while (n-- > 0) ciSonoPiatti.signal();
		} catch (InterruptedException e) {
		} finally {
			l.unlock();
		}
	}

	public void get() {
		try {
			l.lock();
			while (numPiatti == 0) ciSonoPiatti.await();
			numPiatti--;
			System.out.println(Thread.currentThread().toString() +
				" ha prelevato un piatto. " + tipo + " ne contiene " + numPiatti);
			ciSonoPostiVuoti.signal();
		} catch (InterruptedException e) {
		} finally {
			l.unlock();
		}
	}
}

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

	public void put(int n) throws InterruptedException {
		l.lock();
		try {
			while (numPosti - numPiatti < n) ciSonoPostiVuoti.await();
			numPiatti += n;
			ciSonoPiatti.signalAll();
		} finally { l.unlock(); }
	}

	public void get() throws InterruptedException {
		l.lock();
		try {
			while (numPiatti == 0) ciSonoPiatti.await();
			numPiatti--;
			ciSonoPostiVuoti.signal();
		} finally { l.unlock(); }
	}
}

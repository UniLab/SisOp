package esercitazione6;

import java.util.concurrent.TimeUnit;
import java.util.Random;

public class ThreadUtenteHoare extends Thread {
	
	private Hoare m;
	private Random r = new Random();

	public static final int MIN_ATTESA = 50;
	public static final int MAX_ATTESA = 200;

	public ThreadUtenteHoare(Hoare m) {
		this.m = m;
	}

	public void run() {
		while (true) {
			try {
				m.lock();
				attesaCasuale();
				while (condizioneCasuale()) m.await();
				attesaCasuale();
				if (condizioneCasuale() || condizioneCasuale()) m.signal();
				else m.signalAll();
				attesaCasuale();
			} catch(InterruptedException e) {
				System.out.println("Possibile?");
			} finally {
				m.unlock();
			}
		}
	}

	private boolean condizioneCasuale() {
		return r.nextInt(2) == 0;
	}

	private void attesaCasuale() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(r.nextInt(MAX_ATTESA - MIN_ATTESA + 1) + MIN_ATTESA);
	}
}

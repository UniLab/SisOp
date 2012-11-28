package esercitazione5;

import java.util.concurrent.TimeUnit;

public class Consumatore implements Runnable {

	private Buffer buffer;

	public Consumatore(Buffer b) { buffer = b; }

	public void run() {
		try {
			while (true) {
				Elemento e = buffer.get();
				consuma(e);
			}
		} catch (InterruptedException e) {}
	}

	private void consuma(Elemento e) throws InterruptedException {
		TimeUnit.SECONDS.sleep(e.getValore());
	}
}

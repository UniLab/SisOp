package esercitazione5;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Produttore implements Runnable {
	
	private static final int MAX_RANDOM = 10;
	private static final int MIN_TEMPO_PRODUZIONE = 2;
	private static final int MAX_TEMPO_PRODUZIONE = 8;
	private Random random = new Random();
	private Buffer buffer;

	public Produttore(Buffer b) { buffer = b; }
	
	public void run() {
		try {
			while (true) {
				Elemento e = produci();
				buffer.put(e);
			}
		} catch (InterruptedException e) {}
	}

	private Elemento produci() throws InterruptedException {
		attendi(MIN_TEMPO_PRODUZIONE, MAX_TEMPO_PRODUZIONE);
		return new Elemento(random.nextInt(MAX_RANDOM));
	}

	private void attendi(int min, int max) throws InterruptedException {
		TimeUnit.SECONDS.sleep(random.nextInt(max - min + 1) + min);
	}
}

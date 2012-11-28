package esercitazione4;

import java.util.concurrent.TimeUnit;
import java.util.Random;

public class Scrittore implements Runnable {

	private static final int MIN_TEMPO_SCRITTURA = 2;
	private static final int MAX_TEMPO_SCRITTURA = 3;
	private static final int MIN_TEMPO_ALTRO = 10;
	private static final int MAX_TEMPO_ALTRO = 20;

	private MemoriaCondivisa memoria;
	private Random random = new Random();

	public Scrittore(MemoriaCondivisa mem) {
		memoria = mem;
	}
	public void run() {
		try {
			while (true) {
				memoria.inizioScrittura();
				scrivi();
				memoria.fineScrittura();
				faiAltro();
			}
		} catch (InterruptedException e) {}
	}
	private void scrivi() throws InterruptedException {
		attendi(MIN_TEMPO_SCRITTURA, MAX_TEMPO_SCRITTURA);
	}
	private void faiAltro() throws InterruptedException {
		attendi(MIN_TEMPO_ALTRO, MAX_TEMPO_ALTRO);
	}
	private void attendi(int min, int max) throws InterruptedException {
		TimeUnit.SECONDS.sleep(random.nextInt(max - min + 1) + min);
	}
}

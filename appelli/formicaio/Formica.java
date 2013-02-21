package appelli.formicaio;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Formica extends Thread {
	
	public static final int MIN_CERCA_CIBO = 30;
	public static final int MAX_CERCA_CIBO = 70;
	public static final int TEMPO_PERCORRENZA = 40;
	public static final int TEMPO_NUTRIZIONE = 500;

	private Formicaio f;
	private Random r = new Random();

	public Formica(Formicaio f) {
		this.f = f;
	}

	public void run() {
		try {
			while(true) {
				cercaCibo();
				f.occupaIngresso();
				percorriIngresso();
				f.liberaIngresso();
				f.occupaLarva();
				nutriLarva();
				f.liberaLarva();
				f.occupaIngresso();
				percorriIngresso();
				f.liberaIngresso();
			}
		} catch (InterruptedException e) { }
	}

	private void cercaCibo() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(r.nextInt(MAX_CERCA_CIBO - MIN_CERCA_CIBO + 1) + MIN_CERCA_CIBO);
	}

	private void percorriIngresso() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(TEMPO_PERCORRENZA);
	}

	private void nutriLarva() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(TEMPO_NUTRIZIONE);
	}

}

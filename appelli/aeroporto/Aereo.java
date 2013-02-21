package appelli.aeroporto;

import java.util.concurrent.TimeUnit;
import java.util.Random;

public class Aereo extends Thread {
	
	private Aeroporto a;
	private Random r = new Random();
	public static final int MIN_VOLO = 600;
	public static final int MAX_VOLO = 900;
	public static final int TEMPO_ATTERRAGGIO = 300;
	public static final int TEMPO_DECOLLO = 300;

	public Aereo(Aeroporto a) {
		this.a = a;
	}

	public void run() {
		try {
			while (true) {
				vola();
				a.richiediAtterraggio();
				atterra();
				a.rilasciaPista();
				a.attendiDecollo();
				decolla();
				a.rilasciaPista();
			}
		} catch (InterruptedException e) { }
	}

	private void vola() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(r.nextInt(MAX_VOLO - MIN_VOLO + 1) + MIN_VOLO);
	}

	private void atterra() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(TEMPO_ATTERRAGGIO);
	}

	private void decolla() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(TEMPO_DECOLLO);
	}

}

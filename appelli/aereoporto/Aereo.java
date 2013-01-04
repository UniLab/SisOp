package appelli.aereoporto;

import java.util.concurrent.TimeUnit;
import java.util.Random;

public class Aereo extends Thread {
	
	private Aereoporto a;
	private Random r = new Random();
	public static final int MIN_VOLO = 600;
	public static final int MAX_VOLO = 900;
	public static final int TEMPO_ATTERRAGGIO = 300;
	public static final int TEMPO_DECOLLO = 300;

	public Aereo(Aereoporto a) {
		this.a = a;
	}

	public void run() {
		int pista;
		try {
			while (true) {
				vola();
				pista = a.richiediAtterraggio();
				System.out.println("Aereo #" + getId() + " inizia atterraggio sulla pista " + pista);
				atterra();
				System.out.println("Aereo #" + getId() + " termina atterraggio sulla pista " + pista);
				a.rilasciaPista(pista);
				pista = a.attendiDecollo();
				System.out.println("Aereo #" + getId() + " inizia decollo sulla pista " + pista);
				decolla();
				System.out.println("Aereo #" + getId() + " termina decollo sulla pista " + pista);
				a.rilasciaPista(pista);
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

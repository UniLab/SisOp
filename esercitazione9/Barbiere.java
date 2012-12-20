package esercitazione9;

import java.util.concurrent.TimeUnit;
import java.util.Random;

public class Barbiere implements Runnable {
	private Salone salone;
	private Random r = new Random();

	public static final int MIN_TAGLIO = 25;
	public static final int MAX_TAGLIO = 50;

	public Barbiere(Salone s) { salone = s; }

	public void run() {
		try {
			while (true) {
				salone.getCliente();
				tagliaCapelli();
				salone.congedaCliente();
			}
		} catch (InterruptedException e) {}
	}

	private void tagliaCapelli() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(r.nextInt(MAX_TAGLIO - MIN_TAGLIO + 1) + MIN_TAGLIO);
	}
}

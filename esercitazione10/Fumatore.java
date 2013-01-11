package esercitazione10;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Fumatore implements Runnable {
	
	private Tavolo tavolo;
	private int ingredientePosseduto;
	private static final int MIN = 50;
	private static final int MAX = 200;
	private Random r = new Random();

	public Fumatore(Tavolo t, int tipo) {
		tavolo = t;
		ingredientePosseduto = tipo;
	}

	public void run() {
		try {
			while (true) {
				tavolo.prendi(ingredientePosseduto);
				preparaSigarettaEFuma();
			}
		} catch (InterruptedException e) {}
	}

	private void preparaSigarettaEFuma() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(r.nextInt(MAX - MIN + 1) + MIN);
	}
}

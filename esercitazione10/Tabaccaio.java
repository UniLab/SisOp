package esercitazione10;

import java.util.Random;

public class Tabaccaio implements Runnable {

	private Tavolo tavolo;
	private int[] ingredienti = new int[Tavolo.NUM_INGREDIENTI - 1];
	private Random random = new Random();
	
	public Tabaccaio(Tavolo t) {
		this.tavolo = t;
	}

	public void run() {
		try {
			while (true) {
				ingredienti[0] = random.nextInt(Tavolo.NUM_INGREDIENTI);
				for (int i = 1; i < ingredienti.length; i++) {
					ingredienti[i] = (ingredienti[0] + i) % Tavolo.NUM_INGREDIENTI;
				}
				tavolo.metti(ingredienti);
			}
		} catch (InterruptedException e) {}
	}
}

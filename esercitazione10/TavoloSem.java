package esercitazione10;

import java.util.concurrent.Semaphore;

public class TavoloSem extends Tavolo {

	private Semaphore tabaccaio = new Semaphore(1);
	private Semaphore[] fumatori = new Semaphore[NUM_INGREDIENTI];

	public TavoloSem() {
		for (int i = 0; i < fumatori.length; i++)
			fumatori[i] = new Semaphore(0);
	}

	public void metti(int[] ingr) throws InterruptedException {
		tabaccaio.acquire();
		for (int i = 0; i < ingr.length; i++)
			ingredienti[i] = ingr[i];
		int qualeFumatore = (ingredienti[ingredienti.length - 1] + 1) % NUM_INGREDIENTI;
		fumatori[qualeFumatore].release();
	}

	public void prendi(int ingrediente) throws InterruptedException {
		fumatori[ingrediente].acquire();
		for (int i = 0; i < ingredienti.length; i++)
			ingredienti[i] = -1;
		tabaccaio.release();
	}
}

/*

Implementare in Java gli scenari descritti nei due esempi di
mutua esclusione e sincronizzazione descritti in questa
esercitazione, in cui due le istruzioni A e B, eseguite
dai thread p1 e p2, consistono in una stampa su terminale
di “A” e “B” rispettivamente.

*/

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.Random;

class ThreadP1 extends Thread {
	private static final int MAX = 100;
	private static final int MIN = 80;
	private Semaphore mutex;
	private Random random = new Random();
	public ThreadP1(Semaphore s) {
		mutex = s;
	}
	public void run() {
		try {
			attesaCasuale(MAX, MIN);
			System.out.println("Inizio operazioni thread P1");
			System.out.println("A");
			System.out.println("Fine operazioni thread P1");
			mutex.release();
		} catch (InterruptedException e) {}
	}

	private void attesaCasuale(int max, int min) throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(random.nextInt(max - min + 1) + min);
	}
}

class ThreadP2 extends Thread {
	private static final int MAX = 100;
	private static final int MIN = 80;
	private Semaphore mutex;
	private Random random = new Random();
	public ThreadP2(Semaphore s) {
		mutex = s;
	}
	public void run() {
		try {
			attesaCasuale(MAX, MIN);
			mutex.acquire();
			System.out.println("Inizio operazioni thread P2");
			System.out.println("B");
			System.out.println("Fine operazioni thread P2");
		} catch (InterruptedException e) {}
	}

	private void attesaCasuale(int max, int min) throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(random.nextInt(max - min + 1) + min);
	}
}

public class Sincronizzazione {
	public static void main(String[]args) {
		Semaphore mutex = new Semaphore(0);
		ThreadP1 p1 = new ThreadP1(mutex);
		ThreadP2 p2 = new ThreadP2(mutex);
		p1.start(); p2.start();
	}
}

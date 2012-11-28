/*

Implementare in Java gli scenari descritti nei due esempi di
mutua esclusione e sincronizzazione descritti in questa
esercitazione, in cui due le istruzioni A e B, eseguite
dai thread p1 e p2, consistono in una stampa su terminale
di “A” e “B” rispettivamente.

*/

package esercitazione4;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.Random;

class ThreadSync1 extends Thread {
	private static final int MAX = 100;
	private static final int MIN = 80;
	private Semaphore mutex;
	private Random random = new Random();
	public ThreadSync1(Semaphore s) {
		mutex = s;
	}
	public void run() {
		try {
			attesaCasuale(MAX, MIN);
			System.out.println("Inizio operazioni thread t1");
			System.out.println("A");
			System.out.println("Fine operazioni thread t1");
			mutex.release();
		} catch (InterruptedException e) {}
	}

	private void attesaCasuale(int max, int min) throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(random.nextInt(max - min + 1) + min);
	}
}

class ThreadSync2 extends Thread {
	private static final int MAX = 100;
	private static final int MIN = 80;
	private Semaphore mutex;
	private Random random = new Random();
	public ThreadSync2(Semaphore s) {
		mutex = s;
	}
	public void run() {
		try {
			attesaCasuale(MAX, MIN);
			mutex.acquire();
			System.out.println("Inizio operazioni thread t2");
			System.out.println("B");
			System.out.println("Fine operazioni thread t2");
		} catch (InterruptedException e) {}
	}

	private void attesaCasuale(int max, int min) throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(random.nextInt(max - min + 1) + min);
	}
}

public class Sincronizzazione {
	public static void main(String[]args) {
		Semaphore mutex = new Semaphore(0);
		ThreadSync1 t1 = new ThreadSync1(mutex);
		ThreadSync2 t2 = new ThreadSync2(mutex);
		t1.start(); t2.start();
	}
}

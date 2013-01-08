package esercitazione9;

import java.util.concurrent.TimeUnit;
import java.util.Random;

public class Cliente implements Runnable {
	private Salone salone;
	private Thread t;
	private Random r = new Random();

	public static final int MIN = 500;
	public static final int MAX = 2000;

	public Cliente(Salone s) {
		salone = s;
		t = new Thread(this);
	}

	public void start() { t.start(); }

	public void run() {
		try {
			raggiungiSalone();
			if (salone.entra()) System.out.println("Cliente #" + t.getId() + " è stato servito");
			else System.out.println("Cliente #" + t.getId() + " deve cercare un altro barbiere");
		} catch (InterruptedException e) {}
	}

	private void raggiungiSalone() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(r.nextInt(MAX - MIN + 1) + MIN);
	}
}

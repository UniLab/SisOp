package esercitazione5;

import java.util.concurrent.Semaphore;

public class BufferSem extends Buffer {

	private Semaphore ciSonoPostiVuoti;
	private Semaphore ciSonoElementi = new Semaphore(0);
	private Semaphore mutex = new Semaphore(1);

	public BufferSem(int dim) {
		super(dim);
		ciSonoPostiVuoti = new Semaphore(dim);
	}

	public void put(Elemento e) throws InterruptedException {
		ciSonoPostiVuoti.acquire();
		mutex.acquire();
		buffer[in] = e;
		in = (in + 1) % buffer.length;
		stampaStato();
		mutex.release();
		ciSonoElementi.release();
	}

	public Elemento get() throws InterruptedException {
		ciSonoElementi.acquire();
		mutex.acquire();
		Elemento e = buffer[out];
		buffer[out] = null;
		out = (out + 1) % buffer.length;
		stampaStato();
		mutex.release();
		ciSonoPostiVuoti.release();
		return e;
	}

	private void stampaStato() {
		StringBuilder sb = new StringBuilder(30);
		sb.append("Buffer[");
		for (int i = 0; i < buffer.length; i++) {
			sb.append((buffer[i] != null ? "x" : " ") + "|");
		}
		if (sb.length() > 1) sb.setLength(sb.length() - 1);
		sb.append("]");
		System.out.println(sb);
	}

	public static void main(String[] args) {
		int dimensione = 10;
		Buffer buffer = new BufferSem(dimensione);
		int numProduttori = 10;
		int numConsumatori = 10;
		buffer.test(numProduttori, numConsumatori);
	}
}

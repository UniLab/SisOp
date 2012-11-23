/*

Scrivere una soluzione con i semafori della variante del
problema del produttore-consumatore, con il buffer illimitato.

*/

import java.util.concurrent.Semaphore;
import java.util.LinkedList;

public class BufferSemIllimitato extends Buffer {

	private Semaphore ciSonoElementi = new Semaphore(0);
	private Semaphore mutex = new Semaphore(1);

	private LinkedList<Elemento> bufferIllimitato;

	public BufferSemIllimitato() {
		super(0); // Buffer inutilizzato
		bufferIllimitato = new LinkedList<Elemento>();
	}

	public void put(Elemento e) throws InterruptedException {
		mutex.acquire();
		bufferIllimitato.addLast(e);
		stampaStato();
		mutex.release();
		ciSonoElementi.release();
	}

	public Elemento get() throws InterruptedException {
		ciSonoElementi.acquire();
		mutex.acquire();
		Elemento e = bufferIllimitato.removeFirst();
		stampaStato();
		mutex.release();
		return e;
	}

	private void stampaStato() {
		StringBuilder sb = new StringBuilder(bufferIllimitato.size() * 2 + 10);
		sb.append("[");
		for (Elemento e: bufferIllimitato)
			sb.append(e.getValore() + ", ");
		if (sb.length() > 1) sb.setLength(sb.length() - 2);
		sb.append("]");
		System.out.println(sb.toString());
	}

	public static void main(String[] args) {
		int dimensione = 10;
		Buffer buffer = new BufferSemIllimitato();
		int numProduttori = 15;
		int numConsumatori = 10;
		buffer.test(numProduttori, numConsumatori);
	}
}

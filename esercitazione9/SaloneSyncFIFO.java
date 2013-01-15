/*

Risolvere il problema del barbiere addormentato
imponendo una politica FIFO di richiamo dei clienti
dalla sala d'attesa: scrivere una soluzione con i
semafori, una con le Lock e Condition ed una
con i monitor nativi di Java.

*/

package esercitazione9;

import java.util.Queue;
import java.util.LinkedList;

public class SaloneSyncFIFO extends Salone {
	private boolean clienteInPoltrona = false;
	private boolean prossimo = false;
	Queue<Thread> codaAttesa = new LinkedList<Thread>();

	public SaloneSyncFIFO(int c) { super(c); }

	public synchronized boolean entra() throws InterruptedException {
		System.out.println("Cliente #" + Thread.currentThread().getId() + " entra nel salone");
		boolean servito = true;
		if (!clienteInPoltrona && !prossimo) siediInPoltrona();
		else if (numClientiInAttesa < capienza) {
			numClientiInAttesa++;
			codaAttesa.offer(Thread.currentThread());
			while (!prossimo || codaAttesa.peek() != Thread.currentThread()) wait();
			codaAttesa.poll();
			prossimo = false;
			numClientiInAttesa--;
			siediInPoltrona();
		} else servito = false;
		return servito;
	}

	private void siediInPoltrona() throws InterruptedException {
		notifyAll();
		clienteInPoltrona = true;
		while (clienteInPoltrona) wait();
	}

	public synchronized void getCliente() throws InterruptedException {
		if (numClientiInAttesa > 0) {
			prossimo = true;
			notifyAll();
		}
		while (!clienteInPoltrona) wait();
	}

	public synchronized void congedaCliente() throws InterruptedException {
		clienteInPoltrona = false;
		notifyAll();
	}
}

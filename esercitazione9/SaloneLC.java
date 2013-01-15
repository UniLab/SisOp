package esercitazione9;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class SaloneLC extends Salone {
	private Lock l = new ReentrantLock();
	private Condition barbiere = l.newCondition();
	private Condition clientiInAttesa = l.newCondition();
	private Condition clienteServito = l.newCondition();
	private boolean clienteInPoltrona = false;
	private boolean prossimo = false;

	public SaloneLC(int c) { super(c); }

	public boolean entra() throws InterruptedException {
		boolean servito = true;
		l.lock();
		try {
			System.out.println("Cliente #" + Thread.currentThread().getId() + " entra nel salone");
			if (!clienteInPoltrona) siediInPoltrona();
			else if (numClientiInAttesa < capienza) {
				numClientiInAttesa++;
				while (!prossimo) clientiInAttesa.await();
				prossimo = false;
				numClientiInAttesa--;
				siediInPoltrona();
			} else servito = false;
			return servito;
		} finally { l.unlock(); }
	}

	private void siediInPoltrona() throws InterruptedException {
		barbiere.signal();
		clienteInPoltrona = true;
		while (clienteInPoltrona) clienteServito.await();
	}

	public void getCliente() throws InterruptedException {
		l.lock();
		try {
			if (numClientiInAttesa > 0) {
				prossimo = true;
				clientiInAttesa.signal();
			}
			while (!clienteInPoltrona) barbiere.await();
		} finally { l.unlock(); }
	}

	public void congedaCliente() throws InterruptedException {
		l.lock();
		try {
			clienteInPoltrona = false;
			clienteServito.signal();
		} finally { l.unlock(); }
	}
}

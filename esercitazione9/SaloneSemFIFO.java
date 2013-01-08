package esercitazione9;

import java.util.concurrent.Semaphore;

public class SaloneSemFIFO extends Salone {
	private Semaphore mutex = new Semaphore(1);
	private Semaphore barbiere = new Semaphore(0);
	private Semaphore clienteInAttesa = new Semaphore(0, true);
	private Semaphore clienteServito = new Semaphore(0);

	public SaloneSemFIFO(int c) { super(c); }

	public boolean entra() throws InterruptedException {
		System.out.println("Cliente #" + Thread.currentThread().getId() + " entra nel salone");
		boolean servito = true;
		mutex.acquire();
		if (barbiereAddormentato) {
			siediInPoltrona();
		} else if (numClientiInAttesa < capienza) {
			attendiInSalaDAttesa();
			siediInPoltrona();
		} else {
			servito = false;
		}
		mutex.release();
		return servito;
	}

	public void getCliente() throws InterruptedException {
		mutex.acquire();
		if (numClientiInAttesa == 0) {
			barbiereAddormentato = true;
			mutex.release();
			barbiere.acquire();
			barbiereAddormentato = false;
		} else {
			clienteInAttesa.release();
			barbiere.acquire();
		}
		mutex.release();
	}

	public void congedaCliente() throws InterruptedException {
		mutex.acquire();
		clienteServito.release();
	}

	private void attendiInSalaDAttesa() throws InterruptedException {
		numClientiInAttesa++;
		mutex.release();
		clienteInAttesa.acquire();
		numClientiInAttesa--;
	}
	
	private void siediInPoltrona() throws InterruptedException {
		barbiere.release();
		clienteServito.acquire();
	}
}

package esercitazione9;

public class SaloneSync extends Salone {
	private boolean clienteInPoltrona = false;
	private boolean prossimo = false;
	
	public SaloneSync(int c) { super(c); }

	public synchronized boolean entra() throws InterruptedException {
		System.out.println("Cliente #" + Thread.currentThread().getId() + " entra nel salone");
		boolean servito = true;
		if (!clienteInPoltrona) siediInPoltrona();
		else if (numClientiInAttesa < capienza) {
			numClientiInAttesa++;
			while (!prossimo) wait();
			prossimo = false;
			numClientiInAttesa--;
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
			notify();
		}
		while (!clienteInPoltrona) wait();
	}

	public synchronized void congedaCliente() throws InterruptedException {
		clienteInPoltrona = false;
		notifyAll();
	}
}

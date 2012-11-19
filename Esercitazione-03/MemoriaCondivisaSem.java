import java.util.concurrent.Semaphore;

public class MemoriaCondivisaSem extends MemoriaCondivisa {

	Semaphore mutex = new Semaphore(1);
	Semaphore scrittura = new Semaphore(1);
	private int numLettori = 0;

	public void inizioScrittura() throws InterruptedException {
		scrittura.acquire();
		System.out.println("Thread " + Thread.currentThread().getId() + " inizia a scrivere");
		System.out.println("\tNumero lettori: " + numLettori);
	}

	public void fineScrittura() throws InterruptedException {
		System.out.println("\tNumero lettori: " + numLettori);
		System.out.println("Thread " + Thread.currentThread().getId() + " finisce di scrivere");
		scrittura.release();
	}

	public void inizioLettura() throws InterruptedException {
		mutex.acquire();
		if (numLettori == 0) scrittura.acquire();
		numLettori++;
		System.out.println("Thread " + Thread.currentThread().getId() + " inizia a leggere");
		mutex.release();
	}

	public void fineLettura() throws InterruptedException {
		mutex.acquire();
		numLettori--;
		System.out.println("Thread " + Thread.currentThread().getId() + " finisce di leggere");
		if (numLettori == 0) scrittura.release();
		mutex.release();
	}

	public static void main(String[]args) {
		MemoriaCondivisa m = new MemoriaCondivisaSem();
		m.test(20, 5);
	}
}

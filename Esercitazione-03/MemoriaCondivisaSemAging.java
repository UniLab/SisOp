/*

Si implementi, estendendo la classe MemoriaCondivisa,
una soluzione con i semafori del problema dei lettori scrittori
del terzo tipo, utilizzando la tecnica dell'aging.

*/

import java.util.concurrent.Semaphore;
import java.util.Map;
import java.util.HashMap;

public class MemoriaCondivisaSemAging extends MemoriaCondivisa {

	Semaphore mutex = new Semaphore(1);
	Semaphore scrittura = new Semaphore(1);
	Semaphore waitLettori = new Semaphore(0, true);
	Semaphore waitScrittori = new Semaphore(0, true);
	public static final int INITIAL_PRIORITY = 10;
	public static final int MIN_PRIORITY = 0;
	private int numLettori = 0,
		ageLettori = INITIAL_PRIORITY, ageScrittori = INITIAL_PRIORITY,
		lettoriInAttesa = 0, scrittoriInAttesa = 0;

	public void inizioScrittura() throws InterruptedException {
		mutex.acquire();
		if (ageScrittori < MIN_PRIORITY) {
			scrittoriInAttesa++;
			mutex.release();
			waitScrittori.acquire();
		} else mutex.release();
		scrittura.acquire();
		ageScrittori--;
		System.out.println("Thread " + Thread.currentThread().getId() + " inizia a scrivere");
		System.out.println("\tNumero lettori: " + numLettori);
	}

	public void fineScrittura() throws InterruptedException {
		System.out.println("\tNumero lettori: " + numLettori);
		System.out.println("Thread " + Thread.currentThread().getId() + " finisce di scrivere");
		if (lettoriInAttesa > 0) {
			waitLettori.release(lettoriInAttesa);
			lettoriInAttesa = 0;
			ageLettori = INITIAL_PRIORITY;
		}
		scrittura.release();
	}

	public void inizioLettura() throws InterruptedException {
		mutex.acquire();
		if (ageLettori < MIN_PRIORITY) {
			lettoriInAttesa++;
			mutex.release();
			waitLettori.acquire();
			mutex.acquire();
		}
		if (numLettori == 0) scrittura.acquire();
		numLettori++; ageLettori--;
		System.out.println("Thread " + Thread.currentThread().getId() + " inizia a leggere");
		mutex.release();
	}

	public void fineLettura() throws InterruptedException {
		mutex.acquire();
		numLettori--;
		System.out.println("Thread " + Thread.currentThread().getId() + " finisce di leggere");
		if (numLettori == 0) {
			if (scrittoriInAttesa > 0) {
				waitScrittori.release(scrittoriInAttesa);
				scrittoriInAttesa = 0;
				ageScrittori = INITIAL_PRIORITY;
			}
			scrittura.release();
		}
		mutex.release();
	}

	public static void main(String[]args) {
		MemoriaCondivisa m = new MemoriaCondivisaSemAging();
		m.test(20, 5);
	}
}

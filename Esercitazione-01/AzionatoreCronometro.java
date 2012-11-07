/*

Usando il meccanismo per l'interruzione di un thread, modificare ed
estendere la classe Cronometro (che deve implementareestendere Runnable) e
AzionatoreCronometro in modo che:
   - la digitazione di una stringa qualsiasi seguita da invio metta
     in pausa l'orologio e mostri il numero di secondi trascorsi
     dall'ultima pausa;
   - la digitazione della stringa ”stop” termini l'orologio e
     mostri il numero di secondi trascorsi dall'avvio dell'orologio
     e il numero delle pause.

*/

import java.util.Scanner;

class Cronometro implements Runnable {
	private boolean stop = false;
	private int numSecondi = 0, ultimiSecondi = 0, numPause = 0;

	public void run() {
		for (;;) {
			while (!Thread.currentThread().interrupted()) {
				try {
					Thread.currentThread().sleep(1000);
				} catch (InterruptedException e) { break; }
				numSecondi++;
				ultimiSecondi++;
			}
			if (stop) {
				System.out.println("Numero secondi trascorsi:\t" + numSecondi);
				System.out.println("Numero pause:            \t" + numPause);
				break;
			}
			System.out.println("Secondi trascorsi dall'ultima pausa: " + ultimiSecondi);
			ultimiSecondi = 0;
			numPause++;
		}
	}

	public void ferma() { stop = true; }
}

public class AzionatoreCronometro {
	public static void main(String[]args) {
		Scanner in = new Scanner(System.in);
		Cronometro cronometro = new Cronometro();
		Thread threadCronometro = new Thread(cronometro);
		System.out.println("Premi invio per iniziare");
		in.nextLine();
		threadCronometro.start();
		System.out.println("Premi invio per mettere in pausa o digita \"stop\" per fermare il cronometro");
		for (;;) {
			if (in.nextLine().equals("stop")) {
				cronometro.ferma();
				threadCronometro.interrupt();
				break;
			} else threadCronometro.interrupt();
		}
	}
}

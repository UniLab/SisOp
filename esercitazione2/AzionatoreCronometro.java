/*

Usando il meccanismo per l'interruzione di un thread, modificare ed
estendere la classe Cronometro (che deve implementare Runnable) e
AzionatoreCronometro in modo che:
   - la digitazione di una stringa qualsiasi seguita da invio metta
     in pausa l'orologio e mostri il numero di secondi trascorsi
     dall'ultima pausa;
   - la pressione del tasto invio, quando il cronometro è in
     pausa, faccia ripartire il cronometro
   - la digitazione della stringa ”stop” termini l'orologio e
     mostri il numero di secondi trascorsi dall'avvio dell'orologio
     e il numero delle pause.

*/

package esercitazione2;

import java.util.Scanner;

class Cronometro implements Runnable {
	private boolean stop = false;
	private int numSecondi = 0, ultimiSecondi = 0, numPause = 0;
	private Thread t;

	public Cronometro() {
		t = new Thread(this);
	}

	public void run() {
		for (;;) {
			while (!t.interrupted()) {
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
			System.out.println("Premi invio per far ripartire il cronometro");
			while (!t.interrupted()) {
				try {
					Thread.currentThread().sleep(Integer.MAX_VALUE);
				} catch (InterruptedException e) { break; }
			}
		}
	}

	public void avvia() { t.start(); }

	public void pausa() { t.interrupt(); }

	public void stop() { stop = true; t.interrupt(); }
}

public class AzionatoreCronometro {
	public static void main(String[]args) {
		Scanner in = new Scanner(System.in);
		Cronometro cronometro = new Cronometro();
		System.out.println("Premi invio per iniziare");
		in.nextLine();
		cronometro.avvia();
		System.out.println("Premi invio per mettere in pausa o digita \"stop\" per fermare il cronometro");
		while (!in.nextLine().equals("stop")) {
			cronometro.pausa(); // Inizio pausa
			in.nextLine();
			cronometro.pausa(); // Fine pausa
		}
		cronometro.stop();
	}
}

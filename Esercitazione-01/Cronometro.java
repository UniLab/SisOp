/*

Usando il meccanismo per l'interruzione di un thread, modificare ed
estendere la classe Cronometro (che deve estendere Runnable) e
AzionatoreCronometro in modo che:
   - la digitazione di una stringa qualsiasi seguita da invio metta
     in pausa l'orologio e mostri il numero di secondi trascorsi
     dall'ultima pausa;
   - la digitazione della stringa ”stop” termini l'orologio e
     mostri il numero di secondi trascorsi dall'avvio dell'orologio
     e il numero delle pause.

*/

import java.util.Scanner;

class Cronometro extends Thread {
	private boolean interrompi = false;
	private int numSecondi;
	private int numPause;

	public void run() {
		int ultimiSecondi = 0;
		while (!isInterrupted()) {
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				if (interrompi) {
					System.out.println("Numero secondi trascorsi: \t"
							+ numSecondi);
					System.out.println("Numero pause:             \t"
							+ numPause);
					break;
				}
				System.out.println("Secondi trascorsi dall'ultima pausa: "
						+ ultimiSecondi);
				ultimiSecondi = 0;
				numPause++;
			}
			numSecondi++;
			ultimiSecondi++;
		}
	}

	public void interrompi() {
		interrompi = true;
		interrupt();
	}

	public boolean interrotto() {
		return interrompi;
	}

	public static void main(String[]args) {
		Scanner in = new Scanner(System.in);
		Cronometro cronometro = new Cronometro();
		System.out.println("Premi invio per iniziare");
		in.nextLine();
		cronometro.start();
		System.out.println("Premi invio per mettere in pausa o digita \"stop\" per fermare il cronometro");
		do {
			if (in.nextLine().equalsIgnoreCase("stop")) {
				cronometro.interrompi();
			} else
				cronometro.interrupt();
		} while (!cronometro.interrotto());
	}
}

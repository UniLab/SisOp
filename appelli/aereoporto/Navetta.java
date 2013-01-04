package appelli.aereoporto;

import java.util.concurrent.TimeUnit;

public class Navetta extends Thread {
	
	public static final int TEMPO_OPERAZIONI = 400;

	private Aereoporto a;
	private int n;

	public Navetta(Aereoporto a, int n) {
		this.a = a;
		this.n = n;
	}

	public void run() {
		try {
			while (true) {
				a.attendiAereo(n);
				cambioPasseggeri();
				a.liberaAereo(n);
			}
		} catch (InterruptedException e) { }
	}

	private void cambioPasseggeri() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(TEMPO_OPERAZIONI);
	}

}

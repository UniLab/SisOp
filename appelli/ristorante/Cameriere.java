package appelli.ristorante;

import java.util.concurrent.TimeUnit;

public class Cameriere extends Thread {

	private PortaPiatti contenitore;
	public static final int TEMPO_RACCOLTA = 20;

	public Cameriere(PortaPiatti contenitore) {
		this.contenitore = contenitore;
	}

	public void run() {
		try {
			while (true) {
				raccolta();
				contenitore.put(4);
			}
		} catch (InterruptedException e) { }
	}

	private void raccolta() throws InterruptedException {
		TimeUnit.SECONDS.sleep(TEMPO_RACCOLTA);
	}

}

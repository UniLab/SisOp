package appelli.ristorante;

import java.util.concurrent.TimeUnit;

public class LavaPiatti extends Thread {
	private PortaPiatti contenitore;
	private PortaPiatti scolaPiatti;
	public static final int TEMPO_LAVAGGIO = 15;

	public LavaPiatti(PortaPiatti contenitore, PortaPiatti scolaPiatti) {
		this.contenitore = contenitore;
		this.scolaPiatti = scolaPiatti;
	}

	public void run() {
		try {
			while (true) {
				contenitore.get();
				lavaggio();
				scolaPiatti.put(1);
			}
		} catch (InterruptedException e) {}
	}

	private void lavaggio() throws InterruptedException {
		TimeUnit.SECONDS.sleep(TEMPO_LAVAGGIO);
	}

	public String toString() { return "Lavapiatti #" + getId(); }
}

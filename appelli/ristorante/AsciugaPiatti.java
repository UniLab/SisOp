package appelli.ristorante;

import java.util.concurrent.TimeUnit;

public class AsciugaPiatti extends Thread {
	private PortaPiatti scolaPiatti;
	public static final int TEMPO_ASCIUGATURA = 10;

	public AsciugaPiatti(PortaPiatti scolaPiatti) {
		this.scolaPiatti = scolaPiatti;
	}

	public void run() {
		try {
			while (true) {
				scolaPiatti.get();
				asciugatura();
			}
		} catch (InterruptedException e) {}
	}

	private void asciugatura() throws InterruptedException {
		TimeUnit.SECONDS.sleep(TEMPO_ASCIUGATURA);
	}

	public String toString() { return "Asciugapiatti #" + getId(); }
}

package appelli.formicaio;

import java.util.concurrent.Semaphore;

public class FormicaioSem extends Formicaio {
	
	Semaphore ingresso, larve;

	public FormicaioSem(int z, int y) {
		super(z, y);
		ingresso = new Semaphore(z);
		larve = new Semaphore(y, true);
	}

	public void occupaIngresso() throws InterruptedException {
		ingresso.acquire();
	}

	public void liberaIngresso() {
		ingresso.release();
	}

	public void occupaLarva() throws InterruptedException {
		larve.acquire();
	}

	public void liberaLarva() {
		larve.release();
	}
}

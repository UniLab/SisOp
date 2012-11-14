import java.util.concurrent.TimeUnit;
import java.util.Random;

public class Lettore implements Runnable {

	private static final int MIN_TEMPO_LETTURA = 1;
	private static final int MAX_TEMPO_LETTURA = 4;
	private static final int MIN_TEMPO_ALTRO = 6;
	private static final int MAX_TEMPO_ALTRO = 10;
	
	private MemoriaCondivisa memoria;
	private Random random = new Random();
	
	public Lettore(MemoriaCondivisa mem) {
		memoria = mem;
	}
	public void run() {
		try {
			while (true) {
				memoria.inizioLettura();
				leggi();
				memoria.fineLettura();
				faiAltro();
			}
		} catch (InterruptedException e) {}
	}
	private void leggi() throws InterruptedException {
		attendi(MIN_TEMPO_LETTURA, MAX_TEMPO_LETTURA);
	}
	private void faiAltro() throws InterruptedException {
		attendi(MIN_TEMPO_ALTRO, MAX_TEMPO_ALTRO);
	}
	private void attendi(int min, int max) throws InterruptedException {
		TimeUnit.SECONDS.sleep(random.nextInt(max - min + 1) + min);
	}
}

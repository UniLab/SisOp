package esercitazione9;

public class BarbiereAddormentato {
	public static void main(String[]args) {

		Salone s = new SaloneSyncFIFO(10);

		Cliente[] clienti = new Cliente[100];
		for (int i = 0; i < clienti.length; i++) clienti[i] = new Cliente(s);

		Thread barbiere = new Thread(new Barbiere(s));

		barbiere.start();
		for (Cliente c: clienti) c.start();

	}
}

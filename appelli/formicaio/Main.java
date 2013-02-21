package appelli.formicaio;

public class Main {
	public static void main(String[]args) {
		int x = 30, y = 20, z = 15;
		Formicaio f = new FormicaioSem(z, y);
		Formica[] formica = new Formica[x];
		for (int i = 0; i < x; i++)
			formica[i] = new Formica(f);
		for (int i = 0; i < x; i++)
			formica[i].start();
	}
}

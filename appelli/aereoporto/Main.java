package appelli.aereoporto;

public class Main {
	public static void main(String[]args) {
		int p = 10, q = 15, r = 30;
		Aereoporto a = new AereoportoSem(p, q, r);
		Navetta[] navette = new Navetta[q];
		Aereo[] aerei = new Aereo[r];
		for (int i = 0; i < q; i++) navette[i] = new Navetta(a, i);
		for (int i = 0; i < r; i++) aerei[i] = new Aereo(a);
		for (int i = 0; i < q; i++) navette[i].start();
		for (int i = 0; i < r; i++) aerei[i].start();
	}
}

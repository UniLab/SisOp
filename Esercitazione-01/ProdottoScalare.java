/*

Si realizzi un’applicazione multithreaded che calcola il
prodotto scalare di due array di interi di dimensione n.
Il sistema utilizza m oggetti thread istanza di una
classe ProdottoScalare. Ciascun oggetto
ProdottoScalare calcola il prodotto scalare su una
porzione di lunghezza n/m dei due array (si assuma
che n sia multiplo di m).
Si definisca un main che inizializzi due array, e
utilizzando opportunamente gli oggetti thread, calcoli e
stampi il prodotto scalare dei due array.
Si assuma che m sia una costante definita nel
programma.

*/
public class ProdottoScalare extends Thread {
	private int[] a, b;
	private int start, end, p;
	public ProdottoScalare(int[] a, int[] b, int start, int end) {
		this.a = a; this.b = b;
		this.start = start; this.end = end;
		this.p = 0;
	}
	public int getProdotto() {
		try {
			join();
		} catch(InterruptedException e) {
			System.err.println(e);
		}
		return p;
	}
	public void run() {
		for (int i = start; i < end; i++)
			p += a[i] * b[i];
	}
	public static void main(String[]args) {
		int[] a = {2, 3, 6, -5, 4, 1, 8, -3, 6};
		int[] b = {0, 1, 9, 2, -4, 8, -1, 2, 7};
		int n = a.length;
		int m = 3;
		int l = n / m;
		ProdottoScalare[] ps = new ProdottoScalare[m];
		for (int i = 0; i < m; i++) {
			ps[i] = new ProdottoScalare(a, b, i * l, (i + 1) * l);
			ps[i].start();
		}
		int res = 0;
		for (ProdottoScalare p: ps) res += p.getProdotto();
		System.out.println("Prodotto scalare: " + res);
	}
}

/*

Si realizzi un’applicazione multithreaded che, data una
matrice di interi A e due numeri interi x ed y, verifica se
nella matrice A il numero di ripetizioni di x è strettamente
maggiore del numero di ripetizioni di y.
Se A è costituita da k righe, l’applicazione dovrà utilizzare
k thread, ciascuno dei quali conterà le ripetizioni di x ed y
in una determinata riga di A.
Si definisca un main che inizializzi la matrice A e gli interi x
ed y, conta il numero di ripetizioni di x ed y in A utilizzando
un insieme di thread che operano in parallelo, ed infine
stampa true se x è più presente di y, false altrimenti.

*/
public class Ripetizioni extends Thread {
	int[][] A;
	int r, x, y, rx = 0, ry = 0;
	public Ripetizioni(int[][] A, int r, int x, int y) {
		this.A = A; this.r = r;
		this.x = x; this.y = y;
	}
	public int getRX() {
		try { join(); } catch(InterruptedException e) { }
		return rx;
	}
	public int getRY() {
		try { join(); } catch(InterruptedException e) { }
		return ry;
	}
	public void run() {
		for (int i = 0; i < A[r].length; i++) {
			if (A[r][i] == x) rx++;
			if (A[r][i] == y) ry++;
		}
	}
	public static void main(String[]args) {
		int[][] A = {	{3, 2, -3, 1, -5, 7},
				{2, 8, 3, -2, 0, 18},
				{12, 10, 4, -9, 3, 5},
				{-6, 3, 0, 11, 5, 2} };
		int k = A.length;
		int x = 2;
		int y = 3;
		Ripetizioni[] r = new Ripetizioni[k];
		for (int i = 0; i < k; i++) {
			r[i] = new Ripetizioni(A, i, x, y);
			r[i].start();
		}
		int rx = 0, ry = 0;
		for (int i = 0; i < k; i++) {
			rx += r[i].getRX();
			ry += r[i].getRY();
		}
		System.out.println(rx > ry);
	}
}

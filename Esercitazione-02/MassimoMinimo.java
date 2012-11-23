/*

Si realizzi un’applicazione multithreaded che, data una
matrice rettangolare di interi, individua, se esiste, un
elemento che è contemporaneamente il massimo della
propria riga ed il minimo della propria colonna.
In particolare, data una matrice n x m, si utilizzino:
   - n thread, ciascuno dei quali calcola il massimo di una
     determinata riga della matrice;	
   - m thread, ciascuno dei quali calcola il minimo di una
     determinata colonna della matrice.
Si definisca un main che inizializza una matrice di test e,
utilizzando opportunamente gli oggetti thread, individua e
stampa la posizione di un elemento della matrice che è
massimo della propria riga e minimo della propria colonna.

*/
class Massimo extends Thread {
	int[][] m;
	final int r;
	int c = 0;
	public Massimo(int[][] m, int r) {
		this.m = m; this.r = r;
	}
	public int getColonna() {
		try { join(); } catch(InterruptedException e) { }
		return c;
	}
	public void run() {
		for (int j = 1; j < m[r].length; j++)
			if (m[r][j] > m[r][c]) c = j;
	}
}

class Minimo extends Thread {
	int[][] m;
	final int c;
	int r = 0;
	public Minimo(int[][] m, int c) {
		this.m = m; this.c = c;
	}
	public int getRiga() {
		try { join(); } catch(InterruptedException e) { }
		return r;
	}
	public void run() {
		for (int i = 1; i < m.length; i++)
			if (m[i][c] < m[r][c]) r = i;
	}
}

public class MassimoMinimo {
	public static void main(String[]args) {
		int[][] ma = {	{3, 5, -3, 1},
				{2, 8, 3, -2},
				{12, 10, 2, -9},
				{-6, 6, 0, 11},
				{10, 13, 21, 1}	};
		int n = ma.length;
		int m = ma[0].length;
		Massimo[] ms = new Massimo[n];
		Minimo[] mn = new Minimo[m];
		for (int i = 0; i < n; i++) {
			ms[i] = new Massimo(ma, i);
			ms[i].start();
		}
		for (int j = 0; j < m; j++) {
			mn[j] = new Minimo(ma, j);
			mn[j].start();
		}
		int r, c;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				c = ms[i].getColonna();
				r = mn[j].getRiga();
				if (r == i && c == j) {
					System.out.println("Il primo elemento massimo di riga e" +
						"minimo di colonna occupa la posizione (" + r + "," + c + ").");
					return;
				}
			}
		}
	}
}

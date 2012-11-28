package eseercitazione3;

class RigaNTS extends Thread {
	int[][] A; int r;
	public RigaNTS(int[][] A, int r) {
		this.A = A; this.r = r;
	}
	public void run() {
		for (int i = 0; i < A[r].length; i++)
			A[r][i]--;
	}
}

class ColonnaNTS extends Thread {
	int[][] A; int c;
	public ColonnaNTS(int[][] A, int c) {
		this.A = A; this.c = c;
	}
	public void run() {
		for (int i = 0; i < A.length; i++)
			A[i][c]++;
	}
}


public class MatriceNTS {
	public static void main(String[]args) {
		int n = 1000, m = 1000;
		int[][] A = new int[n][m];
		for (int i = 0; i < A.length; i++)
			for (int j = 0; j < A[i].length; j++)
				A[i][j] = 0;
		RigaNTS[] righe = new RigaNTS[n];
		ColonnaNTS[] colonne = new ColonnaNTS[m];
		for (int i = 0; i < n; i++) {
			righe[i] = new RigaNTS(A, i);
			righe[i].start();
		}
		for (int i = 0; i < m; i++) {
			colonne[i] = new ColonnaNTS(A, i);
			colonne[i].start();
		}
		try {
			for (int i = 0; i < n; i++) righe[i].join();
			for (int i = 0; i < m; i++) colonne[i].join();
		} catch(InterruptedException e) { }
		for (int i = 0; i < A.length; i++)
			for (int j = 0; j < A[i].length; j++)
				if (A[i][j] != 0) System.out.println("Errore!");
	}
}

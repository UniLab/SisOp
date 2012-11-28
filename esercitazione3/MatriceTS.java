package esercitazione3;

import java.util.concurrent.atomic.AtomicInteger;

class RigaTS extends Thread {
	AtomicInteger[][] A; int r;
	public RigaTS(AtomicInteger[][] A, int r) {
		this.A = A; this.r = r;
	}
	public void run() {
		for (int i = 0; i < A[r].length; i++)
			A[r][i].getAndDecrement();
	}
}

class ColonnaTS extends Thread {
	AtomicInteger[][] A; int c;
	public ColonnaTS(AtomicInteger[][] A, int c) {
		this.A = A; this.c = c;
	}
	public void run() {
		for (int i = 0; i < A.length; i++)
			A[i][c].getAndIncrement();
	}
}


public class MatriceTS {
	public static void main(String[]args) {
		int n = 1000, m = 1000;
		AtomicInteger[][] A = new AtomicInteger[n][m];
		for (int i = 0; i < A.length; i++)
			for (int j = 0; j < A[i].length; j++)
				A[i][j] = new AtomicInteger(0);
		RigaTS[] righe = new RigaTS[n];
		ColonnaTS[] colonne = new ColonnaTS[m];
		for (int i = 0; i < n; i++) {
			righe[i] = new RigaTS(A, i);
			righe[i].start();
		}
		for (int i = 0; i < m; i++) {
			colonne[i] = new ColonnaTS(A, i);
			colonne[i].start();
		}
		try {
			for (int i = 0; i < n; i++) righe[i].join();
			for (int i = 0; i < m; i++) colonne[i].join();
		} catch(InterruptedException e) { }
		for (int i = 0; i < A.length; i++)
			for (int j = 0; j < A[i].length; j++)
				if (A[i][j].get() != 0) System.out.println("Errore!");
	}
}

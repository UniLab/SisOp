package appelli.aereoporto;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.Queue;
import java.util.LinkedList;

public class AereoportoLC extends Aereoporto {
	
	private Lock l = new ReentrantLock();
	private Condition pista = l.newCondition(), pistaDecollo = l.newCondition(), navetta = l.newCondition();
	private Queue<Thread> codaAtterraggi = new LinkedList<Thread>(),
		codaDecolli = new LinkedList<Thread>();
	private Condition[] navette, imbarchi;
	private int numNavetteLibere = 0;

	public AereoportoLC(int p, int q, int r) {
		super(p, q, r);
		navette = new Condition[q];
		imbarchi = new Condition[q];
		for (int i = 0; i < q; i++) {
			navette[i] = l.newCondition();
			imbarchi[i] = l.newCondition();
		}
	}

	public int richiediAtterraggio() throws InterruptedException {
		l.lock();
		try {
			System.out.println("Aereo #" + Thread.currentThread().getId() + " richiede atterraggio");
			codaAtterraggi.offer(Thread.currentThread());
			while (numPisteLibere == 0 || codaAtterraggi.peek() != Thread.currentThread()) pista.await();
			codaAtterraggi.poll();
			int pista = scegliPista();
			pisteLibere[pista] = false;
			numPisteLibere--;
			return pista;
		} finally { l.unlock(); }
	}
	
	public int attendiDecollo() throws InterruptedException {
		l.lock();
		try {
			System.out.println("Aereo #" + Thread.currentThread().getId() + " attende navetta");
			while (numNavetteLibere == 0) navetta.await();
			int n = scegliNavetta();
			numNavetteLibere--;
			navetteLibere[n] = false;
			navette[n].signal();
			while (!navetteLibere[n]) imbarchi[n].await();
			System.out.println("Aereo #" + Thread.currentThread().getId() + " richiede decollo");
			codaDecolli.offer(Thread.currentThread());
			while (numPisteLibere <= codaAtterraggi.size() || codaDecolli.peek() != Thread.currentThread()) pistaDecollo.await();
			codaDecolli.poll();
			int pista = scegliPista();
			pisteLibere[pista] = false;
			numPisteLibere--;
			return pista;
		} finally { l.unlock(); }
	}

	public void rilasciaPista(int p) throws InterruptedException {
		l.lock();
		try {
			pisteLibere[p] = true;
			numPisteLibere++;
			if (codaAtterraggi.size() > 0) pista.signalAll();
			else if (codaDecolli.size() > 0) pistaDecollo.signalAll();
		} finally { l.unlock(); }
	}

	public void attendiAereo(int n) throws InterruptedException {
		l.lock();
		try {
			numNavetteLibere++;
			navetta.signal();
			while (navetteLibere[n]) navette[n].await();
		} finally { l.unlock(); }
	}

	public void liberaAereo(int n) {
		l.lock();
		try {
			navetteLibere[n] = true;
			imbarchi[n].signal();
		} finally { l.unlock(); }
	}

}

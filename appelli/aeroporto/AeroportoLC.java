package appelli.aeroporto;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.LinkedList;

public class AeroportoLC extends Aeroporto {
	
	private Lock lockPiste = new ReentrantLock();
	private Lock lockNavette = new ReentrantLock();
	private Condition pistaAtterraggio = lockPiste.newCondition(),
		pistaDecollo = lockPiste.newCondition(),
		navetta = lockNavette.newCondition();
	private LinkedList<Thread> codaAtterraggi = new LinkedList<Thread>(),
		codaDecolli = new LinkedList<Thread>();
	private Condition[] navette, imbarchi;
	private int numNavetteLibere = 0;

	public AeroportoLC(int p, int q, int r) {
		super(p, q, r);
		navette = new Condition[q];
		imbarchi = new Condition[q];
		for (int i = 0; i < q; i++) {
			navette[i] = lockNavette.newCondition();
			imbarchi[i] = lockNavette.newCondition();
		}
	}

	public void richiediAtterraggio() throws InterruptedException {
		lockPiste.lock();
		try {
			codaAtterraggi.addLast(Thread.currentThread());
			while (!turnoAtterraggio()) pistaAtterraggio.await();
			codaAtterraggi.removeFirst();
			numPisteLibere--;
		} finally { lockPiste.unlock(); }
	}
	
	public void attendiDecollo() throws InterruptedException {
		lockNavette.lock();
		try {
			while (numNavetteLibere == 0) navetta.await();
			int n = scegliNavetta();
			numNavetteLibere--;
			navetteLibere[n] = false;
			navette[n].signal();
			while (!navetteLibere[n]) imbarchi[n].await();
		} finally { lockNavette.unlock(); }
		lockPiste.lock();
		try {
			codaDecolli.addLast(Thread.currentThread());
			while (!turnoDecollo()) pistaDecollo.await();
			codaDecolli.removeFirst();
			numPisteLibere--;
		} finally { lockPiste.unlock(); }
	}

	public void rilasciaPista() throws InterruptedException {
		lockPiste.lock();
		try {
			numPisteLibere++;
			if (codaAtterraggi.size() > 0) pistaAtterraggio.signalAll();
			else if (codaDecolli.size() > 0) pistaDecollo.signalAll();
		} finally { lockPiste.unlock(); }
	}

	public void attendiAereo(int n) throws InterruptedException {
		lockNavette.lock();
		try {
			numNavetteLibere++;
			navetta.signal();
			while (navetteLibere[n]) navette[n].await();
		} finally { lockNavette.unlock(); }
	}

	public void liberaAereo(int n) {
		lockNavette.lock();
		try {
			navetteLibere[n] = true;
			imbarchi[n].signal();
		} finally { lockNavette.unlock(); }
	}

	private boolean turnoAtterraggio() {
		return numPisteLibere > 0 && codaAtterraggi.getFirst() == Thread.currentThread();
	}

	private boolean turnoDecollo() {
		return numPisteLibere > codaAtterraggi.size() && codaDecolli.getFirst() == Thread.currentThread();
	}

}

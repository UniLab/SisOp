package esercitazione7;

import esercitazione6.Hoare;
import esercitazione6.ThreadUtenteHoare;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.Deque;
import java.util.LinkedList;

public class MonitorHoareLC implements Hoare {
	private Lock l = new ReentrantLock();
	private Condition ingresso = l.newCondition();
	private Condition condition = l.newCondition();
	private Condition segnalanti = l.newCondition();
	private Deque<ThreadInterno> stackSegnalanti = new LinkedList<ThreadInterno>();
	private Deque<Integer> stackSegnalati = new LinkedList<Integer>();

	private Thread threadCorrente = null;
	private int threadInAttesa = 0, threadSegnalanti = 0;

	public enum Stato {SPURIOUS, SIGNAL};

	private static class ThreadInterno {
		private Thread t;
		private Stato s = Stato.SPURIOUS;

		public ThreadInterno(Thread t) { this.t = t; }

		public Thread getT() { return t; }

		public Stato getS() { return s; }
		public void setS(Stato s) { this.s = s; }
	}

	public void lock() throws InterruptedException {
		l.lock();
		attendiThreadInterni();
		threadCorrente = Thread.currentThread();
		System.out.println("Thread " + Thread.currentThread().getId() + " entra nel monitor");
	}
	
	public void unlock() {
		l.lock();
		try {
			if (threadCorrente != Thread.currentThread()) throw new IllegalMonitorStateException();
			System.out.println("Thread " + Thread.currentThread().getId() + " lascia il monitor");
			threadCorrente = null;
			if (threadSegnalanti > 0 && stackSegnalati.peekFirst() == 0) {
				stackSegnalanti.peekFirst().setS(Stato.SIGNAL);
				segnalanti.signalAll();
			} else ingresso.signal();
		} finally { l.unlock(); }
	}
	
	public void await() throws InterruptedException {
		l.lock();
		if (threadCorrente != Thread.currentThread()) throw new IllegalMonitorStateException();
		threadCorrente = null;
		threadInAttesa++;
		if (threadSegnalanti > 0 && stackSegnalati.peekFirst() == 0) {
			stackSegnalanti.peekFirst().setS(Stato.SIGNAL);
			segnalanti.signalAll();
		} else ingresso.signal();
		System.out.println("Thread " + Thread.currentThread().getId() + " attende nella condition");
		while (stackSegnalati.isEmpty() || stackSegnalati.peekFirst() == 0) condition.await();
		System.out.println("Thread " + Thread.currentThread().getId() + " esce dalla condition");
		stackSegnalati.push(stackSegnalati.pop() - 1);
		threadCorrente = Thread.currentThread();
	}
	
	public void signal() throws InterruptedException {
		l.lock();
		if (threadCorrente != Thread.currentThread()) throw new IllegalMonitorStateException();
		if (threadInAttesa > 0) {
			condition.signal();
			threadInAttesa--;
			stackSegnalati.addFirst(1);
			stackSegnalanti.addFirst(new ThreadInterno(Thread.currentThread()));
			threadCorrente = null;
			threadSegnalanti++;
			System.out.println("Thread " + Thread.currentThread().getId() + " esegue una signal");
			while (stackSegnalanti.peekFirst().getT() != Thread.currentThread() ||
				stackSegnalanti.peekFirst().getS() == Stato.SPURIOUS) segnalanti.await();
			System.out.println("Thread " + Thread.currentThread().getId() + " ritorna dopo la signal");
			threadSegnalanti--;
			threadCorrente = Thread.currentThread();
			stackSegnalati.removeFirst();
			stackSegnalanti.removeFirst();
		}
	}
	
	public void signalAll() throws InterruptedException {
		l.lock();
		if (threadCorrente != Thread.currentThread()) throw new IllegalMonitorStateException();
		if (threadInAttesa > 0) {
			condition.signalAll();
			stackSegnalati.addFirst(threadInAttesa);
			stackSegnalanti.addFirst(new ThreadInterno(Thread.currentThread()));
			threadInAttesa = 0;
			threadCorrente = null;
			threadSegnalanti++;
			System.out.println("Thread " + Thread.currentThread().getId() + " esegue una signalAll");
			while (stackSegnalanti.peekFirst().getT() != Thread.currentThread() ||
				stackSegnalanti.peekFirst().getS() == Stato.SPURIOUS) segnalanti.await();
			System.out.println("Thread " + Thread.currentThread().getId() + " ritorna dopo la signalAll");
			threadSegnalanti--;
			threadCorrente = Thread.currentThread();
			stackSegnalati.removeFirst();
			stackSegnalanti.removeFirst();
		}
	}

	private void attendiThreadInterni() throws InterruptedException {
		// Precedenza a chi è già dentro il monitor
		while (threadCorrente != null || !stackSegnalati.isEmpty() || !stackSegnalanti.isEmpty())
			ingresso.await();
	}

	public void test(int threads) {
		ThreadUtenteHoare[] t = new ThreadUtenteHoare[threads];
		for (int i = 0; i < t.length; i++)
			t[i] = new ThreadUtenteHoare(this);
		for (int i = 0; i < t.length; i++)
			t[i].start();
	}

	public static void main(String[]args) {
		MonitorHoareLC m = new MonitorHoareLC();
		m.test(10);
	}
}

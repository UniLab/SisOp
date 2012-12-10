/*

Scrivere una classe che, utilizzando i monitor nativi di Java,
implementi un semaforo contatore che garantisca il risveglio in
ordine LIFO dei thread sospesi, e che implementi la seguente interfaccia:

public interface Semaphore {
	public void acquire() throws InterruptedException;
	public void acquire(int p) throws InterruptedException;
	public void release();
	public void release(int p);
}

*/


package esercitazione8;

import java.util.Deque;
import java.util.LinkedList;

public class SemaphoreNativo implements Semaphore {
	
	Deque<Request> stack = new LinkedList<Request>();
	private Object lock = new Object();
	private int permits;

	private static class Request {
		Thread thread;
		int permits;
		public Request(Thread t, int p) {
			thread = t; permits = p;
		}
	}

	public SemaphoreNativo(int p) {
		permits = p;
	}

	public synchronized void acquire() throws InterruptedException {
		acquire(1);
	}

	public synchronized void acquire(int p) throws InterruptedException {
		System.out.println("Thread #" + Thread.currentThread().getId() + " ne richiede " + p + ". Disponibili: " + permits);
		stack.addFirst(new Request(Thread.currentThread(), 1));
		while (!permessiSufficienti(p)) wait();
		// Anche se i permessi sono sufficienti, aspetto
		// i thread prima di me rispettando l'ordine LIFO
		while (stack.peekFirst().thread != Thread.currentThread())
			synchronized(lock) { lock.wait(); }
		stack.removeFirst();
		permits -= p;
		synchronized(lock) { lock.notifyAll(); }
		System.out.println("Thread #" + Thread.currentThread().getId() + " ne ottiene " + p + ". Disponibili: " + permits);
	}

	public synchronized void release() { release(1); }

	public synchronized void release(int p) {
		permits += p;
		notifyAll();
		System.out.println("Thread #" + Thread.currentThread().getId() + " ne rilascia " + p + ". Disponibili: " + permits);
	}

	private boolean permessiSufficienti(int p) {
		int permessiResidui = permits;
		// Tengo conto dei permessi che verranno acquisiti prima di me
		for (Request r: stack) {
			if (r.thread == Thread.currentThread()) break;
			permessiResidui -= r.permits;
		}
		return permessiResidui >= p;
	}
}

package appelli.formicaio;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.LinkedList;

public class FormicaioLC extends Formicaio {
	
	private Lock ingresso = new ReentrantLock();
	private Condition postoIngresso = ingresso.newCondition();
	private Lock nutrizione = new ReentrantLock();
	private Condition larvaDisponibile = nutrizione.newCondition();
	private LinkedList<Thread> coda = new LinkedList<Thread>();
	private int postiIngresso, larveLibere;

	public FormicaioLC(int z, int y) {
		super(z, y);
		postiIngresso = capacitaIngresso;
		larveLibere = numLarve;
	}

	public void occupaIngresso() throws InterruptedException {
		ingresso.lock();
		try {
			while (postiIngresso == 0) postoIngresso.await();
			postiIngresso--;
		} finally {
			ingresso.unlock();
		}
	}

	public void liberaIngresso() {
		ingresso.lock();
		try {
			postiIngresso++;
			postoIngresso.signal();
		} finally {
			ingresso.unlock();
		}
	}

	public void occupaLarva() throws InterruptedException {
		nutrizione.lock();
		try {
			coda.addLast(Thread.currentThread());
			while (!mioTurno()) larvaDisponibile.await();
			larveLibere--;
			coda.removeFirst();
		} finally {
			nutrizione.unlock();
		}
	}

	public void liberaLarva() {
		nutrizione.lock();
		try {
			larveLibere++;
			larvaDisponibile.signalAll();
		} finally {
			nutrizione.unlock();
		}
	}

	private boolean mioTurno() {
		return larveLibere > 0 && coda.getFirst() == Thread.currentThread();
	}

}

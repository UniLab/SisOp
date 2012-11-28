package esercitazione6;

public interface Hoare {

	public void lock() throws InterruptedException;
	
	public void unlock();
	
	public void await() throws InterruptedException;
	
	public void signal() throws InterruptedException;
	
	public void signalAll() throws InterruptedException;
}

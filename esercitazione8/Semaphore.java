package esercitazione8;

public interface Semaphore {

	public void acquire() throws InterruptedException;
	
	public void acquire(int p) throws InterruptedException;
	
	public void release();
	
	public void release(int p);

}

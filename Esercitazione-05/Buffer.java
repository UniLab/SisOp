public abstract class Buffer {
	
	protected Elemento[] buffer;
	protected int in = 0;
	protected int out = 0;

	public Buffer(int dimensione) {
		buffer = new Elemento[dimensione];
	}

	public abstract void put(Elemento e) throws InterruptedException;
	public abstract Elemento get() throws InterruptedException;

	public void test(int numProduttori, int numConsumatori) {
		for (int i = 0; i < numProduttori; i++) {
			new Thread(new Produttore(this)).start();
		}
		for (int i = 0; i < numConsumatori; i++) {
			new Thread(new Consumatore(this)).start();
		}
	}
}

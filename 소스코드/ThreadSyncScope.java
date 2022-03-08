class Counter{
	long count = 0;
	public synchronized void add(long value){
		this.count += value;
		System.out.println(Thread.currentThread().getId() + " : " + this.count);
	}
}

class CounterThread extends Thread{
	protected Counter counter = null;
	public CounterThread(Counter counter){
		this.counter = counter;
	}

	public void run() {
		for(int i=0; i<10; i++){
			counter.add(i);
		}
	}
}

public class ThreadSyncScope {
	public static void main(String[] args){
		Counter counter = new Counter();
		Thread  threadA = new CounterThread(counter);
		Thread  threadB = new CounterThread(counter);

		threadA.start();
		threadB.start();
	}
}

/*
public class ThreadSyncScope {
	public static void main(String[] args){
		Counter counter1 = new Counter();
		Counter counter2 = new Counter();
		Thread  threadA = new CounterThread(counter1);
		Thread  threadB = new CounterThread(counter2);

		threadA.start();
		threadB.start();
	}
}
*/

class Counter{
	long count = 0;
	public synchronized void add(long value){
		this.count += value;
		System.out.println(Thread.currentThread().getId() + " : " + this.count);
	}
}

class CounterThread implements Runnable{
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



public class ThreadSyncScopeRun {
	public static void main(String[] args){
		Counter counter = new Counter();
		Runnable rA = new CounterThread(counter);
		Thread  threadA = new Thread(rA);
		Thread  threadB = new Thread(rA);

		threadA.start();
		threadB.start();
	}
}

/*
public class ThreadSyncScopeRun {
	public static void main(String[] args){
		Counter counter = new Counter();
		Runnable rA = new CounterThread(counter);
		Runnable rB = new CounterThread(counter);
		Thread  threadA = new Thread(rA);
		Thread  threadB = new Thread(rB);

		threadA.start();
		threadB.start();
	}
}
*/

/*
public class ThreadSyncScopeRun {
	public static void main(String[] args){
		Counter counter1 = new Counter();
		Counter counter2 = new Counter();
		Runnable rA = new CounterThread(counter1);
		Runnable rB = new CounterThread(counter2);
		Thread  threadA = new Thread(rA);
		Thread  threadB = new Thread(rB);

		threadA.start();
		threadB.start();
	}
}
*/


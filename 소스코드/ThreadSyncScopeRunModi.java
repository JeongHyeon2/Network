class Counter{
	long count = 0;
	public synchronized void add(long value){
		this.count += value;
		System.out.println(Thread.currentThread().getId() + " : " + this.count);
	}
}

class CounterThread implements Runnable{
	protected Counter counter = new Counter();

	public void run() {
		for(int i=0; i<10; i++){
			counter.add(i);
		}
	}
}



public class ThreadSyncScopeRunModi {
	public static void main(String[] args){
		Runnable rA = new CounterThread();
		Thread  threadA = new Thread(rA);
		Thread  threadB = new Thread(rA);

		threadA.start();
		threadB.start();
	}
}


/*
public class ThreadSyncScopeRunModi {
	public static void main(String[] args){
		Runnable rA = new CounterThread();
		Runnable rB = new CounterThread();
		Thread  threadA = new Thread(rA);
		Thread  threadB = new Thread(rB);

		threadA.start();
		threadB.start();
	}
}
*/



class TimerThread extends Thread {
	int n = 0;
	public void run() {
		System.out.println("쓰레드 실행");
		while(true) { // 무한루프 실행
			System.out.println(n); 
			n++;
			 try {
				sleep(1000); //1초 동안 잠을 잔 후 깨어남
			}
			catch(InterruptedException e){return;}
		}
	}
}

public class TestThread {
	public static void main(String [] args) {
		TimerThread th = new TimerThread();
		th.start();
	}
}


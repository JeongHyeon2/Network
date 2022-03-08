class TimerThread extends Thread {
	int n = 0;
	boolean flag = false; // false로 초기화
	public void finish() { flag = true; }
	public void run() {
		while(true) {
			System.out.println(n); // 화면에 카운트 값 출력
			n++;
			try {
				sleep(1000);
				if(flag == true)
					return; // 스레드 종료
			}
			catch(InterruptedException e){
				return;
			}
		}
	}
}

class ThreadFlag{
	public static void main(String [] args) {
		TimerThread th = new  TimerThread();
		th.start();
		
		th.finish(); // TimerThread 강제 종료
	}
}


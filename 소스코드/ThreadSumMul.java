// 2개 쓰레드 생성 및 실행
class ThreadSum extends Thread{

	@Override
	public void run() {
		// 1부터 10까지 더해서 결과 출력하기
		int sum=0;
		for(int i=1 ; i <= 10 ; i++ ) {
			sum = sum + i ;
			System.out.println(i + "더하기: " + sum);
		}
		
		System.out.println("1~10 합계 결과: " + sum);
	}
}

class ThreadMul extends Thread{

	@Override
	public void run() {
		// 1부터 10까지 곱해서 결과 출력하기
		int multiply=1;
		for(int i=1 ; i <= 10 ; i++ ) {
			multiply = multiply * i ;
			System.out.println(i + "곱하기: " + multiply);
		}
		
		System.out.println("1~10 곱하기 결과: " + multiply);
	}
}

public class ThreadSumMul {
	public static void main(String [] args) {
		ThreadSum ths = new ThreadSum();
		ths.start();

		ThreadMul thm = new ThreadMul();
		thm.start();
	}
}


import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleThreadPoolSocketServer {
	// 연결할 포트를 지정합니다.
	private static final int PORT = 5000;
	// 스레드 풀의 최대 스레드 개수를 지정합니다.
	private static final int THREAD_CNT = 2;
	private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_CNT);
	public static void main(String[] args) {

		try {
			// 서버소켓 생성
			ServerSocket serverSocket = new ServerSocket(PORT);

			// 소켓서버가 종료될때까지 무한루프
			while(true){
				// 소켓 접속 요청이 올때까지 대기합니다.
				Socket socket = serverSocket.accept();
				try{
					// 요청이 오면 스레드 풀의 스레드로 소켓을 넣어줍니다.
					// 이후는 스레드 내에서 처리합니다.
					threadPool.execute(new ConnectionWrap(socket));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

// 소켓 처리용 래퍼 클래스입니다.
class ConnectionWrap implements Runnable{

	private Socket theSocket = null;
	InputStream is;
	BufferedReader reader;
	OutputStream os;
	BufferedWriter writer;
	String theLine;

	public ConnectionWrap(Socket socket) {
		this.theSocket = socket;
	}

	@Override
	public void run() {

		try {
			is = theSocket.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is));
			os = theSocket.getOutputStream();
			writer = new BufferedWriter(new OutputStreamWriter(os));
			while((theLine = reader.readLine()) != null ){ // 클라이언트 데이터 수신
				System.out.println(theLine); // 수신 데이터 화면 출력
				writer.write(theLine+'\r'+'\n'); 
				writer.flush(); // 클라이언트에 데이터 재전송
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				theSocket.close(); // 소켓 종료
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
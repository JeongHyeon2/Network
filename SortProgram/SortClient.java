import java.net.*;
import java.io.*;

public class SortClient {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

		Socket socket = new Socket("localhost", 3003);
		OutputStream os = socket.getOutputStream();
		InputStream is = socket.getInputStream();

		SortProtocol protocol = new SortProtocol();
		byte[] buf = protocol.getPacket();

		while (true) {
			is.read(buf);
			int packetType = buf[0];
			protocol.setPacket(packetType, buf);
			if (packetType == Protocol.PT_EXIT) {
				System.out.println("클라이언트 종료");
				break;
			}
			switch (packetType) {
			case SortProtocol.PT_REQ_DATA: //서버의 데이터 요청
				System.out.println("서버가 단어, 정수 요청");
				BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
				System.out.print("단어: ");
				String word = userIn.readLine();
				System.out.print("정수 : ");
				String integer = userIn.readLine();
				// 단어 정보 전송
				protocol = new SortProtocol(SortProtocol.PT_RES_DATA_WORD);
				protocol.setWord(word);
				System.out.println("단어 정보 전송");
				os.write(protocol.getPacket());
				// 숫자 정보 전송
				protocol = new SortProtocol(SortProtocol.PT_RES_DATA_INT);
				protocol.setWord(integer);
				System.out.println("숫자 정보 전송");
				os.write(protocol.getPacket());
				break;
			case SortProtocol.PT_RES_SORT_WORD: // 서버의 정렬 단어 결과를 수신 
				System.out.println("서버가 정렬 단어 결과 전송.");
				String result1 = protocol.getWord();
				System.out.println("정렬 결과 :" + result1);
				break;
			case SortProtocol.PT_RES_SORT_INT: // 서버의 정렬 숫자 결과를 수신 
				System.out.println("서버가 정렬 숫자 결과 전송.");
				String result2 = protocol.getInt();
				System.out.println("정렬 결과 :" + result2);
			}
		}
		os.close();
		is.close();
		socket.close();
	}
}

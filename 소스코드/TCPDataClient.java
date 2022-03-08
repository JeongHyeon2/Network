import java.io.*;
import java.net.*;
public class TCPDataClient
{
	public static int DT_LEN = 4;	// 키보드로부터 입력받은 데이터 길이를 저장할 변수 크기 (4바이트)

	public static void main(String args[]){
		Socket theSocket = null;
		String host;
		String theLine;

		if(args.length>0){
			host=args[0]; //  입력받은 호스트를 사용
		}else{
			host="localhost"; // 로컬 호스트를 사용
		}
		try{
			theSocket = new Socket(host, 5000); // 서버 접속

			InputStream is = theSocket.getInputStream();
			DataInputStream dis = new DataInputStream(is);

			OutputStream os = theSocket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);

			BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

			while(true){
				System.out.print("데이터 입력: ");
				theLine = userInput.readLine(); // 키보드로부터 데이터 입력
				if(theLine.equals("quit")) break; // 프로그램 종료

				byte[] payload = theLine.getBytes();
				byte[] sendData = new byte[payload.length + DT_LEN];

				int index = 0;
				// 데이터 길이 저장
				//byte[] payloadSize = intToByteArray(payload.length, DT_LEN);
				byte[] payloadSize = intToBytes(payload.length);
				sendData[0] = (byte)payloadSize[0];
				sendData[1] = (byte)payloadSize[1];
				sendData[2] = (byte)payloadSize[2];
				sendData[3] = (byte)payloadSize[3];
				index = 4;

			   // 데이터 내용 추가
				for(int i = 0; i < payload.length; i++){
					sendData[index] = payload[i];
					index++;
				}

				dos.write(sendData);
				dos.flush();
			}
		}catch(UnknownHostException e){
			System.err.println(args[0]+" 호스트를 찾을 수 없습니다.");
		}catch(IOException e){
			System.err.println(e);
		}finally{
			try{
				theSocket.close(); // 소켓을 닫는다.
			}catch(IOException e){
				System.out.println(e);
			}
		}
	}


	private static byte[] intToBytes(final int data) { 
		return new byte[] { 
			(byte)((data >> 24) & 0xff), 
			(byte)((data >> 16) & 0xff), 
			(byte)((data >> 8) & 0xff), 
			(byte)((data >> 0) & 0xff), 
		}; 
	} 

}   


/*
	public static byte[] intToByteArray(int value, int sizeofdata) {
		byte[] b = new byte[sizeofdata];
		for(int i=0; i < sizeofdata; i++) {
			int offset = (b.length - 1 - i) * 8;
			b[i] = (byte)((value >>> offset) & 0xFF);
		}
		return b;
	}
*/

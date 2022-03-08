import java.io.*;
import java.net.*;

public class TCPDataServer
{
	public static int MAX_LEN = 200;	// 최대 데이터 크기
	public static int DT_LEN = 4;	// 키보드로부터 입력받은 데이터 길이를 저장할 변수 크기 (4바이트)
	private ServerSocket server;

	public TCPDataServer(int portnum)
	{
		try
		{
			server = new ServerSocket(portnum);
		}
		catch (Exception err)
		{
			System.out.println(err);
		}
	}

	public void serve()
	{
		try
		{
			Socket theSocket = server.accept();

			while (true)
			{
				InputStream is = theSocket.getInputStream();
				DataInputStream dis = new DataInputStream(is);

				// 데이터 크기 수신
				byte[] recvHeader = new byte[DT_LEN];   // 수신용 데이터 크기 배열 생성
				int totalBytesRcvd = 0;  // Total bytes received so far
				int bytesRcvd;           // Bytes received in last read
				while (totalBytesRcvd < DT_LEN) {
					if ((bytesRcvd = dis.read(recvHeader, totalBytesRcvd, DT_LEN - totalBytesRcvd)) == -1)
						throw new SocketException("Connection close prematurely");
						totalBytesRcvd += bytesRcvd;
				}

				int dataLength = byteArrayToInt(recvHeader);
//				System.out.println("Received Length: " + dataLength);

				// 데이터 수신
				byte[] recvData = new byte[MAX_LEN];   // 수신용 데이터 배열 생성
				totalBytesRcvd = 0;  // Total bytes received so far
				while (totalBytesRcvd < dataLength) {
					if ((bytesRcvd = dis.read(recvData, totalBytesRcvd, dataLength - totalBytesRcvd)) == -1)
					throw new SocketException("Connection close prematurely");
						totalBytesRcvd += bytesRcvd;
				}
				System.out.println("Received Data(" + dataLength + " 바이트): " + new String(recvData));
			}
		}
		catch (Exception err)
		{
			System.err.println(err);
		}

	}

	private static int byteArrayToInt(byte[] data) { 
		if (data == null || data.length != 4) return 0x0; 
		// ---------- 
		return (int)( // NOTE: type cast not necessary for int 
				(0xff & data[0]) << 24  | 
				(0xff & data[1]) << 16  | 
				(0xff & data[2]) << 8   | 
				(0xff & data[3]) << 0 
				); 
	}

	public static void main(String[] args)
	{
		TCPDataServer s = new TCPDataServer(5000);
		s.serve();
	}

}



/*
	public static int byteArrayToInt(byte[] b) {
		int value = 0;
		int length = b.length;	// length of byte array
		for(int i=0; i < length; i++) {
			int shift = (length - 1 - i) * 8;
			value += (b[i] & 0x000000FF) << shift;
		}
		return value;
	}
*/


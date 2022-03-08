import java.io.*;
import java.net.*;

public class TCPOptionServer
{
	public static void main(String args[]){
		try {
*/
			// bind() 사용하는 경우
 			ServerSocket serverSocket = new ServerSocket();
			serverSocket.setSoTimeout(10000);
			System.out.println("SO_REUSEADDR is enabled: " + serverSocket.getReuseAddress());
			serverSocket.setReuseAddress(true);
			System.out.println("SO_REUSEADDR is enabled: " + serverSocket.getReuseAddress());
			InetAddress Address = InetAddress.getLocalHost();
			SocketAddress addr = new InetSocketAddress(Address,8800);
			serverSocket.bind(addr);


			// bind() 사용하지 않는 경우
/*
 			ServerSocket serverSocket = new ServerSocket(8800);
			serverSocket.setSoTimeout(10000);
			System.out.println("SO_REUSEADDR is enabled: " + serverSocket.getReuseAddress());
			serverSocket.setReuseAddress(true);
			System.out.println("SO_REUSEADDR is enabled: " + serverSocket.getReuseAddress());
*/

			// accept a new client
			Socket clientSocket = serverSocket.accept();

			// read data from client
			byte[] dataReceived = new byte[1000];
			int read = 0;
			do {
				read += clientSocket.getInputStream().read(dataReceived, read, dataReceived.length - read);
			} while (read < dataReceived.length);

			// write data back to client
			clientSocket.getOutputStream().write(dataReceived);

			clientSocket.close();
			serverSocket.close();

		} catch(IOException e) {
			System.out.println(e);
		}
	}
}
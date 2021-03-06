import java.net.*;
import java.io.*;
public class LoginClient {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
	    if(args.length < 2) System.out.println("사용법 : " + "java LoginClient 주소 포트번호");

	    Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
 	    OutputStream os = socket.getOutputStream();
	    InputStream is = socket.getInputStream();

  	    Protocol protocol = new Protocol();
	    byte[] buf = protocol.getPacket();

		while(true){
			is.read(buf);
			int packetType = buf[0];
			protocol.setPacket(packetType,buf);
			if(packetType == Protocol.PT_EXIT){
				System.out.println("클라이언트 종료");
				break;
			}

			switch(packetType){
				case Protocol.PT_REQ_LOGIN:
					System.out.println("서버가 로그인 정보 요청");
					BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
					System.out.print("아이디 : ");
					String id = userIn.readLine();
					System.out.print("암호 : ");
					String pwd = userIn.readLine();
					// 로그인 정보 생성 및 패킷 전송
					protocol = new Protocol(Protocol.PT_RES_LOGIN);
					protocol.setId(id);
					protocol.setPassword(pwd);
					System.out.println("로그인 정보 전송");
					os.write(protocol.getPacket());
					break;

				case Protocol.PT_LOGIN_RESULT:
					System.out.println("서버가 로그인 결과 전송.");
					String result = protocol.getLoginResult();
					if(result.equals("1")){
						System.out.println("로그인 성공");
					}else if(result.equals("2")){	
						System.out.println("암호 틀림");
					}else if(result.equals("3")){
						System.out.println("아이디가 존재하지 않음");
					}
					protocol = new Protocol(Protocol.PT_EXIT);
					System.out.println("종료 패킷 전송");
					os.write(protocol.getPacket());
					break;
			}			
	    }
 	    os.close();
	    is.close();
	    socket.close();
	}
}

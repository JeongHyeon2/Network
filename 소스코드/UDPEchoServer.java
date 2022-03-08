import java.net.*;
import java.io.*;

public class UDPEchoServer {

       private static final int ECHOMAX = 255; // Maximum size of echo datagram		
       public static void main(String[] args){
             if(args.length != 1){
                    System.out.println("사용법 : java UDPEchoServer port");
                    System.exit(1);
             }

             int port = 0;
             try{
                    port = Integer.parseInt(args[0]);
             }catch(Exception e){
                    System.out.println("Port 번호는 양의 정수로 입력해서 주세요.");
                    System.exit(1);
             }

             DatagramSocket dsock = null;
             try{
                    System.out.println("접속 대기상태입니다.");
                    dsock = new DatagramSocket(port);	// DatagramSocket 객체 생성

                    byte[] buffer = new byte[ECHOMAX];
                    DatagramPacket receivePacket = new DatagramPacket(buffer, ECHOMAX);

                    while(true){
                           // 클라이언트로부터 DatagramPacket 수신 대기
                           dsock.receive(receivePacket);

                           // 수신한 데이터를 String 객체에 지정하고 출력
                           String msg = new String(receivePacket.getData(), 0, receivePacket.getLength());
                           System.out.println("수신 데이터: " + msg);

                           if(msg.equals("quit")) break;

                           // 수신한 데이터 클라이언트에게 전송
                           DatagramPacket sendPacket = new DatagramPacket(receivePacket.getData(), receivePacket.getData().length, receivePacket.getAddress(), receivePacket.getPort());
                           dsock.send(sendPacket);

						   receivePacket.setLength(ECHOMAX);	// Reset length to avoid shrinking buffer
                    }

                    System.out.println("UDPEchoServer를 종료합니다.");
             }catch(Exception e){
                    System.out.println(e);
             }finally{
                    if(dsock != null) dsock.close();
             }
       }
}

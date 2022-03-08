import java.net.*;
import java.io.*;

public class UDPEchoClient {

       public static void main(String[] args){
             if(args.length != 2){
                    System.out.println("사용법 : java UDPEchoClient ip port");
                    System.exit(1);
             }

             String ip = args[0];
             int port = 0;
             try{
                    port = Integer.parseInt(args[1]);
             }catch(Exception e){
                    System.out.println("port 번호는 양의 정수로 입력해주세요");
                    System.exit(1);
             }

             InetAddress inetaddr = null;
             try{
                    inetaddr = InetAddress.getByName(ip);	// DatagramPacket에 들어갈 ip 주소
             }catch(UnknownHostException e){
                    System.out.println("잘못된 도메인이나 ip입니다.");
                    System.exit(1);
             }

             DatagramSocket dsock = null;
             try{
                    // 키보드로부터 문자열 입력, System.in을 BufferedReader 형태로 변환
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

                    dsock = new DatagramSocket();
					String line = null;
                    while((line = br.readLine())!= null){
                           // DatagramPacket에 각 정보를 담고 전송
                           DatagramPacket sendPacket = new DatagramPacket(line.getBytes(), line.getBytes().length, inetaddr, port);
                           dsock.send(sendPacket);

                           if(line.equals("quit")) break;

                           byte[] buffer = new byte[line.getBytes().length];

                           // 서버로부터 DatagramPacket 수신 대기
                           DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                           dsock.receive(receivePacket);

                           // 수신한 데이터를 String 객체에 지정하고 출력
                           String msg = new String(receivePacket.getData(), 0, receivePacket.getData().length);
                           System.out.println("수신 데이터: " + msg);
                    }

                    System.out.println("UDPEchoClient를 종료합니다.");
             }catch(Exception ex){
                    System.out.println(ex);
             }finally{
                    if(dsock != null) dsock.close();
             }
       }
}


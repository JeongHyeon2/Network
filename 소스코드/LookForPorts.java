import java.io.*;
import java.net.*;
public class LookForPorts
{
   public static void main(String args[]){
      Socket theSocket;
      String host="localhost"; // 호스트를 지정하지 않으면 사용
      if(args.length == 1){
         host=args[0]; // 명령어 라인에서 입력된 호스트 이름을 사용
      }
      for(int i=1; i<1024; i++){
         try{
            theSocket  = new Socket(InetAddress.getByName(host), i);
            System.out.println(host+"의 "+i+"번 포트는 특정한 서버가 사용합니다.");
         }catch(UnknownHostException e){
            System.err.println(e);
            break;
         }catch(IOException e){
         }
      }
   }
}
import java.io.*;
import java.net.*;
public class TCPEchoServer
{
   public static void main(String args[]){
      ServerSocket theServer;
      Socket theSocket = null;
      InputStream is;
      BufferedReader reader;
      OutputStream os;
      BufferedWriter writer;
      String theLine;
      try{
         theServer = new ServerSocket(5000); // 서버 포트 
         theSocket = theServer.accept(); // 서버측의 소켓 생성.
         is = theSocket.getInputStream();
         reader = new BufferedReader(new InputStreamReader(is));
         os = theSocket.getOutputStream();
         writer = new BufferedWriter(new OutputStreamWriter(os));
         while((theLine = reader.readLine()) != null ){ // 클라이언트 데이터 수신
            System.out.println(theLine); // 수신 데이터 화면 출력
            writer.write(theLine+'\r'+'\n'); 
            writer.flush(); // 클라이언트에 데이터 재전송 
         }
      }catch(UnknownHostException e){
         System.err.println(e);
      }catch(IOException e){
         System.err.println(e);
      }finally{
         if(theSocket != null){
            try{
               theSocket.close();
            }catch(IOException e){
               System.out.println(e);
            }
         }
      }
   }
}   
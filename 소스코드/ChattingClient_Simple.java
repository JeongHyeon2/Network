import java.io.*;
import java.net.*;
public class ChattingClient_Simple
{
   public static void main(String args[]){
      Socket theSocket = null;
      String host;
      InputStream is;
      BufferedReader reader, userInput;
      OutputStream os;
      BufferedWriter writer;
      String theLine;
      if(args.length>0){
         host=args[0]; //  입력받은 호스트를 사용
      }else{
         host="localhost"; // 로컬 호스트를 사용
      }
      try{
         theSocket = new Socket(host, 5000); // 서버 접속
         is = theSocket.getInputStream();
         reader = new BufferedReader(new InputStreamReader(is));
         userInput = new BufferedReader(new InputStreamReader(System.in));
         os = theSocket.getOutputStream();
         writer = new BufferedWriter(new OutputStreamWriter(os));

         //read input from server
         theLine = (String)reader.readLine();
         System.out.println("Server> " + theLine);

         while(true){
            System.out.print("문장 입력: ");
			theLine = userInput.readLine(); // 데이터 입력
            if(theLine.equals("bye")) break; // 프로그램 종료
            writer.write(theLine+'\r'+'\n'); 
            writer.flush(); // 서버에 데이터 전송 
            System.out.println("Server> " + reader.readLine()); //서버가 전송한 데이터 읽고, 화면 출력
         }
      }catch(UnknownHostException e){
         System.err.println(args[0]+" 호스트를 찾을 수 없습니다.");
      }catch(IOException e){
         System.err.println(e);
      }finally{
         try{
            theSocket.close(); // 소켓 종료
         }catch(IOException e){
            System.out.println(e);
         }
      }
   }
}   
import java.io.*;
import java.net.*;

public class ShutDownEx {
 public static void main(String[] ar){

  //초기화
  InetAddress ia = null;
  Socket soc = null;
  PrintWriter out = null;
  BufferedReader br = null;

  try{
   ia = InetAddress.getByName("www.kumoh.ac.kr");  // 학교 서버 주소를 얻어 InetAddress 객체를 생성한다.
   soc = new Socket(ia, 80);    //ia 객체에 저장된 IP 주소로 연결하여 TCP의 80번 포트로 연결을 요청한다.

   out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())));
   //출력 스트림을 생성한다.

   br = new BufferedReader(new InputStreamReader(soc.getInputStream())); 
   //입력 스트림을 생성한다.

  }catch(IOException ie){
   System.err.println("연결 오류 : "+ie.toString());
   System.exit(-1);
  }
  
  try{
   out.println("GET http://www.kumoh.ac.kr/main.do HTTP/1.0\r\n\n");
   //요청 형식에 맞게 main 내용을 요청한다.
   out.flush();
   //버퍼를 비운다. 위에서 작성한 내용을 서버로 전송한다.
   
   //서버에서 응답해 올 데이터를 입력 받는다.
   while(true){
    String str = br.readLine();
    //서버에서 전송되어 오는 내용을 읽어 들인다.

    if (str == null){ //더이상 읽어올 데이터가 없다면
     break;
    }
    System.out.println(str);
   }
   soc.shutdownOutput();  //소켓 출력 스트림 객체를 닫는다.
   soc.shutdownInput();    //소켓 입력 스트림 객체를 닫는다.
   soc.close();                //연결을 종료한다.
  }catch(IOException ie){}
 }
}

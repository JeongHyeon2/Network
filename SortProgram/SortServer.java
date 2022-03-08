import java.net.*;
import java.util.Arrays;
import java.io.*;

public class SortServer {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        ServerSocket sSocket = new ServerSocket(3003);
        System.out.println("클라이언트 접속 대기중...");
        Socket socket = sSocket.accept();
        System.out.println("클라이언트 접속");

        OutputStream os = socket.getOutputStream();
        InputStream is = socket.getInputStream();

        // 단어 및 정수 요청용 프로토콜 객체 생성 및 전송
        SortProtocol protocol = new SortProtocol(SortProtocol.PT_REQ_DATA);
        os.write(protocol.getPacket());

        boolean program_stop = false;

        while (true) {
            protocol = new SortProtocol(); // 새 Protocol 객체 생성
            byte[] buf = protocol.getPacket();
            is.read(buf); // 클라이언트로부터 단어,정수 수신
            int packetType = buf[0]; // 수신 데이터에서 패킷 타입 얻음
            protocol.setPacket(packetType, buf);

            switch (packetType) {
                case SortProtocol.PT_EXIT: // 프로그램 종료 수신
                    protocol = new SortProtocol(SortProtocol.PT_EXIT);
                    os.write(protocol.getPacket());
                    program_stop = true;
                    System.out.println("서버종료");
                    break;
                case SortProtocol.PT_RES_DATA_WORD: // 단어 정보를 받아 정렬 후 송신
                    System.out.println("클라이언트가 단어 정보를 보냈습니다");
                    String words = protocol.getWord();
                    System.out.println(words);

                    String[] arr = words.split(" "); // 단어를 배열에 저장
                    Arrays.sort(arr); // 정렬
                    protocol = new SortProtocol(SortProtocol.PT_RES_SORT_WORD);
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < arr.length; i++) {
                        sb.append(arr[i]);
                        sb.append(" ");
                    } // 정렬 결과 송신
                    protocol.setWord(sb.toString());
                    os.write(protocol.getPacket());
                    break;
                case SortProtocol.PT_RES_DATA_INT: // 숫자 정보를 받아 정렬 후 송신
                    System.out.println("클라이언트가 숫자 정보를 보냈습니다");
                    String integer = protocol.getInt();
                    System.out.println(integer);

                    String[] tmp = integer.split(" ");
                    int[] intArr = new int[tmp.length]; // 숫자를 배열에 저장
                    for (int i = 0; i < tmp.length; i++) {
                        intArr[i] = Integer.parseInt(tmp[i]);
                    }
                    Arrays.sort(intArr); // 숫자 정렬
                    protocol = new SortProtocol(SortProtocol.PT_RES_SORT_WORD);
                    StringBuffer sb2 = new StringBuffer();
                    for (int i = 0; i < intArr.length; i++) {
                        sb2.append(intArr[i]);
                        sb2.append(" ");
                    } // 숫자 정렬 결과 송신
                    protocol.setWord(sb2.toString());
                    os.write(protocol.getPacket());
                    break;
            }
            if (program_stop)
                break;

        }

        is.close();
        os.close();
        socket.close();
    }
}
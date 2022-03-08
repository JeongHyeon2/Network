//java server example
import java.io.*;
import java.net.*;
 
public class ChattingServer_Simple
{
    public static void main(String args[])
    {
        ServerSocket s = null;
        Socket conn = null;
        String message = null;
        InputStream is;
        BufferedReader reader, userInput;
        OutputStream os;
        BufferedWriter writer;
         
        try
        {
            //1. creating a server socket - 1st parameter is port number and 2nd is the backlog
            s = new ServerSocket(5000 , 10);
             
            //2. Wait for an incoming connection
            echo("Server socket created. Waiting for connection...");
            //get the connection socket
            conn = s.accept();
            //print the hostname and port number of the connection
            echo("Connection received from " + conn.getInetAddress().getHostName() + " : " + conn.getPort());
             
            //3. get Input and Output streams
            is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is));
            userInput = new BufferedReader(new InputStreamReader(System.in));
            os = conn.getOutputStream();
            writer = new BufferedWriter(new OutputStreamWriter(os));

            writer.write("Welcome to the Server"+'\r'+'\n'); 
            writer.flush(); // 클라이언트에 데이터 전송 
 
 
            //4. The two parts communicate via the input and output streams
            do
            {
                //read input from client
                message = (String)reader.readLine();
                echo("Client> " + message);
                 
                if(message != null)
                {
                   System.out.print("문장 입력: ");
		           message = userInput.readLine(); // 데이터 입력
                   if(message.equals("bye")) break; // 프로그램 종료
                   writer.write(message+'\r'+'\n'); 
                   writer.flush(); // 클라이언트에 데이터 전송 
                }
                else
                {
                    echo("Client has disconnected");
                    break;
                }
            }
            while(!message.equals("bye"));
        }
         
        catch(IOException e)
        {
            System.err.println("IOException");
        }
         
        //5. close the connections
        try
        {
            s.close();
        }
         
        catch(IOException ioException)
        {
            System.err.println("Unable to close. IOexception");
        }
    }
     
    public static void echo(String msg)
    {
        System.out.println(msg);
    }
}
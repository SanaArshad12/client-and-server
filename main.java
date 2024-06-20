import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class Server{

    public void startServer() throws IOException {
        // Start a server hosting on port 3333
        ServerSocket ss = new ServerSocket(3333);
        Socket s = ss.accept();
        // For sending data onto the socket connection stream
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        //For reading data from the socket connection stream
        DataInputStream dis = new DataInputStream(s.getInputStream());
        //for taking input from user
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // Remember, server always reads first
        String response = "";
        while(!response.equals("BYE")) {
            response = dis.readUTF();
            System.out.println("Client Says: " + response);
            System.out.println("send message as server:-");
            String sendmsg = br.readLine();
            dos.writeUTF(sendmsg);
        }}}
class Client{
    public void startClient() throws IOException {
        Socket s = new Socket("localhost", 3333);
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        DataInputStream dis = new DataInputStream(s.getInputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String response = "";
        //Client writes first
        while (!response.equals("BYE")){
            System.out.println("send message as Client:-");
            String sendmsg = br.readLine();
            dos.writeUTF(sendmsg);
            response = dis.readUTF();
            System.out.println("Server says: " + response);
        }
    }
}


public class main {

    public static void main(String [] args){

        Thread t1 = new Thread(()->{
            try {
                new Server().startServer();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t2 = new Thread(()->{
            try {
                new Client().startClient();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        t1.start();
        t2.start();
    }
}

package src.Multiplayer.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiplayerServer{
    public static void main(String[] args) throws IOException{
        int portNumber = 6969;
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            Socket clientSocket = serverSocket.accept();
            
            DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());

            while (true) {
                try{
                    String msg = dataInputStream.readUTF();
                    System.out.println(msg);
                    String msgToSend = "Hello Client";
                    dataOutputStream.writeUTF(msgToSend);
                }catch(Exception error){
                    System.err.println("An Error Occured: " + error);
                }
            }
        }
        
    }
}

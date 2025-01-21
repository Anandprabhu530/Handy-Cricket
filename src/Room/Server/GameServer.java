package src.Room.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(6969);
            Socket clientSocket = serverSocket.accept();

            DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());

            int n = dataInputStream.readInt();

            //33, 126
            StringBuilder roomcode = new StringBuilder();
            if (n == 0) {
                while (roomcode.length() != 6) {
                    roomcode.append(roomcode);
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    
    public static void randomString() {
        char temp = 122 + '0';
    }
}

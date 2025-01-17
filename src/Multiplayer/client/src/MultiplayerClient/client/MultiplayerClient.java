package src.MultiplayerClient.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class MultiplayerClient {
    public static void main(String[] args) {
        int portNumber = 6969;
        try {
            Socket socket = new Socket("localhost", portNumber);
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            while (true) {
                try {
                    String msg = "Hello Server!";
                    dataOutputStream.writeUTF(msg);
                    String data = dataInputStream.readUTF();
                    System.out.println(data);
                } catch (Exception e) {
                    System.err.println("Error: " + e);
                }
            }
        } catch (Exception e) {
            System.err.println("Connection Failed: " + e);
        }
    }
}

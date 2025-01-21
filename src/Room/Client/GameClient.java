package src.Room.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Game");
        Scanner sc = new Scanner(System.in);
        System.out.print("0: Create a Room\n1.Join a room\nChoose one: ");
        int n = sc.nextInt();

        try {
            Socket socket = new Socket("locahost", 6969);
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            byte[] arr = new byte[2];
            arr[0] = n == 0 ? (byte) 0 : 1;
            String temp = "radomcode";
            
        } catch (Exception e) {
            System.out.println("Unable to connect to server: " + e);
        }
    }
}

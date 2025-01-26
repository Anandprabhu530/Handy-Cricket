package src.Room.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class GameClient{
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Game");
        Scanner sc = new Scanner(System.in);
        System.out.print("0: Create a Room\n1.Join a room\nChoose one: ");
        int n = sc.nextInt();

        try {
            Socket socket = new Socket("localhost", 6969);
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            byte[] roomInput = new byte[2];

            roomInput[0] = n == 0 ? (byte) 0 : 1;
            if (n == 1) {
                System.out.print("\nEnter room code: ");
                int roomCode = sc.nextInt();
                roomInput[1] = (byte) roomCode;
            }
            dataOutputStream.write(roomInput);
            byte[] msg = new byte[2];

            dataInputStream.read(msg);
            System.out.println("msg[0]: " +msg[0] + "     msg[1]: " +msg[1]);
        } catch (Exception e) {
            System.out.println("Unable to connect to server: " + e);
        }
    }
}



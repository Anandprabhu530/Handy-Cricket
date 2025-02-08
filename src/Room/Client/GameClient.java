package src.Room.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Scanner;

public class GameClient {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Game");
        Scanner sc = new Scanner(System.in);
        System.out.print("0: Create a Room\n1.Join a room\nChoose one: ");
        int n = sc.nextInt();

        try {
            Socket socket = new Socket("localhost", 6969);
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            byte[] roomInput = new byte[5];

            roomInput[0] = n == 0 ? (byte) 0 : 1;
            if (n == 1) {
                System.out.print("\nEnter room code: ");
                int roomCode = sc.nextInt();
                // Add roomCode to the byte array
                ByteBuffer.wrap(roomInput, 1, 4).putInt(roomCode);
            }
            // Write to the server
            dataOutputStream.write(roomInput);

            // Byte Array to read response from server
            byte[] msg = new byte[5];

            // Read response from Server
            dataInputStream.read(msg);
            int status = msg[0];
            if (status == 1) {
                int roomCode = ByteBuffer.wrap(msg, 1, 4).getInt();
                System.out.println("RoomCode: " + roomCode);
                System.out.println("Joined room successfully");
            } else if (status == 2) {
                System.out.println("Room Already Full");
            } else {
                System.out.println("Room Not Found");
            }

            int temp = dataInputStream.read();
            System.out.println(temp);
            int njd = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Unable to connect to server: " + e);
        }
    }
}

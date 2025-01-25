package src.Room.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class GameServer {
    private static HashSet<Integer> set;
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(6969);
            Socket clientSocket = serverSocket.accept();
            set = new HashSet<>();
            System.out.println("Started");
            DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());

            

            byte[] response = new byte[2];
            byte[] userChoice = new byte[2];
            //33, 126
            while (true) {
                System.out.println("Scanning");
                dataInputStream.read(userChoice);
                switch ((int) userChoice[0]) {
                    case 0:
                        int roomCode = roomCodeGenerator();
                        while (set.contains(roomCode))
                            roomCode = roomCodeGenerator();
                        set.add(roomCode);
                        response[0] = (byte) 0;
                        response[1] = (byte) roomCode;
                        System.out.println("Room code: " + roomCode);
                        break;
                    case 1:
                        System.out.println(set);
                        if (set.contains((int) userChoice[1])) {
                            response[0] = (byte) 1;
                            System.out.println("Found");
                        } else {
                            response[0] = (byte) -1;
                            System.out.println("Not Found");
                        }
                        break;
                }
                System.out.println("User reached here");
                dataOutputStream.write(response);
            }
            

            
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println("An error occured: " + e);
        }
    }
    
    public static int roomCodeGenerator() {
        return (int) Math.floor(Math.random() * ((124-0) + 1));
    }
}

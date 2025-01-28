package src.Room.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;


class SharedHashSet {
    private static HashSet<Integer> set = new HashSet<>();
    private SharedHashSet() {};
    public static HashSet<Integer> getHashSet() {
        return set;
    }
}

public class GameServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(6969);

            System.out.println("Started");

            byte[] response = new byte[2];
            byte[] userChoice = new byte[2];
            //33, 126
            while (true) {
                System.out.println("Scanning");
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, response, userChoice);
                new Thread(clientHandler).start();
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println("An error occured: " + e);
        }
    }

}

class Player {
    private String name;
    private boolean isBatting;
    private int score;
    private int noOfBalls;
    
    Player(String name) {
        this.name = name;
    }
}

class GameRoom {
    private Player player01;
    private Player player02;
    private int roomCode;
    private boolean isGameStarted;  
}

class ClientHandler implements Runnable{
    private Socket clientSocket;
    private byte[] response;
    private byte[] userChoice;

    ClientHandler(Socket socket, byte[] response, byte[] userChoice) {
        this.clientSocket = socket;
        this.response = response;
        this.userChoice = userChoice;
    }

    public static int roomCodeGenerator() {
        return (int) Math.floor(Math.random() * ((9999 - 1000) + 1));
    }

    HashSet<Integer> set = SharedHashSet.getHashSet();
    
    public void run() {
        try (DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream())) {
            DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
            dataInputStream.read(userChoice);

                    
            switch ((int) userChoice[0]) {
                case 0:
                    int roomCode = roomCodeGenerator();
                    while (!set.isEmpty() && set.contains(roomCode)) roomCode = roomCodeGenerator();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
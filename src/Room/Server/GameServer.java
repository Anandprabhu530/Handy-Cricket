package src.Room.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

class sharedHashMap {
    private static HashMap<Integer, GameRoom> recordBook = new HashMap<>();

    private sharedHashMap() {
    };

    public static HashMap<Integer, GameRoom> getRecordBook() {
        return recordBook;
    }
}

public class GameServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(6969);

            System.out.println("Started");

            byte[] response = new byte[5];
            byte[] userChoice = new byte[2];
            // 33, 126
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
    private ArrayList<Player> players;
    private int roomCode;
    private boolean isGameStarted;

    GameRoom(int roomCode) {
        this.players = new ArrayList<>();
        this.roomCode = roomCode;
    }

    ArrayList<Player> getAllPlayer() {
        return players;
    }

    int addPlayer(Player player) {
        if (players.size() == 2) {
            return -1;
        }
        players.add(player);
        return 1;
    }
}

// store the Player Id - int is better
// and store it with socket in a hashmap.
// have a method to find current player turn and
// change according to it.
class ClientHandler implements Runnable {
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

    HashMap<Integer, GameRoom> recordBook = sharedHashMap.getRecordBook();

    public void run() {
        try (DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream())) {
            DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
            dataInputStream.read(userChoice);
            switch ((int) userChoice[0]) {
                case 0:
                    int roomCode = roomCodeGenerator();
                    while (!recordBook.isEmpty() && recordBook.containsKey(roomCode))
                        roomCode = roomCodeGenerator();
                    Player player01 = new Player("Test Player11");
                    GameRoom room = new GameRoom(roomCode);
                    room.addPlayer(player01);
                    recordBook.put(roomCode, room);

                    // Total response array size id 5
                    // response[0] -> represents room created successfully
                    // Next 4 byte will for the int roomcode
                    // 1 - room created successfully
                    response[0] = 1;
                    ByteBuffer.wrap(response, 1, 4).putInt(roomCode);
                    System.out.println("Room Created Successfully \nRoom code: " + roomCode);
                    break;
                case 1:
                    System.out.println(recordBook);
                    int userRoomCode = ByteBuffer.wrap(response, 1, 4).getInt();
                    System.out.println(userRoomCode);
                    if (recordBook.containsKey(userRoomCode)) {
                        GameRoom obj = recordBook.get(userRoomCode);
                        ArrayList<Player> temp = obj.getAllPlayer();
                        if (temp.size() == 2) {
                            // 2 for room full
                            response[0] = (byte) 2;
                            System.out.println("Room full");
                            break;
                        } else {
                            Player player02 = new Player("Test Player22");
                            obj.addPlayer(player02);
                            System.out.println("Joined Room");
                            response[0] = (byte) 1;
                        }
                    } else {
                        // -1 for room not found
                        response[0] = (byte) -1;
                        System.out.println("Room Not Found");
                    }
                    break;
            }
            System.out.println("User reached here");
            dataOutputStream.write(response);
            dataOutputStream.write(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
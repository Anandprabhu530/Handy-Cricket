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
            byte[] userChoice = new byte[4];
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
    private DataOutputStream dataOutputStream;

    Player(String name, DataOutputStream dataOutputStream) {
        this.name = name;
        this.dataOutputStream = dataOutputStream;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
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

class ClientHandler implements Runnable {
    // Status Codes
    private static final byte SUCCESS_SERVER_RESPONSE = 1;
    private static final byte SERVER_FULL = 2;
    private static final byte ROOM_NOT_FOUND = -1;

    private Socket clientSocket;
    private byte[] response;
    private byte[] userChoice;
    private byte zeroOrOne;

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
            zeroOrOne = dataInputStream.readByte();
            switch ((int) zeroOrOne) {
                case 0:
                    int roomCode = roomCodeGenerator();
                    while (!recordBook.isEmpty() && recordBook.containsKey(roomCode))
                        roomCode = roomCodeGenerator();
                    Player player01 = new Player("Test Player11", dataOutputStream);
                    GameRoom room = new GameRoom(roomCode);
                    DataOutputStream player01DataOutputStream = player01.getDataOutputStream();
                    room.addPlayer(player01);
                    recordBook.put(roomCode, room);
                    response[0] = SUCCESS_SERVER_RESPONSE;
                    ByteBuffer.wrap(response, 1, 4).putInt(roomCode);
                    System.out.println("Room Created Successfully \nRoom code: " + roomCode);
                    player01DataOutputStream.write(response);

                    break;
                case 1:
                    System.out.println(recordBook);

                    dataInputStream.read(userChoice);
                    Player player02 = new Player("Test Player22", dataOutputStream);
                    DataOutputStream player02DataOutputStream = player02.getDataOutputStream();

                    int userRoomCode = ByteBuffer.wrap(userChoice, 0, 4).getInt();
                    System.out.println("User room Code: " + userRoomCode);

                    if (recordBook.containsKey(userRoomCode)) {
                        GameRoom obj = recordBook.get(userRoomCode);
                        obj.addPlayer(player02);
                        ArrayList<Player> totalPlayers = obj.getAllPlayer();
                        System.out.println("Array List:" + totalPlayers);
                        if (totalPlayers.size() > 2) {
                            response[0] = SERVER_FULL;
                            System.out.println("Room Full");
                            break;
                        } else {
                            System.out.println("Joined Room");
                            response[0] = SUCCESS_SERVER_RESPONSE;
                        }
                    } else {
                        response[0] = ROOM_NOT_FOUND;
                        System.out.println("Room Not Found");
                    }
                    player02DataOutputStream.write(response);
                    break;
            }
            System.out.println("----End----");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
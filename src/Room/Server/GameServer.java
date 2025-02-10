package src.Room.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

class Player {
    private String name;
    public boolean isBatting;
    public int score;
    public int noOfBalls;
    private DataOutputStream outputStream;

    Player(String name, DataOutputStream outputStream) {
        this.name = name;
        this.outputStream = outputStream;
        this.score = 0;
        this.noOfBalls = 0;
    }

    public DataOutputStream getOutputStream() {
        return outputStream;
    }

    public void setIsBatting(boolean isBatting) {
        this.isBatting = isBatting;
    }

    public void updateScore(int runs) {
        this.score += runs;
    }
}

class GameRoom {
    private ArrayList<Player> players;
    private int roomCode;
    private boolean isGameStarted;

    GameRoom(int roomCode) {
        this.players = new ArrayList<>();
        this.roomCode = roomCode;
        this.isGameStarted = false;
    }

    synchronized int addPlayer(Player player) {
        if (players.size() >= 2) {
            return -1;
        }
        players.add(player);
        return players.size();
    }

    Player getPlayer(int index) {
        return players.get(index);
    }

    boolean isFull() {
        return players.size() == 2;
    }

    void startGame() {
        isGameStarted = true;
        // Randomly decide who bats first
        players.get(0).setIsBatting(Math.random() < 0.5);
        players.get(1).setIsBatting(!players.get(0).isBatting);
    }
}

class GameServer {
    private static final int PORT = 6969;
    private static HashMap<Integer, GameRoom> rooms = new HashMap<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    private static class ClientHandler implements Runnable {
        private static final byte ROOM_CREATED = 1;
        private static final byte GAME_READY = 2;
        private static final byte ROOM_FULL = 3;
        private static final byte ROOM_NOT_FOUND = 4;

        private Socket clientSocket;
        private DataInputStream in;
        private DataOutputStream out;

        ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        private int generateRoomCode() {
            int code;
            do {
                code = 1000 + (int) (Math.random() * 9000);
            } while (rooms.containsKey(code));
            return code;
        }

        @Override
        public void run() {
            try {
                in = new DataInputStream(clientSocket.getInputStream());
                out = new DataOutputStream(clientSocket.getOutputStream());

                byte choice = in.readByte();

                if (choice == 0) { // Create new room
                    handleRoomCreation();
                } else { // Join existing room
                    handleRoomJoin();
                }
            } catch (IOException e) {
                System.err.println("Error handling client: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.err.println("Error closing socket: " + e.getMessage());
                }
            }
        }

        private void handleRoomCreation() throws IOException {
            int roomCode = generateRoomCode();
            GameRoom room = new GameRoom(roomCode);

            Player player = new Player("Player 1", out);
            room.addPlayer(player);
            rooms.put(roomCode, room);

            // Send room code to client
            out.writeByte(ROOM_CREATED);
            out.writeInt(roomCode);
            out.flush();

            System.out.println("Room created: " + roomCode);

            // Wait for game updates
            waitForGameUpdates(room, player);
        }

        private void handleRoomJoin() throws IOException {
            int roomCode = in.readInt();
            GameRoom room = rooms.get(roomCode);

            if (room == null) {
                out.writeByte(ROOM_NOT_FOUND);
                out.flush();
                return;
            }

            if (room.isFull()) {
                out.writeByte(ROOM_FULL);
                out.flush();
                return;
            }

            Player player = new Player("Player 2", out);
            room.addPlayer(player);

            // Notify both players that game can start
            out.writeByte(GAME_READY);
            room.getPlayer(0).getOutputStream().writeByte(GAME_READY);
            out.flush();
            room.getPlayer(0).getOutputStream().flush();

            System.out.println("Player joined room: " + roomCode);
            room.startGame();

            // Wait for game updates
            waitForGameUpdates(room, player);
        }

        private void waitForGameUpdates(GameRoom room, Player currentPlayer) throws IOException {
            while (true) {
                int move = in.readInt();
                // Send the move to the other player
                Player otherPlayer = room.getPlayer(currentPlayer == room.getPlayer(0) ? 1 : 0);
                otherPlayer.getOutputStream().writeInt(move);
                otherPlayer.getOutputStream().flush();
            }
        }
    }
}

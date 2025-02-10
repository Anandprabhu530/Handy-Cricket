package src.Room.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 6969;

    private static final byte ROOM_CREATED = 1;
    private static final byte GAME_READY = 2;
    private static final byte ROOM_FULL = 3;
    private static final byte ROOM_NOT_FOUND = 4;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Hand Cricket Game!");
        System.out.println("0: Create a Room");
        System.out.println("1: Join a Room");
        System.out.print("Choose one: ");

        int choice = scanner.nextInt();

        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

            // Send initial choice to server
            out.writeByte(choice);

            if (choice == 1) {
                System.out.print("Enter room code: ");
                int roomCode = scanner.nextInt();
                out.writeInt(roomCode);
            }

            // Get initial response from server
            byte status = in.readByte();
            handleInitialResponse(status, in);

            // Start game loop
            gameLoop(in, out, scanner);

        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }

    private static void handleInitialResponse(byte status, DataInputStream in) throws IOException {
        switch (status) {
            case ROOM_CREATED:
                int roomCode = in.readInt();
                System.out.println("Room created successfully!");
                System.out.println("Your room code is: " + roomCode);
                System.out.println("Waiting for another player to join...");
                break;

            case GAME_READY:
                System.out.println("Game is ready to start!");
                System.out.println("You've joined the room successfully.");
                break;

            case ROOM_FULL:
                System.out.println("Error: Room is already full!");
                System.exit(1);
                break;

            case ROOM_NOT_FOUND:
                System.out.println("Error: Room not found!");
                System.exit(1);
                break;

            default:
                System.out.println("Unknown response from server: " + status);
                System.exit(1);
        }
    }

    private static void gameLoop(DataInputStream in, DataOutputStream out, Scanner scanner) throws IOException {
        while (true) {
            // Wait for game ready signal
            byte gameStatus = in.readByte();

            if (gameStatus == GAME_READY) {
                System.out.println("\nGame is starting!");

                // int isBatting = in.readInt();
                // System.out.println(isBatting);
                // if (isBatting == 200) {
                // System.out.println("You are batting");
                // } else {
                // System.out.println("You are bowling");
                // }
                System.out.println("Enter a number between 1 and 6 for each ball.");

                while (true) {
                    System.out.print("\nYour move (1-6): ");
                    int move = scanner.nextInt();

                    if (move < 1 || move > 6) {
                        System.out.println("Invalid move! Please enter a number between 1 and 6.");
                        continue;
                    }

                    // Send move to server
                    out.writeInt(move);

                    // Wait for opponent's move
                    int opponentMove = in.readInt();
                    System.out.println("Opponent played: " + opponentMove);

                    // Check for out
                    if (move == opponentMove) {
                        System.out.println("OUT!");
                        // Handle innings change or game end here
                    }
                }
            }
        }
    }
}
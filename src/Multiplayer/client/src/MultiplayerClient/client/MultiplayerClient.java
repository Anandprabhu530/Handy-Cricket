package src.MultiplayerClient.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class MultiplayerClient {
    public static void main(String[] args) {
        int portNumber = 6969;
        Scanner sc = new Scanner(System.in);
        try {
            Socket socket = new Socket("localhost", portNumber);
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            while (true) {
                try {
                    // Send 1 to check server status
                    int firstMsg = 1;
                    dataOutputStream.writeInt(firstMsg);
                    int serverCheck = dataInputStream.readInt();

                    // if Server does not respond with 1, Then server is not ready
                    if (serverCheck != 1) {
                        throw new Error("Server Not ready!");
                    }
                    System.out.println("---------Game Starts---------\n\n");

                    // get toss status
                    // getToss[0] --> Odd or Even
                    // getToss[1] --> Number provided for Toss Choice
                    int[] getToss = new int[2];
                    getToss = throwToss(sc);
                    
                    // Send OddorEven and Number choosen for toss to server
                    byte[] toSend = { (byte) getToss[0], (byte) getToss[1] };
                    dataOutputStream.write(toSend);
                    
                    // Get whether you won the status or not
                    byte[] readResponse = new byte[2];
                    dataInputStream.read(readResponse);
                    int inningsChoosen;

                    if (readResponse[0] == 0) {
                        System.out.println("\nYou won the toss");
                        
                        // Choose Bat or Bowl
                        inningsChoosen = twoChoice(sc, 1);
                        while (inningsChoosen < 0 || inningsChoosen > 1) inningsChoosen = twoChoice(sc, 1);

                    } else {
                        System.out.println("\nYou loose the toss");
                        inningsChoosen = readResponse[1];
                        System.out.println("Opponent choose to " + (inningsChoosen == 0 ? "Bowl" : "Bat"));
                    }

                    System.out.println("Now you are " + (inningsChoosen == 0 ? "Batting" : "Bowling"));

                    int firstInningsScore = 0, firstInningsBalls = 0;
                    byte[] innings = new byte[2];

                    while (true) {
                        int score = getScore(sc);

                        // Do not allow score less than 1 or greater than 6
                        while (score <= 0 || score > 6) score = getScore(sc);
                        System.out.println();

                        // Store the score and state(Batsman or Bowler) in innings
                        innings[0] = (byte) score;
                        innings[1] = (byte) inningsChoosen;
                        dataOutputStream.write(innings);

                        dataInputStream.read(innings);
                        if (innings[0] == -1) {
                            System.out.println("Game Over!");
                            if (inningsChoosen == 0) {
                                System.out.println("You scored" + innings[1]);
                            } else {
                                System.out.println("Opponent scored " + innings[1]);
                            }
                            break;
                        }
                        if (inningsChoosen == 0) {
                            firstInningsScore += score;
                        } else {
                            firstInningsScore += innings[0];
                        }
                        firstInningsBalls++;
                        System.out.println("Current Score:" + firstInningsScore + "\t\t\tNumber of Balls:" + firstInningsBalls);
                    }
                    System.exit(1);
                    socket.close();
                } catch (Exception e) {
                    System.err.println("Error: " + e);
                }
            }
        } catch (Exception e) {
            System.err.println("Connection Failed: " + e);
        }
    }
    
    public static int[] throwToss(Scanner sc) {
        System.out.println("---------Throw Toss---------");

        // Use case 0 --> Odd or Even
        // Use case 1 --> Bat or Bowl
        int oddOrEven = twoChoice(sc, 0);
        while (oddOrEven < 0 || oddOrEven > 1) oddOrEven = twoChoice(sc, 0);

        System.out.print("Enter a number between 1 - 6: ");
        int tossChoice = getScore(sc);

        // Check for score between 1-6 or else retry until you get one
        while (tossChoice <= 0 || tossChoice > 6) getScore(sc);
        return new int[] { oddOrEven, tossChoice };
    }
    
    public static int getScore(Scanner sc) {
        System.out.print("Enter your score (1-6): ");
        return sc.nextInt();
    }

    public static int twoChoice(Scanner sc,int useCase) {
        if (useCase == 0) {
            System.out.println("Choose Odd or Even");
            System.out.println("0.Odd\n1.Even");
        } else {
            System.out.println("Bat or Bowl");
            System.out.println("0.Bat\n1.Bowl");
        }
        return sc.nextInt();
    }
}

package src.Multiplayer.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class MultiplayerServer {
    private static int inningsScore = 0;

    private final static int portNumber = 6969;
        public static void main(String[] args) throws IOException {
            try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
                Socket clientSocket = serverSocket.accept();

            DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());

            while (true) {
                try {
                    int InitialDatafrom = dataInputStream.readInt();
                    if (InitialDatafrom == 1) {
                        dataOutputStream.writeInt(1);
                    }
                    int randomNumber = randomNumberGenerator(6);
                    byte[] userToss = new byte[2];

                    // read the incoming byte array
                    dataInputStream.read(userToss);

                    int tempScore = randomNumber + userToss[1];

                    // create a byte array to respond
                    byte[] response = new byte[2];
                    // Response - 0 -> User won the toss
                    // Response - 1 -> System won the toss
                    
                    // User Choose Even or Odd
                    // User won the toss
                    if ((tempScore % 2 == 0 && userToss[0] == 1) || (tempScore % 2 != 0 && userToss[0] == 0)) {
                        // User can choose to Bat or Bowl
                        response[0] = 0;
                    } else {
                        randomNumber = randomNumberGenerator(2);
                        // System won the toss
                        response[0] = 1;
                        if (randomNumber == 2) {
                            // System Choose to Bat
                            response[1] = 0;
                        } else {
                            // System Choose to Bowl
                            response[1] = 1;
                        }
                    }

                    // Reply with toss.. Incase system win add system choose to bowl or Bat
                    dataOutputStream.write(response);

                    // keep tack of innings Score
                    while (true) {
                        dataInputStream.read(response);
                        int randomScore = randomNumberGenerator(6);
                        // Check for the input score
                        // response[0] --> userScore
                        // response[1] --> Batsman (0) or Bowler(1)

                        // If userscore and randomScore matches. Then Player is out
                        // Send back the response[0] --> -1 to identify out
                        // Add total score response[1] --> inningsScore
                        if (response[0] == randomScore) {
                            response[0] = -1;
                            response[1] = (byte) inningsScore;
                            dataOutputStream.write(response);
                            break;
                        }

                        // If the user is bowler then add the randomNumber to score and send it to the user
                        // response[0] --> send the score taken by system
                        // response[1] --> send the totalInnings Score
                        if (response[1] == 1) {
                            response[0] = (byte) randomScore;
                            response[1] = (byte) inningsScore;
                            inningsScore += randomScore;
                        } else {

                            // If the user is Batsman, add userscore and send it to User
                            // response[0] --> send the score taken by User
                            // response[1] --> send the totalInnings Score
                            inningsScore += response[0];
                        }
                        dataOutputStream.write(response);
                    }
                } catch (SocketException error) {
                    System.err.println("Connection Closed by Client");
                    System.exit(1);
                }
            }
        }
    }
    
    public static int randomNumberGenerator(int range) {
        return (int) Math.floor((Math.random() * range) + 1);
    }

}

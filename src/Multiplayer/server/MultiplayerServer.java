package src.Multiplayer.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiplayerServer {
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
                    System.exit(1);
                } catch (Exception error) {
                    System.err.println("An Error Occured: " + error);
                }
            }
        }

    }
    
    public static int randomNumberGenerator(int range) {
        return (int) Math.floor((Math.random() * range) + 1);
    }

}

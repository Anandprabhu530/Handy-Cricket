package src.Multiplayer.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiplayerServer{
    public static void main(String[] args) throws IOException{
        int portNumber = 6969;
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            Socket clientSocket = serverSocket.accept();
            
            DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());

            while (true) {
                try{
                    int InitialDatafrom = dataInputStream.readInt();
                    if (InitialDatafrom == 1) {
                        dataOutputStream.writeInt(1);
                    }
                    int randomNumber = (int) Math.floor((Math.random() * 6) + 1);
                    byte[] userToss = new byte[2];
                    dataInputStream.read(userToss);
                    int tempScore = randomNumber + userToss[1];
                    if ((tempScore % 2 == 0 && userToss[0]==1) || (tempScore % 2 != 0 && userToss[0] == 0)) {
                        dataOutputStream.writeInt(0);
                    } else{
                        dataOutputStream.writeInt(1);
                    }
                    System.exit(1);                    
                }catch(Exception error){
                    System.err.println("An Error Occured: " + error);
                }
            }
        }
        
    }
}

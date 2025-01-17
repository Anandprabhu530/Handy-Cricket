package src.Multiplayer.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiplayerServer {
    public static void main(String[] args) {
        int portNumber = 8080;
        try (ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clienSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clienSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clienSocket.getInputStream()));
        ) {
            String inputString;
            while ((inputString=in.readLine())!=null) {
                System.out.println("Input:" + inputString);
            }
            
        } catch (IOException e) {
            System.err.println("IO Exception: " + e);
        }
    }
}

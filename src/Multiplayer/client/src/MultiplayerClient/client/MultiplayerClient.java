package src.MultiplayerClient.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MultiplayerClient {
    public static void main(String[] args) {
        int portNumber = 8080;
        String hostName = "127.0.0.1";

        try(Socket clienSocket = new Socket(hostName,portNumber);
            PrintWriter out = new PrintWriter(clienSocket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(clienSocket.getInputStream()));
        ) {
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            
            String fromServer, fromUser;

            while ((fromServer = in.readLine()) != null) {
                System.out.println("Server:" + fromServer);
                fromUser = stdin.readLine();
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }
            }
        }catch (UnknownHostException e) {
            System.err.println("hostName not found");
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Unkown error:" + e);
        }
    }
}

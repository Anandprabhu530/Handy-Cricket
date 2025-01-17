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
                    int firstMsg = 1;
                    dataOutputStream.writeInt(firstMsg);
                    int serverCheck = dataInputStream.readInt();
                    if (serverCheck != 1) {
                        throw new Error("Server Not ready!");
                    }
                    System.out.println("---------Game Starts---------");
                    System.out.println("---------Throw Toss---------");
                    System.out.println("Choose Odd or Even");
                    System.out.println("0.Odd\n1.Even");
                    int oddOrEven = sc.nextInt();
                    System.out.print("Enter a number between 1 - 6: ");
                    int tossChoice = sc.nextInt();
                    System.out.println();
                    byte[] toSend = { (byte) oddOrEven, (byte)tossChoice };
                    if (oddOrEven % 2 == 0) {
                        dataOutputStream.write(toSend);
                    } else {
                        dataOutputStream.write(toSend);
                    }
                    int toss = dataInputStream.readInt();
                    if (toss == 0) {
                        System.out.println("You won the toss");
                    } else {
                        System.out.println("You losse the toss");
                    }
                    System.exit(1);
                } catch (Exception e) {
                    System.err.println("Error: " + e);
                }
            }
        } catch (Exception e) {
            System.err.println("Connection Failed: " + e);
        }
    }
}

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {   
            Socket socketConnection = new Socket("127.0.0.1",3000);
            BufferedReader in = new BufferedReader(new InputStreamReader(socketConnection.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(socketConnection.getOutputStream(),true);
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("echo: " + in.readLine());
            }

        } catch (Exception e) {
            System.err.println(e);
        }
        // int score = 0;
        // System.out.println("Welcome to Hand Cricket");
        // System.out.println("You will be the batsman");
        // while (true) {
        //     System.out.println("Current score is " + score);
            
        //     System.out.print("Enter your Choice: ");
        //     int userChoice = sc.nextInt();
        //     if (userChoice > 6 || userChoice < 0) {
        //         while (userChoice > 6 || userChoice < 0) {
        //             System.out.println("You can only score between 1 - 6");
        //             userChoice = sc.nextInt();
        //         }
        //     }
        //     int randomNumber = (int) (Math.floor(Math.random() * 6));
        //     if(randomNumber==0){
        //         while (randomNumber==0) {
        //             randomNumber = (int) (Math.floor(Math.random() * 6));
        //         }
        //     }
        //     if (userChoice == randomNumber) {
        //         System.out.println("\n Out!");
        //         break;
        //     } else {
        //         score += userChoice;
        //     }

        // }
        // sc.close();
        // System.out.println("Your total score is " + score);
    }
}
import java.util.Scanner;

public class SinglePlayer extends Player {
    SinglePlayer(String name, boolean isBatter) {
        super(name, isBatter);   
    }
    public void startGame(Scanner sc, boolean isBatter) {
        System.out.println("\n Game Started");
        System.out.println("You are the " + (isBatter ? "Batsman" : "Bowler"));
        int temp = 0;
        while (true) {
            System.out.println("Enter number between 1 - 6");
            int userChoice = sc.nextInt();
            int ComputerGuess = (int) Math.floor(Math.random() * 6)+1;
            if (userChoice == ComputerGuess) {
                System.out.println((isBatter ? "Your" : "Opponent") + " innings over \n");
                break;
            }
            temp += userChoice;
            System.out.println((isBatter ? "Your" : "Opponent") + " current score is: " + temp);
            setRuns(userChoice);
        }

        int[] ans = new int[2];
        ans = getRunsAndBallsFaced();
        System.out.println((isBatter ? "You" : "Opponent") + " scored " + ans[0] + " runs in " + ans[1] + " balls");
        int ballsFaced = 0;
        temp = 0;
        while (true) {
            System.out.println(ballsFaced);
            System.out.println("Enter number between 1 - 6");
            int userChoice = sc.nextInt();
            int ComputerGuess = (int) Math.floor(Math.random() * 6)+1;
            temp += userChoice;
            ballsFaced++;

            // If the balls faced Exceeds your or opponents score
            if (ballsFaced > ans[1]) {
                System.out.println("You " + (isBatter ? "won" : "loose"));
                break;
            }

            // If both the batsman and bolwer use the same score
            if (ComputerGuess == userChoice) {
                System.out.println("You " + (isBatter ? "won" : "loose"));
                break;
            }
            if (temp > ans[0] && ballsFaced <= ans[1]) {
                System.out.println("You " + (isBatter ? "loose" : "won"));
                break;
            }
            System.out.println((isBatter ? "Oppenent" : "Your") + " current score is: " + temp);
        }
    }
}

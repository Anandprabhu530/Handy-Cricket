import java.util.Scanner;

public class SinglePlayer extends Player {
    SinglePlayer(String name, boolean isBatter) {
        super(name, isBatter);   
    }
    public void startGame(Scanner sc, boolean isBatter) {
        System.out.println("\n Game Started");
        System.out.println("You are the " + (isBatter ? "Batsman" : "Bowler"));
        // int temp = 0;
        // while (true) {
        //     System.out.print("\033[H\033[2J");  
        //     System.out.flush();
        //     System.out.println("Enter number between 1 - 6");
        //     int userChoice = sc.nextInt();
        //     int ComputerGuess = (int) Math.floor(Math.random() * 6)+1;
        //     if (userChoice == ComputerGuess) {
        //         System.out.println((isBatter ? "Your" : "Opponent") + " innings over \n");
        //         break;
        //     }
        //     temp += userChoice;
        //     System.out.println((isBatter ? "Your" : "Opponent") + " current score is: " + temp);
        //     setRuns(userChoice);
        // }

        int[] firstInningsScore = new int[2];
        // firstInningsScore = getRunsAndBallsFaced();
        firstInningsScore[0] = 15;
        firstInningsScore[1] = 4;
        System.out.println((isBatter ? "You" : "Opponent") + " scored " + firstInningsScore[0] + " runs in " + firstInningsScore[1] + " balls");
        int ballsFaced = 0;
        int secondInningsScore = 0;
        
        //temp
        int[] scoreet = { 4, 6, 3, 3, 1 };
        // 6 4 4 1 2
        int y = 0;
        while (true) {
            System.out.println(ballsFaced);
            System.out.println("Enter number between 1 - 6");
            int userChoice = sc.nextInt();
            // int ComputerGuess = (int) Math.floor(Math.random() * 6)+1;
            
            //temp 
            int ComputerGuess = scoreet[y++];

            secondInningsScore += userChoice;
            ballsFaced++;

            // If the balls faced Exceeds your or opponents score
            // if (ballsFaced > firstInningsScore[1]) {
            //     System.out.println("You " + (isBatter ? "won" : "loose"));
            //     break;
            // }

            // If both the batsman and bolwer use the same score
            // check for tie
            if (ComputerGuess == userChoice && ballsFaced < firstInningsScore[1]) {
                System.out.println("ComputerGuess == userChoice && ballsFaced < firstInningsScore[1]");
                System.out.println(ComputerGuess +"==" + userChoice + "&&" + ballsFaced +"<" + firstInningsScore[1]);
                System.out.println("You " + (isBatter ? "won" : "loose"));
                break;
            } else if (ComputerGuess == userChoice && ballsFaced == firstInningsScore[1]) {
                System.out.println(ComputerGuess == userChoice && ballsFaced == firstInningsScore[1]);
                System.out.println(ComputerGuess + "==" + userChoice + "&&" + ballsFaced + "==" + firstInningsScore[1]);
                System.out.println("It's a tie");
                break;
            }

            if (secondInningsScore > firstInningsScore[0] && ballsFaced < firstInningsScore[1]) {
                System.out.println("secondInningsScore > firstInningsScore[0] && ballsFaced <= firstInningsScore[1]");
                System.out.println(secondInningsScore + ">" +  firstInningsScore[0] + "&&" +  ballsFaced  + "<=" + firstInningsScore[1]);
                System.out.println("You " + (isBatter ? "won" : "loose"));
                break;
            }
            System.out.println((isBatter ? "Oppenent" : "Your") + " current score is: " + secondInningsScore + "   current ball:"+ballsFaced );
        }
    }
}

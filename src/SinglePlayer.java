import java.util.Scanner;

public class SinglePlayer extends Player {
    SinglePlayer(String name, boolean isBatter) {
        super(name, isBatter);   
    }

    public void startGame(Scanner sc, boolean isBatter) {
        System.out.println("\n Game Started");
        System.out.println("You are the " + (isBatter ? "Batsman" : "Bowler"));
        int firstInningTotalScore = 0, firstInningTotalBalls = 0;

        while (true) {
            System.out.print("Enter a number between 1-6:");
            int currentScore = sc.nextInt();
            System.out.println();
            int opponentScore = randomScoreGenerator();
            if (currentScore == opponentScore) {
                firstInningTotalBalls++;
                break;
            }
            firstInningTotalScore += (isBatter ? currentScore : opponentScore);
            firstInningTotalBalls++;
            System.out.println(firstInningTotalScore + " runs in " + firstInningTotalBalls + " balls.");
        }
        
        System.out.println((isBatter ? "You" : "Opponent") + " scored " + firstInningTotalScore + " runs in "
                + firstInningTotalBalls + " balls.\n");
        
        System.out.println("Now you are the " + (isBatter ? "Bowler" : "Batter"));
        int secondInningsScore = 0, secondInningsBalls = 0;

        while (true) {
            System.out.print("Enter a number between 1-6:");
            int currentScore = sc.nextInt();
            System.out.println();
            int opponentScore = randomScoreGenerator();

            if (currentScore == opponentScore) {
                if(secondInningsScore!=firstInningTotalScore){
                    System.out.println((isBatter) ? "You win" : "Opponent wins");
                    break;
                } else {
                    System.out.print("It's a tie");
                    break;
                }
            }
            secondInningsScore += (isBatter?opponentScore:currentScore);
            secondInningsBalls++;
            if (secondInningsBalls > firstInningTotalBalls) {
                System.out.println((isBatter) ? "You win" : "Opponent wins");
                break;
            }
            if (secondInningsScore > firstInningTotalScore && secondInningsBalls <= firstInningTotalBalls) {
                System.out.println((isBatter) ? "Opponent wins" : "You win");
                break;
            }
            System.out.println(secondInningsScore + " runs in " + secondInningsBalls + " balls.");
        }
    }
    
    public static int randomScoreGenerator() {
        return (int) Math.floor((Math.random() * 6) + 1);
    }
}

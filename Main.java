import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("-------------Welcome to Hand Cricket------------- \n\n");
        System.out.println("Choose anyone option below");
        System.out.println("0: Single Player \n 1: Multi-Player \n 2: Instructions \n");
        System.out.print("Enter your choice: ");
        int n = sc.nextInt();
        System.out.println();
        System.out.println("----------------Toss----------------");
        System.out.println("Odd or Even");
        System.out.println("0: Odd \n 1: Even");
        int tossChoosen = sc.nextInt();
        boolean isEven = tossChoosen == 0 ? false : true;
        System.out.print("Enter number between 1 - 6: ");
        int choice = sc.nextInt();
        System.out.println();
        switch (n) {
            case 0:
                // Need to implement with a inbuilt function;
                int tossResult = throwToss(isEven, choice, n);
                if (tossResult == 1) {
                    System.out.print("You won the toss");
                    SinglePlayer player = new SinglePlayer("User01", true);
                    player.startGame(sc, true);
                } else {
                    System.out.print("You loose the toss");
                    SinglePlayer player = new SinglePlayer("User01", false);
                    player.startGame(sc, false);
                }
                break;
            case 1:
                // against two players in different terminals
                break;
            case 2:
                // show instructions
                break;
            default:
                System.out.println("Enter a valid choice");
                break;
        }
    }
    
    public static int throwToss(boolean isEven, int score, int choice) {
        if (choice == 0) {
            int computerNumber = (int) Math.floor(Math.random() * 6);
            // need to check once
            if ((computerNumber + score)%2 ==0 && isEven) {
                return 1;
            } else {
                return 0;
            }
        } else {
            // need to implement to get both values from two different players
            return 0;
        }
    }
}
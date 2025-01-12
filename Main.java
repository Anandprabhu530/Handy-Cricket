import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("-------------Welcome to Hand Cricket------------- \n\n");
        System.out.println("Choose anyone option below");
        System.out.println("0: Single Player");
        System.out.println("1: Multi-Player");
        System.out.println("2: Instructions");
        int n = sc.nextInt();

        switch (n) {
            case 0:
                // Need to implement with a inbuilt function;
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
    
    public int throwToss(boolean isEven, int score, int choice) {
        if (choice == 0) {
            int computerNumber = (int) Math.floor(Math.random() * 6);
            // need to check once
            if ((computerNumber + score)%2 ==0 && isEven) {
                return 1;
            } else {
                return 0;
            }
        } else {
            // need to implement to get both values from two different playerss
            return 0;
        }
    }
}
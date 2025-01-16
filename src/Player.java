package src;
public class Player {
    String name;
    private static int runs = 0;
    boolean isBatting;
    private static int ballsFaced = 0;

    Player(String name, boolean isBatting) {
        this.name = name;
        this.isBatting = isBatting;
    }
    
    public static int[] getRunsAndBallsFaced() {
        return new int[] {runs,ballsFaced};
    }

    public static void setRuns(int newRun) {
        runs += newRun;
        ballsFaced += 1;

    }
}

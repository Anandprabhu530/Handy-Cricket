public class Player {
    String name;
    private int runs = 0;
    boolean isBatting;
    private int ballsFaced = 0;

    Player(String name, boolean isBatting) {
        this.name = name;
        this.isBatting = isBatting;
    }
    
    public int[] getRunsAndBallsFaced() {
        return new int[] {runs,ballsFaced};
    }

    public void setRuns(int newRun) {
        runs += newRun;
        ballsFaced += 1;

    }
}

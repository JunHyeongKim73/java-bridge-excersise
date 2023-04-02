package bridge.domain;

public class GameResult {
    private final boolean isSuccess;
    private final int tries;

    public GameResult(boolean isSuccess, int tries) {
        this.isSuccess = isSuccess;
        this.tries = tries;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public int getTries() {
        return tries;
    }
}

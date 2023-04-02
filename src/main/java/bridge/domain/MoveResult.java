package bridge.domain;

public class MoveResult {
    private final int position;
    private final boolean success;

    public MoveResult(int position, boolean success) {
        this.position = position;
        this.success = success;
    }

    public int getPosition() {
        return position;
    }

    public boolean isSuccess() {
        return success;
    }
}

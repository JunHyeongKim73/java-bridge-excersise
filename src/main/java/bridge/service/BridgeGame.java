package bridge.service;

import bridge.domain.BridgeCell;
import bridge.domain.BridgeMaker;
import bridge.domain.GameResult;
import bridge.domain.MoveResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 다리 건너기 게임을 관리하는 클래스
 */
public class BridgeGame {

    private static final int BRIDGE_MIN_SIZE = 3;
    private static final int BRIDGE_MAX_SIZE = 20;

    private final BridgeMaker bridgeMaker;

    private List<String> bridge;
    private final List<MoveResult> map;

    private int currentBridgePosition = 0;
    private int tries = 1;

    public BridgeGame(BridgeMaker bridgeMaker) {
        this.bridgeMaker = bridgeMaker;
        this.map = new ArrayList<>();
    }

    public void makeBridge(int size) {
        validateSize(size);

        this.bridge = bridgeMaker.makeBridge(size);
    }

    private void validateSize(int size) {
        if (size < BRIDGE_MIN_SIZE || size > BRIDGE_MAX_SIZE) {
            throw new IllegalArgumentException("[ERROR] 다리의 길이는 3부터 20 사이의 숫자여야 합니다.");
        }
    }

    /**
     * 사용자가 칸을 이동할 때 사용하는 메서드
     * <p>
     * 이동을 위해 필요한 메서드의 반환 타입(return type), 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public MoveResult move(String moving) {
        final String movingCell = BridgeCell.from(moving);
        final String answerCell = this.bridge.get(this.currentBridgePosition);

        final boolean canMove = answerCell.equals(movingCell);

        final MoveResult result = new MoveResult(BridgeCell.getPosition(movingCell), canMove);
        this.map.add(result);

        this.currentBridgePosition++;

        return result;
    }

    public List<MoveResult> getMap() {
        return Collections.unmodifiableList(map);
    }

    /**
     * 사용자가 게임을 다시 시도할 때 사용하는 메서드
     * <p>
     * 재시작을 위해 필요한 메서드의 반환 타입(return type), 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void retry() {
        this.currentBridgePosition--;
        this.tries++;
        this.map.remove(this.map.size() - 1);
    }

    public boolean isEnd() {
        return this.currentBridgePosition == this.bridge.size();
    }

    public GameResult getResult() {
        final boolean success = isSuccess();
        return new GameResult(success, this.tries);
    }

    private boolean isSuccess() {
        if (this.map.size() != this.bridge.size()) {
            return false;
        }

        return this.map.stream()
                .allMatch(MoveResult::isSuccess);
    }
}

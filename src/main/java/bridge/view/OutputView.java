package bridge.view;

import bridge.domain.BridgeCell;
import bridge.domain.MoveResult;
import java.util.List;

/**
 * 사용자에게 게임 진행 상황과 결과를 출력하는 역할을 한다.
 */
public class OutputView {

    public void printStartMessage() {
        System.out.println("다리 건너기 게임을 시작합니다.");
    }

    /**
     * 현재까지 이동한 다리의 상태를 정해진 형식에 맞춰 출력한다.
     * <p>
     * 출력을 위해 필요한 메서드의 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void printMap(List<MoveResult> maps) {
        for (int i = 0; i < BridgeCell.size(); i++) {
            System.out.print("[ ");
            for (int j = 0; j < maps.size(); j++) {
                if (j != 0) {
                    System.out.print("| ");
                }

                printElement(i, maps.get(j));
            }
            System.out.println("]");
        }
    }

    private void printElement(int i, MoveResult result) {
        if (i != result.getPosition()) {
            System.out.print("  ");
            return;
        }
        printSuccessOrFail(result.isSuccess());
    }

    private void printSuccessOrFail(boolean success) {
        if (success) {
            System.out.print("O ");
            return;
        }
        System.out.print("X ");
    }

    /**
     * 게임의 최종 결과를 정해진 형식에 맞춰 출력한다.
     * <p>
     * 출력을 위해 필요한 메서드의 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void printResult(List<MoveResult> maps, boolean success, int tries) {
        System.out.println("최종 게임 결과");
        printMap(maps);
        System.out.println();

        String result = "실패";
        if (success) {
            result = "성공";
        }

        System.out.println("게임 성공 여부: " + result);
        System.out.println("총 시도한 횟수: " + tries);
    }
}

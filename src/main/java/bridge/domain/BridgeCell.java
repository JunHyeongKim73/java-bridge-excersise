package bridge.domain;

import java.util.List;

public class BridgeCell {
    private static final List<String> cells = List.of("D", "U");

    private BridgeCell() {
    }

    public static String from(String value) {
        return cells.stream()
                .filter(cell -> cell.equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] " + value + "는 올바르지 않은 칸입니다."));
    }

    public static String from(int value) {
        return cells.get(value);
    }

    public static int size() {
        return cells.size();
    }

    public static int getPosition(String value) {
        return 1 - cells.indexOf(value);
    }
}

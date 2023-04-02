package bridge;

import static org.assertj.core.api.Assertions.assertThat;

import bridge.domain.BridgeMaker;
import java.util.List;
import org.junit.jupiter.api.Test;

class BridgeCellMakerTest {

    private final BridgeMaker bridgeMaker = new BridgeMaker(() -> 0);

    @Test
    void 랜덤한_다리를_생성한다() {
        // given & when
        final List<String> bridge = bridgeMaker.makeBridge(3);

        // then
        assertThat(bridge).containsExactly("D", "D", "D");
    }
}
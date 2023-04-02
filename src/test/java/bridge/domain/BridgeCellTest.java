package bridge.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class BridgeCellTest {

    @Test
    void 다리_셀을_생성한다() {
        // given
        final String validCell = "D";

        // when & then
        assertThatCode(() -> BridgeCell.from(validCell))
                .doesNotThrowAnyException();
    }

    @Test
    void 유효하지_않은_다리_셀이면_예외를_던진다() {
        // given
        final String inValidCell = "I";

        // when & then
        assertThatThrownBy(() -> BridgeCell.from(inValidCell))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
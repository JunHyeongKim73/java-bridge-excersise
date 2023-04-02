package bridge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;

import bridge.domain.BridgeMaker;
import bridge.domain.GameResult;
import bridge.domain.MoveResult;
import bridge.service.BridgeGame;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class BridgeGameTest {

    final BridgeGame bridgeGame = new BridgeGame(new BridgeMaker(() -> 0));

    @Nested
    class 다리를_생성할_때_ {
        @ParameterizedTest
        @ValueSource(ints = {2, 21})
        void 유효하지_않은_길이의_다리를_만들_수_없다(int size) {
            assertThatThrownBy(() -> bridgeGame.makeBridge(size))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @ValueSource(ints = {3, 20})
        void 유효한_길이라면_정상적으로_다리를_생성한다(int size) {
            assertThatCode(() -> bridgeGame.makeBridge(size))
                    .doesNotThrowAnyException();
        }
    }

    @Nested
    class 다리를_이동할_때_ {

        @Test
        void 다리_건너기를_실패했으면_false를_반환한다() {
            // given
            bridgeGame.makeBridge(3);

            // when
            final MoveResult result = bridgeGame.move("U");

            // then
            assertThat(result.isSuccess()).isFalse();
        }

        @Test
        void 다리_건너기를_성공했으면_true를_반환한다() {
            // given
            bridgeGame.makeBridge(3);

            // when
            final MoveResult result = bridgeGame.move("D");

            // then
            assertThat(result.isSuccess()).isTrue();
        }
    }

    @Nested
    class 다리_건너기_이력을_확인할_때_ {

        @Test
        void 다리_건너기_이력을_확인할_수_있다() {
            // given
            bridgeGame.makeBridge(3);

            // when
            bridgeGame.move("D");
            bridgeGame.move("D");
            bridgeGame.move("U");

            // then
            assertThat(bridgeGame.getMap()).extracting("position", "success").containsExactly(
                    tuple(1, true),
                    tuple(1, true),
                    tuple(0, false)
            );

        }
    }

    @Nested
    class 다리_건너기를_재시작하면_ {

        @Test
        void 마지막_결과가_삭제된다() {
            // given
            bridgeGame.makeBridge(3);

            bridgeGame.move("D");
            bridgeGame.move("D");
            bridgeGame.move("U");

            bridgeGame.retry();

            // when
            final List<MoveResult> results = bridgeGame.getMap();

            // then
            assertThat(results).extracting("position", "success").containsExactly(
                    tuple(1, true),
                    tuple(1, true)
            );
        }
    }

    @Nested
    class isEnd_메서드_호출_시_ {

        @Test
        void 게임이_종료되었으면_true를_반환한다() {
            // given
            bridgeGame.makeBridge(3);

            bridgeGame.move("D");
            bridgeGame.move("D");
            bridgeGame.move("D");

            // when
            final boolean end = bridgeGame.isEnd();

            // then
            assertThat(end).isTrue();
        }

        @Test
        void 게임이_종료되지_않았으면_false를_반환한다() {
            // given
            bridgeGame.makeBridge(3);

            bridgeGame.move("D");
            bridgeGame.move("D");

            // when
            final boolean end = bridgeGame.isEnd();

            // then
            assertThat(end).isFalse();
        }
    }

    @Nested
    class 다리_건너기를_종료했을_때_ {

        @Test
        void 건너기를_성공했으면_true를_반환한다() {
            // given
            bridgeGame.makeBridge(3);

            bridgeGame.move("D");
            bridgeGame.move("D");
            bridgeGame.move("D");

            // when
            final GameResult gameResult = bridgeGame.getResult();

            // then
            assertAll(
                    () -> assertThat(gameResult.isSuccess()).isTrue(),
                    () -> assertThat(gameResult.getTries()).isEqualTo(1)
            );
        }

        @Test
        void 건너기를_실패했으면_false를_반환한다() {
            // given
            bridgeGame.makeBridge(3);

            bridgeGame.move("D");
            bridgeGame.move("D");
            bridgeGame.move("U");

            // when
            final GameResult gameResult = bridgeGame.getResult();

            // then
            assertAll(
                    () -> assertThat(gameResult.isSuccess()).isFalse(),
                    () -> assertThat(gameResult.getTries()).isEqualTo(1)
            );
        }
    }
}
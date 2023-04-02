package bridge;

import bridge.domain.BridgeMaker;
import bridge.domain.GameResult;
import bridge.domain.MoveResult;
import bridge.service.BridgeGame;
import bridge.service.BridgeNumberGenerator;
import bridge.view.InputView;
import bridge.view.OutputView;

public class Application {
    private static final InputView inputView = new InputView();
    private static final OutputView outputView = new OutputView();

    private static final BridgeNumberGenerator numberGenerator = new BridgeRandomNumberGenerator();
    private static final BridgeMaker bridgeMaker = new BridgeMaker(numberGenerator);
    private static final BridgeGame bridgeGame = new BridgeGame(bridgeMaker);
    
    public static void main(String[] args) {

        playBridgeGame();
    }

    private static void playBridgeGame() {
        outputView.printStartMessage();
        final int bridgeSize = inputView.readBridgeSize();

        bridgeGame.makeBridge(bridgeSize);

        final GameResult gameResult = getBridgeGameResult();

        outputView.printResult(bridgeGame.getMap(), gameResult.isSuccess(), gameResult.getTries());
    }

    private static GameResult getBridgeGameResult() {
        while (true) {
            final String moving = inputView.readMoving();

            final MoveResult moveResult = bridgeGame.move(moving);
            outputView.printMap(bridgeGame.getMap());
            
            if (!moveResult.isSuccess()) {
                final String retryInput = inputView.readGameCommand();
                if (retryInput.equals("Q")) {
                    break;
                }

                bridgeGame.retry();
            }

            if(bridgeGame.isEnd()) {
                break;
            }
        }
        return bridgeGame.getResult();
    }
}

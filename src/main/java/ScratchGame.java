import analysis.GameAnalyzer;
import board.GameBoard;
import board.GameBoardGenerator;
import config.Config;
import outcome.GameOutcome;

import java.math.BigDecimal;

public class ScratchGame {

    private final Config config;

    public ScratchGame(Config config) {
        this.config = config;
    }

    public GameOutcome play(BigDecimal bettingAmount) {

        return new GameAnalyzer(config.winCombinations(), config.symbols()).analyze(init(), bettingAmount);
    }

    private GameBoard init() {
        return new GameBoardGenerator().generate(config.columns(), config.rows(),
                                                 config.probabilities().standardSymbols(),
                                                 config.probabilities().bonusSymbols());
    }
}

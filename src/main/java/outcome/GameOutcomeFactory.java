package outcome;

import board.GameBoard;
import config.Symbol;
import config.WinCombination;
import reward.RewardCalculator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameOutcomeFactory {

    private final RewardCalculator rewardCalculator;
    private final Map<String, Symbol> symbols;
    private final Map<String, WinCombination> winCombinations;

    public GameOutcomeFactory(RewardCalculator rewardCalculator, Map<String, Symbol> symbols,
                              Map<String, WinCombination> winCombinations) {
        this.rewardCalculator = rewardCalculator;
        this.symbols = symbols;
        this.winCombinations = winCombinations;
    }

    public GameOutcome createGameOutcome(GameBoard board, Map<String, List<String>> appliedWinCombinations,
                                         BigDecimal bettingAmount) {
        return appliedWinCombinations.isEmpty() ? createLostOutcome(board.board()) : createWinningOutcome(board,
                                                                                                          appliedWinCombinations,
                                                                                                          bettingAmount);
    }

    private GameOutcome createWinningOutcome(GameBoard board, Map<String, List<String>> appliedWinCombinations,
                                            BigDecimal bettingAmount) {
        Map<String, List<WinCombination>> appliedWinCombinationsBySymbolMap = convertToWinCombinationMap(
                appliedWinCombinations);
        BigDecimal totalReward = rewardCalculator.calculateReward(bettingAmount, symbols,
                                                                  appliedWinCombinationsBySymbolMap,
                                                                  board.bonusSymbol());
        String bonusSymbol = "MISS".equals(board.bonusSymbol()) ? null : board.bonusSymbol();

        return new GameOutcome(board.board(), totalReward, appliedWinCombinations, bonusSymbol);
    }

    public GameOutcome createLostOutcome(String[][] board) {
        return new GameOutcome(board, BigDecimal.ZERO, null, null);
    }

    private Map<String, List<WinCombination>> convertToWinCombinationMap(
            Map<String, List<String>> appliedWinCombinations) {
        return appliedWinCombinations.entrySet().stream()
                                     .collect(Collectors.toMap(
                                             Map.Entry::getKey,
                                             entry -> entry.getValue().stream().map(winCombinations::get).toList()
                                     ));
    }


}


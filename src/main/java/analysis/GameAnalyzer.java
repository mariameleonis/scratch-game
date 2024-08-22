package analysis;

import board.GameBoard;
import config.Symbol;
import config.WinCombination;
import outcome.GameOutcome;
import outcome.GameOutcomeFactory;
import reward.RewardCalculator;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is responsible for analyzing the game board and determining the outcome
 * based on the current state of the board and the betting amount.
 * <p>
 * The class first identifies potential win candidates on the board, which are positions
 * where symbols match predefined winning conditions. If any win candidates are found,
 * they are further analyzed to collect the actual win combinations. The final game outcome
 * is then generated based on these win combinations and the betting amount.
 * </p>
 */
public class GameAnalyzer {

    private final WinCombinationCollector winCombinationCollector;
    private final GameOutcomeFactory gameOutcomeFactory;

    /**
     * This value represents the minimum but not necessarily sufficient condition for a symbol
     * to be considered a candidate for winning.
     */
    private final int minSymbolFrequencyNeededForWin;

    public GameAnalyzer(Map<String, WinCombination> winCombinations,
                        Map<String, Symbol> symbols) {
        this.winCombinationCollector = new WinCombinationCollector(winCombinations);
        this.gameOutcomeFactory = new GameOutcomeFactory(new RewardCalculator(),
                                                         symbols,
                                                         winCombinations);
        this.minSymbolFrequencyNeededForWin = calculateMinSymbolsAmountNeededForWin(
                winCombinations.values());
    }

    public GameOutcome analyze(GameBoard board, BigDecimal bettingAmount) {
        Map<String, List<String>> appliedWinCombinations = getAppliedWinCombinations(board.symbolCoordinatesMap());
        return gameOutcomeFactory.createGameOutcome(board, appliedWinCombinations, bettingAmount);
    }

    private Map<String, List<String>> getAppliedWinCombinations(Map<String, Set<String>> coordinates) {
        Map<String, Set<String>> winCandidates = filterWinCandidates(coordinates);
        return winCandidates.isEmpty() ? Collections.emptyMap() : winCombinationCollector.collectWinCombinations(
                winCandidates);

    }

    private Map<String, Set<String>> filterWinCandidates(
            Map<String, Set<String>> coordinates) {
        return coordinates.entrySet().stream()
                          .filter(entry -> entry.getValue()
                                                .size() >= minSymbolFrequencyNeededForWin)
                          .collect(Collectors.toMap(Map.Entry::getKey,
                                                    Map.Entry::getValue));
    }

    private int calculateMinSymbolsAmountNeededForWin(
            Collection<WinCombination> winCombinations) {
        return winCombinations.stream()
                              .mapToInt(wc -> wc.getWhen()
                                                .getMinSymbolsAmountNeededForWin(
                                                        wc))
                              .min()
                              .orElse(Integer.MAX_VALUE);
    }
}

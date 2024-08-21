package analysis;

import board.GameBoard;
import config.Symbol;
import config.WinCombination;
import outcome.GameOutcome;
import outcome.GameOutcomeFactory;
import reward.RewardCalculator;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class GameAnalyzer {

    private final WinCombinationChecker winCombinationChecker;
    private final GameOutcomeFactory gameOutcomeFactory;
    private final int minSymbolFrequencyNeededForWin;

    public GameAnalyzer(Map<String, WinCombination> winCombinations,
                        Map<String, Symbol> symbols) {
        this.winCombinationChecker = new WinCombinationChecker(winCombinations);
        this.gameOutcomeFactory = new GameOutcomeFactory(new RewardCalculator(),
                                                         symbols,
                                                         winCombinations);
        this.minSymbolFrequencyNeededForWin = calculateMinSymbolsAmountNeededForWin(
                winCombinations.values());
    }

    public GameOutcome analyze(GameBoard board, BigDecimal bettingAmount) {
        return Optional.ofNullable(filterWinCandidates(board.coordinates()))
                       .filter(winCandidates -> !winCandidates.isEmpty())
                       .map(winCombinationChecker::determineWinningCombinations)
                       .filter(appliedWinCombinations -> !appliedWinCombinations.isEmpty())
                       .map(appliedWinCombinations -> gameOutcomeFactory.createWinningOutcome(
                               board, appliedWinCombinations, bettingAmount))
                       .orElseGet(() -> gameOutcomeFactory.createLostOutcome(
                               board.board()));
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

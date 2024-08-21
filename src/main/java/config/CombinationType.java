package config;

import analysis.*;

import java.util.Set;

public enum CombinationType {
    LINEAR_SYMBOLS(new LinearSymbolsWinChecker(), new LinearSymbolsWinCombinationLengthDeterminer()),
    SAME_SYMBOLS(new SameSymbolsWinChecker(), new SameSymbolsWinCombinationLengthDeterminer());

    private final WinChecker winChecker;
    private final WinCombinationLengthDeterminer winCombinationLengthDeterminer;

    CombinationType(WinChecker winChecker, WinCombinationLengthDeterminer winCombinationLengthDeterminer) {
        this.winChecker = winChecker;
        this.winCombinationLengthDeterminer = winCombinationLengthDeterminer;
    }

    public boolean checkWin(WinCombination winCombination, Set<String> candidateCoordinates) {
        return winChecker.check(winCombination, candidateCoordinates);
    }

    public int getMinSymbolsAmountNeededForWin(WinCombination winCombination) {
        return winCombinationLengthDeterminer.determine(winCombination);
    }
}

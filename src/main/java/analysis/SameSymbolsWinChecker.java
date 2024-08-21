package analysis;

import config.WinCombination;

import java.util.Set;

public class SameSymbolsWinChecker implements WinChecker {
    @Override
    public boolean check(WinCombination winCombination, Set<String> candidateCoordinates) {
        return candidateCoordinates.size() >= winCombination.getCount();
    }
}

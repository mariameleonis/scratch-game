package analysis;

import config.WinCombination;

import java.util.Set;

public class LinearSymbolsWinChecker implements WinChecker {
    @Override
    public boolean check(WinCombination winCombination, Set<String> candidateCoordinates) {
        return winCombination.getCoveredAreas().stream().anyMatch(candidateCoordinates::containsAll);
    }
}

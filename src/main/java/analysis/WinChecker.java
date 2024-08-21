package analysis;

import config.WinCombination;

import java.util.Set;

@FunctionalInterface
public interface WinChecker {
    boolean check(WinCombination winCombination, Set<String> candidateCoordinates);
}

package analysis;

import config.WinCombination;

import java.util.List;

public class LinearSymbolsWinCombinationLengthDeterminer implements WinCombinationLengthDeterminer {
    @Override
    public int determine(WinCombination combination) {
        return combination.getCoveredAreas().stream().mapToInt(List::size).min().orElse(Integer.MAX_VALUE);
    }
}

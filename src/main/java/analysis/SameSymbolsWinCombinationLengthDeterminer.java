package analysis;

import config.WinCombination;

public class SameSymbolsWinCombinationLengthDeterminer implements WinCombinationLengthDeterminer {
    @Override
    public int determine(WinCombination combination) {
        return combination.getCount();
    }
}

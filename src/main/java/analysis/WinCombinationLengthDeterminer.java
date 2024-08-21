package analysis;

import config.WinCombination;

@FunctionalInterface
public interface WinCombinationLengthDeterminer {
    int determine(WinCombination combination);
}

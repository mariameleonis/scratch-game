package analysis;

import config.WinCombination;

import java.util.*;
import java.util.stream.Collectors;

public class WinCombinationChecker {

    private final Map<String, WinCombination> winCombinations;

    public WinCombinationChecker(Map<String, WinCombination> winCombinations) {
        this.winCombinations = winCombinations;
    }

    public Map<String, List<String>> determineWinningCombinations(Map<String, Set<String>> winCandidates) {
        return winCandidates.entrySet().stream()
                            .map(entry -> Map.entry(entry.getKey(), findWinningCombinations(entry.getValue())))
                            .filter(entry -> !entry.getValue().isEmpty())
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<String> findWinningCombinations(Set<String> candidateCoordinates) {
        Map<String, List<WinCombination>> combinationsByGroup = groupCombinationsByGroup(candidateCoordinates);
        Map<String, WinCombination> maxRewardMultiplierByGroup = getMaxRewardMultiplierByGroup(combinationsByGroup);
        return extractCombinationNames(maxRewardMultiplierByGroup);
    }

    private Map<String, List<WinCombination>> groupCombinationsByGroup(Set<String> candidateCoordinates) {
        return winCombinations.entrySet().stream()
                              .filter(entry -> entry.getValue().getWhen()
                                                    .checkWin(entry.getValue(), candidateCoordinates))
                              .collect(Collectors.groupingBy(
                                      entry -> entry.getValue().getGroup(),
                                      Collectors.mapping(
                                              Map.Entry::getValue,
                                              Collectors.toList()
                                      )
                              ));
    }

    private Map<String, WinCombination> getMaxRewardMultiplierByGroup(
            Map<String, List<WinCombination>> combinationsByGroup) {
        return combinationsByGroup.entrySet().stream()
                                  .collect(Collectors.toMap(
                                          Map.Entry::getKey,
                                          entry -> entry.getValue().stream()
                                                        .max(Comparator.comparing(WinCombination::getRewardMultiplier))
                                                        .orElseThrow(() -> new IllegalStateException(
                                                                "Cannot find a winning combination with the highest reward multiplier for group: " + entry.getKey()))
                                  ));
    }

    private List<String> extractCombinationNames(Map<String, WinCombination> maxRewardMultiplierByGroup) {
        return maxRewardMultiplierByGroup.values().stream().map(WinCombination::getName).toList();
    }
}


package analysis;

import config.WinCombination;

import java.util.*;
import java.util.stream.Collectors;

public class WinCombinationCollector {

    private final Map<String, WinCombination> winCombinations;

    public WinCombinationCollector(Map<String, WinCombination> winCombinations) {
        this.winCombinations = winCombinations;
    }

    public Map<String, List<String>> collectWinCombinations(Map<String, Set<String>> winCandidates) {
        return winCandidates.entrySet().stream()
                            .map(entry -> Map.entry(entry.getKey(), checkWinCombinations(entry.getValue())))
                            .filter(entry -> !entry.getValue().isEmpty())
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<String> checkWinCombinations(Set<String> candidateCoordinates) {
        Map<String, List<WinCombination>> combinationsByGroup = getAllApplicableWinCombinationsByGroup(candidateCoordinates);
        Map<String, WinCombination> appliedWinCombinations = filterByMaxRewardMultiplier(combinationsByGroup);
        return extractCombinationNames(appliedWinCombinations);
    }

    private Map<String, List<WinCombination>> getAllApplicableWinCombinationsByGroup(Set<String> candidateCoordinates) {
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

    private Map<String, WinCombination> filterByMaxRewardMultiplier(
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


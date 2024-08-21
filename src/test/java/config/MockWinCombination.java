package config;

import java.math.BigDecimal;
import java.util.List;

public class MockWinCombination extends WinCombination {

    private MockWinCombination(String name, BigDecimal rewardMultiplier, CombinationType when, Integer count, String group, List<List<String>> coveredAreas) {
        super(name, rewardMultiplier, when, count, group, coveredAreas);
    }

    public static MockWinCombination createLinearSymbolMockWinCombination(String name, BigDecimal rewardMultiplier, String group, List<List<String>> coveredAreas) {
        return new MockWinCombination(name, rewardMultiplier,CombinationType.LINEAR_SYMBOLS, null, group, coveredAreas);
    }

    public static MockWinCombination createSameSymbolMockWinCombination(String name, BigDecimal rewardMultiplier, Integer count) {
        return new MockWinCombination(name, rewardMultiplier, CombinationType.SAME_SYMBOLS, count, "same_symbols", null);
    }

    public static MockWinCombination createLTetrominoMockWinCombination(String name, BigDecimal rewardMultiplier) {
        List<List<String>> coveredAreas = List.of(
                List.of("0:0", "0:1", "0:2", "1:0"),
                List.of("0:0", "0:1", "1:1", "2:1"),
                List.of("1:0", "1:1", "1:2", "0:2"),
                List.of("0:0", "1:0", "2:0", "2:1")
        );
        return new MockWinCombination(name, rewardMultiplier, CombinationType.LINEAR_SYMBOLS, null, "tetromino", coveredAreas);
    }

    public static MockWinCombination createTTetrominoMockWinCombination(String name, BigDecimal rewardMultiplier) {
        List<List<String>> coveredAreas = List.of(
                List.of("0:0", "0:1", "0:2", "1:1"),
                List.of("0:1", "1:0", "1:1", "2:1"),
                List.of("1:0", "1:1", "1:2", "0:1"),
                List.of("0:1", "1:1", "1:2", "2:1")
        );
        return new MockWinCombination(name, rewardMultiplier, CombinationType.LINEAR_SYMBOLS, null, "tetromino", coveredAreas);
    }

    public static MockWinCombination createZTetrominoMockWinCombination(String name, BigDecimal rewardMultiplier) {
        List<List<String>> coveredAreas = List.of(
                List.of("0:0", "0:1", "1:1", "1:2"),
                List.of("0:1", "1:0", "1:1", "2:0"),
                List.of("0:1", "0:2", "1:0", "1:1"),
                List.of("0:0", "1:0", "1:1", "2:1")
        );
        return new MockWinCombination(name, rewardMultiplier, CombinationType.LINEAR_SYMBOLS, null, "tetromino", coveredAreas);
    }

}

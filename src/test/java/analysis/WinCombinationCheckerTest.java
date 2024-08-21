package analysis;

import config.MockWinCombination;
import config.WinCombination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class WinCombinationCheckerTest {

    private WinCombinationChecker checker;
    private Map<String, WinCombination> winCombinations;

    @BeforeEach
    void setUp() {
        winCombinations = new HashMap<>();

        winCombinations.put("same_symbols_horizontally", MockWinCombination.createLinearSymbolMockWinCombination("same_symbols_horizontally", BigDecimal.valueOf(2), "horizontally_linear_symbols", List.of(List.of("0:0", "0:1", "0:2"), List.of("1:0", "1:1", "1:2"), List.of("2:0", "2:1", "2:2"))));
        winCombinations.put("same_symbols_vertically", MockWinCombination.createLinearSymbolMockWinCombination("same_symbols_vertically", BigDecimal.valueOf(2), "vertically_linear_symbols", List.of(List.of("0:0", "1:0", "2:0"), List.of("0:1", "1:1", "2:1"), List.of("0:2", "1:2", "2:2"))));
        winCombinations.put("same_symbol_3_times", MockWinCombination.createSameSymbolMockWinCombination("same_symbol_3_times", BigDecimal.valueOf(1), 3));
        winCombinations.put("same_symbol_4_times", MockWinCombination.createSameSymbolMockWinCombination("same_symbol_4_times", BigDecimal.valueOf(1.5), 4));
        winCombinations.put("same_symbol_5_times", MockWinCombination.createSameSymbolMockWinCombination("same_symbol_5_times", BigDecimal.valueOf(2), 5));
        winCombinations.put("l-tetromino", MockWinCombination.createLTetrominoMockWinCombination("l-tetromino", BigDecimal.valueOf(10)));
        winCombinations.put("t-tetromino", MockWinCombination.createTTetrominoMockWinCombination("t-tetromino", BigDecimal.valueOf(15)));
        winCombinations.put("z-tetromino", MockWinCombination.createZTetrominoMockWinCombination("z-tetromino", BigDecimal.valueOf(20)));

        checker = new WinCombinationChecker(winCombinations);
    }

    @Test
    void testDetermineWinningCombinationsBasic() {
        Map<String, Set<String>> winCandidates = new HashMap<>();
        winCandidates.put("X", Set.of("0:1", "1:0", "1:1", "2:0"));
        winCandidates.put("Y", Set.of("0:2", "1:2", "2:2"));
        winCandidates.put("Z", Set.of("0:0"));
        winCandidates.put("W", Set.of("2:1"));

        Map<String, List<String>> result = checker.determineWinningCombinations(winCandidates);

        assertEquals(2, result.size());
        assertTrue(result.get("X").containsAll(List.of("z-tetromino", "same_symbol_4_times")));
        assertFalse(result.get("X").contains("same_symbol_3_times"));
        assertTrue(result.get("Y").containsAll(List.of("same_symbol_3_times", "same_symbols_vertically")));
        assertFalse(result.keySet().containsAll(List.of("Z", "W")));
    }

    @Test
    void testDetermineWinningCombinationsEmptyCandidateSet() {
        Map<String, Set<String>> winCandidates = new HashMap<>();
        winCandidates.put("X", Collections.emptySet());

        Map<String, List<String>> result = checker.determineWinningCombinations(winCandidates);

        assertTrue(result.isEmpty());
    }

    @Test
    void testDetermineWinningCombinationsNoWinningCombinations() {
        Map<String, Set<String>> winCandidates = new HashMap<>();
        winCandidates.put("Z", Set.of("0:0"));
        winCandidates.put("W", Set.of("2:1"));

        Map<String, List<String>> result = checker.determineWinningCombinations(winCandidates);

        assertTrue(result.isEmpty());
    }

    @Test
    void testDetermineWinningCombinationsOneWinningCombinationPerGroup() {
        Map<String, Set<String>> winCandidates = new HashMap<>();
        winCandidates.put("X", Set.of("0:1", "1:0", "1:1", "2:0", "0:2", "1:2"));

        Map<String, List<String>> result = checker.determineWinningCombinations(winCandidates);

        assertEquals(1, result.size());
        assertTrue(result.get("X").containsAll(List.of("z-tetromino", "same_symbol_5_times", "same_symbols_horizontally")));
        assertFalse(result.get("X").containsAll(List.of("l-tetronimo", "t-tetronimo", "same_symbol_4_times", "same_symbol_3_times")));

    }
}
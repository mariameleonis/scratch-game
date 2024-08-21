package board;

import java.util.Map;
import java.util.Set;

public record GameBoard(
        String[][] board,
        Map<String, Set<String>> coordinates,
        String bonusSymbol
) {
}

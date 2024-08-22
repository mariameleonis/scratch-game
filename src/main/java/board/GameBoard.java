package board;

import java.util.Map;
import java.util.Set;

public record GameBoard(
        String[][] matrix,
        Map<String, Set<String>> symbolCoordinatesMap,
        String bonusSymbol
) {
}

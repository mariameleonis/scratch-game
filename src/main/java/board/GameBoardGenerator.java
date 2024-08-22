package board;

import config.Probability;

import java.util.*;

public class GameBoardGenerator {

    private final RandomSymbolGenerator randomSymbolGenerator;

    public GameBoardGenerator() {
        this.randomSymbolGenerator = new RandomSymbolGenerator();
    }

    public GameBoard generate(int cols, int rows, List<Probability> standardSymbols, Probability bonusSymbols) {
        String[][] matrix = new String[rows][cols];
        Map<String, Set<String>> coordinatesMap = new HashMap<>();

        Map<String, List<SymbolProbability>> probabilitiesMap = createProbabilitiesMap(standardSymbols);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                String key = getKey(row, col);
                List<SymbolProbability> probabilities = probabilitiesMap.getOrDefault(key, probabilitiesMap.get(
                        getKey(0, 0)));
                String symbol = randomSymbolGenerator.getRandomSymbol(probabilities);
                matrix[row][col] = symbol;
                coordinatesMap.computeIfAbsent(symbol, k -> new HashSet<>()).add(row + ":" + col);
            }
        }

        String bonusSymbol = generateAndPlaceBonusSymbol(cols, rows, bonusSymbols, matrix, coordinatesMap);

        return new GameBoard(matrix, coordinatesMap, bonusSymbol);
    }

    private Map<String, List<SymbolProbability>> createProbabilitiesMap(List<Probability> standardSymbols) {
        Map<String, List<SymbolProbability>> probabilitiesMap = new HashMap<>();
        for (Probability probability : standardSymbols) {
            String key = getKey(probability.row(), probability.column());
            probabilitiesMap.put(key, createProbabilities(probability.symbols()));
        }
        return probabilitiesMap;
    }

    private String generateAndPlaceBonusSymbol(int cols, int rows, Probability bonusSymbols, String[][] matrix,
                                               Map<String, Set<String>> coordinatesMap) {
        List<SymbolProbability> bonusProbabilities = createProbabilities(bonusSymbols.symbols());

        Random random = new Random();

        int randomRow = random.nextInt(rows);
        int randomCol = random.nextInt(cols);

        removeFromCoordinatesMap(matrix, coordinatesMap, randomRow, randomCol);

        String bonusSymbol = randomSymbolGenerator.getRandomSymbol(bonusProbabilities);
        matrix[randomRow][randomCol] = bonusSymbol;

        return bonusSymbol;
    }

    private static void removeFromCoordinatesMap(String[][] matrix, Map<String, Set<String>> coordinatesMap, int randomRow,
                                  int randomCol) {
        String currentSymbol = matrix[randomRow][randomCol];

        coordinatesMap.computeIfPresent(currentSymbol, (key, coordinatesSet) -> {
            coordinatesSet.remove(randomRow + ":" + randomCol);
            return coordinatesSet.isEmpty() ? null : coordinatesSet;
        });
    }

    private String getKey(int row, int column) {
        return row + "_" + column;
    }

    private List<SymbolProbability> createProbabilities(Map<String, Integer> symbolProbabilities) {
        List<SymbolProbability> probabilities = new ArrayList<>();
        int totalProbability = symbolProbabilities.values().stream().mapToInt(Integer::intValue).sum();

        for (Map.Entry<String, Integer> entry : symbolProbabilities.entrySet()) {
            double probabilityPercentage = (double) entry.getValue() / totalProbability;
            probabilities.add(new SymbolProbability(entry.getKey(), probabilityPercentage));
        }

        return probabilities;
    }

}

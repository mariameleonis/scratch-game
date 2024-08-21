package board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class RandomSymbolGeneratorTest {

    private RandomSymbolGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new RandomSymbolGenerator(new Random(12345));
    }

    @Test
    void getGetRandomSymbol() {
        List<SymbolProbability> probabilities = List.of(
                new SymbolProbability("A", 0.1),
                new SymbolProbability("B", 0.3),
                new SymbolProbability("C", 0.6)
        );

        Map<String, Integer> symbolCounts = new HashMap<>();

        int totalSamples = 1000000;

        for (int i = 0; i < totalSamples; i++) {
            String symbol = generator.getRandomSymbol(probabilities);
            symbolCounts.compute(symbol, (k, v) -> v == null ? 1 : v + 1);
        }

        double tolerance = 0.01;
        assertEquals(0.1, normalize(symbolCounts.get("A"), totalSamples), tolerance);
        assertEquals(0.3, normalize(symbolCounts.get("B"), totalSamples), tolerance);
        assertEquals(0.6, normalize(symbolCounts.get("C"), totalSamples), tolerance);
    }

    private double normalize(int count, int totalSamples) {
        return (double) count / totalSamples;
    }
}
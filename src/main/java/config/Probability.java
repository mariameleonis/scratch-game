package config;

import java.util.Map;

public record Probability(
        int column,
        int row,
        Map<String, Integer> symbols
) {
}

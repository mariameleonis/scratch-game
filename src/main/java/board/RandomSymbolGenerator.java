package board;

import java.util.List;
import java.util.Random;

public class RandomSymbolGenerator {

    private final Random random;

    public RandomSymbolGenerator(Random random) {
        this.random = random;
    }

    public RandomSymbolGenerator() {
        this(new Random());
    }

    public String getRandomSymbol(List<SymbolProbability> probabilities) {
        double rand = random.nextDouble();
        double cumulative = 0.0;
        for (SymbolProbability symbolProbability : probabilities) {
            cumulative += symbolProbability.probability();
            if (rand <= cumulative) {
                return symbolProbability.symbol();
            }
        }
        // As a safeguard, return the last symbol in the list
        return probabilities.get(probabilities.size() - 1).symbol();
    }
}


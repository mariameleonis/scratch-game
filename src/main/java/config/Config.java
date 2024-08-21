package config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public record Config(
        int columns,
        int rows,
        Map<String, Symbol> symbols,

        Probabilities probabilities,
        @JsonProperty("win_combinations")
        Map<String, WinCombination> winCombinations
) {

    public record Probabilities(
            @JsonProperty("standard_symbols")
            List<Probability> standardSymbols,
            @JsonProperty("bonus_symbols")
            Probability bonusSymbols
    ) {
    }

}

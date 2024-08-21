package config;

import java.math.BigDecimal;

public record Params(
        Config config,
        BigDecimal bettingAmount
        ) {
}

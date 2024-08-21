package config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record Symbol(
        String symbol,
        @JsonProperty("reward_multiplier")
        BigDecimal rewardMultiplier,
        String type,
        ImpactType impact,
        BigDecimal extra
) {
}

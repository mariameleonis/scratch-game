package config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public class WinCombination {
    private String name;
    @JsonProperty(value = "reward_multiplier")
    private BigDecimal rewardMultiplier;
    private CombinationType when;
    private Integer count;

    private String group;
    @JsonProperty("covered_areas")
    private List<List<String>> coveredAreas;

    public WinCombination() {
    }

    public WinCombination(String name, BigDecimal rewardMultiplier, CombinationType when, Integer count, String group,
                          List<List<String>> coveredAreas) {
        this.name = name;
        this.rewardMultiplier = rewardMultiplier;
        this.when = when;
        this.count = count;
        this.group = group;
        this.coveredAreas = coveredAreas;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRewardMultiplier(BigDecimal rewardMultiplier) {
        this.rewardMultiplier = rewardMultiplier;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getRewardMultiplier() {
        return rewardMultiplier;
    }

    public CombinationType getWhen() {
        return when;
    }

    public Integer getCount() {
        return count;
    }

    public String getGroup() {
        return group;
    }

    public List<List<String>> getCoveredAreas() {
        return coveredAreas;
    }
}

package reward;

import config.ImpactType;
import config.Symbol;
import config.WinCombination;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class RewardCalculator {
    public BigDecimal calculateReward(BigDecimal bettingAmount, Map<String, Symbol> allSymbols,
                                      Map<String, List<WinCombination>> appliedWinCombinations, String bonusSymbol) {
        BigDecimal totalReward = appliedWinCombinations.entrySet().stream()
                                                       .map(entry -> calculateCombinationReward(bettingAmount, entry,
                                                                                                allSymbols.get(
                                                                                                        entry.getKey())))
                                                       .reduce(BigDecimal.ZERO, BigDecimal::add);

        return applyBonusSymbol(totalReward, allSymbols.get(bonusSymbol));
    }

    private BigDecimal calculateCombinationReward(BigDecimal bettingAmount,
                                                  Map.Entry<String, List<WinCombination>> combinationEntry,
                                                  Symbol symbol) {
        BigDecimal symbolMultiplier = symbol.rewardMultiplier();
        BigDecimal totalMultiplier = calculateTotalMultiplierForCombination(combinationEntry.getValue());

        return bettingAmount.multiply(symbolMultiplier).multiply(totalMultiplier);
    }

    private BigDecimal calculateTotalMultiplierForCombination(List<WinCombination> combinations) {
        return combinations.stream()
                           .map(WinCombination::getRewardMultiplier)
                           .reduce(BigDecimal.ONE, BigDecimal::multiply);
    }

    private BigDecimal applyBonusSymbol(BigDecimal totalReward, Symbol bonusSymbol) {

        ImpactType impact = bonusSymbol.impact();

        return switch (impact) {
            case EXTRA_BONUS -> totalReward.add(bonusSymbol.extra());
            case MULTIPLY_REWARD -> totalReward.multiply(bonusSymbol.rewardMultiplier());
            case MISS -> totalReward;
        };
    }

}

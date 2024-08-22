package reward;

import config.Symbol;
import config.WinCombination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RewardCalculatorTest {

    private RewardCalculator rewardCalculator;

    @BeforeEach
    void setUp() {
        rewardCalculator = new RewardCalculator();
    }

    @Test
    void testCalculateRewardEmptyWinningCombinationsAndEmptyBonusSymbol() {
        Map<String, List<WinCombination>> appliedWinCombinations = Collections.emptyMap();
        String bonusSymbol = "";
        Map<String, Symbol> symbols = Collections.emptyMap();
        BigDecimal bettingAmount = BigDecimal.valueOf(100);

        BigDecimal reward = rewardCalculator.calculateReward(bettingAmount, symbols, appliedWinCombinations,
                                                             bonusSymbol);
        assertEquals(0, reward.signum());
    }

    @Test
    void testCalculateRewardSingleWinningCombinationsForSingleSymbol() {
        Symbol symbol = new Symbol(BigDecimal.valueOf(2), "standard", null, null);

        WinCombination winCombination = new WinCombination();
        winCombination.setRewardMultiplier(BigDecimal.valueOf(1.5));

        Map<String, List<WinCombination>> appliedWinCombinations = Map.of("A", List.of(winCombination));
        String bonusSymbol = "";
        Map<String, Symbol> symbols = Map.of("A", symbol);
        BigDecimal bettingAmount = BigDecimal.valueOf(100);

        BigDecimal reward = rewardCalculator.calculateReward(bettingAmount, symbols, appliedWinCombinations,
                                                             bonusSymbol);
        assertEquals(BigDecimal.valueOf(300.0), reward);
    }
}
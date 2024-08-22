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
        String bonusSymbolValue = "";
        Map<String, Symbol> symbols = Collections.emptyMap();
        BigDecimal bettingAmount = BigDecimal.valueOf(100);

        BigDecimal reward = rewardCalculator.calculateReward(bettingAmount, symbols, appliedWinCombinations,
                                                             bonusSymbolValue);
        assertEquals(0, reward.signum());
    }
}
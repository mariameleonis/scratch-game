package reward;

import config.ImpactType;
import config.Symbol;
import config.WinCombination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RewardCalculatorTest {

    private RewardCalculator rewardCalculator;

    @BeforeEach
    void setUp() {
        rewardCalculator = new RewardCalculator();
    }
    @ParameterizedTest(name = "{index}. {0}")
    @MethodSource("provideTestCases")
    void testCalculateReward(CalculateRewardTestCase testCase) {
        BigDecimal reward = rewardCalculator.calculateReward(
                testCase.getBettingAmount(),
                testCase.getSymbols(),
                testCase.getAppliedWinCombinations(),
                testCase.getBonusSymbol()
        );

        assertEquals(testCase.getExpectedReward(), reward, testCase.getTestName());
    }

    private static Stream<Arguments> provideTestCases() {
        Symbol symbolA = new Symbol(BigDecimal.valueOf(2), "standard", null, null);
        Symbol symbolB = new Symbol(BigDecimal.valueOf(3), "standard", null, null);

        WinCombination winCombinationOne = new WinCombination();
        winCombinationOne.setRewardMultiplier(BigDecimal.valueOf(1.5));

        WinCombination winCombinationTwo = new WinCombination();
        winCombinationTwo.setRewardMultiplier(BigDecimal.valueOf(2));

        WinCombination winCombinationThree = new WinCombination();
        winCombinationThree.setRewardMultiplier(BigDecimal.valueOf(3));

        Symbol bonusExtra = new Symbol(null, "bonus", ImpactType.EXTRA_BONUS, BigDecimal.valueOf(1000));
        Symbol bonusMultiply = new Symbol(BigDecimal.valueOf(5), "bonus", ImpactType.MULTIPLY_REWARD, null);
        Symbol bonusMiss = new Symbol(null, "bonus", ImpactType.MISS, null);

        return Stream.of(
                Arguments.of(new CalculateRewardTestCase(
                        BigDecimal.valueOf(100),
                        Collections.emptyMap(),
                        "",
                        Collections.emptyMap(),
                        BigDecimal.ZERO,
                        "Test with empty winning combinations and empty bonus symbolA"
                )),
                Arguments.of(new CalculateRewardTestCase(
                        BigDecimal.valueOf(100),
                        Map.of("A", List.of(winCombinationOne)),
                        "",
                        Map.of("A", symbolA),
                        BigDecimal.valueOf(300.0),
                        "Test with single winning combination for single symbol and empty bonus"
                )),
                Arguments.of(new CalculateRewardTestCase(
                        BigDecimal.valueOf(100),
                        Map.of("A", List.of(winCombinationOne)),
                        "MISS",
                        Map.of("A", symbolA, "MISS", bonusMiss),
                        BigDecimal.valueOf(300.0),
                        "Test with single winning combination for single symbol and MISS bonus"
                )),
                Arguments.of(new CalculateRewardTestCase(
                        BigDecimal.valueOf(100),
                        Map.of("A", List.of(winCombinationOne)),
                        "+1000",
                        Map.of("A", symbolA, "+1000", bonusExtra),
                        BigDecimal.valueOf(1300.0),
                        "Test with single winning combination for single symbol and extra bonus"
                )),
                Arguments.of(new CalculateRewardTestCase(
                        BigDecimal.valueOf(100),
                        Map.of("A", List.of(winCombinationOne)),
                        "x5",
                        Map.of("A", symbolA, "x5", bonusMultiply),
                        BigDecimal.valueOf(1500.0),
                        "Test with single winning combination for single symbol and multiply bonus"
                )),
                Arguments.of(new CalculateRewardTestCase(
                        BigDecimal.valueOf(100),
                        Map.of("A", List.of(winCombinationOne, winCombinationTwo, winCombinationThree)),
                        "",
                        Map.of("A", symbolA),
                        BigDecimal.valueOf(1800.0),
                        "Test with multiple winning combinations for single symbol and empty bonus"
                )),
                Arguments.of(new CalculateRewardTestCase(
                        BigDecimal.valueOf(100),
                        Map.of("A", List.of(winCombinationOne, winCombinationTwo, winCombinationThree)),
                        "MISS",
                        Map.of("A", symbolA, "MISS", bonusMiss),
                        BigDecimal.valueOf(1800.0),
                        "Test with multiple winning combinations for single symbol and MISS bonus"
                )),
                Arguments.of(new CalculateRewardTestCase(
                        BigDecimal.valueOf(100),
                        Map.of("A", List.of(winCombinationOne, winCombinationTwo),
                               "B", List.of(winCombinationThree)),
                        "MISS",
                        Map.of("A", symbolA,
                               "B", symbolB,
                               "MISS", bonusMiss),
                        BigDecimal.valueOf(1500.0),
                        "Test with multiple winning combinations for multiple symbols and MISS bonus"
                )),
                Arguments.of(new CalculateRewardTestCase(
                        BigDecimal.valueOf(100),
                        Map.of("A", List.of(winCombinationOne, winCombinationTwo),
                               "B", List.of(winCombinationThree)),
                        "+1000",
                        Map.of("A", symbolA,
                               "B", symbolB,
                               "+1000", bonusExtra),
                        BigDecimal.valueOf(2500.0),
                        "Test with multiple winning combinations for multiple symbols and extra bonus"
                )),
                Arguments.of(new CalculateRewardTestCase(
                        BigDecimal.valueOf(100),
                        Map.of("A", List.of(winCombinationOne, winCombinationTwo),
                               "B", List.of(winCombinationThree)),
                        "x5",
                        Map.of("A", symbolA,
                               "B", symbolB,
                               "x5", bonusMultiply),
                        BigDecimal.valueOf(7500.0),
                        "Test with multiple winning combinations for multiple symbols and multiply bonus"
                ))
        );
    }

    private static class CalculateRewardTestCase {
        private final BigDecimal bettingAmount;
        private final Map<String, List<WinCombination>> appliedWinCombinations;
        private final String bonusSymbol;
        private final Map<String, Symbol> symbols;
        private final BigDecimal expectedReward;
        private final String testName;

        public CalculateRewardTestCase(BigDecimal bettingAmount, Map<String, List<WinCombination>> appliedWinCombinations,
                                       String bonusSymbol, Map<String, Symbol> symbols, BigDecimal expectedReward, String testName) {
            this.bettingAmount = bettingAmount;
            this.appliedWinCombinations = appliedWinCombinations;
            this.bonusSymbol = bonusSymbol;
            this.symbols = symbols;
            this.expectedReward = expectedReward;
            this.testName = testName;
        }

        public BigDecimal getBettingAmount() {
            return bettingAmount;
        }

        public Map<String, List<WinCombination>> getAppliedWinCombinations() {
            return appliedWinCombinations;
        }

        public String getBonusSymbol() {
            return bonusSymbol;
        }

        public Map<String, Symbol> getSymbols() {
            return symbols;
        }

        public BigDecimal getExpectedReward() {
            return expectedReward;
        }

        public String getTestName() {
            return testName;
        }

        @Override
        public String toString() {
            return testName;
        }
    }
}
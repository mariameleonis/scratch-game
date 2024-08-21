import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import config.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import outcome.GameOutcome;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ScratchGameTest {

    private Config config;

    @BeforeEach
    void setUp() throws IOException {
        ObjectMapper objectMapper = JsonMapper.builder()
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                .build();
        config = objectMapper.readValue(getClass().getClassLoader().getResource("testconfig.json"), Config.class);
        config.winCombinations().forEach((key, value) -> value.setName(key));
    }

    @Test
    void play() {
        ScratchGame game = new ScratchGame(config);

        GameOutcome outcome = game.play(BigDecimal.valueOf(100));

        assertNotNull(outcome);
        assertNotNull(outcome.matrix());
        assertEquals(config.rows(), outcome.matrix().length);
        assertEquals(config.columns(), outcome.matrix()[0].length);
        assertNotNull(outcome.reward());
        assertEquals(outcome.reward().signum() == 0, outcome.winningCombinations() == null);
        assertEquals(outcome.reward().signum() == 0, outcome.bonusSymbol() == null);
    }
}
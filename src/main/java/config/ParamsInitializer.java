package config;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ParamsInitializer {

    private static final String HELP_TEXT = String.join("\n",
                                                        "Usage: java -jar scratch_game.jar [options]",
                                                        "Options:",
                                                        "  --config <path>            Path to config file",
                                                        "  --betting-amount <amount>  Betting amount"
    );
    public static final String OPTION_PREFIX = "--";


    public static Params init(String[] args) throws IOException {
        if (args.length != 4) {
            printHelp();
        }

        Map<String, String> arguments = parseArguments(args);

        String configFilePath = validateFilePath(arguments.get("config"));

        Config config = getConfig(configFilePath);
        populateWinCombinationWithNames(config.winCombinations());
        BigDecimal bettingAmount = validateBettingAmount(arguments.get("betting-amount"));

        return new Params(config, bettingAmount);
    }

    private static void populateWinCombinationWithNames(Map<String, WinCombination> winCombinationMap) {
        winCombinationMap.forEach((key, value) -> value.setName(key));
    }

    private static Config getConfig(String configPath) throws IOException {
        ObjectMapper objectMapper = JsonMapper.builder()
                                              .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                                              .build();
        return objectMapper.readValue(new File(configPath), Config.class);
    }

    private static void printHelp() {
        System.out.println(HELP_TEXT);
    }

    private static Map<String, String> parseArguments(String[] args) {
        Map<String, String> arguments = new HashMap<>();

        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith(OPTION_PREFIX)) {
                String key = args[i].substring(2);
                if (i + 1 < args.length && !args[i + 1].startsWith(OPTION_PREFIX)) {
                    arguments.put(key, args[i + 1]);
                } else {
                    arguments.put(key, null);
                }
            }
        }

        return arguments;
    }

    private static String validateFilePath(String value) {
        return value;
    }

    private static BigDecimal validateBettingAmount(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Betting amount cannot be null.");
        }

        try {
            BigDecimal amount = new BigDecimal(value);
            if (amount.signum() > 0) {
                return amount;
            }
        } catch (NumberFormatException ignored) {
            // Ignored because we'll throw an exception below if the format is wrong.
        }
        throw new IllegalArgumentException("Betting amount must be a valid number greater than zero.");
    }

}

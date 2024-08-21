package outcome;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GameOutcomePrinter {
    public static void print(GameOutcome result) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonOutput = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        System.out.println(jsonOutput);
    }
}

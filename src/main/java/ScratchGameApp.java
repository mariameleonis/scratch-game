import outcome.GameOutcome;
import outcome.GameOutcomePrinter;
import config.Params;
import config.ParamsInitializer;

public class ScratchGameApp {

    public static void main(String[] args) {
        try {
            Params params = ParamsInitializer.init(args);
            GameOutcome result = new ScratchGame(params.config()).play(params.bettingAmount());
            GameOutcomePrinter.print(result);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}

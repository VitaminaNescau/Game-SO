import java.util.logging.Logger;

public class Debug {
    public static void logInfo(String startingGame) {
        Logger.getLogger("INFO").info(startingGame);
    }
    public static void logWarning(String startingGame) {
        Logger.getLogger("WARNING").warning(startingGame);
    }
}

package utils;

import java.util.logging.Logger;

public class Debug {

    public static void logInfo(String message) {

        Logger.getGlobal().info(message);
    }
    public static void logError(String message) {
        Logger.getGlobal().severe(message);
    }

}

package sr.pago.sdk.utils;

import android.util.Log;

import java.util.ArrayList;

import sr.pago.sdk.definitions.Definitions;

/**
 * Created by Rodolfo on 21/09/2015.
 */
public class Logger {
    private static boolean LOG = true;

    public static void logDebug(String title, String message) {
        if (LOG) {
            for (String toPrint : wrapString(message)) {
                Log.d(Definitions.SR_PAGO() + "_" + title, toPrint);
            }
        }
    }

    public static void logMessage(String title, String message) {
        if (LOG) {
            for (String toPrint : wrapString(message)) {
                Log.d(Definitions.SR_PAGO() + "_" + title, toPrint);
            }
        }
    }

    public static void logWarning(String title, String message) {
        if (LOG) {
            for (String toPrint : wrapString(message)) {
                Log.e(Definitions.SR_PAGO() + "_" + title, toPrint);
            }
        }
    }

    public static void logError(Exception ex) {
        if (LOG) {
            Log.e("Error", "error", ex);
        }
    }

    public static void enableLog(boolean isAllowed) {
        LOG = isAllowed;
    }

    private static ArrayList<String> wrapString(String message) {
        if (message == null) {
            return new ArrayList<>();
        }

        int maxLogSize = 999;

        ArrayList<String> messages = new ArrayList<>();

        for (int i = 0; i <= message.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i + 1) * maxLogSize;
            end = end > message.length() ? message.length() : end;
            messages.add(message.substring(start, end));
        }
        return messages;
    }
}

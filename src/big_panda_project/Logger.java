package big_panda_project;

// class for debug logging

public class Logger {

    public static void sendLog(int logLevel, String logMessage) {
        if (logLevel < Globals.getLogLevel()) {
            return;
        }
        System.out.println(logMessage);
    }

    public static String checkBinaryContent(int logLevel, String text) {
        if (logLevel < Globals.getLogLevel()) {
            return text;
        }

        long start = System.nanoTime();

        String clean = cleanTextContent(text);

        long end = System.nanoTime();
        long microseconds = (end - start) / 1000;
        Logger.sendLog(Globals.verbose, "cleanTextContent took " +
                microseconds + " microseconds");

        return clean;
    }

    private static String cleanTextContent(String text) {
        // strips off all non-ASCII characters
        text = text.replaceAll("[^\\x00-\\x7F]", "");

        // erases all the ASCII control characters
        text = text.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");

        // removes non-printable characters from Unicode
        text = text.replaceAll("\\p{C}", "");

        return text.trim();
    }
}
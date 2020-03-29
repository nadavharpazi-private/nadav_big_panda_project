public class Logger {

    public static void sendLog(int logLevel, String logMessage) {
        if (logLevel < Globals.activeLogLevel) {
            return;
        }
        System.out.println(logMessage);
    }

    public static String checkBinaryContent(int logLevel, String text) {
        if (logLevel < Globals.activeLogLevel) {
            return text;
        }
        String cleaned = cleanTextContent(text);
        if(!cleaned.equals(text)) {
            return "contains non-printable characters.";
        }
        return text;

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
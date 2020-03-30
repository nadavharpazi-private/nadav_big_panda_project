import com.fasterxml.jackson.core.JsonParseException;

import java.io.IOException;
import java.util.Map;
import java.util.Timer;

public class InputHandler {

    private final static String logOfValid = "handling input line: ==> a valid json: ";
    private final static String logInvalid = "handling input line: ==> invalid json: ";

    private final JsonParser jsonParser;
    private final SystemCounters systemCounter;

    public InputHandler(final SystemCounters systemCounter) {
        this.jsonParser = new JsonParser();
        this.systemCounter = systemCounter;
    }

    void handleLine(final String inputLine) {
        systemCounter.incrementTotalLines();
        boolean isValid = true;
        Map<String, String> jsonMap = null;

        long start = System.nanoTime();
        try {

            jsonMap = jsonParser.parseInputLine(inputLine);

        } catch (IOException exp) {
            isValid = false;

        } finally {
            long end = System.nanoTime();
            long microseconds = (end - start) / 1000;
            handleValidOrInvalid(jsonMap, isValid, inputLine, microseconds);
        }
    }

    private void handleValidOrInvalid(final Map<String, String> jsonMap, final boolean isValid,
                                      final String inputLine, final long microseconds){

        inputLogger(false, inputLine, microseconds);
        if (isValid) {
            handleValidInput(jsonMap, inputLine, microseconds);
            return;
        }
        handleInvalidInput(inputLine, microseconds);
    }

    private void handleValidInput(final Map<String, String> jsonMap,
                                  final String inputLine, final long microseconds) {
        String eventType = jsonParser.parseEventType(jsonMap);
        systemCounter.updateEventCount(eventType);

        String [] words = jsonParser.parseWords(jsonMap);
        systemCounter.updateWordCount(words);
        systemCounter.incrementValidJson();
    }

    private void handleInvalidInput(final String inputLine, final long microseconds) {
//        String cleaned = Logger.checkBinaryContent(Globals.debug, inputLine);
        systemCounter.incrementInvalidJson();
    }

    private void inputLogger(final boolean isValid, final String inputLine, final long microseconds) {
        String header = isValid ? logOfValid : logInvalid;
        Logger.sendLog(Globals.debug, header + "(parse time: " + microseconds + " microseconds)" + inputLine);
    }

}

import com.fasterxml.jackson.core.JsonParseException;

import java.io.IOException;
import java.util.Map;
import java.util.Timer;

public class InputHandler {

    private final static String header = "handling input line: ";
    private final static String logOfValid = " ==> a valid json: ";
    private final static String logInvalid = " ==> invalid json: ";

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
            inputLogger(isValid, inputLine, microseconds);
            handleValidOrInvalid(jsonMap, isValid);
        }
    }

    private void handleValidOrInvalid(final Map<String, String> jsonMap, final boolean isValid){
        if (isValid) {
            handleValidInput(jsonMap);
            return;
        }
        handleInvalidInput();
    }

    private void handleValidInput(final Map<String, String> jsonMap) {
        String eventType = jsonParser.parseEventType(jsonMap);
        systemCounter.updateEventCount(eventType);

        String [] words = jsonParser.parseWords(jsonMap);
        systemCounter.updateWordCount(words);
        systemCounter.incrementValidJson();
    }

    private void handleInvalidInput() {
//        String cleaned = Logger.checkBinaryContent(Globals.debug, inputLine);
        systemCounter.incrementInvalidJson();
    }

    private void inputLogger(final boolean isValid, final String inputLine, final long microseconds) {
        long lineNumber = systemCounter.getTotalLinesCounter();
        String log = header + "#" + lineNumber + (isValid ? logOfValid : logInvalid);

        Logger.sendLog(Globals.debug, log + "(parse time: " + microseconds + " microseconds)" + inputLine);
    }

}

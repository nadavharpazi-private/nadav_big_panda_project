import com.fasterxml.jackson.core.JsonParseException;

import java.io.IOException;
import java.util.Map;

public class InputHandler {

    private final static String logOfValid = "handling current line from input: ==> got a valid json: ";
    private final static String logInvalid = "handling current line from input: ==> got invalid json: ";

    private final JsonParser jsonParser;
    private final SystemCounters systemCounter;

    public InputHandler(final SystemCounters systemCounter) {
        this.jsonParser = new JsonParser();
        this.systemCounter = systemCounter;
    }

    void handleLine(final String inputLine) {
        systemCounter.incrementTotalLines();
        try {

            Map<String, String> jsonMap = jsonParser.parseInputLine(inputLine);
            handleValidInput(jsonMap, inputLine);

        } catch (IOException exp) {
            handleInvalidInput(inputLine);
        }
    }

    void handleValidInput(final Map<String, String> jsonMap, final String inputLine) {
        Logger.sendLog(Globals.debug, logOfValid + inputLine);

        String eventType = jsonParser.parseEventType(jsonMap);
        systemCounter.updateEventCount(eventType);

        String [] words = jsonParser.parseWords(jsonMap);
        systemCounter.updateWordCount(words);
        systemCounter.incrementValidJson();
    }

    void handleInvalidInput(final String inputLine) {
        String cleaned = Logger.checkBinaryContent(Globals.debug, inputLine);
        if(!cleaned.equals(inputLine)) {
            cleaned = "contains non-printable characters.";
        } else {
            cleaned = inputLine;
        }
        Logger.sendLog(Globals.debug, logInvalid + cleaned);
        systemCounter.incrementInvalidJson();
    }
}

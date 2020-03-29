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

    protected void handleLine(String inputLine) {
        systemCounter.incrementTotalLines();
        try {

            Map<String, String> jsonMap = jsonParser.parseInputLine(inputLine);
            handleValidInput(jsonMap, inputLine);

        } catch (IOException exp) {
            handleInvalidInput(inputLine);
        }
    }

    protected void handleValidInput(Map<String, String> jsonMap, String inputLine) {
        Logger.sendLog(Globals.debug, logOfValid + inputLine);

        String eventType = jsonParser.parseEventType(jsonMap);
        systemCounter.updateEventCount(eventType);

        String [] words = jsonParser.parseWords(jsonMap);
        systemCounter.updateWordCount(words);
        systemCounter.incrementValidJson();
    }

    protected void handleInvalidInput(String inputLine) {
        inputLine = Logger.checkBinaryContent(Globals.debug, inputLine);
        Logger.sendLog(Globals.debug, logInvalid + inputLine);
        systemCounter.incrementInvalidJson();
    }
}

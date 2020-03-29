import java.io.*;
import java.util.Map;

public class InputReader {

    protected final Process process;
    protected final JsonParser jsonParser;
    protected final SystemCounters systemCounter;

    public InputReader(final Process process, final JsonParser jsonParser, final SystemCounters systemCounter) {
        this.process = process;
        this.jsonParser = jsonParser;
        this.systemCounter = systemCounter;
    }

    public void readInput() throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        try {
            while ((line = input.readLine()) != null) {
                handleInputLine(line);
            }
        } catch (EOFException eof) {
            Logger.sendLog(Globals.info,"end of input");
        }
        input.close();
    }

    protected void handleInputLine(String inputLine) throws IOException {
        systemCounter.incrementTotalLines();
        Map<String, String> jsonMap = jsonParser.parseInputLine(inputLine);
        if (jsonMap == null) {
            handleInvalidInput();
            return;
        }
        handleValidInput(jsonMap);
    }

    protected void handleValidInput(Map<String, String> jsonMap) {
        String eventType = jsonParser.parseEventType(jsonMap);
        systemCounter.updateEventCount(eventType);

        String [] words = jsonParser.parseWords(jsonMap);
        systemCounter.updateWordCount(words);
        systemCounter.incrementValidJson();
    }

    protected void handleInvalidInput() {
        systemCounter.incrementInvalidJson();
    }

}

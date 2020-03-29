import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;

public class JsonParser {

    final ObjectMapper mapper = new ObjectMapper();

    protected Map<String, String> parseInputLine(String inputLine) throws IOException {
        // convert JSON string to Map
        String log = "handling current line from input: ";
        Map<String, String> jsonMap;

        try {

            jsonMap = mapper.readValue(inputLine, Map.class);

        } catch (JsonParseException exp) {
            inputLine = Logger.checkBinaryContent(Globals.debug, inputLine);
            Logger.sendLog(Globals.debug, log + " ==> got invalid json: " + inputLine);
            return null;
        }

        log += " ==> got a valid json: ";
        Logger.sendLog(Globals.debug, log + inputLine);
        return jsonMap;
    }

    protected String parseEventType(Map<String, String> jsonMap) {
        return jsonMap.get(Globals.eventTypeField);
    }

    protected String[] parseWords(Map<String, String> jsonMap)  {
        String words = jsonMap.get(Globals.dataField);
        if (words == null) {
            return null;
        }
        return words.split(Globals.dataDelimiter);
    }


}

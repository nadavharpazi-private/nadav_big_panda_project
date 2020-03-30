package big_panda_project;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;

// this class parses the json string using jackson-core package, into a Map of key-values from the json.
// on parsing failure it throws an exception

public class JsonParser {

    final ObjectMapper mapper = new ObjectMapper();

    Map<String, String> parseInputLine(String inputLine) throws IOException {
        // convert JSON string to Map
        return mapper.readValue(inputLine, Map.class);
    }

    String parseEventType(Map<String, String> jsonMap) {
        return jsonMap.get(Globals.eventTypeField);
    }

    String[] parseWords(Map<String, String> jsonMap)  {
        String words = jsonMap.get(Globals.dataField);
        if (words == null) {
            return null;
        }
        return words.split(Globals.dataDelimiter);
    }


}

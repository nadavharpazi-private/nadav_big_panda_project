package big_panda_project;

import big_panda_project.JsonParser;
import com.fasterxml.jackson.core.JsonParseException;
import org.junit.jupiter.api.Assertions;
import java.io.IOException;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class JsonParserTest {

    String validInput = "{\"event_type\": \"baz\", \"data\": \"amet\", \"timestamp\": 1585320572}";
    String validInputManyWords = "{\"event_type\": \"baz\", \"data\": \"amet mamet bamet yyy ttt 567kj7 mnlkdf9\", \"timestamp\": 1585320572}";
    String invalidMissingEvenType = "{\"event_typ\": \"baz\", \"data\": \"amet\", \"timestamp\": 1585320572}";
    String invalidMissingData = "{\"event_type\": \"baz\", \"dat\": \"amet\", \"timestamp\": 1585320572}";
    String validJsonInvalidFields = "{\"event_typesasdasd\": \"baz\", \"datafsd\": \"adf53453met\",  \"timestamp\": 1585320572}";
    String justTestJson = "{\"name\":\"lili\", \"age\":\"37\"}";
    String invalidString1 = "{s'lm';sdm;lkdmfglkdmfg}";
    String invalidString2 = "{\"name\":\"dfggglili\"\"ageASD\":::\"3asd7\"}";
    String invalidString3 = "{FM;LKSMLKSMNF;KLMNL;KMSDFS9034N5L;3VSC#$%$namedfggglili\"\"ageASD\":::\"3asd7\"}";

    final JsonParser jsonParser = new JsonParser();

    @org.junit.jupiter.api.Test
    public void parseInputJsonTest() throws IOException {
        String [] validJsons = new String[]{
                validInput, invalidMissingData, invalidMissingEvenType,
                validJsonInvalidFields, justTestJson};
        for (String json : validJsons) {
            Map<String, String> jsonMap = jsonParser.parseInputLine(json);
            assertNotNull(jsonMap);
        }
    }

    @org.junit.jupiter.api.Test
    public void parseInputJsonTest_invalid_strings_should_throw_exception() {
        String [] invalidJsons = new String[]{invalidString1, invalidString2, invalidString3};
        for (String json : invalidJsons) {
            Assertions.assertThrows(JsonParseException.class, () -> jsonParser.parseInputLine(json));
        }
    }

    @org.junit.jupiter.api.Test
    public void parseEventTypeTest_valid_json() throws IOException {
        Map<String, String> jsonMap = jsonParser.parseInputLine(validInput);
        String eventType = jsonParser.parseEventType(jsonMap);
        assertEquals("baz", eventType);
    }

    @org.junit.jupiter.api.Test
    public void parseEventTypeTest_valid_json_missing_data() throws IOException {
        Map<String, String> jsonMap = jsonParser.parseInputLine(invalidMissingData);
        String eventType = jsonParser.parseEventType(jsonMap);
        assertEquals("baz", eventType);
    }

    @org.junit.jupiter.api.Test
    public void parseEventTypeTest_valid_json_missing_event_type() throws IOException {
        Map<String, String> jsonMap = jsonParser.parseInputLine(invalidMissingEvenType);
        String eventType = jsonParser.parseEventType(jsonMap);
        assertNull(eventType);
    }

    @org.junit.jupiter.api.Test
    public void parseEventTypeTest_valid_json_invalid_fields() throws IOException {
        Map<String, String> jsonMap = jsonParser.parseInputLine(validJsonInvalidFields);
        String eventType = jsonParser.parseEventType(jsonMap);
        assertNull(eventType);
    }

    @org.junit.jupiter.api.Test
    public void parseWordsTest_valid_data_1_word() throws IOException {
        Map<String, String> jsonMap = jsonParser.parseInputLine(validInput);
        String[] words = jsonParser.parseWords(jsonMap);
        assertEquals(1, words.length);
        assertEquals("amet", words[0]);
    }

    @org.junit.jupiter.api.Test
    public void parseWordsTest_valid_data_with_many_words() throws IOException {
        Map<String, String> jsonMap = jsonParser.parseInputLine(validInputManyWords);
        String[] words = jsonParser.parseWords(jsonMap);
        assertEquals(7, words.length);
        assertEquals("amet", words[0]);
        assertEquals("mamet", words[1]);
        assertEquals("bamet", words[2]);
        assertEquals("yyy", words[3]);
        assertEquals("ttt", words[4]);
        assertEquals("567kj7", words[5]);
        assertEquals("mnlkdf9", words[6]);
    }

    @org.junit.jupiter.api.Test
    public void parseWordsTest_invalid_data() throws IOException {
        String [] validJsonsMissingData = new String[]{
                invalidMissingData, validJsonInvalidFields, justTestJson};

        for (String json : validJsonsMissingData) {
            Map<String, String> jsonMap = jsonParser.parseInputLine(validInput);
            String [] words = jsonParser.parseWords(jsonMap);
        }
    }
}
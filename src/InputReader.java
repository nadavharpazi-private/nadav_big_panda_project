import java.io.*;

public class InputReader {

    protected final Process process;
    protected final JsonParser jsonParser;
    protected final SystemCounters systemCounter;
    protected final InputHandler inputHandler;

    public InputReader(final Process process, final JsonParser jsonParser, final SystemCounters systemCounter) {
        this.process = process;
        this.jsonParser = jsonParser;
        this.systemCounter = systemCounter;
        this.inputHandler = new InputHandler(jsonParser, systemCounter);
    }

    public void readInput() throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        try {
            while ((line = input.readLine()) != null) {
                inputHandler.handleLine(line);
            }
        } catch (EOFException eof) {
            Logger.sendLog(Globals.info,"end of input");
        }
        input.close();
    }

}

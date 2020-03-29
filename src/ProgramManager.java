import java.io.IOException;

public class ProgramManager {

    private final InputReader inputReader;

    public ProgramManager(int httpPort) throws IOException {
        GeneratorRunner generatorRunner = new GeneratorRunner();
        Process process = generatorRunner.runGenerator();
        JsonParser jsonParser = new JsonParser();
        SystemCounters systemCounter = new SystemCounters();
        HttpPublisher httpPublisher = new HttpPublisher(httpPort, systemCounter);
        this.inputReader = new InputReader(process, jsonParser, systemCounter);
    }

    private void readInput() {
        try {
            inputReader.readInput();
        } catch (IOException exp) {
            Logger.sendLog(Globals.critical, "failed to read input from process, got IOException: ");
            exp.printStackTrace();
        }
    }

    void startWorking() {
        readInput();
    }

}
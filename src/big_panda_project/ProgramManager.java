package big_panda_project;

// the manager of the program: start and runs all the core objects of the program
// the input generator, the input reader thread, the http server and the System Counters

import java.io.IOException;

public class ProgramManager {

    private final InputReader inputReader;

    public ProgramManager(int httpPort) throws IOException {
        GeneratorRunner generatorRunner = new GeneratorRunner();
        Process process;
        try {
            process = generatorRunner.runGenerator();
        } catch (IOException e) {
            Logger.sendLog(Globals.critical, "failed to run generator. existing.");
            throw e;
        }
        SystemCounters systemCounter = new SystemCounters();
        this.inputReader = new InputReader(process, systemCounter);
        try {
            new HttpPublisher(httpPort, systemCounter);
        } catch (IOException e) {
            Logger.sendLog(Globals.critical, "failed to run http server. existing.");
            throw e;
        }
    }

    private void readInput() {
        try {
            Thread readerThread = new Thread(inputReader);
            Logger.sendLog(Globals.info, "running reader thread:");
            readerThread.start();
        } catch (Exception exp) {
            Logger.sendLog(Globals.critical, "failed to read input from process, Exception: ");
            exp.printStackTrace();
        }
    }

    void startWorking() {
        readInput();
    }

}
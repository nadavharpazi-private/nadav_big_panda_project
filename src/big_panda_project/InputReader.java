package big_panda_project;

import java.io.*;

public class InputReader implements Runnable {

    private final Process process;
    private final InputHandler inputHandler;

    public InputReader(final Process process, final SystemCounters systemCounter) {
        this.process = process;
        this.inputHandler = new InputHandler(systemCounter);
    }

    @Override
    public void run() {

        Logger.sendLog(Globals.info, "==> reader thread: running.");

        InputStreamReader fileInputStream = new InputStreamReader(process.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(fileInputStream);
        boolean closing = false;

        Logger.sendLog(Globals.info, "==> reader thread: start to read input.");

        try {
            while (!Thread.interrupted()) {
                if (!bufferedReader.ready()) {
                    continue;
                }

                String inputLine = bufferedReader.readLine();
                if (inputLine == null) {
                    closing = true;
                    break;
                }
                inputHandler.handleLine(inputLine);

            }
        } catch (IOException e) {
            Logger.sendLog(Globals.warn, "got IO Exception. closing");
            closing = true;

        } finally {
            if (closing) {
                Logger.sendLog(Globals.info, "end of input. closing");
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    Logger.sendLog(Globals.warn, "got IO exception when trying to close the input.");
                }
            }
        }
    }
}

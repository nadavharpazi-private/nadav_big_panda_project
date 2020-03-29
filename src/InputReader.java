import java.io.*;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class InputReader implements Runnable {

    protected final Process process;
    protected final SystemCounters systemCounter;
    protected final InputHandler inputHandler;

    public InputReader(final Process process, final SystemCounters systemCounter) {
        this.process = process;
        this.systemCounter = systemCounter;
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
                if (bufferedReader.ready()) {
                    String inputLine = bufferedReader.readLine();
                    if (inputLine == null) {
                        closing = true;
                        break;
                    }
                    inputHandler.handleLine(inputLine);
                }
            }
        } catch (IOException e) {
            closing = true;
        } finally {
            if (closing) {
                Logger.sendLog(Globals.info,"end of input. closing");
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    Logger.sendLog(Globals.error,"got exception when closing the input.");
                }
            }
        }
    }
}

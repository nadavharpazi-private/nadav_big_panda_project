import java.io.IOException;

public class TheMain {

    private static int logLevel = Globals.debug;
    private static int httpPort = Globals.defaultHttpPort;

    private static void parserArgs(String [] args) {
        if (args.length < 2) {
            return;
        }
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "log_level":
                    if (args.length > i + 1) {
                        String logLevelStr = args[i + 1];
                        logLevel = Integer.parseInt(logLevelStr);
                    }
                case "port":
                    if (args.length > i + 1) {
                        String portStr = args[i + 1];
                        httpPort = Integer.parseInt(portStr);
                    }
            }
        }
    }

    public static void main(String[] args) {
        parserArgs(args);
        Globals.activeLogLevel = logLevel;
        Logger.sendLog(Globals.info, "program started");
        Logger.sendLog(Globals.info, ("log level: " + Globals.activeLogLevel));
        Logger.sendLog(Globals.info, ("http port: " + httpPort));

        try {
            ProgramManager myProgramManager = new ProgramManager(httpPort);
            myProgramManager.startWorking();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.sendLog(Globals.info,"going down..");
    }
}

package big_panda_project;

public class Globals
{
    enum OperatingSystem {
        windows,
        mac,
        linux,
        other
    }

    static final int verbose = 0;
    static final int debug = 1;
    static final int info = 2;
    static final int warn = 3;
    static final int error = 4;
    static final int critical = 5;
    static final int defaultHttpPort = 9000;
    static int activeLogLevel = debug;

    static final String eventTypeField = "event_type";
    static final String dataField = "data";
    static final String dataDelimiter = " ";

    private static final String OS = System.getProperty("os.name").toLowerCase();
    private static boolean isMac() {
        return (OS.contains("mac"));
    }
    private static boolean isLinux() {
        return (OS.contains("linux"));
    }
    private static boolean isWindows() {
        return (OS.contains("win"));
    }

    public static OperatingSystem getCurrentOS() {

        Logger.sendLog(Globals.info, "operating system: " + OS);

        if (isWindows()) {
            Logger.sendLog(Globals.info, "This is a Windows operating system.");
            return OperatingSystem.windows;
        } else if (isMac()) {
            Logger.sendLog(Globals.info, "This is a Mac operating system.");
            return OperatingSystem.mac;
        } else if (isLinux()) {
            Logger.sendLog(Globals.info, "This is a Linux operating system.");
            return OperatingSystem.linux;
        } else {
            Logger.sendLog(Globals.critical, "this operating system: " + OS + " is not supported!");
            throw new RuntimeException();
        }
    }


}

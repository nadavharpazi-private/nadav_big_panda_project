import java.io.IOException;

public class GeneratorRunner {
    protected static final String localPath = ".";
    protected static final String slashWindows = "\\";
    protected static final String slashMacLinux = "/";

    protected static final String generatorFolder = "test_generators";
    protected static final String generatorLinux = "generator-linux-amd64";
    protected static final String generatorForMac = "generator-macosx-amd64";
    protected static final String generatorWindows = "generator-windows-amd64.exe";

    protected static final String locationWindows = localPath + slashWindows + generatorFolder + slashWindows;
    protected static final String locationMacLinux = localPath + slashMacLinux + generatorFolder + slashMacLinux;
    protected static final String generatorPathLinux = locationMacLinux + generatorLinux;
    protected static final String generatorPathForMac = locationMacLinux + generatorForMac;
    protected static final String generatorPathWindows = locationWindows + generatorWindows;

    final private String actualPath;

    public GeneratorRunner() {
        this.actualPath = getGeneratorPath();
    }

    protected String getGeneratorPath() {
        Globals.OperatingSystem operatingSystem = Globals.getCurrentOS();
        switch (operatingSystem) {
            case mac:
                return generatorPathForMac;
            case linux:
                return generatorPathLinux;
            case windows:
                return generatorPathWindows;
            default:
                return "not supported.";
        }
    }

    protected Process runGenerator() throws IOException {
        Process process;
        Logger.sendLog(Globals.info, "about to run generator: " + actualPath + "...");
        try {
            process = Runtime.getRuntime().exec(actualPath);
        } catch (IOException e) {
            Logger.sendLog(Globals.critical, "failed to run generator: " + actualPath);
            throw e;
        }
        Logger.sendLog(Globals.info, "==> succeeded to run generator.");
        return process;
    }

}

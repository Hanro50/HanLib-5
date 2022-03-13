package za.net.hanro50.util.log;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import za.net.hanro50.util.file.Config;

@LogAvoid
public class Settings extends Config implements Provider {
    private static Map<String, String> pluginMap = new HashMap<>();
    private static Logger logger;
    @Option(name = "Debug Mode", comment = "Launches Hanlib in debug mode")
    boolean debugMode = false;
    @Option(name = "Debug Format", comment = "States the debug format Hanlib uses")
    String debugFormat = "[${source}/${class}:${line}] ${message}";
    @Option(name = "Normal Format", comment = "States the normal format Hanlib uses when debugging is disable.\nUsed to cut down on console spam")
    String normformat = "[${source}] ${message}";

    public static void Register(String pluginName) {
        pluginMap.put(new Meta().codeSource, pluginName);
        Console.out("Registered new hook for: " + pluginName);
    }

    private String format(Meta meta, String mes) {
        return (debugMode ? debugFormat : normformat)
                .replace("${source}", pluginMap.getOrDefault(meta.codeSource, meta.codeSource))
                .replace("${line}", String.valueOf(meta.line))
                .replace("${class}", String.valueOf(meta.clazzName))
                .replace("${message}", mes);
    }

    public Settings(File file, Logger logger) {
        super(file);
        Console.provider = this;
        Settings.logger = logger;
    }

    @Override
    public void out(Meta metdata, Object... out) {
        for (Object object : out) {
            logger.info(format(metdata, object.toString()));
        }
    }

    @Override
    public void err(Meta metdata, Object... err) {
        for (Object object : err) {
            logger.warning(format(metdata, object.toString()));
        }
    }

    @Override
    public void crt(Meta metdata, Object... err) {
        err(metdata, "[!!!CRITICAL ERROR!!!] THINGS ARE ABOUT TO GO HORRIFICALLY WRONG.");
        for (Object object : err) {
            logger.warning(format(metdata, object.toString()));
        }
    }

    @Override
    public void trc(Meta metdata, Throwable err, Object... trc) {
        err(metdata, "[Starting Trace Log]");
        for (Object object : trc) {
            logger.warning(format(metdata, object.toString()));
        }
        for (StackTraceElement STE : err.getStackTrace()) {
            logger.warning(format(new Meta(STE), "Step trace..."));
        }

    }

}

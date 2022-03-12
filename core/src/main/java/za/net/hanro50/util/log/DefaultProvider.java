package za.net.hanro50.util.log;

class DefaultProvider implements Provider {

    @Override
    public void out(Meta metdata, Object... out) {
        for (Object object : out) {
            System.out.println(
                    "[info][" + metdata.clazzName + ":" + metdata.line + "] " + object);
        }
    }

    @Override
    public void err(Meta metdata, Object... err) {
        for (Object object : err) {
            System.err.println(
                    "[error][" + metdata.codeSource + "," + metdata.clazzName + ":" + metdata.line + "] " + object);
        }
    }

    @Override
    public void crt(Meta metdata, Object... crt) {
        System.err.println("[!!!CRITICAL ERROR!!!] THINGS ARE ABOUT TO GO HORRIFICALLY WRONG.");
        for (Object object : crt) {
            System.err.println(
                    "[CRT][" + metdata.codeSource + "," + metdata.clazzName + ":" + metdata.line + "] " + object);
        }
        System.err.println("[CRT][Trace] Printing unabridged step trace");
        Thread.currentThread().getStackTrace();
        Thread.getAllStackTraces().forEach((k, v) -> {
            System.err.println("[CRT][Trace] THREAD :" + k.getName() + "#" + k.getId());
            for (StackTraceElement trace : v) {
                System.err.println(
                        "[CRT][Trace] " + trace.getMethodName() + "/" + trace.getClassName() + "(" + trace.getFileName()
                                + ":"
                                + trace.getLineNumber() + ")");
            }
        });

        System.err.println("[!!!CRITICAL ERROR!!!] PLEASE REPORT CRASH LOG.");
    }

    @Override
    public void trc(Meta metdata, Throwable err, Object... trc) {
        for (Object object : trc) {
            System.err.println(
                    "[trace][" + metdata.codeSource + "," + metdata.clazzName + ":" + metdata.line + "] " + object);
        }
        System.err.println("[trace][" + metdata.codeSource + "," + metdata.clazzName + ":" + metdata.line + "][header] "
                + err.getMessage());
        for (StackTraceElement trace : err.getStackTrace()) {
            try {
                Class<?> clazz = Class.forName(trace.getClassName());
                String file = null;
                try {
                    file = clazz.getProtectionDomain().getCodeSource().getLocation().getFile();
                    if (file.contains("target/"))
                        file = file.substring(0, file.indexOf("target/")) + "src/"
                                + (file.indexOf("test-classes") > file.indexOf("target/") ? "test/java/" : "main/java/")
                                + trace.getClassName().replace(".", "/");

                    else
                        file = null;
                } catch (NullPointerException e) {

                }
                if (file != null) {
                    System.err.println("[trace] " + trace.getClassName() + trace.getClassName() + "(" +
                            trace.getFileName() + ":"
                            + trace.getLineNumber() + ") #vscode://file" + file + ".java:"
                            + trace.getLineNumber() + ":0");
                } else {
                    System.err.println(
                            "[trace] " + trace.getClassName() + "/" + trace.getClassName() + "(" +
                                    trace.getFileName() + ":"
                                    + trace.getLineNumber() + ")");
                }
            } catch (ClassNotFoundException e) {

                e.printStackTrace();
            }

            // System.err.println(
            // "[trace] " + trace.getClassName() + "/" + trace.getClassName() + "(" +
            // trace.getFileName() + ":"
            // + trace.getLineNumber() + ")");
        }

    }

}

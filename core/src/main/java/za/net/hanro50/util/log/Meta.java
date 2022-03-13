package za.net.hanro50.util.log;

@LogAvoid
final class Meta {
    public final StackTraceElement trace;
    public final String codeSource;
    public final String clazzName;
    public final int line;
    private String file;
    public String getFile() {
        return this.file != null ? this.file : "";
    }
    private static StackTraceElement LastObject() {
        StackTraceElement resultcall = Thread.currentThread().getStackTrace()[1];
        LogAvoid LastAnnon = Meta.class.getAnnotation(LogAvoid.class);
        for (StackTraceElement LastClass : Thread.currentThread().getStackTrace()) {
            try {
                Class<?> LastClassObject = Class.forName(LastClass.getClassName());
                if (!(LastClassObject.isAnnotationPresent(LogAvoid.class)
                        || LastClass.getClassName().equals("java.lang.Thread")
                        || (LastAnnon != null
                                && LastClass.getClassName().startsWith(LastAnnon.Package())
                                && !LastAnnon.Package().equals("")))) {
                    resultcall = LastClass;

                    return resultcall;
                }
                LogAvoid NewAnnon = LastClassObject.getAnnotation(LogAvoid.class);
                LastAnnon = NewAnnon != null ? NewAnnon : LastAnnon;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return resultcall;
    }
    public Meta() {
        this(LastObject());
    }
    public Meta(StackTraceElement trace) {
        this.trace = trace;
        this.line = trace.getLineNumber();
        this.clazzName = trace.getClassName();
        this.codeSource = trace.getFileName();
        if (Console.debug) {
            try {
                Class<?> clazz = Class.forName(trace.getClassName());
                this.file = null;

                this.file = clazz.getProtectionDomain().getCodeSource().getLocation().getFile();
                if (this.file.contains("target/"))
                    this.file = file.substring(0, file.indexOf("target/")) + "src/"
                            + (this.file.indexOf("test-classes") > file.indexOf("target/") ? "test/java/"
                                    : "main/java/")
                            + trace.getClassName().replace(".", "/");
                else
                    this.file = null;

                if (this.file != null) {
                    this.file = "#vscode://file" + file + ".java:" + trace.getLineNumber() + ":0";
                }
            } catch (ClassNotFoundException | NullPointerException e) {
            }
        }
    }
}

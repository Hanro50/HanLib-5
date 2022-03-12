package za.net.hanro50.util.log;

@LogAvoid
public final class Meta {
    public final StackTraceElement lor;
    public final String codeSource;
    public final String clazzName;
    public final int line;

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

    Meta() {
        this.lor = LastObject();
        this.line = lor.getLineNumber();
        this.clazzName = lor.getClassName();
        this.codeSource = lor.getFileName();
    }
}

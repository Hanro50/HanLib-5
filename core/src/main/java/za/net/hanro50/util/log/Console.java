package za.net.hanro50.util.log;

@LogAvoid
public class Console {
    public static boolean debug = false;
    public static Provider provider = new DefaultProvider();

    public static void out(Object... out) {
        provider.out(new Meta(), out);
    }

    public static void log(Object... out) {
        if (debug)
            provider.out(new Meta(), out);
    }

    public static void err(Object... err) {
        provider.err(new Meta(), err);

    }

    public static void wrn(Object... err) {
        if (err == null)
            err = new Object[] { "NULL" };
        if (debug)
            provider.err(new Meta(), err);

    }

    public static void crt(Object... crt) {
        provider.crt(new Meta(), crt);
    }

    public static void trc(Throwable err, Object... trc) {
        provider.trc(new Meta(), err, trc);
    }
}

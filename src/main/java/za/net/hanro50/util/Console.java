package za.net.hanro50.util;

public class Console {
    public static boolean debug = false;

    public static void out(Object... out) {
        if (out == null)
            out = new Object[] { "NULL" };
        for (Object object : out) {
            System.out.println(object);
        }
    }

    public static void log(Object... out) {
        if (out == null)
            out = new Object[] { "NULL" };
        if (debug)
            for (Object object : out) {
                System.out.println(object);
            }
    }

    public static void err(Object... err) {
        if (err == null)
            err = new Object[] { "NULL" };
        for (Object object : err) {
            System.out.println(object);
        }

    }

    public static void wrn(Object... err) {
        if (err == null)
            err = new Object[] { "NULL" };
        if (debug)
            for (Object object : err) {
                System.out.println(object);
            }

    }

    public static void crt(Object... crt) {
        if (crt == null)
            crt = new Object[] { "NULL" };
        System.err.println("[!!!CRITICAL ERROR!!!]");
        System.err.println("This type of error indicates a coding mistake on the part of the programmer!");
        System.err.println("[!!!CRITICAL ERROR!!!]");
        for (Object object : crt) {
            System.out.println(object);
        }
    }
}

package za.net.hanro50.util;

public class console {
    public static void out(Object... out) {
        for (Object object : out) {
            System.out.println(object.toString());
        }
    }

    public static void err(Object... err) {
        for (Object object : err) {
            System.out.println(object.toString());
        }

    }

    public static void crt(Object... crt) {
        System.err.println("[!!!CRITICAL ERROR!!!]");
        System.err.println("This type of error indicates a coding mistake on the part of the programmer!");
        System.err.println("[!!!CRITICAL ERROR!!!]");
        for (Object object : crt) {
            System.out.println(object.toString());
        }
    }
}

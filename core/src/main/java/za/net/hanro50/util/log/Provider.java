package za.net.hanro50.util.log;

public interface Provider {
    public void out(Meta metdata, Object... out);

    public void err(Meta metdata, Object... err);

    public void crt(Meta metdata, Object... err);

    public void trc(Meta metdata, Throwable err, Object... trc);
}

package za.net.hanro50.util.file;

import java.io.File;
import java.io.IOException;

public abstract class Savable<T> extends Writable {
    private Runnable onInitialization = () -> {
    };

    protected final void setOnInit(Runnable onInit) {
        if (onInit != null)
            this.onInitialization = onInit;
    }

    public Savable(File file) {
        super(file);
    }

    public final T init() throws IOException {
        T result = initialize();
        onInitialization.run();
        return result;
    }

    public abstract T save() throws IOException;

    protected abstract T initialize() throws IOException;
}

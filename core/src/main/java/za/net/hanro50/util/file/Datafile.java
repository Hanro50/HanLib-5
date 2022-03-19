package za.net.hanro50.util.file;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import za.net.hanro50.util.log.Console;

public abstract class Datafile extends Savable<Datafile> {
    private static final Gson encoder = new GsonBuilder().setPrettyPrinting().setLenient()
            .excludeFieldsWithoutExposeAnnotation().serializeNulls().setDateFormat("yyyy-MM-dd'T'HH:mm:ssz").create();

    public Datafile(File file) {
        super(file);
    }

    public String toString() {
        return encoder.toJson(this);
    }

    @Override
    protected Datafile initialize() throws IOException {
        String file = "";
        for (String i : this.read())
            file += i + "\n";
        Datafile restore = encoder.fromJson(file, this.getClass());
        if (restore != null) {
            for (Field f : this.getClass().getDeclaredFields()) {
                Expose exp = f.getAnnotation(Expose.class);
                if (exp != null) {
                    f.setAccessible(true);
                    try {
                        f.set(this, f.get(restore));
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        Console.err("Could transfer value from file to field: " + f.getName());
                        e.printStackTrace();
                    }
                }
            }
        }
        return this;
    };

    @Override
    public Datafile save() throws IOException {
        this.clear();
        this.write(this.toString());
        return this;
    }
}

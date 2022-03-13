package za.net.hanro50.util.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Writable {
    private final File file;

    public Writable(File file) {
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }

    public void write(Object... lines) throws IOException {
        if (!this.file.exists()) {
            this.file.getParentFile().mkdirs();
            this.file.createNewFile();
        }
        FileWriter writer = new FileWriter(this.file, true);
        for (Object line : lines) {
            writer.write(line.toString()+"\n");
        }
        writer.close();
    }

    public void clear() throws IOException {
        if (this.file.exists()) this.file.delete();
    }

    public List<String> read() throws IOException {
        if (this.file.exists())
            return Files.readAllLines(this.file.toPath());
        else
            return new ArrayList<>();
    }
}

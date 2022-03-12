package za.net.hanro50;

import java.io.File;
import java.io.IOException;

import com.google.gson.annotations.Expose;

import org.junit.Test;

import za.net.hanro50.util.file.Config;
import za.net.hanro50.util.file.Datafile;
import za.net.hanro50.util.log.Console;

/**
 * Unit test for simple App.
 */
public class AppTest {

    public class test {
        public String test = "Hellow\n world!!";
    }

    public class configTest extends Config {
        public configTest(File file) {
            super(file);
        }

        @Option(name = "inttest", comment = "Testing if interger encoding and decoding is working!")
        private int inttest = 44;
        @Option(name = "strtest", comment = "Testing if String encoding and decoding is working!")
        private String strtest = "Hello world";
        @Option(name = "cmplx test", comment = "Tests if the complex object encoding works")
        private test test = new test();
        @Option(name = "arr test", comment = "Tests if the complex object encoding works")
        private String[] arrtest = { "Hello world", "t4" };

    }

    public class dataTest extends Datafile {
        public dataTest(File file) {
            super(file);
        }

        @Expose
        private int inttest = 44;
        @Expose
        private String strtest = "Hello world";
        @Expose
        private test test = new test();
        @Expose
        private String[] arrtest = { "Hello world", "t4" };
    }

    /**
     * Should update the config folder
     * 
     * @throws IOException
     */
    @Test
    public void testConfigHandlerSaver() throws IOException {
        Console.debug = true;
        configTest t = (configTest) new configTest(new File("./testFiles/test.txt").getAbsoluteFile()).init();
        t.save();
    }

    /**
     * Change the generated values and check if they have changed
     * 
     * @throws IOException
     */
    @Test
    public void testConfigHandlerReader() throws IOException {
        Console.debug = true;
        configTest t = (configTest) new configTest(new File("./testFiles/test.txt").getAbsoluteFile()).init();
        System.out.print(t.inttest);

    }

    /**
     * Change the generated values and check if they have changed
     * 
     * @throws IOException
     */
    @Test
    public void testDataFile() throws IOException {
        Console.debug = true;
        dataTest t = (dataTest) new dataTest(new File("./testFiles/data.json").getAbsoluteFile()).init();
        t.save();
        System.out.print(t.inttest);

    }

    @Test
    public void testLog() throws IOException {

        Console.crt("Expected failure");
        Console.out("Hello world");
        Console.err("Hello world");

        Console.log("Hello world");
        Console.wrn("Hello world");

        Console.debug = true;

        Console.log("Hello world");
        Console.wrn("Hello world");

        Console.out(1, 2, 3, 4, 5, 6, 7, 8, 9, 0);
        Console.out(10, 20, 30, 40, 50, 60, 70, 80, 90, 0);

        Console.trc(new IOException("Expected failure"));
    }
}

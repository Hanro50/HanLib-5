package za.net.hanro50;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import za.net.hanro50.util.config;
import za.net.hanro50.util.console;

/**
 * Unit test for simple App.
 */
public class AppTest {

    public class test {
        public String test = "Hellow\n world!!";
    }

    public class configTest extends config {
        public configTest(File file) {
            super(file);
        }
        @option(name = "inttest", comment = "Testing if interger encoding and decoding is working!")
        private int inttest = 44;
        @option(name = "strtest", comment = "Testing if String encoding and decoding is working!")
        private String strtest = "Hello world";
        @option(name = "cmplx test", comment = "Tests if the complex object encoding works")
        private test test = new test();

    }
    /**
     * Should update the config folder
     * 
     * @throws IOException
     */
    @Test
    public void testConfigHandlerSaver() throws IOException {
        console.debug =true;
        configTest t = (configTest) new configTest(new File("./util/test.txt").getAbsoluteFile()).init();
        t.save();
    }

    /**
     * Change the generated values and check if they have changed
     * 
     * @throws IOException
     */
    @Test
    public void testConfigHandlerReader() throws IOException {
        console.debug =true;
        configTest t = (configTest) new configTest(new File("./util/test.txt").getAbsoluteFile()).init();
        System.out.print(t.inttest);

    }
}

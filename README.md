# HanLib-5
The 5th iteration of my java utilities 

[![](https://jitpack.io/v/Hanro50/HanLib-5.svg)](https://jitpack.io/#Hanro50/HanLib-5)

# This is still WIP
Breaking changes will change the mayor release number. Minor releases indicate bug fixes or new features. 

# config 
The config class allows you to generate config files easily with comments. 

For example:
```java
//Any data class needs to extend config for this to work. 
public class configTest extends config {
    public configTest(File file) {
        super(file);
    }
    //config can bypass java access rules. So it doesn't matter if class are private, public or protected. 
    @option(name = "inttest", comment = "Testing if interger encoding and decoding is working!")
    private int inttest = 44;
    @option(name = "strtest", comment = "Testing if String encoding and decoding is working!")
    private String strtest = "Hello world";
    @option(name = "cmplx test", comment = "Tests if the complex object encoding works")
    private test test = new test();
    }
```
The code to handle this class will look something like this

```java
public void testConfigHandlerReader() throws IOException {
    //This will create the data object and load in the values from the file. This is done with a 2 step process to avoid having data be overwritten by the init methods. 
    configTest t = (configTest) new configTest(new File("./util/test.txt").getAbsoluteFile()).init();
    System.out.print(t.inttest);
    t.save();
}
```
Ultimately the resulting output should look like this
```txt
//Testing if String encoding and decoding is working!
strtest:"Hello world"
//Testing if interger encoding and decoding is working!
inttest:44
//Tests if the complex object encoding works
cmplx test:{"test":"Hellow\n world!!"}
```

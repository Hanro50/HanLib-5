package za.net.hanro50.util.log;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LogAvoid {
    /**
     * Will continue to ignore all packages that start with this header.
     */
    String Package() default "";
}

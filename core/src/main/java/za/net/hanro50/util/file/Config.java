package za.net.hanro50.util.file;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import za.net.hanro50.util.log.Console;

public abstract class Config extends Writable {
    private Map<String, constr> settings = new HashMap<>();
    private static final Gson gson = new GsonBuilder().setLenient().create();
    private static final String lineBreak = "</br>";

    private static class constr {
        public final Option option;
        public final Field field;
        private boolean ticked = false;
        private Config parent;

        public constr(Option option, Field field, Config parent) {
            this.option = option;
            this.field = field;
            this.parent = parent;
        }

        private static Object parse(Class<?> clazz, String value) {
            if (Boolean.class == clazz || boolean.class == clazz)
                return Boolean.parseBoolean(value);
            if (Byte.class == clazz || byte.class == clazz)
                return Byte.parseByte(value);
            if (Short.class == clazz || short.class == clazz)
                return Short.parseShort(value);
            if (Integer.class == clazz || int.class == clazz)
                return Integer.parseInt(value);
            if (Long.class == clazz || long.class == clazz)
                return Long.parseLong(value);
            if (Float.class == clazz || float.class == clazz)
                return Float.parseFloat(value);
            if (Double.class == clazz || double.class == clazz)
                return Double.parseDouble(value);
            return value;
        }

        public void setValue(String val) throws IllegalArgumentException, IllegalAccessException {
            val = val.replaceAll(lineBreak, "\n");
            if (field.getType().isPrimitive())
                this.field.set(parent, parse(this.field.getType(), val));
            else
                this.field.set(parent, gson.fromJson(val.toString(), this.field.getType()));
            this.ticked = true;

        }

        public boolean isTicked() {
            return ticked;
        }

    }

    private void check(Function<constr, Boolean> func) {
        settings.forEach((k, v) -> {
            try {
                if (func.apply(v)) {
                    String disc = "//" + v.option.comment().replaceAll("\n", "\n//");
                    this.write(disc);
                    if (v.field.getType().isPrimitive())
                        this.write(v.option.name() + ":" + v.field.get(this).toString());
                    else
                        this.write(v.option.name() + ":" + gson.toJson(v.field.get(this)));
                }
            } catch (IOException | IllegalArgumentException | IllegalAccessException e) {
                Console.err("Could not write to file");
                e.printStackTrace();
            }
        });
    }

    public Config init() {
        for (Field f : this.getClass().getDeclaredFields()) {
            Option option = f.getAnnotation(Option.class);
            if (option != null) {
                f.setAccessible(true);
                settings.put(option.name(), new constr(option, f, this));
            }
        }
        if (this.getFile().exists()) {
            try {
                this.read().forEach(e -> {
                    e = e.trim();
                    if (!e.startsWith("//") && !e.startsWith("*") && e.contains(":")) {
                        String[] prop = e.split(":", 2);
                        constr ext = settings.get(prop[0]);
                        try {
                            if (ext != null) {
                                ext.setValue(prop[1]);
                            }
                        } catch (IllegalArgumentException | IllegalAccessException e1) {
                            Console.err("Could not read property in from file ");
                            e1.printStackTrace();
                        }
                    }
                });
            } catch (IOException e) {
                Console.err("Could not configure file");
                e.printStackTrace();
            }
        }
        check(v -> !v.isTicked() && v.option.required());
        /*
         * settings.forEach((k, v) -> {
         * try {
         * if (!v.isTicked() && v.option.required()) {
         * String disc = "//" + v.option.comment().replaceAll("\n", "\n//");
         * this.write(disc);
         * if (v.field.getType().isPrimitive())
         * this.write(v.option.name() + ":" +
         * v.field.get(this).toString().replaceAll("\n", lineBreak));
         * else
         * this.write(v.option.name() + ":" +
         * gson.toJson(v.field.get(this)).replaceAll("\n", lineBreak));
         * }
         * } catch (IOException | IllegalArgumentException | IllegalAccessException e) {
         * Console.err("Could not write to file");
         * e.printStackTrace();
         * }
         * });
         */
        return this;
    }

    public Config(File file) {
        super(file);
    }

    public Config save() throws IOException {
        this.clear();
        check(v -> {
            try {
                return v.field.get(this) != null;
            } catch (IllegalArgumentException | IllegalAccessException e1) {
                return false;
            }
        });
        /*
         * settings.forEach((k, v) -> {
         * try {
         * if (v.field.get(this) != null) {
         * String disc = "//" + v.option.comment().replaceAll("\n", "\n//");
         * this.write(disc);
         * if (v.field.getType().isPrimitive())
         * this.write(v.option.name() + ":" + v.field.get(this).toString());
         * else
         * this.write(v.option.name() + ":" + gson.toJson(v.field.get(this)));
         * }
         * } catch (IOException | IllegalArgumentException | IllegalAccessException e) {
         * Console.err("Could not write to file");
         * e.printStackTrace();
         * }
         * });
         */
        return this;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    protected @interface Option {
        /** The name assigned to the option value */
        String name();

        /** The comment assigned to it */
        String comment() default "";

        /** Writes the default value if non are detected */
        boolean required() default false;
    }
}

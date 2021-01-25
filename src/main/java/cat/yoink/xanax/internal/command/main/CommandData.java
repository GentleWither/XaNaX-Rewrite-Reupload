package cat.yoink.xanax.internal.command.main;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yoink
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandData
{
    String name();

    String[] aliases();

    String usage() default "";

    String description() default "";
}
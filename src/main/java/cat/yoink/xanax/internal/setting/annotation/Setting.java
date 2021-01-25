package cat.yoink.xanax.internal.setting.annotation;

import cat.yoink.xanax.internal.setting.annotation.types.List;
import cat.yoink.xanax.internal.setting.annotation.types.Number;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yoink
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Setting
{
    String name();

    String description() default "Descriptionless";

    Number number() default @Number;

    List list() default @List;
}

package cat.yoink.xanax.internal.setting.annotation.types;

/**
 * @author yoink
 */
public @interface Number
{
    double min() default 0;

    double max() default 10;

    double increment() default 1;
}

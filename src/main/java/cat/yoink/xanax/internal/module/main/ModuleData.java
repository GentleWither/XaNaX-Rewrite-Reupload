package cat.yoink.xanax.internal.module.main;

import cat.yoink.xanax.internal.module.ModuleCategory;
import org.lwjgl.input.Keyboard;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yoink
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModuleData
{
    String name();

    String[] aliases() default {};

    ModuleCategory category();

    String description() default "Descriptionless";

    int defaultBind() default Keyboard.KEY_NONE;

    boolean hidden() default false;

    boolean enabled() default false;

    boolean noSave() default false;
}

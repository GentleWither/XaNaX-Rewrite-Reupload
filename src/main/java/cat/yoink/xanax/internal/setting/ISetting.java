package cat.yoink.xanax.internal.setting;

import cat.yoink.xanax.internal.module.main.Module;

import java.lang.reflect.Field;

/**
 * @author yoink
 */
public interface ISetting<T>
{
    T getValue();

    void setValue(Module module, T value);

    Field getField();

    Module getModule();
}

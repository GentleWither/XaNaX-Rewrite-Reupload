package cat.yoink.xanax.internal.setting.types;

import cat.yoink.xanax.internal.module.main.Module;
import cat.yoink.xanax.internal.setting.Setting;

import java.lang.reflect.Field;

/**
 * @author yoink
 */
public final class StateSetting extends Setting<Boolean>
{
    private boolean value;

    public StateSetting(Field field, Module module, boolean value)
    {
        super(module, field);
        this.value = value;
    }

    @Override
    public Boolean getValue()
    {
        return value;
    }

    @Override
    public void setValue(Module module, Boolean value)
    {
        this.value = value;
        update();
    }
}

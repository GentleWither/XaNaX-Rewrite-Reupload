package cat.yoink.xanax.internal.setting.types;

import cat.yoink.xanax.internal.module.main.Module;
import cat.yoink.xanax.internal.setting.Setting;

import java.awt.*;
import java.lang.reflect.Field;

/**
 * @author yoink
 */
public final class ColorSetting extends Setting<Color>
{
    private Color value;

    public ColorSetting(Field field, Module module, Color value)
    {
        super(module, field);
        this.value = value;
    }

    @Override
    public Color getValue()
    {
        return value;
    }

    @Override
    public void setValue(Module module, Color value)
    {
        this .value = value;
        update();
    }
}

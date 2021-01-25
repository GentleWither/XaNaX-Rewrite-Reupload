package cat.yoink.xanax.internal.setting.reflect;

import cat.yoink.xanax.internal.module.main.Module;
import cat.yoink.xanax.internal.setting.annotation.Setting;
import cat.yoink.xanax.internal.setting.types.ColorSetting;
import cat.yoink.xanax.internal.setting.types.ListSetting;
import cat.yoink.xanax.internal.setting.types.NumberSetting;
import cat.yoink.xanax.internal.setting.types.StateSetting;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @author yoink
 */
public enum Reflection
{
    INSTANCE;

    public java.util.List<cat.yoink.xanax.internal.setting.Setting<?>> getSettings(Module instance)
    {
        java.util.List<cat.yoink.xanax.internal.setting.Setting<?>> settings = new ArrayList<>();
        for (Field field : instance.getClass().getDeclaredFields())
        {
            if (field.isAnnotationPresent(Setting.class))
            {
                try
                {
                    Setting setting = field.getAnnotation(Setting.class);
                    Object value = field.get(instance);

                    if (value instanceof Boolean) settings.add(new StateSetting(field, instance, (Boolean) value));
                    else if (value instanceof String) settings.add(new ListSetting(field, instance, (String) value, setting.list().value()));
                    else if (value instanceof Double) settings.add(new NumberSetting(field, instance, (Double) value, setting.number().min(), setting.number().max(), setting.number().increment()));
                    else if (value instanceof Color) settings.add(new ColorSetting(field, instance, (Color) value));
                }
                catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return settings;
    }

    public void setValue(Module instance, cat.yoink.xanax.internal.setting.Setting<?> setting, Object value)
    {
        try
        {
            setting.getField().set(instance, value);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

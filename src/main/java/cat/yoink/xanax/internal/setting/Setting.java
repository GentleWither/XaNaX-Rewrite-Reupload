package cat.yoink.xanax.internal.setting;

import cat.yoink.xanax.internal.module.main.Module;
import cat.yoink.xanax.internal.setting.reflect.Reflection;
import cat.yoink.xanax.internal.traits.interfaces.Describable;
import cat.yoink.xanax.internal.traits.interfaces.Nameable;

import java.lang.reflect.Field;

/**
 * @author yoink
 */
public abstract class Setting<T> implements Nameable, Describable, ISetting<T>
{
    private final String name;
    private final String description;
    private final Module module;
    private final Field field;

    public Setting(Module module, Field field)
    {
        this.name = field.getAnnotation(cat.yoink.xanax.internal.setting.annotation.Setting.class).name();
        this.description = field.getAnnotation(cat.yoink.xanax.internal.setting.annotation.Setting.class).description();
        this.field = field;
        this.module = module;
    }

    @Override
    public final String getName()
    {
        return name;
    }

    @Override
    public Field getField()
    {
        return field;
    }

    @Override
    public Module getModule()
    {
        return module;
    }

    @Override
    public String getDescription()
    {
        return description;
    }

    protected void update()
    {
        Reflection.INSTANCE.setValue(module, this, getValue());
    }
}

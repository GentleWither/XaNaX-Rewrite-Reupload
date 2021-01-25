package cat.yoink.xanax.internal.setting.types;

import cat.yoink.xanax.internal.module.main.Module;
import cat.yoink.xanax.internal.setting.Setting;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * @author yoink
 */
public final class ListSetting extends Setting<String>
{
    private final List<String> values;
    private int index;

    public ListSetting(Field field, Module module, String sDefault, String... values)
    {
        super(module, field);
        this.values = Arrays.asList(values);
        if (!this.values.contains(sDefault)) this.values.add(sDefault);
        index = this.values.indexOf(sDefault);
    }

    @Override
    public String getValue()
    {
        return values.get(index);
    }

    @Override
    public void setValue(Module module, String value)
    {
        index = values.indexOf(value);
        update();
    }

    public void cycleForward()
    {
        if (index < values.size() - 1) index++;
        else index = 0;
        update();
    }

    public void cycleBackward()
    {
        if (index > 0) index--;
        else index = values.size() - 1;
        update();
    }
}

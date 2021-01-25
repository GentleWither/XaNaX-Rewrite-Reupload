package cat.yoink.xanax.internal.module.main;

import cat.yoink.xanax.internal.module.ModuleCategory;
import cat.yoink.xanax.internal.setting.Setting;
import cat.yoink.xanax.internal.setting.reflect.Reflection;
import cat.yoink.xanax.internal.traits.interfaces.Describable;
import cat.yoink.xanax.internal.traits.interfaces.Minecraft;
import cat.yoink.xanax.internal.traits.interfaces.Nameable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yoink
 */
public abstract class Module implements Minecraft, Describable, Nameable, IModule
{
    protected final String name;
    protected final String[] aliases;
    protected final ModuleCategory category;
    protected final String description;
    protected final boolean hidden;
    protected int bind;
    protected boolean noSave;
    protected final List<Setting<?>> settings;

    protected Module()
    {
        name = getClass().getAnnotation(ModuleData.class).name();
        aliases = getClass().getAnnotation(ModuleData.class).aliases();
        category = getClass().getAnnotation(ModuleData.class).category();
        description = getClass().getAnnotation(ModuleData.class).description();
        hidden = getClass().getAnnotation(ModuleData.class).hidden();
        bind = getClass().getAnnotation(ModuleData.class).defaultBind();
        noSave = getClass().getAnnotation(ModuleData.class).noSave();
        settings = new ArrayList<>();
        settings.addAll(Reflection.INSTANCE.getSettings(this));
    }

    public Module(ModuleData data)
    {
        name = data.name();
        aliases = data.aliases();
        category = data.category();
        description = data.description();
        hidden = data.hidden();
        bind = data.defaultBind();
        noSave = data.noSave();
        settings = new ArrayList<>();
        settings.addAll(Reflection.INSTANCE.getSettings(this));
    }

    protected final boolean isSafe()
    {
        return mc.player != null && mc.world != null;
    }

    public final <T extends Setting<?>> T addSetting(T setting)
    {
        settings.add(setting);
        return setting;
    }

    public final Setting<?> getSetting(String name)
    {
        return settings.stream().filter(setting -> setting.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }

    @Override
    public final String getName()
    {
        return name;
    }

    @Override
    public String[] getAliases()
    {
        return aliases;
    }

    @Override
    public final ModuleCategory getCategory()
    {
        return category;
    }

    @Override
    public final String getDescription()
    {
        return description;
    }

    @Override
    public final int getBind()
    {
        return bind;
    }

    @Override
    public final void setBind(int bind)
    {
        this.bind = bind;
    }

    @Override
    public final boolean isHidden()
    {
        return hidden;
    }

    @Override
    public boolean noSave()
    {
        return noSave;
    }

    @Override
    public final List<Setting<?>> getSettings()
    {
        return settings;
    }
}

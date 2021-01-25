package cat.yoink.xanax.internal.module;

import cat.yoink.xanax.internal.XANAX;
import cat.yoink.xanax.internal.module.main.Module;
import cat.yoink.xanax.internal.module.state.StateModule;
import cat.yoink.xanax.internal.setting.reflect.Reflection;
import cat.yoink.xanax.internal.setting.types.ListSetting;
import cat.yoink.xanax.internal.setting.types.NumberSetting;
import cat.yoink.xanax.internal.setting.types.StateSetting;
import cat.yoink.xanax.internal.traits.interfaces.Configurable;
import cat.yoink.xanax.internal.traits.interfaces.Minecraft;
import cat.yoink.xanax.internal.traits.manager.ListRegistry;
import cat.yoink.xanax.internal.util.FileUtil;
import com.google.common.reflect.ClassPath;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yoink
 */
public final class ModuleManager extends ListRegistry<Module> implements Configurable, Minecraft
{
    public static final ModuleManager INSTANCE = new ModuleManager();

    @SuppressWarnings("all")
    private ModuleManager()
    {
        try
        {
            addModules(ClassPath.from(Thread.currentThread().getContextClassLoader()).getTopLevelClassesRecursive("cat.yoink.xanax.internal.module.impl").stream()
                    .map(info -> info.load())
                    .filter(clazz -> Module.class.isAssignableFrom(clazz))
                    .map(clazz -> { try { return (Module) clazz.newInstance(); } catch (Exception ignored) { ignored.printStackTrace(); return null; } } )
                    .toArray());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean save(String name)
    {
        JsonObject config = new JsonObject();

        getRegistry().forEach(module -> {
            JsonObject mod = new JsonObject();

            JsonObject settings = new JsonObject();

            module.getSettings().forEach(setting -> {
                if (setting instanceof StateSetting) settings.addProperty(setting.getName(), ((StateSetting) setting).getValue());
                else if (setting instanceof NumberSetting) settings.addProperty(setting.getName(), ((NumberSetting) setting).getValue());
                else if (setting instanceof ListSetting) settings.addProperty(setting.getName(), ((ListSetting) setting).getValue());
            });

            boolean value = !(module instanceof StateModule) || ((StateModule) module).getState();
            if (module.noSave()) value = false;
            mod.addProperty("state", value);
            mod.add("settings", settings);

            config.add(module.getName(), mod);
        });

        return FileUtil.saveFile(new File(directory.getAbsolutePath() + File.separator + name, "Modules.json"), XANAX.INSTANCE.gson.toJson(config));
    }

    @Override
    public boolean load(String name)
    {
        String contents = FileUtil.getContents(new File(directory.getAbsolutePath() + File.separator + name, "Modules.json"));
        if (contents.equals("")) return false;
        JsonObject json = new JsonParser().parse(contents).getAsJsonObject();

        getRegistry().forEach(module -> {
            JsonObject jsonModule = json.get(module.getName()).getAsJsonObject();

            if (module instanceof StateModule) ((StateModule) module).setState(jsonModule.get("state").getAsBoolean());

            JsonObject settings = jsonModule.get("settings").getAsJsonObject();

            module.getSettings().forEach(setting -> {
                JsonElement element = settings.get(setting.getName());

                if (setting instanceof StateSetting) ((StateSetting) setting).setValue(module, element.getAsBoolean());
                else if (setting instanceof NumberSetting) ((NumberSetting) setting).setValue(module, element.getAsDouble());
                else if (setting instanceof ListSetting) ((ListSetting) setting).setValue(module, element.getAsString());
            });
        });
        return true;
    }

    private void addModules(Object... modules)
    {
        for (Object module : modules)
        {
            if (!(module instanceof StateModule)) MinecraftForge.EVENT_BUS.register(module);

            add((Module) module);
        }

        getRegistry().forEach(module -> {
            module.getSettings().clear();
            module.getSettings().addAll(Reflection.INSTANCE.getSettings(module));
        });

        getRegistry().sort(Comparator.comparing(module -> -mc.fontRenderer.getStringWidth(module.getName())));
    }

    public StateModule getStateModule(Class<? extends StateModule> name)
    {
        return getFilteredRegistry().stream().filter(module -> module.getClass().equals(name)).findAny().orElse(null);
    }

    public Module getModule(Class<? extends Module> name)
    {
        return getFilteredRegistry().stream().filter(module -> module.getClass().equals(name)).findAny().orElse(null);
    }

    public List<StateModule> getFilteredRegistry()
    {
        return getRegistry().stream().filter(m -> m instanceof StateModule).map(module -> (StateModule) module).collect(Collectors.toList());
    }
}

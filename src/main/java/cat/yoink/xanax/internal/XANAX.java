package cat.yoink.xanax.internal;

import cat.yoink.xanax.internal.command.CommandManager;
import cat.yoink.xanax.internal.other.FriendManager;
import cat.yoink.xanax.internal.module.ModuleManager;
import cat.yoink.xanax.internal.traits.interfaces.Configurable;
import cat.yoink.xanax.internal.traits.interfaces.Nameable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

/**
 * @author yoink
 */
public enum XANAX implements Configurable, Runnable, Nameable
{
    INSTANCE;

    public final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void run()
    {
        if (!load("main"))
        {
            save("main");
            load("main");
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> save("main")));
    }

    @Override
    public boolean save(String name)
    {
        File dir = new File(directory + File.separator + name);
        if (!dir.exists() && !dir.mkdirs()) return false;

        try { return ModuleManager.INSTANCE.save(name) && CommandManager.INSTANCE.save(name) && FriendManager.INSTANCE.save(name); }
        catch (Exception ignored) { return false; }
    }

    @Override
    public boolean load(String name)
    {
        if (!new File(directory + File.separator + name).exists()) return false;

        try { return ModuleManager.INSTANCE.load(name) && CommandManager.INSTANCE.load(name) && FriendManager.INSTANCE.load(name); }
        catch (Exception ignored) { return false; }
    }

    @Override
    public String getName()
    {
        return "XANAX";
    }
}

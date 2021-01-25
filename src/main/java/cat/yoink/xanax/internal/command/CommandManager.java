package cat.yoink.xanax.internal.command;

import cat.yoink.xanax.internal.XANAX;
import cat.yoink.xanax.internal.command.main.Command;
import cat.yoink.xanax.internal.traits.interfaces.Configurable;
import cat.yoink.xanax.internal.traits.interfaces.Minecraft;
import cat.yoink.xanax.internal.traits.manager.ListRegistry;
import cat.yoink.xanax.internal.util.ChatUtil;
import cat.yoink.xanax.internal.util.FileUtil;
import com.google.common.reflect.ClassPath;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraftforge.client.event.ClientChatEvent;

import java.io.File;
import java.util.Arrays;

/**
 * @author yoink
 */
public final class CommandManager extends ListRegistry<Command> implements Configurable, Minecraft
{
    public static final CommandManager INSTANCE = new CommandManager();

    private String prefix = ".";

    @SuppressWarnings("all")
    private CommandManager()
    {
        try
        {
            ClassPath path = ClassPath.from(Thread.currentThread().getContextClassLoader());
            for (ClassPath.ClassInfo info : path.getTopLevelClassesRecursive("cat.yoink.xanax.internal.command.impl"))
            {
                Class<?> cmd = info.load();
                if (Command.class.isAssignableFrom(cmd)) add((Command) cmd.newInstance());
            }
        }
        catch (Exception ignored) {}
    }

    public void parseCommand(ClientChatEvent event)
    {
        if (!event.getMessage().startsWith(prefix)) return;
        event.setCanceled(true);
        ChatUtil.addSendMessage(event.getMessage());

        String[] split = event.getMessage().split(" ");
        getRegistry().stream().filter(c -> Arrays.stream(c.getAliases()).anyMatch(s -> s.equalsIgnoreCase(split[0].substring(prefix.length()))))
                .forEach(c -> c.run(Arrays.copyOfRange(split, 1, split.length)));
    }

    @Override
    public boolean save(String name)
    {
        JsonObject config = new JsonObject();
        config.addProperty("Prefix", prefix);
        return FileUtil.saveFile(new File(directory.getAbsolutePath() + File.separator + name, "Prefix.json"), XANAX.INSTANCE.gson.toJson(config));
    }

    @Override
    public boolean load(String name)
    {
        String contents = FileUtil.getContents(new File(directory.getAbsolutePath() + File.separator + name, "Prefix.json"));
        if (contents.equals("")) return false;
        JsonObject parser = new JsonParser().parse(contents).getAsJsonObject();
        prefix = parser.get("Prefix").getAsString();
        return true;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public void setPrefix(String prefix)
    {
        this.prefix = prefix;
    }
}

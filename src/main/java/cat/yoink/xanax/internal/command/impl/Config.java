package cat.yoink.xanax.internal.command.impl;

import cat.yoink.xanax.internal.XANAX;
import cat.yoink.xanax.internal.command.main.Command;
import cat.yoink.xanax.internal.command.main.CommandData;
import cat.yoink.xanax.internal.traits.interfaces.Configurable;
import cat.yoink.xanax.internal.util.ChatUtil;
import cat.yoink.xanax.internal.util.FileUtil;

import java.io.File;

/**
 * @author yoink
 */
@CommandData(
        name = "Config",
        aliases = "config",
        usage = "config <load|save|delete> <name>"
)
public final class Config extends Command
{
    @Override
    public void run(String... arguments)
    {
        if (arguments.length != 2 && printUsage()) return;

        String type = arguments[0];

        if (type.equalsIgnoreCase("load"))
            if (XANAX.INSTANCE.load(arguments[1])) ChatUtil.sendPrivateMessage("Loaded config.");
            else ChatUtil.sendPrivateMessage("Failed to load config.");
        else if (type.equalsIgnoreCase("save"))
            if (XANAX.INSTANCE.save(arguments[1])) ChatUtil.sendPrivateMessage("Saved config.");
            else ChatUtil.sendPrivateMessage("Failed to save config.");
        else if (type.equalsIgnoreCase("delete"))
            if (FileUtil.deleteFolder(new File(Configurable.directory.getAbsolutePath() + File.separator + arguments[1]))) ChatUtil.sendPrivateMessage("Deleted config.");
            else ChatUtil.sendPrivateMessage("Failed to delete config.");
        else printUsage();
    }
}

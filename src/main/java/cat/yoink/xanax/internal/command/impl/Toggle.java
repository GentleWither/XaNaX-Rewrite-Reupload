package cat.yoink.xanax.internal.command.impl;

import cat.yoink.xanax.internal.command.main.Command;
import cat.yoink.xanax.internal.command.main.CommandData;
import cat.yoink.xanax.internal.module.ModuleManager;
import cat.yoink.xanax.internal.util.ChatUtil;

import java.util.Arrays;

/**
 * @author yoink
 */
@CommandData(
        name = "Toggle",
        aliases = {"toggle", "t"},
        usage = "toggle <Module>"
)
public final class Toggle extends Command
{
    @Override
    public void run(String... arguments)
    {
        if (arguments.length != 1 && printUsage()) return;

        ModuleManager.INSTANCE.getFilteredRegistry().stream()
                .filter(module -> Arrays.stream(module.getAliases()).anyMatch(arguments[0]::equalsIgnoreCase))
                .forEach(module -> {
                    module.toggle();
                    ChatUtil.sendPrivateMessage(module.getState() ? "&f" + module.getName() + " &7[&aON&7]" : "&f" + module.getName() + " &7[&cOFF&7]");
                });
    }
}

package cat.yoink.xanax.internal.command.main;

import cat.yoink.xanax.internal.traits.interfaces.Describable;
import cat.yoink.xanax.internal.traits.interfaces.Minecraft;
import cat.yoink.xanax.internal.traits.interfaces.Nameable;
import cat.yoink.xanax.internal.traits.interfaces.Runnable;
import cat.yoink.xanax.internal.util.ChatUtil;

/**
 * @author yoink
 */
public abstract class Command implements Nameable, Describable, Runnable, Minecraft, ICommand
{
    protected final String name;
    protected final String[] aliases;
    protected final String usage;
    protected final String description;

    protected Command()
    {
        name = getClass().getAnnotation(CommandData.class).name();
        aliases = getClass().getAnnotation(CommandData.class).aliases();
        usage = getClass().getAnnotation(CommandData.class).usage();
        description = getClass().getAnnotation(CommandData.class).description();
    }

    public Command(CommandData data)
    {
        name = data.name();
        aliases = data.aliases();
        usage = data.usage();
        description = data.description();
    }

    protected final boolean printUsage()
    {
        ChatUtil.sendPrivateMessage("Usage: " + usage);
        return true;
    }

    @Override
    public final String getName()
    {
        return name;
    }

    @Override
    public final String[] getAliases()
    {
        return aliases;
    }

    @Override
    public final String getUsage()
    {
        return usage;
    }

    @Override
    public final String getDescription()
    {
        return description;
    }
}

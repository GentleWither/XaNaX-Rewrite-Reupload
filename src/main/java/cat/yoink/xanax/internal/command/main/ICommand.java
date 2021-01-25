package cat.yoink.xanax.internal.command.main;

/**
 * @author yoink
 */
public interface ICommand
{
    String[] getAliases();

    String getUsage();
}

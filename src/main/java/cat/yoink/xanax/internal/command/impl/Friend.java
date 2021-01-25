package cat.yoink.xanax.internal.command.impl;

import cat.yoink.xanax.internal.command.main.Command;
import cat.yoink.xanax.internal.command.main.CommandData;
import cat.yoink.xanax.internal.other.FriendManager;
import cat.yoink.xanax.internal.util.ChatUtil;

/**
 * @author yoink
 */
@CommandData(
        name = "Friend",
        aliases = {"friend", "f"},
        usage = "friend <add|del|list> <name>"
)
public final class Friend extends Command
{
    @Override
    public void run(String... arguments)
    {
        if ((arguments.length != 1 && arguments.length != 2) || (!arguments[0].equalsIgnoreCase("list") && arguments.length == 1) && printUsage()) return;

        if (arguments[0].equalsIgnoreCase("list"))
        {
            StringBuilder builder = new StringBuilder();
            for (String s : FriendManager.INSTANCE.getRegistry()) builder.append(s).append(", ");
            String friends = builder.substring(0, builder.toString().length() - 2);
            ChatUtil.sendPrivateMessage("[" + friends.split(", ").length + "] Friends: " + friends + ".");
        }
        else if (arguments[0].equalsIgnoreCase("add"))
        {
            if (FriendManager.INSTANCE.isFriend(arguments[1])) ChatUtil.sendPrivateMessage(arguments[1] + " is already your friend.");
            else
            {
                FriendManager.INSTANCE.add(arguments[1]);
                ChatUtil.sendPrivateMessage("Added " + arguments[1] + " to your friends.");
            }
        }
        else if (arguments[0].equalsIgnoreCase("del"))
        {
            if (FriendManager.INSTANCE.isFriend(arguments[1]))
            {
                FriendManager.INSTANCE.remove(arguments[1]);
                ChatUtil.sendPrivateMessage("Removed " + arguments[1] + " from your friends.");
            }
            else ChatUtil.sendPrivateMessage(arguments[1] + " is not your friend.");
        }
    }
}

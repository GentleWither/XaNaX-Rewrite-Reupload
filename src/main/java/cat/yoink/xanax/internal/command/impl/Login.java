package cat.yoink.xanax.internal.command.impl;

import cat.yoink.xanax.internal.command.main.Command;
import cat.yoink.xanax.internal.command.main.CommandData;
import cat.yoink.xanax.internal.util.SessionUtil;

/**
 * @author yoink
 */
@CommandData(
        name = "Login",
        aliases = "login",
        usage = "login <email> <pass>"
)
public final class Login extends Command
{
    @Override
    public void run(String... arguments)
    {
        if (arguments.length != 2 && printUsage()) return;

        mc.session = SessionUtil.getSession(arguments[0], arguments[1]);
    }
}

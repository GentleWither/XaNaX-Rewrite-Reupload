package cat.yoink.xanax.internal.util;

import cat.yoink.xanax.internal.traits.interfaces.Minecraft;
import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.minecraft.util.Session;

import java.net.Proxy;

/**
 * @author yoink
 */
public final class SessionUtil implements Minecraft
{
    public static Session getSession(String username, String password)
    {
        try
        {
            YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
            YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service.createUserAuthentication(Agent.MINECRAFT);

            auth.setUsername(username);
            auth.setPassword(password);

            auth.logIn();

            return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
        }
        catch (AuthenticationException e)
        {
            return null;
        }
    }
}

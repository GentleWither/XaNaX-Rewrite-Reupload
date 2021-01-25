package cat.yoink.xanax.internal.util;

import cat.yoink.xanax.internal.XANAX;
import cat.yoink.xanax.internal.traits.interfaces.Minecraft;
import net.minecraft.util.text.TextComponentString;

/**
 * @author yoink
 */
public final class ChatUtil implements Minecraft
{
    public static void sendPublicMessage(String message)
    {
        mc.player.sendChatMessage(message);
    }

    public static void sendPrivateMessage(String message)
    {
        String s = String.format("&7[&c" + XANAX.INSTANCE.getName() + "&7]&7 %s", message);
        mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString(s.replace("&", "§")));
    }

    public static void addSendMessage(String message)
    {
        mc.ingameGUI.getChatGUI().addToSentMessages(message);
    }
}

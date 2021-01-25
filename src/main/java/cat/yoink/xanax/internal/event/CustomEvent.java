package cat.yoink.xanax.internal.event;

import cat.yoink.xanax.internal.traits.interfaces.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * @author yoink
 */
public abstract class CustomEvent<E extends CustomEvent<E>> extends Event implements Minecraft
{
    @SuppressWarnings("unchecked")
    public final E dispatch()
    {
        MinecraftForge.EVENT_BUS.post(this);
        return (E) this;
    }
}

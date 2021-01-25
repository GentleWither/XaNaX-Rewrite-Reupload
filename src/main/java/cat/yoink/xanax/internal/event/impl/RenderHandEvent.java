package cat.yoink.xanax.internal.event.impl;

import cat.yoink.xanax.internal.event.CustomEvent;
import net.minecraft.util.EnumHandSide;

/**
 * @author yoink
 */
public final class RenderHandEvent extends CustomEvent<RenderHandEvent>
{
    private final EnumHandSide side;

    public RenderHandEvent(EnumHandSide side)
    {
        this.side = side;
    }

    public EnumHandSide getSide()
    {
        return side;
    }
}

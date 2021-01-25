package cat.yoink.xanax.internal.event.impl;

import cat.yoink.xanax.internal.event.CustomEvent;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

/**
 * @author yoink
 */
@Cancelable
public final class DamageBlockEvent extends CustomEvent<DamageBlockEvent>
{
    private final BlockPos pos;
    private final EnumFacing face;

    public DamageBlockEvent(final BlockPos pos, final EnumFacing face)
    {
        this.pos = pos;
        this.face = face;
    }

    public BlockPos getPos()
    {
        return this.pos;
    }

    public EnumFacing getFace()
    {
        return this.face;
    }
}

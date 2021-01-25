package cat.yoink.xanax.internal.event.impl;

import cat.yoink.xanax.internal.event.CustomEvent;
import net.minecraft.entity.player.EntityPlayer;

public final class PopEvent extends CustomEvent<PopEvent>
{
    private final EntityPlayer player;

    public PopEvent(EntityPlayer player)
    {
        this.player = player;
    }

    public EntityPlayer getPlayer()
    {
        return player;
    }
}

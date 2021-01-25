package cat.yoink.xanax.internal.event.impl;

import cat.yoink.xanax.internal.event.CustomEvent;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

/**
 * @author yoink
 */
@Cancelable
public final class CollisionEvent extends CustomEvent<CollisionEvent>
{
    private final Entity entity;

    public CollisionEvent(Entity entity)
    {
        this.entity = entity;
    }

    public Entity getEntity()
    {
        return entity;
    }
}

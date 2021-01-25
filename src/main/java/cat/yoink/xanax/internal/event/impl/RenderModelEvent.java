package cat.yoink.xanax.internal.event.impl;

import cat.yoink.xanax.internal.event.CustomEvent;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

/**
 * @author yoink
 */
@Cancelable
public final class RenderModelEvent extends CustomEvent<RenderModelEvent>
{
    private final Entity entitylivingbaseIn;
    private final float limbSwing;
    private final float limbSwingAmount;
    private final float ageInTicks;
    private final float netHeadYaw;
    private final float headPitch;
    private final float scaleFactor;

    public RenderModelEvent(Entity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
    {
        this.entitylivingbaseIn = entitylivingbaseIn;
        this.limbSwing = limbSwing;
        this.limbSwingAmount = limbSwingAmount;
        this.ageInTicks = ageInTicks;
        this.netHeadYaw = netHeadYaw;
        this.headPitch = headPitch;
        this.scaleFactor = scaleFactor;
    }

    public Entity getEntitylivingbaseIn()
    {
        return entitylivingbaseIn;
    }

    public float getLimbSwing()
    {
        return limbSwing;
    }

    public float getLimbSwingAmount()
    {
        return limbSwingAmount;
    }

    public float getAgeInTicks()
    {
        return ageInTicks;
    }

    public float getNetHeadYaw()
    {
        return netHeadYaw;
    }

    public float getHeadPitch()
    {
        return headPitch;
    }

    public float getScaleFactor()
    {
        return scaleFactor;
    }
}

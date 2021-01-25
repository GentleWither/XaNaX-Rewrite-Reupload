package cat.yoink.xanax.external.mixin;

import cat.yoink.xanax.internal.event.impl.RenderModelEvent;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author yoink
 */
@Mixin(RenderLivingBase.class)
public abstract class RenderLivingBasePatch<T extends EntityLivingBase> extends Render<T>
{
    protected RenderLivingBasePatch()
    {
        super(null);
    }

    @Inject(method = "renderModel", at = @At("INVOKE"), cancellable = true)
    private void renderModel(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, CallbackInfo ci)
    {
        if (new RenderModelEvent(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor).dispatch().isCanceled()) ci.cancel();
    }
}

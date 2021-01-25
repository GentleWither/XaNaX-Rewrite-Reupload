package cat.yoink.xanax.external.mixin;

import cat.yoink.xanax.internal.event.impl.CollisionEvent;
import cat.yoink.xanax.internal.event.impl.WaterPushEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author yoink
 */
@Mixin(EntityPlayer.class)
public abstract class EntityPlayerPatch
{
    @Inject(method = "applyEntityCollision", at = @At("HEAD"), cancellable = true)
    private void applyEntityCollision(Entity entity, CallbackInfo ci)
    {
        if (new CollisionEvent(entity).dispatch().isCanceled()) ci.cancel();
    }

    @Inject(method = "isPushedByWater", at = @At("HEAD"), cancellable = true)
    public void isPushedByWater(CallbackInfoReturnable<Boolean> cir)
    {
        if (new WaterPushEvent().dispatch().isCanceled()) cir.setReturnValue(false);
    }
}

package cat.yoink.xanax.external.mixin;

import cat.yoink.xanax.internal.other.CapeManager;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author yoink
 */
@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerPatch
{
    @Inject(method = "getLocationCape", at = @At("HEAD"), cancellable = true)
    private void getLocationCape(CallbackInfoReturnable<ResourceLocation> cape)
    {
        cape.setReturnValue(CapeManager.INSTANCE.getValue(((AbstractClientPlayer) (Object) this).getUniqueID().toString()));
    }
}


package cat.yoink.xanax.external.mixin;

import cat.yoink.xanax.internal.event.impl.DamageBlockEvent;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author yoink
 */
@Mixin(PlayerControllerMP.class)
public class PlayerControllerMPPatch
{
    @Inject(method = "onPlayerDamageBlock", at = @At("INVOKE"), cancellable = true)
    private void onPlayerDamageBlock(BlockPos posBlock, EnumFacing directionFacing, CallbackInfoReturnable<Boolean> cir)
    {
        if (new DamageBlockEvent(posBlock, directionFacing).dispatch().isCanceled()) cir.cancel();
    }
}

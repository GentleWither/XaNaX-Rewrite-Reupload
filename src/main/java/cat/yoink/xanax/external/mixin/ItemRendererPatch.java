package cat.yoink.xanax.external.mixin;

import cat.yoink.xanax.internal.event.impl.RenderHandEvent;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.util.EnumHandSide;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author yoink
 */
@Mixin(ItemRenderer.class)
public class ItemRendererPatch
{
    @Inject(method = "transformSideFirstPerson", at = @At("HEAD"))
    private void transformSideFirstPerson(EnumHandSide hand, float p_187459_2_, CallbackInfo ci)
    {
        new RenderHandEvent(hand).dispatch();
    }

    @Inject(method = "transformFirstPerson", at = @At("HEAD"))
    private void transformFirstPerson(EnumHandSide hand, float p_187453_2_, CallbackInfo ci)
    {
        new RenderHandEvent(hand).dispatch();
    }
}

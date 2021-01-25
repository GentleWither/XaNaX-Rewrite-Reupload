package cat.yoink.xanax.external.mixin;

import cat.yoink.xanax.internal.event.impl.PacketEvent;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author yoink
 */
@Mixin(NetworkManager.class)
public class NetworkManagerPatch
{
    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void onSendPacket(Packet<?> packet, CallbackInfo callbackInfo)
    {
        if (new PacketEvent(packet, PacketEvent.Type.OUTGOING).dispatch().isCanceled()) callbackInfo.cancel();
    }

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    private void onChannelRead(ChannelHandlerContext context, Packet<?> packet, CallbackInfo callbackInfo)
    {
        if (new PacketEvent(packet, PacketEvent.Type.INCOMING).dispatch().isCanceled()) callbackInfo.cancel();
    }
}

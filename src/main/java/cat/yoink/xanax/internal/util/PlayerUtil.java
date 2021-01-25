package cat.yoink.xanax.internal.util;

import cat.yoink.xanax.internal.traits.interfaces.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.math.BlockPos;

public final class PlayerUtil implements Minecraft
{
    public static void shootSelf()
    {
        mc.player.connection.sendPacket(new CPacketPlayer.Rotation(mc.player.rotationYaw, -90, mc.player.onGround));
        mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
        mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(mc.player.getActiveHand()));
        mc.player.stopActiveHand();
    }
}

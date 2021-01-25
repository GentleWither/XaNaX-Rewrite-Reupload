package cat.yoink.xanax.internal.module.impl.toggleable.combat;

import cat.yoink.xanax.internal.event.impl.CollisionEvent;
import cat.yoink.xanax.internal.event.impl.PacketEvent;
import cat.yoink.xanax.internal.event.impl.WaterPushEvent;
import cat.yoink.xanax.internal.module.ModuleCategory;
import cat.yoink.xanax.internal.module.main.ModuleData;
import cat.yoink.xanax.internal.module.state.StateModule;
import cat.yoink.xanax.internal.setting.annotation.types.Number;
import cat.yoink.xanax.internal.setting.annotation.Setting;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author yoink
 */
@ModuleData(
        name = "Velocity",
        aliases = {"Velocity", "AntiKB", "AntiKnockback"},
        category = ModuleCategory.COMBAT,
        description = "Anti knockback"
)
public final class Velocity extends StateModule
{
    @Setting(name = "Velocity", description = "Knockback")
    public boolean velocity = true;
    @Setting(name = "Explosions", description = "Explosion knockback")
    public boolean explosions = true;
    @Setting(name = "Horizontal", description = "Horizontal velocity", number = @Number(max = 100))
    public double horizontal = 0;
    @Setting(name = "Vertical", description = "Vertical velocity", number = @Number(max = 100))
    public double vertical = 0;
    @Setting(name = "Fishable", description = "Allow yourself to take knockback from fish rod pulls")
    public boolean fishable = false;
    @Setting(name = "NoPush", description = "Pushed by other players or blocks")
    public boolean noPush = true;

    @SubscribeEvent
    public void onPlayerSPPushOutOfBlocks(PlayerSPPushOutOfBlocksEvent event)
    {
        if (noPush && event.getEntity().equals(mc.player)) event.setCanceled(true);
    }

    @SubscribeEvent
    public void onPacket(PacketEvent event)
    {
        if (isSafe())
        {
            if (event.getPacket() instanceof SPacketEntityStatus && !fishable && ((SPacketEntityStatus) event.getPacket()).getOpCode() == 31 && ((SPacketEntityStatus) event.getPacket()).getEntity(mc.world) instanceof EntityFishHook && ((EntityFishHook) ((SPacketEntityStatus) event.getPacket()).getEntity(mc.world)).caughtEntity.equals(mc.player))
            {
                event.setCanceled(true);
            }

            if (event.getPacket() instanceof SPacketEntityVelocity && velocity && ((SPacketEntityVelocity) event.getPacket()).getEntityID() == mc.player.getEntityId())
            {
                SPacketEntityVelocity packet = (SPacketEntityVelocity) event.getPacket();

                packet.motionX = packet.motionX / 100 * (int) horizontal;
                packet.motionY = packet.motionY / 100 * (int) vertical;
                packet.motionZ = packet.motionZ / 100 * (int) horizontal;
            }

            if (event.getPacket() instanceof SPacketExplosion && explosions)
            {
                SPacketExplosion packet = ((SPacketExplosion) event.getPacket());

                packet.motionX = packet.motionX / 100 * (int) horizontal;
                packet.motionY = packet.motionY / 100 * (int) vertical;
                packet.motionZ = packet.motionZ / 100 * (int) horizontal;
            }
        }
    }

    @SubscribeEvent
    public void onWaterPush(WaterPushEvent event)
    {
        if (noPush) event.setCanceled(true);
    }

    @SubscribeEvent
    public void onCollision(CollisionEvent event)
    {
        if (noPush) event.setCanceled(true);
    }
}

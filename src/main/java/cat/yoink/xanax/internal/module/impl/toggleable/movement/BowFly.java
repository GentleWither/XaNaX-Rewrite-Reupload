package cat.yoink.xanax.internal.module.impl.toggleable.movement;

import cat.yoink.xanax.internal.event.impl.PacketEvent;
import cat.yoink.xanax.internal.module.ModuleCategory;
import cat.yoink.xanax.internal.module.main.ModuleData;
import cat.yoink.xanax.internal.module.state.StateModule;
import cat.yoink.xanax.internal.setting.annotation.Setting;
import cat.yoink.xanax.internal.setting.annotation.types.Number;
import cat.yoink.xanax.internal.util.PlayerUtil;
import cat.yoink.xanax.internal.util.WorldUtil;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

/**
 * @author yoink
 */
@ModuleData(
        name = "BowFly",
        aliases = {"BowFly", "BowFlight"},
        category = ModuleCategory.MOVEMENT,
        description = "Allows you to fly upwards using a bow",
        noSave = true
)
public final class BowFly extends StateModule
{
    @Setting(name = "Timer", description = "Down timer speed", number = @Number(min = 0.1, max = 5, increment = 0.1))
    public double timer = 3;
    @Setting(name = "Motion", description = "Speed to move up at", number = @Number(min = 0.2, max = 2, increment = 0.01))
    public double motion = 0.6;
    @Setting(name = "BowTicks", description = "Bow pull ticks", number = @Number(min = 1))
    public double bowTicks = 2;
    private float prev;

    @SubscribeEvent
    public void onTickClientTick(TickEvent.ClientTickEvent event)
    {
        if (isSafe() && Mouse.isButtonDown(1))
        {
            if (prev > mc.player.getHealth() + mc.player.getAbsorptionAmount())
            {
                mc.player.motionX = 0;
                mc.player.motionY = motion;
                mc.player.motionZ = 0;
            }
            if (mc.player.motionY < -0.08 &&  mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow)
            {
                WorldUtil.setTPS(timer);
                if (mc.player.isHandActive() && mc.player.getItemInUseMaxCount() > bowTicks) PlayerUtil.shootSelf();
            }
            else WorldUtil.setTPS(20);
            prev = mc.player.getHealth() + mc.player.getAbsorptionAmount();
        }
        else WorldUtil.setTPS(20);
    }

    @SubscribeEvent
    public void onPacket(PacketEvent event)
    {
        if (isSafe() && event.getType().equals(PacketEvent.Type.INCOMING))
        {
            if (event.getPacket() instanceof SPacketPlayerPosLook)
            {
                SPacketPlayerPosLook packet = (SPacketPlayerPosLook) event.getPacket();
                packet.yaw = mc.player.rotationYaw;
                packet.pitch = mc.player.rotationPitch;
            }
        }
    }

    @Override
    public void onDisable()
    {
        WorldUtil.setTPS(20);
    }
}

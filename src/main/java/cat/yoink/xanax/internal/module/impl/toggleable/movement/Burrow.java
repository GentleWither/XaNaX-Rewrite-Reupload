package cat.yoink.xanax.internal.module.impl.toggleable.movement;

import cat.yoink.xanax.internal.module.ModuleCategory;
import cat.yoink.xanax.internal.module.main.ModuleData;
import cat.yoink.xanax.internal.module.state.StateModule;
import cat.yoink.xanax.internal.setting.annotation.Setting;
import cat.yoink.xanax.internal.setting.annotation.types.List;
import cat.yoink.xanax.internal.setting.annotation.types.Number;
import cat.yoink.xanax.internal.util.InventoryUtil;
import cat.yoink.xanax.internal.util.WorldUtil;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * @author yoink
 */
@ModuleData(
        name = "Burrow",
        aliases = {"Burrow", "SelfPlace"},
        category = ModuleCategory.MOVEMENT,
        description = "Places a block inside of yourself",
        noSave = true
)
public final class Burrow extends StateModule
{
    @Setting(name = "LagBackMode", description = "Way you get teleported back", list = @List({"Jump", "HighJump", "TPBack", "TP", "Packet"}))
    public String mode = "HighJump";
    @Setting(name = "Height", description = "Height you should be teleported back at", number = @Number(min = 1, max = 1.3, increment = 0.01))
    public double height = 1.25;
    @Setting(name = "Strength", description = "Jump strength", number = @Number(min = 0.05, max = 2, increment = 0.05))
    public double strength = 3;
    private BlockPos originalPos;

    @Override
    public void onEnable()
    {
        this.originalPos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);

        if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ)).getBlock().equals(Blocks.OBSIDIAN) || WorldUtil.isInterceptedByOther(this.originalPos) || InventoryUtil.getHotbarSlot(Blocks.OBSIDIAN) == -1)
        {
            setState(false);
            return;
        }

        mc.player.jump();
    }

    @SubscribeEvent
    public void onTickClientTick(TickEvent.ClientTickEvent event)
    {
        if (mc.player.posY > this.originalPos.getY() + this.height)
        {
            final int oldSlot = mc.player.inventory.currentItem;

            int slot = InventoryUtil.getHotbarSlot(Blocks.OBSIDIAN);
            if (slot == -1)
            {
                setState(false);
                return;
            }
            mc.player.inventory.currentItem = slot;

            WorldUtil.placeBlock(this.originalPos);

            mc.player.inventory.currentItem = oldSlot;

            switch (this.mode)
            {
                case "Jump":
                    mc.player.jump();
                    break;
                case "HighJump":
                    mc.player.motionY = strength;
                    break;
                case "TPBack":
                    mc.player.posY = this.originalPos.getY();
                    break;
                case "TP":
                    mc.player.setPosition(0, -1, 0);
                    break;
                case "Packet":
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, this.originalPos.getY(), mc.player.posZ, true));
                    break;
                default:
                    break;
            }

            setState(false);
        }
    }
}

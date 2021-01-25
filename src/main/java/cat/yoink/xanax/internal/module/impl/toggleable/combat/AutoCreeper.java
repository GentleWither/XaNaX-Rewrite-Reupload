package cat.yoink.xanax.internal.module.impl.toggleable.combat;

import cat.yoink.xanax.internal.other.FriendManager;
import cat.yoink.xanax.internal.module.ModuleCategory;
import cat.yoink.xanax.internal.module.main.ModuleData;
import cat.yoink.xanax.internal.module.state.StateModule;
import cat.yoink.xanax.internal.setting.annotation.types.List;
import cat.yoink.xanax.internal.setting.annotation.types.Number;
import cat.yoink.xanax.internal.setting.annotation.Setting;
import cat.yoink.xanax.internal.util.InventoryUtil;
import cat.yoink.xanax.internal.util.WorldUtil;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * @author yoink
 */
@ModuleData(
        name = "AutoCreeper",
        aliases = {"AutoCreeper", "Creeper", "AutoSpawn", "Spawner"},
        category = ModuleCategory.COMBAT,
        description = "Automatically spawns creepers inside someone",
        noSave = true
)
public final class AutoCreeper extends StateModule
{
    @Setting(name = "Mode", description = "Choose when it should spawn", list = @List({"Hole", "Always"}))
    public String mode = "Always";
    @Setting(name = "Distance", description = "Enemy maximum distance", number = @Number(min = 2, max = 7, increment = 0.1))
    public double distance = 4;
    @Setting(name = "Delay", description = "Spawn delay", number = @Number(min = 1, max = 20))
    public double delay = 4;

    private int oldSlot;

    @Override
    public void onEnable()
    {
        oldSlot = -1;
        switchSlot();
    }

    @Override
    public void onDisable()
    {
        if (oldSlot != -1) mc.player.inventory.currentItem = oldSlot;
    }

    @SubscribeEvent
    public void onTickClientTick(TickEvent.ClientTickEvent event)
    {
        if (isSafe() && mc.player.ticksExisted % delay == 0)
        {
            mc.world.playerEntities.stream()
                    .filter(player -> mc.player.getDistance(player) <= distance)
                    .filter(player -> !player.equals(mc.player))
                    .filter(player -> !FriendManager.INSTANCE.isFriend(player.getName()))
                    .filter(player -> mode.equals("Always") || WorldUtil.isInHole(player))
                    .forEach(player -> {
                        BlockPos blockPos = new BlockPos(player.posX, player.posY - 1, player.posZ);
                        if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR))return;
                        switchSlot();
                        mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(blockPos, EnumFacing.UP, EnumHand.MAIN_HAND, blockPos.getX(), blockPos.getY(), blockPos.getZ()));
                        mc.player.swingArm(EnumHand.MAIN_HAND);
                    });
        }
    }

    private void switchSlot()
    {
        if (!mc.player.getHeldItemMainhand().getItem().equals(Items.SPAWN_EGG))
        {
            int slot = InventoryUtil.getHotbarSlot(Items.SPAWN_EGG);
            if (slot == -1)
            {
                toggle();
                return;
            }
            mc.player.inventory.currentItem = slot;
        }
    }
}

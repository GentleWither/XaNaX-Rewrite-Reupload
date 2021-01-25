package cat.yoink.xanax.internal.module.impl.toggleable.combat;

import cat.yoink.xanax.internal.other.FriendManager;
import cat.yoink.xanax.internal.module.ModuleCategory;
import cat.yoink.xanax.internal.module.main.ModuleData;
import cat.yoink.xanax.internal.module.state.StateModule;
import cat.yoink.xanax.internal.setting.annotation.Setting;
import cat.yoink.xanax.internal.setting.annotation.types.Number;
import cat.yoink.xanax.internal.util.InventoryUtil;
import cat.yoink.xanax.internal.util.WorldUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Objects;

/**
 * @author yoink
 */
@ModuleData(
        name = "CrystalPiston",
        aliases = {"CrystalPiston", "AutoPiston", "PistonAura"},
        category = ModuleCategory.COMBAT,
        description = "Automatically pushes crystals into holes with pistons",
        noSave = true
)
public final class CrystalPiston extends StateModule
{
    @Setting(name = "Distance", description = "Enemy distance", number = @Number(min = 1, max = 6, increment = 0.1))
    public double distance = 4;
    private int stage;

    @SubscribeEvent
    public void onTickClientTick(TickEvent.ClientTickEvent event)
    {
        if (isSafe() && getState())
        {
            EntityPlayer target = WorldUtil.getClosestPlayer();
            if (target == null || FriendManager.INSTANCE.isFriend(target.getName()) || !WorldUtil.isInHole(target) || mc.player.getDistance(target) > distance) return;
            BlockPos targetPos = new BlockPos(target.posX, target.posY, target.posZ);

            int crystalSlot = InventoryUtil.getHotbarSlot(Items.END_CRYSTAL);
            int pistonSlot = InventoryUtil.getHotbarSlot(Blocks.PISTON);
            int redstoneSlot = InventoryUtil.getHotbarSlot(Blocks.REDSTONE_BLOCK);

            if (crystalSlot == -1 || pistonSlot == -1 || redstoneSlot == -1) return;

            switch (stage)
            {
                case 0:
                    WorldUtil.placeBlock(targetPos.add(2, 1, 0), pistonSlot, true);
                    mc.player.inventory.currentItem = crystalSlot;
                    break;

                case 3:
                    if (mc.world.getBlockState(targetPos.add(2, 1, 0)).getBlock().equals(Blocks.PISTON)) Objects.requireNonNull(mc.getConnection()).sendPacket(new CPacketPlayerTryUseItemOnBlock(targetPos.add(1, 0, 0), EnumFacing.UP, EnumHand.MAIN_HAND, 0, 0, 0));
                    else stage = -1;
                    break;

                case 6:
                    if (mc.world.getLoadedEntityList().stream().anyMatch(e -> new BlockPos(e.posX, e.posY, e.posZ).add(-1, -1, 0).equals(targetPos))) WorldUtil.placeBlock(targetPos.add(3, 1, 0), redstoneSlot, true);
                    else stage = 2;
                    break;

                case 15:
                    Entity en = mc.world.getLoadedEntityList().stream().filter(e -> new BlockPos(e.posX, e.posY, e.posZ).down().equals(targetPos)).findAny().orElse(null);
                    if (en == null) stage = 5;
                    else WorldUtil.attack(en);
                    break;

                case 18:
                    stage = -1;
                    break;

                default:
                    break;
            }

            stage++;
        }
    }
}

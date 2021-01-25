package cat.yoink.xanax.internal.module.impl.toggleable.combat;

import cat.yoink.xanax.internal.module.ModuleCategory;
import cat.yoink.xanax.internal.module.main.ModuleData;
import cat.yoink.xanax.internal.module.state.StateModule;
import cat.yoink.xanax.internal.setting.annotation.Setting;
import cat.yoink.xanax.internal.setting.annotation.types.List;
import cat.yoink.xanax.internal.setting.annotation.types.Number;
import cat.yoink.xanax.internal.util.InventoryUtil;
import cat.yoink.xanax.internal.util.WorldUtil;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Arrays;

@ModuleData(
        name = "Surround",
        aliases = {"Surround", "AutoFeetPlace", "AutoObsidian", "AutoPlace"},
        category = ModuleCategory.COMBAT,
        description = "Automatically places obsidian around you",
        noSave = true
)
public final class Surround extends StateModule
{
    private final java.util.List<Vec3d> positions = Arrays.asList(new Vec3d(1, -1, 0), new Vec3d(-1, -1, 0), new Vec3d(0, -1, 1), new Vec3d(0, -1, -1), new Vec3d(1, 0, 0), new Vec3d(-1, 0, 0), new Vec3d(0, 0, 1), new Vec3d(0, 0, -1));

    @Setting(name = "Disable", description = "When the module should disable", list = @List({"WhenDone", "OnJump", "YChange", "Off"}))
    public String disable = "WhenDone";
    @Setting(name = "BTP", description = "Blocks per tick", number = @Number(min = 1))
    public double btp = 1;
    private boolean finished;
    private double startY;

    @Override
    public void onEnable()
    {
        finished = false;
        startY = mc.player.posY;
    }

    @SubscribeEvent
    public void onTickClientTick(TickEvent.ClientTickEvent event)
    {
        if (isSafe())
        {
            if (disable.equals("YChange") && mc.player.posY != startY)
            {
                setState(false);
                return;
            }

            if (finished && (disable.equals("WhenDone") || (disable.equals("OnJump") && !mc.player.onGround)))
            {
                setState(false);
                return;
            }

            int blocksPlaced = 0;

            for (Vec3d position : positions)
            {
                BlockPos pos = new BlockPos(position.add(mc.player.getPositionVector()));

                if (mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR))
                {
                    int oldSlot = mc.player.inventory.currentItem;
                    int newSlot = InventoryUtil.getHotbarSlot(Blocks.OBSIDIAN);

                    if (newSlot == -1)
                    {
                        setState(false);
                        return;
                    }

                    mc.player.inventory.currentItem = newSlot;
                    if (WorldUtil.placeBlock(pos)) blocksPlaced++;
                    mc.player.inventory.currentItem = oldSlot;

                    if (blocksPlaced == btp) return;
                }
            }

            if (blocksPlaced == 0) finished = true;
        }
    }
}

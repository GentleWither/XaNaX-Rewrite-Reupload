package cat.yoink.xanax.internal.module.impl.toggleable.world;

import cat.yoink.xanax.internal.event.impl.DamageBlockEvent;
import cat.yoink.xanax.internal.module.ModuleCategory;
import cat.yoink.xanax.internal.module.main.ModuleData;
import cat.yoink.xanax.internal.module.state.StateModule;
import cat.yoink.xanax.internal.setting.annotation.types.List;
import cat.yoink.xanax.internal.setting.annotation.types.Number;
import cat.yoink.xanax.internal.setting.annotation.Setting;
import cat.yoink.xanax.internal.util.InventoryUtil;
import cat.yoink.xanax.internal.util.RenderUtil;
import cat.yoink.xanax.internal.util.Timer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;

/**
 * @author yoink
 */
@ModuleData(
        name = "PacketMine",
        aliases = {"PacketMine", "SpeedMine", "GhostMine"},
        category = ModuleCategory.WORLD,
        description = "Mine blocks with packets"
)
public final class PacketMine extends StateModule
{
    @Setting(name = "Render", description = "Render the mine block", list = @List({"Specific", "Full", "Off"}))
    public String render = "Specific";
    @Setting(name = "Color", description = "Color for rendering")
    public Color color = new Color(0, 0, 255);
    @Setting(name = "Box", description = "Box for rendering")
    public boolean box = true;
    @Setting(name = "Alpha", description = "Alpha for rendering", number = @Number(max = 255))
    public double alpha = 100;
    @Setting(name = "Outline", description = "Outline for rendering")
    public boolean outline = true;
    @Setting(name = "Change", description = "Change color based on break progress")
    public boolean change = false;
    @Setting(name = "NoBreak", description = "Stops you from mining the block")
    public boolean noBreak = false;
    @Setting(name = "Swing", description = "Swing when mining multiple blocks")
    public boolean swing = true;
    @Setting(name = "Silent", description = "Mine without holding a pickaxe")
    public boolean silent = false;
    @Setting(name = "Time", description = "Max time before stopping", number = @Number(min = 100, max = 1000, increment = 10))
    public double time = 300;
    @Setting(name = "CancelClick", description = "Cancels other actions while ghost mining")
    public boolean cancel = true;

    private BlockPos breakBlock;
    private final Timer timer = new Timer();

    @SubscribeEvent
    public void onDamageBlock(DamageBlockEvent event)
    {
        if (isSafe())
        {
            if (swing) mc.player.swingArm(EnumHand.MAIN_HAND);
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getPos(), event.getFace()));
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(), event.getFace()));

            if (render.equals("Full") || render.equals("Specific")) breakBlock = event.getPos();
            if (noBreak) event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event)
    {
        if (isSafe())
        {
            if (silent && breakBlock != null && !(mc.player.inventory.getItemStack().getItem() instanceof ItemPickaxe))
            {
                int slot = InventoryUtil.getHotbarSlot(Items.DIAMOND_PICKAXE);
                if (slot != -1) mc.player.connection.sendPacket(new CPacketHeldItemChange(slot));
            }
            if (breakBlock != null) timer.tick();
            if (breakBlock != null && timer.hasPassed((int) time))
            {
                timer.reset();
                breakBlock = null;

                if (silent) mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
            }
        }
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event)
    {
        if (isSafe())
        {
            if (breakBlock != null && mc.world.getBlockState(breakBlock).getBlock() == Blocks.AIR)
            {
                breakBlock = null;
                timer.reset();
                if (silent) mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
            }
            else if (breakBlock != null && timer.getTicks() != 0)
            {
                java.awt.Color c;
                if (change)
                    c = !timer.hasPassed(50) ? new java.awt.Color(200, 10, 10, 150) : new java.awt.Color(10, 200, 10, 150);
                else c = new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue(), (int) alpha);

                if (render.equals("Specific"))
                    RenderUtil.drawBox(RenderUtil.convertBox(mc.world.getBlockState(breakBlock).getBoundingBox(mc.world, breakBlock).offset(breakBlock)), c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha(), box, outline);
                else RenderUtil.drawBox(breakBlock, c, box, outline);
            }
        }
    }

    @SubscribeEvent
    public void onInputMouseInput(InputEvent.MouseInputEvent event)
    {
        if (isSafe() && cancel)
        {
            mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
        }
    }
}

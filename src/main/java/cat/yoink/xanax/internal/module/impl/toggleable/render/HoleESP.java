package cat.yoink.xanax.internal.module.impl.toggleable.render;

import cat.yoink.xanax.internal.module.ModuleCategory;
import cat.yoink.xanax.internal.module.main.ModuleData;
import cat.yoink.xanax.internal.module.state.StateModule;
import cat.yoink.xanax.internal.setting.annotation.types.Number;
import cat.yoink.xanax.internal.setting.annotation.Setting;
import cat.yoink.xanax.internal.util.RenderUtil;
import cat.yoink.xanax.internal.util.WorldUtil;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yoink
 */
@ModuleData(
        name = "HoleESP",
        aliases = {"HoleESP", "Holes", "HoleHighlight", "Hole"},
        category = ModuleCategory.RENDER,
        description = "Highlights nearby holes"
)
public final class HoleESP extends StateModule
{
    @Setting(name = "Bedrock", description = "Bedrock hole color")
    public Color bedrockColor = new Color(255, 0, 0);
    @Setting(name = "Obsidian", description = "Obsidian hole color")
    public Color obsidianColor = new Color(0, 255, 0);
    @Setting(name = "BedrockAlpha", description = "Bedrock hole alpha", number = @Number(max = 255))
    public double bedrockAlpha = 150;
    @Setting(name = "ObsidianAlpha", description = "Obsidian hole alpha", number = @Number(max = 255))
    public double obsidianAlpha = 150;
    @Setting(name = "Height", description = "Height of the esp", number = @Number(min = -1, max = 1, increment = 0.1))
    public double height = 0.1;
    @Setting(name = "Range", description = "Distance it should show holes as", number = @Number(min = 2, max = 20))
    public double range = 8;
    @Setting(name = "Performance", description = "Scan delay", number = @Number(min = 1, max = 20))
    public double performance = 5;
    @Setting(name = "Box", description = "ESP box")
    public boolean box = true;
    @Setting(name = "Outline", description = "ESP outline")
    public boolean outline = true;
    @Setting(name = "Wide", description = "2 block holes")
    public boolean wide = true;

    private final List<BlockPos> bedrockHoles = new ArrayList<>();
    private final List<BlockPos> obsidianHoles = new ArrayList<>();
    private final List<BlockPos> doubleBedrockHolesX = new ArrayList<>();
    private final List<BlockPos> doubleBedrockHolesZ = new ArrayList<>();
    private final List<BlockPos> doubleObsidianHolesX = new ArrayList<>();
    private final List<BlockPos> doubleObsidianHolesZ = new ArrayList<>();

    @SubscribeEvent
    public void onTickClientTick(TickEvent.ClientTickEvent event)
    {
        if (isSafe() && mc.player.ticksExisted % performance == 0)
        {
            bedrockHoles.clear();
            obsidianHoles.clear();
            doubleBedrockHolesX.clear();
            doubleBedrockHolesZ.clear();
            doubleObsidianHolesX.clear();
            doubleObsidianHolesZ.clear();

            assert mc.renderViewEntity != null;
            Vec3i vec3i = new Vec3i(mc.renderViewEntity.posX, mc.renderViewEntity.posY, mc.renderViewEntity.posZ);

            for (int i = vec3i.getX() - (int) range; i < vec3i.getX() + range; ++i)
            {
                for (int j = vec3i.getZ() - (int) range; j < vec3i.getZ() + range; ++j)
                {
                    for (int k = vec3i.getY() + (int) range; k > vec3i.getY() - range; --k)
                    {
                        BlockPos blockPos = new BlockPos(i, k, j);

                        if (WorldUtil.isBedrockHole(blockPos)) bedrockHoles.add(blockPos);
                        else if (WorldUtil.isHole(blockPos)) obsidianHoles.add(blockPos);
                        else if (wide)
                        {
                            if (WorldUtil.isDoubleBedrockHoleX(blockPos)) doubleBedrockHolesX.add(blockPos);
                            else if (WorldUtil.isDoubleBedrockHoleZ(blockPos)) doubleBedrockHolesZ.add(blockPos);
                            else if (WorldUtil.isDoubleHoleX(blockPos)) doubleObsidianHolesX.add(blockPos);
                            else if (WorldUtil.isDoubleHoleZ(blockPos)) doubleObsidianHolesZ.add(blockPos);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event)
    {
        int bedrockR = bedrockColor.getRed();
        int bedrockG = bedrockColor.getGreen();
        int bedrockB = bedrockColor.getBlue();
        int bedrockA = (int) bedrockAlpha;
        int obsidianR = obsidianColor.getRed();
        int obsidianG = obsidianColor.getGreen();
        int obsidianB = obsidianColor.getBlue();
        int obsidianA = (int) obsidianAlpha;

        bedrockHoles.stream().map(hole -> new AxisAlignedBB(hole.getX() - mc.getRenderManager().viewerPosX, hole.getY() - mc.getRenderManager().viewerPosY, hole.getZ() - mc.getRenderManager().viewerPosZ, hole.getX() + 1 - mc.getRenderManager().viewerPosX, hole.getY() + height - mc.getRenderManager().viewerPosY, hole.getZ() + 1 - mc.getRenderManager().viewerPosZ)).forEach(axisAlignedBB -> RenderUtil.drawBox(axisAlignedBB, bedrockR, bedrockG, bedrockB, bedrockA, box, outline));
        obsidianHoles.stream().map(hole -> new AxisAlignedBB(hole.getX() - mc.getRenderManager().viewerPosX, hole.getY() - mc.getRenderManager().viewerPosY, hole.getZ() - mc.getRenderManager().viewerPosZ, hole.getX() + 1 - mc.getRenderManager().viewerPosX, hole.getY() + height - mc.getRenderManager().viewerPosY, hole.getZ() + 1 - mc.getRenderManager().viewerPosZ)).forEach(axisAlignedBB -> RenderUtil.drawBox(axisAlignedBB, obsidianR, obsidianG, obsidianB, obsidianA, box, outline));
        if (!wide) return;
        doubleBedrockHolesX.stream().map(hole -> new AxisAlignedBB(hole.getX() - mc.getRenderManager().viewerPosX, hole.getY() - mc.getRenderManager().viewerPosY, hole.getZ() - mc.getRenderManager().viewerPosZ, hole.getX() + 2 - mc.getRenderManager().viewerPosX, hole.getY() + height - mc.getRenderManager().viewerPosY, hole.getZ() + 1 - mc.getRenderManager().viewerPosZ)).forEach(axisAlignedBB -> RenderUtil.drawBox(axisAlignedBB, bedrockR, bedrockG, bedrockB, bedrockA, box, outline));
        doubleBedrockHolesZ.stream().map(hole -> new AxisAlignedBB(hole.getX() - mc.getRenderManager().viewerPosX, hole.getY() - mc.getRenderManager().viewerPosY, hole.getZ() - mc.getRenderManager().viewerPosZ, hole.getX() + 1 - mc.getRenderManager().viewerPosX, hole.getY() + height - mc.getRenderManager().viewerPosY, hole.getZ() + 2 - mc.getRenderManager().viewerPosZ)).forEach(axisAlignedBB -> RenderUtil.drawBox(axisAlignedBB, bedrockR, bedrockG, bedrockB, bedrockA, box, outline));
        doubleObsidianHolesX.stream().map(hole -> new AxisAlignedBB(hole.getX() - mc.getRenderManager().viewerPosX, hole.getY() - mc.getRenderManager().viewerPosY, hole.getZ() - mc.getRenderManager().viewerPosZ, hole.getX() + 2 - mc.getRenderManager().viewerPosX, hole.getY() + height - mc.getRenderManager().viewerPosY, hole.getZ() + 1 - mc.getRenderManager().viewerPosZ)).forEach(axisAlignedBB -> RenderUtil.drawBox(axisAlignedBB, obsidianR, obsidianG, obsidianB, obsidianA, box, outline));
        doubleObsidianHolesZ.stream().map(hole -> new AxisAlignedBB(hole.getX() - mc.getRenderManager().viewerPosX, hole.getY() - mc.getRenderManager().viewerPosY, hole.getZ() - mc.getRenderManager().viewerPosZ, hole.getX() + 1 - mc.getRenderManager().viewerPosX, hole.getY() + height - mc.getRenderManager().viewerPosY, hole.getZ() + 2 - mc.getRenderManager().viewerPosZ)).forEach(axisAlignedBB -> RenderUtil.drawBox(axisAlignedBB, obsidianR, obsidianG, obsidianB, obsidianA, box, outline));
    }
}

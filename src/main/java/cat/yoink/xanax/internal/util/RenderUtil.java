package cat.yoink.xanax.internal.util;

import cat.yoink.xanax.internal.traits.interfaces.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author yoink
 */
public final class RenderUtil implements Minecraft
{
    public static AxisAlignedBB convertBox(AxisAlignedBB box)
    {
        return new AxisAlignedBB(box.minX - mc.getRenderManager().viewerPosX, box.minY - mc.getRenderManager().viewerPosY, box.minZ - mc.getRenderManager().viewerPosZ, box.maxX - mc.getRenderManager().viewerPosX, box.maxY - mc.getRenderManager().viewerPosY, box.maxZ - mc.getRenderManager().viewerPosZ);
    }

    public static void drawBox(BlockPos blockPos, Color color, boolean box, boolean outline)
    {
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(blockPos.getX() - mc.getRenderManager().viewerPosX, blockPos.getY() - mc.getRenderManager().viewerPosY, blockPos.getZ() - mc.getRenderManager().viewerPosZ, blockPos.getX() + 1 - mc.getRenderManager().viewerPosX, blockPos.getY() + 1 - mc.getRenderManager().viewerPosY, blockPos.getZ() + 1 - mc.getRenderManager().viewerPosZ);
        drawBox(axisAlignedBB, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), box, outline);
    }

    public static void drawBox(AxisAlignedBB bb, int red, int green, int blue, int alpha, boolean box, boolean outline)
    {
        glStart();
        if (box) RenderGlobal.renderFilledBox(bb, red / 255f, green / 255f, blue / 255f, alpha / 255f);
        if (outline) RenderGlobal.drawSelectionBoundingBox(bb, red / 255f, green / 255f, blue / 255f, (alpha / 255f) * 1.5F);
        glStop();
    }

    public static void glStart()
    {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        glEnable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        glLineWidth(1.5f);
    }

    public static void glStop()
    {
        glDisable(GL_LINE_SMOOTH);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}

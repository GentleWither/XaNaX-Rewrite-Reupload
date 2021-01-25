package cat.yoink.xanax.internal.guiscreen.main;

import cat.yoink.xanax.internal.module.ModuleManager;
import cat.yoink.xanax.internal.util.GuiUtil;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yoink
 */
public final class MainMenu extends GuiScreen
{
    public static final MainMenu INSTANCE = new MainMenu();
    private final ResourceLocation texture = new ResourceLocation("main.png");
    private final List<CustomButton> buttons;
    private float panoramaTimer;
    private final ResourceLocation MINECRAFT_TITLE_TEXTURES;
    private ResourceLocation backgroundTexture;
    private static final ResourceLocation[] TITLE_PANORAMA_PATHS;

    private MainMenu()
    {
        buttons = new ArrayList<>();
        MINECRAFT_TITLE_TEXTURES = new ResourceLocation("textures/gui/title/minecraft.png");
    }

    public void initGui()
    {
        buttons.clear();
        buttons.add(new CustomButton(0, width / 2 - 75, height / 2 - 25, 150, 20, "Singleplayer"));
        buttons.add(new CustomButton(1, width / 2 - 75, height / 2, 150, 20, "Multiplayer"));
        buttons.add(new CustomButton(2, width / 2 - 75, height / 2 + 25, 72, 20, "Options"));
        buttons.add(new CustomButton(3, width / 2 + 3, height / 2 + 25, 72, 20, "Quit"));
        backgroundTexture = mc.getTextureManager().getDynamicTextureLocation("background", new DynamicTexture(256, 256));
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        if (ModuleManager.INSTANCE.getModule(cat.yoink.xanax.internal.module.impl.toggleable.client.MainMenu.class).getSetting("Mode").getValue().equals("Minecraft"))
        {
            renderBackground(partialTicks);
        }
        else
        {
            mc.getTextureManager().bindTexture(texture);
            Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, width, height, width, height);
            mc.getTextureManager().deleteTexture(texture);
        }

        Gui.drawRect(width / 2 - 100, height / 2 - 140, width / 2 - 100 + 200, height / 2 - 140 + 72, new Color(1711276032, true).getRGB());
        GL11.glPushMatrix();
        GL11.glScalef(6.0f, 6.0f, 0.0f);
        mc.fontRenderer.drawStringWithShadow("XANAX", (float) ((width / 2 - 100 + 15) / 6), (float) ((height / 2 - 140 + 15) / 6), -1);
        GL11.glPopMatrix();
        for (CustomButton button : buttons)
        {
            button.drawButton(mc, mouseX, mouseY);
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            for (CustomButton button : buttons)
            {
                if (GuiUtil.isHover(button.x, button.y, button.buttonWidth, button.buttonHeight, mouseX, mouseY))
                {
                    switch (button.id)
                    {
                        case 0:
                        {
                            mc.displayGuiScreen(new GuiWorldSelection(this));
                            continue;
                        }
                        case 1:
                        {
                            mc.displayGuiScreen(new GuiMultiplayer(this));
                            continue;
                        }
                        case 2:
                        {
                            mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                            continue;
                        }
                        case 3:
                        {
                            mc.shutdown();
                        }
                    }
                }
            }
        }
    }

    private void renderBackground(float partialTicks)
    {
        panoramaTimer += partialTicks;
        GlStateManager.disableAlpha();
        renderSkybox();
        GlStateManager.enableAlpha();
        drawGradientRect(0, 0, width, height, -2130706433, 16777215);
        drawGradientRect(0, 0, width, height, 0, Integer.MIN_VALUE);
        mc.getTextureManager().bindTexture(MINECRAFT_TITLE_TEXTURES);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void renderSkybox()
    {
        mc.getFramebuffer().unbindFramebuffer();
        GlStateManager.viewport(0, 0, 256, 256);
        drawPanorama();
        for (int i = 0; i < 7; ++i)
        {
            rotateAndBlurSkybox();
        }
        mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.viewport(0, 0, mc.displayWidth, mc.displayHeight);
        float f = 120.0f / Math.max(width, height);
        float f2 = height * f / 256.0f;
        float f3 = width * f / 256.0f;
        int j = width;
        int k = height;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos(0.0, k, zLevel).tex(0.5f - f2, 0.5f + f3).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        bufferbuilder.pos(j, k, zLevel).tex(0.5f - f2, 0.5f - f3).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        bufferbuilder.pos(j, 0.0, zLevel).tex(0.5f + f2, 0.5f - f3).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        bufferbuilder.pos(0.0, 0.0, zLevel).tex(0.5f + f2, 0.5f + f3).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        tessellator.draw();
    }

    private void rotateAndBlurSkybox()
    {
        mc.getTextureManager().bindTexture(backgroundTexture);
        GlStateManager.glTexParameteri(3553, 10241, 9729);
        GlStateManager.glTexParameteri(3553, 10240, 9729);
        GlStateManager.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.colorMask(true, true, true, false);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        GlStateManager.disableAlpha();
        for (int j = 0; j < 3; ++j)
        {
            float f = 1.0f / (j + 1);
            int k = width;
            int l = height;
            float f2 = (j - 1) / 256.0f;
            bufferbuilder.pos(k, l, zLevel).tex(0.0f + f2, 1.0).color(1.0f, 1.0f, 1.0f, f).endVertex();
            bufferbuilder.pos(k, 0.0, zLevel).tex(1.0f + f2, 1.0).color(1.0f, 1.0f, 1.0f, f).endVertex();
            bufferbuilder.pos(0.0, 0.0, zLevel).tex(1.0f + f2, 0.0).color(1.0f, 1.0f, 1.0f, f).endVertex();
            bufferbuilder.pos(0.0, l, zLevel).tex(0.0f + f2, 0.0).color(1.0f, 1.0f, 1.0f, f).endVertex();
        }
        tessellator.draw();
        GlStateManager.enableAlpha();
        GlStateManager.colorMask(true, true, true, true);
    }

    private void drawPanorama()
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.matrixMode(5889);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        Project.gluPerspective(120.0f, 1.0f, 0.05f, 10.0f);
        GlStateManager.matrixMode(5888);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.disableCull();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        for (int j = 0; j < 64; ++j)
        {
            GlStateManager.pushMatrix();
            float f = (j % 8 / 8.0f - 0.5f) / 64.0f;
            float f2 = (j / 8f / 8f - 0.5f) / 64.0f;
            GlStateManager.translate(f, f2, 0.0f);
            GlStateManager.rotate(MathHelper.sin(panoramaTimer / 400.0f) * 25.0f + 20.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(-panoramaTimer * 0.1f, 0.0f, 1.0f, 0.0f);
            for (int k = 0; k < 6; ++k)
            {
                GlStateManager.pushMatrix();
                if (k == 1)
                {
                    GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (k == 2)
                {
                    GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                }
                if (k == 3)
                {
                    GlStateManager.rotate(-90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (k == 4)
                {
                    GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                }
                if (k == 5)
                {
                    GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
                }
                mc.getTextureManager().bindTexture(MainMenu.TITLE_PANORAMA_PATHS[k]);
                bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                int l = 255 / (j + 1);
                bufferbuilder.pos(-1.0, -1.0, 1.0).tex(0.0, 0.0).color(255, 255, 255, l).endVertex();
                bufferbuilder.pos(1.0, -1.0, 1.0).tex(1.0, 0.0).color(255, 255, 255, l).endVertex();
                bufferbuilder.pos(1.0, 1.0, 1.0).tex(1.0, 1.0).color(255, 255, 255, l).endVertex();
                bufferbuilder.pos(-1.0, 1.0, 1.0).tex(0.0, 1.0).color(255, 255, 255, l).endVertex();
                tessellator.draw();
                GlStateManager.popMatrix();
            }
            GlStateManager.popMatrix();
            GlStateManager.colorMask(true, true, true, false);
        }
        bufferbuilder.setTranslation(0.0, 0.0, 0.0);
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.matrixMode(5889);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
    }

    static
    {
        TITLE_PANORAMA_PATHS = new ResourceLocation[]{new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png")};
    }
}

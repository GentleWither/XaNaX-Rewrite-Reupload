package cat.yoink.xanax.internal.guiscreen.clickgui.buttons.settings;

import cat.yoink.xanax.internal.guiscreen.clickgui.buttons.SettingButton;
import cat.yoink.xanax.internal.font.CFontRenderer;
import cat.yoink.xanax.internal.module.ModuleManager;
import cat.yoink.xanax.internal.module.impl.toggleable.client.GuiModule;
import cat.yoink.xanax.internal.module.main.Module;
import cat.yoink.xanax.internal.setting.types.ColorSetting;
import cat.yoink.xanax.internal.setting.types.StateSetting;
import cat.yoink.xanax.internal.util.GuiUtil;

import java.awt.*;

/**
 * @author yoink
 */
public final class ColorButton extends SettingButton
{
    private final ColorSetting setting;
    private int sliderWidth;
    private boolean dragging;
    private boolean left;

    public ColorButton(Module module, int x, int y, int w, int h, ColorSetting setting)
    {
        super(module, x, y, w, h);
        this.setting = setting;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, int windowX, int windowY, boolean self)
    {
        updateSlider(mouseX);
        boolean outline = ((StateSetting) ModuleManager.INSTANCE.getModule(GuiModule.class).getSetting("Outline")).getValue();
        float[] hue = new float[]{(float) (System.currentTimeMillis() % 11520L) / 11520.0f};
        Color c = new Color(Color.HSBtoRGB(hue[0], 1.0f, 1.0f));

        GuiUtil.drawRect(x + 5, y + 5, 100, 10, new Color(20, 20, 20).getRGB(), outline, c.getRGB());
        GuiUtil.drawRect(x + sliderWidth - 2 + 8, y + 6, 4, 8, Color.getHSBColor(sliderWidth / 96f, 1, 1).getRGB());

        CFontRenderer.TEXT.drawString(setting.getName(), x + 109, y + 5.5f, -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton, boolean self)
    {
        if (GuiUtil.isHover(x, y, w, h - 1, mouseX, mouseY)) dragging = true;
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state)
    {
        if (dragging) left = mouseX < x + w / 2;
        dragging = false;
    }

    @Override
    public void onGuiClosed()
    {
        dragging = false;
    }

    private void updateSlider(int mouseX)
    {
        double diff = Math.min(94, Math.max(0, mouseX - x - 8));

        sliderWidth = (int) (94f * (Color.RGBtoHSB(setting.getValue().getRed(), setting.getValue().getGreen(), setting.getValue().getBlue(), null)[0]));
        if (!dragging && sliderWidth == 0 && !left) sliderWidth = 94;

        if (dragging)
        {
            if (sliderWidth == 0 && mouseX > x + 94f / 2) sliderWidth = 94;
            if (diff == 0) setting.setValue(module, Color.getHSBColor(0, 1, 1));
            else if (diff == 94f) setting.setValue(module, new Color(255, 0, 0));
            else setting.setValue(module, Color.getHSBColor((float) (diff / 94), 1, 1));
        }
    }

    @Override
    public ColorSetting getSetting()
    {
        return setting;
    }
}

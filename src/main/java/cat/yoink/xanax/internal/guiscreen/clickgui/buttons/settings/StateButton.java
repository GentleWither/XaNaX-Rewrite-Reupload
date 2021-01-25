package cat.yoink.xanax.internal.guiscreen.clickgui.buttons.settings;

import cat.yoink.xanax.internal.guiscreen.clickgui.buttons.SettingButton;
import cat.yoink.xanax.internal.font.CFontRenderer;
import cat.yoink.xanax.internal.module.ModuleManager;
import cat.yoink.xanax.internal.module.impl.toggleable.client.GuiModule;
import cat.yoink.xanax.internal.module.main.Module;
import cat.yoink.xanax.internal.setting.types.StateSetting;
import cat.yoink.xanax.internal.util.GuiUtil;

import java.awt.*;

/**
 * @author yoink
 */
public final class StateButton extends SettingButton
{
    private final StateSetting setting;

    public StateButton(Module module, int x, int y, int w, int h, StateSetting setting)
    {
        super(module, x, y, w, h);
        this.setting = setting;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, int windowX, int windowY, boolean self)
    {
        boolean outline = ((StateSetting) ModuleManager.INSTANCE.getModule(GuiModule.class).getSetting("Outline")).getValue();

        float[] hue = new float[]{(float) (System.currentTimeMillis() % 11520L) / 11520.0f};
        Color c = new Color(Color.HSBtoRGB(hue[0], 1.0f, 1.0f));

        GuiUtil.drawSmoothRect(x + 5, y + 5, 10, 10, 1, new Color(20, 20, 20).getRGB(), outline, c.getRGB());

        if (setting.getValue()) GuiUtil.drawSmoothRect(x + 7, y + 7, 6, 6, 1, c.getRGB());

        CFontRenderer.TEXT.drawString(setting.getName(), x + 20, y + 5.5f, -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton, boolean self)
    {
        if (GuiUtil.isHover(x, y, w, h - 1, mouseX, mouseY)) setting.setValue(module, !setting.getValue());
    }

    @Override
    public StateSetting getSetting()
    {
        return setting;
    }
}

package cat.yoink.xanax.internal.guiscreen.clickgui.buttons.settings;

import cat.yoink.xanax.internal.guiscreen.clickgui.buttons.SettingButton;
import cat.yoink.xanax.internal.font.CFontRenderer;
import cat.yoink.xanax.internal.module.ModuleManager;
import cat.yoink.xanax.internal.module.impl.toggleable.client.GuiModule;
import cat.yoink.xanax.internal.module.main.Module;
import cat.yoink.xanax.internal.setting.types.ListSetting;
import cat.yoink.xanax.internal.setting.types.StateSetting;
import cat.yoink.xanax.internal.util.GuiUtil;

import java.awt.*;

/**
 * @author yoink
 */
public final class ListButton extends SettingButton
{
    private final ListSetting setting;

    public ListButton(Module module, int x, int y, int w, int h, ListSetting setting)
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

        GuiUtil.drawRect(x + 5, y + 5, 50, 10, new Color(20, 20, 20).getRGB(), outline, c.getRGB());
        CFontRenderer.SMALLTEXT.drawString(setting.getValue(), x + 8, y + 6.5f, -1);

        CFontRenderer.TEXT.drawString(setting.getName(), x + 59, y + 5.5f, -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton, boolean self)
    {
        if (GuiUtil.isHover(x, y, w, h - 1, mouseX, mouseY))
        {
            if (mouseButton == 0) setting.cycleForward();
            else if (mouseButton == 1) setting.cycleBackward();
        }
    }

    @Override
    public ListSetting getSetting()
    {
        return setting;
    }
}

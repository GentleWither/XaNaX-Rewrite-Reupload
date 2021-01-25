package cat.yoink.xanax.internal.guiscreen.clickgui.buttons;

import cat.yoink.xanax.internal.guiscreen.clickgui.IGui;
import cat.yoink.xanax.internal.module.main.Module;
import cat.yoink.xanax.internal.setting.Setting;

/**
 * @author yoink
 */
public abstract class SettingButton implements IGui
{
    protected final Module module;
    protected final int w;
    protected final int h;
    protected int x;
    protected int y;

    protected SettingButton(Module module, int x, int y, int w, int h)
    {
        this.module = module;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, int windowX, int windowY, boolean self)
    {

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton, boolean self)
    {

    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state)
    {

    }

    @Override
    public void keyTyped(char typedChar, int keyCode)
    {

    }

    @Override
    public void onGuiClosed()
    {

    }

    public abstract Setting<?> getSetting();
}

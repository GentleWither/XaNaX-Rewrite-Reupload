package cat.yoink.xanax.internal.guiscreen.main;

import cat.yoink.xanax.internal.traits.interfaces.Minecraft;
import cat.yoink.xanax.internal.util.GuiUtil;
import net.minecraft.client.gui.Gui;

import java.awt.*;

/**
 * @author yoink
 */
public final class CustomButton implements Minecraft
{
    public int buttonWidth;
    public int buttonHeight;
    public int x;
    public int y;
    public String displayString;
    public int id;
    private int timeHovered;

    public CustomButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText)
    {
        id = buttonId;
        this.x = x;
        this.y = y;
        buttonWidth = widthIn;
        buttonHeight = heightIn;
        displayString = buttonText;
    }

    public void drawButton(net.minecraft.client.Minecraft mc, int mouseX, int mouseY)
    {
        if (GuiUtil.isHover(x, y, buttonWidth, buttonHeight, mouseX, mouseY))
        {
            timeHovered += 14;
            if (timeHovered > buttonWidth / 2)
            {
                timeHovered = buttonWidth / 2;
            }
        }
        else if (!GuiUtil.isHover(x, y, buttonWidth, buttonHeight, mouseX, mouseY))
        {
            timeHovered -= 14;
            if (timeHovered < 0)
            {
                timeHovered = 0;
            }
        }
        Gui.drawRect(x, y, x + buttonWidth, y + buttonHeight, new Color(0, 0, 0, 112).getRGB());
        Gui.drawRect(x + buttonWidth / 2 - timeHovered, y + buttonHeight - 1, x + buttonWidth / 2 + timeHovered, y + buttonHeight, new Color(0, 0, 0, 123).getRGB());
        mc.fontRenderer.drawStringWithShadow(displayString, x + buttonWidth / 2.0f - mc.fontRenderer.getStringWidth(displayString) / 2.0f, y + buttonHeight / 2.0f - mc.fontRenderer.FONT_HEIGHT / 2.0f, new Color(255, 255, 255, 255).getRGB());
    }
}

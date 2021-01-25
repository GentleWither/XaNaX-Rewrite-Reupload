package cat.yoink.xanax.internal.guiscreen.clickgui.buttons;

import cat.yoink.xanax.internal.guiscreen.clickgui.ClickGUI;
import cat.yoink.xanax.internal.guiscreen.clickgui.IGui;
import cat.yoink.xanax.internal.font.CFontRenderer;
import cat.yoink.xanax.internal.module.ModuleCategory;
import cat.yoink.xanax.internal.module.ModuleManager;
import cat.yoink.xanax.internal.module.impl.toggleable.client.GuiModule;
import cat.yoink.xanax.internal.module.main.Module;
import cat.yoink.xanax.internal.setting.types.StateSetting;
import cat.yoink.xanax.internal.traits.interfaces.Minecraft;
import cat.yoink.xanax.internal.util.GuiUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yoink
 */
public final class CategoryButton implements IGui, Minecraft
{
    private final List<ModuleButton> buttons = new ArrayList<>();
    private final ModuleCategory category;
    private final int w;
    private final int h;
    private int x;
    private int y;
    private boolean selected;
    private int windowX, windowY;
    private int tab;

    public CategoryButton(ModuleCategory category, int x, int y, int w, int h, int windowX, int windowY)
    {
        this.category = category;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        final List<Module> modules = ModuleManager.INSTANCE.getRegistry().stream()
                .filter(m -> m.getCategory().equals(category) && !m.isHidden())
                .collect(Collectors.toList());
        for (int i = 0; i < modules.size(); i++)
        {
            buttons.add(new ModuleButton(modules.get(i), windowX + 30 + i * 65, y + 20, 60, 15, this, windowX, windowY));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, int windowX, int windowY, boolean self)
    {
        this.windowX = windowX;
        this.windowY = windowY;

        if (selected)
        {
            boolean outline = ((StateSetting) ModuleManager.INSTANCE.getModule(GuiModule.class).getSetting("Outline")).getValue();

            float[] hue = new float[]{(float) (System.currentTimeMillis() % 11520L) / 11520.0f};
            Color c = new Color(Color.HSBtoRGB(hue[0], 1.0f, 1.0f));

            if (outline) GuiUtil.drawSmoothRect(x - 1, y - 1, w + 2, h + 2, 2, c.getRGB());
            GuiUtil.drawSmoothRect(x, y, w, h + 3, 2, new Color(43, 43, 43).getRGB());
        }

        CFontRenderer.TEXT.drawString(category.getName(), x + (w / 2f) - (CFontRenderer.TEXT.getStringWidth(category.getName()) / 2f), y + 3, -1);

        if (selected)
        {
            int modX = 0;
            for (int i = 0; i < buttons.size(); i++)
            {
                if (i < tab || i > tab + 4)
                {
                    buttons.get(i).drawScreen(mouseX, mouseY, windowX, windowY, false);
                    continue;
                }

                ModuleButton button = buttons.get(i);

                button.setX(windowX + 30 + modX * 65);
                button.setY(y + 20);
                buttons.get(i).drawScreen(mouseX, mouseY, windowX, windowY, true);

                modX++;
            }
        }
    }

    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton, final boolean self)
    {
        if (GuiUtil.isHover(x, y, w, h, mouseX, mouseY) && mouseButton == 0)
        {
            ClickGUI.INSTANCE.getButtons().forEach(b -> b.selected = false);
            selected = true;
        }

        if (mouseButton == 0 && selected)
        {
            if (GuiUtil.isHover(windowX + 15, windowY + 57, 10, 10, mouseX, mouseY) && tab > 0)
                tab--;
            if (GuiUtil.isHover(windowX + 355, windowY + 57, 10, 10, mouseX, mouseY) && tab < buttons.size() - 5)
                tab++;
        }

        if (selected)
        {
            for (int i = 0; i < buttons.size(); i++)
            {
                if (i < tab || i > tab + 4)
                {
                    buttons.get(i).mouseClicked(mouseX, mouseY, mouseButton, false);
                    continue;
                }

                buttons.get(i).mouseClicked(mouseX, mouseY, mouseButton, true);
            }
        }
    }

    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state)
    {
        if (selected) buttons.forEach(button -> button.mouseReleased(mouseX, mouseY, state));
    }

    @Override
    public void keyTyped(final char typedChar, final int keyCode)
    {
        if (selected) buttons.forEach(button -> button.keyTyped(typedChar, keyCode));
    }

    @Override
    public void onGuiClosed()
    {
        if (selected) buttons.forEach(ModuleButton::onGuiClosed);
    }

    public void setX(final int x)
    {
        this.x = x;
    }

    public void setY(final int y)
    {
        this.y = y;
    }

    public List<ModuleButton> getButtons()
    {
        return buttons;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public int getTab()
    {
        return tab;
    }
}

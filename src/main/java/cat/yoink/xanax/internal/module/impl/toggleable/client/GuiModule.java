package cat.yoink.xanax.internal.module.impl.toggleable.client;

import cat.yoink.xanax.internal.guiscreen.clickgui.ClickGUI;
import cat.yoink.xanax.internal.module.ModuleCategory;
import cat.yoink.xanax.internal.module.main.ModuleData;
import cat.yoink.xanax.internal.module.state.StateModule;
import cat.yoink.xanax.internal.setting.annotation.types.List;
import cat.yoink.xanax.internal.setting.annotation.Setting;
import org.lwjgl.input.Keyboard;

/**
 * @author yoink
 */
@ModuleData(
        name = "ClickGUI",
        aliases = {"ClickGUI", "GUI", "Menu"},
        category = ModuleCategory.CLIENT,
        defaultBind = Keyboard.KEY_RSHIFT,
        description = "Toggle modules and settings in a gui",
        noSave = true
)
public final class GuiModule extends StateModule
{
    @Setting(name = "Outline", description = "Adds an outline to the elements")
    public boolean outline = false;
    @Setting(name = "Closing", description = "Changes how the gui closes", list = @List({"Keyboard", "Button", "Both"}))
    public String closing = "Keyboard";

    @Override
    public void onEnable()
    {
        mc.displayGuiScreen(ClickGUI.INSTANCE);
    }
}

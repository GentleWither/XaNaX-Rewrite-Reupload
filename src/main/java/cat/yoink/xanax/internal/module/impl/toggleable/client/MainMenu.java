package cat.yoink.xanax.internal.module.impl.toggleable.client;

import cat.yoink.xanax.internal.module.ModuleCategory;
import cat.yoink.xanax.internal.module.main.ModuleData;
import cat.yoink.xanax.internal.module.state.StateModule;
import cat.yoink.xanax.internal.setting.annotation.types.List;
import cat.yoink.xanax.internal.setting.annotation.Setting;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author yoink
 */
@ModuleData(
        name = "MainMenu",
        aliases = {"MainMenu", "Main", "Screen"},
        category = ModuleCategory.CLIENT,
        description = "Custom main menu",
        enabled = true
)
public final class MainMenu extends StateModule
{
    @Setting(name = "Mode", description = "Choose which background to use", list = @List({"Minecraft", "Color"}))
    public String mode = "Minecraft";
    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event)
    {
        if (event.getGui() instanceof GuiMainMenu) event.setGui(cat.yoink.xanax.internal.guiscreen.main.MainMenu.INSTANCE);
    }
}

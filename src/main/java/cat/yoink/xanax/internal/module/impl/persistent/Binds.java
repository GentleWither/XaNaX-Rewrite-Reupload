package cat.yoink.xanax.internal.module.impl.persistent;

import cat.yoink.xanax.internal.module.ModuleCategory;
import cat.yoink.xanax.internal.module.ModuleManager;
import cat.yoink.xanax.internal.module.main.Module;
import cat.yoink.xanax.internal.module.main.ModuleData;
import cat.yoink.xanax.internal.module.state.StateModule;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

/**
 * @author yoink
 */
@ModuleData(
        name = "Binds",
        aliases = {"Binds", "Keybinds"},
        category = ModuleCategory.CLIENT,
        hidden = true,
        description = "Binds for all the modules"
)
public final class Binds extends Module
{
    @SubscribeEvent
    public void onInputKeyInput(InputEvent.KeyInputEvent event)
    {
        if (Keyboard.getEventKeyState() && Keyboard.getEventKey() != Keyboard.KEY_NONE)
            ModuleManager.INSTANCE.getFilteredRegistry().stream()
                    .filter(module -> module.getBind() == Keyboard.getEventKey())
                    .forEach(StateModule::toggle);
    }
}


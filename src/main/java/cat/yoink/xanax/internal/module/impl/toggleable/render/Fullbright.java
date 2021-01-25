package cat.yoink.xanax.internal.module.impl.toggleable.render;

import cat.yoink.xanax.internal.module.ModuleCategory;
import cat.yoink.xanax.internal.module.main.ModuleData;
import cat.yoink.xanax.internal.module.state.StateModule;

/**
 * @author yoink
 */
@ModuleData(
        name = "Fullbright",
        aliases = {"Fullbright", "Brightness", "Lights"},
        category = ModuleCategory.RENDER,
        description = "Makes everything bright"
)
public final class Fullbright extends StateModule
{
    private float old;

    @Override
    public void onEnable()
    {
        old = mc.gameSettings.gammaSetting;
        mc.gameSettings.gammaSetting = 100;
    }

    @Override
    public void onDisable()
    {
        mc.gameSettings.gammaSetting = old;
    }
}

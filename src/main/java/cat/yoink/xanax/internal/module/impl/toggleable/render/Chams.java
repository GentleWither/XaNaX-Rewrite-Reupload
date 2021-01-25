package cat.yoink.xanax.internal.module.impl.toggleable.render;

import cat.yoink.xanax.internal.event.impl.RenderModelEvent;
import cat.yoink.xanax.internal.module.ModuleCategory;
import cat.yoink.xanax.internal.module.main.ModuleData;
import cat.yoink.xanax.internal.module.state.StateModule;
import cat.yoink.xanax.internal.setting.annotation.Setting;
import cat.yoink.xanax.internal.setting.annotation.types.Number;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

/**
 * @author yoink
 */
@ModuleData(
        name = "Chams",
        aliases = "Chams",
        category = ModuleCategory.RENDER,
        description = "Changes how players render"
)
public final class Chams extends StateModule
{
    @Setting(name = "Opacity", description = "Opacity of the player", number = @Number(max = 100))
    public double opacity = 50;
    @Setting(name = "Limit", description = "Limit the distance")
    public boolean limit = false;
    @Setting(name = "Distance", description = "Max distance", number = @Number(max = 30, increment = 0.1))
    public double distance = 1;
    @Setting(name = "CustomColor", description = "Change the color of the player")
    public boolean customColor = false;
    @Setting(name = "Color", description = "Custom color of the player")
    public Color color = new Color(255, 0, 0);

    @SubscribeEvent
    public void onRenderModel(RenderModelEvent event)
    {
        if (isSafe() && (!limit || !(event.getEntitylivingbaseIn().getDistance(mc.player) > distance)))
        {
            if (customColor) GlStateManager.color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, (float) (opacity / 100));
            else GlStateManager.color(1, 1, 1, (float) (opacity / 100));
        }
    }
}

package cat.yoink.xanax.internal.module.impl.toggleable.render;

import cat.yoink.xanax.internal.event.impl.RenderHandEvent;
import cat.yoink.xanax.internal.module.ModuleCategory;
import cat.yoink.xanax.internal.module.main.ModuleData;
import cat.yoink.xanax.internal.module.state.StateModule;
import cat.yoink.xanax.internal.setting.annotation.types.List;
import cat.yoink.xanax.internal.setting.annotation.types.Number;
import cat.yoink.xanax.internal.setting.annotation.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * @author yoink
 * @author neko
 */
@ModuleData(
        name = "ViewModel",
        aliases = {"ViewModel", "ViewModelChanger", "VMC"},
        category = ModuleCategory.RENDER,
        description = "Changes your view model"
)
public final class ViewModel extends StateModule
{
    @Setting(name = "ArmPitch", description = "Pitch of your arm")
    public boolean armPitch = false;
    @Setting(name = "ArmPitchValue", description = "Amount of pitch", number = @Number(min = -750, max = 750, increment = 10))
    public double armPitchValue = 90;
    @Setting(name = "Swing", description = "Keep your hand swinging")
    public boolean swing = false;
    @Setting(name = "SwingValue", description = "Amount your hand should be swinging at", number = @Number(max = 1, increment = 0.01))
    public double swingValue = 0.86;
    @Setting(name = "FOV", description = "Change your field of view")
    public boolean fov = false;
    @Setting(name = "FovMode", description = "Field of view mode", list = @List({"Hard", "Soft"}))
    public String fovMode = "Hard";
    @Setting(name = "FovValue", description = "Field of view", number = @Number(min = 80, max = 180))
    public double fovValue = 120;
    @Setting(name = "MoveHand", description = "Change position of your hands")
    public boolean moveHand = false;
    @Setting(name = "MainX", description = "MainHand X position", number = @Number(min = -1, max = 1, increment = 0.01))
    public double mainX = 0;
    @Setting(name = "MainY", description = "MainHand Y position", number = @Number(min = -1, max = 1, increment = 0.01))
    public double mainY = 0;
    @Setting(name = "MainZ", description = "MainHand Z position", number = @Number(min = -1, max = 1, increment = 0.01))
    public double mainZ = 0;
    @Setting(name = "OffX", description = "OffHand X position", number = @Number(min = -1, max = 1, increment = 0.01))
    public double offX = 0;
    @Setting(name = "OffY", description = "OffHand Y position", number = @Number(min = -1, max = 1, increment = 0.01))
    public double offY = 0;
    @Setting(name = "OffZ", description = "OffHand Z position", number = @Number(min = -1, max = 1, increment = 0.01))
    public double offZ = 0;
    @Setting(name = "Animations", description = "Old 1.8 animations")
    public boolean animations = false;

    private float oldFOV;

    @Override
    public void onEnable()
    {
        oldFOV = mc.gameSettings.fovSetting;
    }

    @Override
    public void onDisable()
    {
        mc.gameSettings.fovSetting = oldFOV;
    }

    @SubscribeEvent
    public void onTickClientTick(TickEvent.ClientTickEvent event)
    {
        if (isSafe())
        {
            if (fov && fovMode.equals("Hard")) mc.gameSettings.fovSetting = (float) fovValue;
            else mc.gameSettings.fovSetting = oldFOV;

            if (armPitch) mc.player.renderArmPitch = (float) armPitchValue;

            if (swing) mc.player.swingProgress = (float) swingValue;

            if (animations)
            {
                if (mc.entityRenderer.itemRenderer.prevEquippedProgressMainHand >= 0.9)
                {
                    mc.entityRenderer.itemRenderer.equippedProgressMainHand = 1;
                    mc.entityRenderer.itemRenderer.itemStackMainHand = mc.player.getHeldItem(EnumHand.MAIN_HAND);
                }
                if (mc.entityRenderer.itemRenderer.prevEquippedProgressOffHand >= 0.9)
                {
                    mc.entityRenderer.itemRenderer.equippedProgressOffHand = 1;
                    mc.entityRenderer.itemRenderer.itemStackOffHand = mc.player.getHeldItem(EnumHand.OFF_HAND);
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityViewRenderFOVModifier(EntityViewRenderEvent.FOVModifier event)
    {
        if (isSafe() && fov && fovMode.equals("Soft"))
            event.setFOV((float) fovValue);
    }

    @SubscribeEvent
    public void onRenderHand(RenderHandEvent event)
    {
        if (isSafe() && moveHand)
        {
            if (event.getSide() == EnumHandSide.RIGHT) GlStateManager.translate(mainX, mainY, mainZ);
            else if (event.getSide() == EnumHandSide.LEFT) GlStateManager.translate(offX, offY, offZ);
        }
    }
}

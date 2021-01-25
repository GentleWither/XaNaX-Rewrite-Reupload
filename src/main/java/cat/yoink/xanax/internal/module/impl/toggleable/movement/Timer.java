package cat.yoink.xanax.internal.module.impl.toggleable.movement;

import cat.yoink.xanax.internal.module.ModuleCategory;
import cat.yoink.xanax.internal.module.main.ModuleData;
import cat.yoink.xanax.internal.module.state.StateModule;
import cat.yoink.xanax.internal.setting.annotation.Setting;
import cat.yoink.xanax.internal.setting.annotation.types.List;
import cat.yoink.xanax.internal.setting.annotation.types.Number;
import cat.yoink.xanax.internal.util.WorldUtil;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * @author yoink
 * @author TBM
 */
@ModuleData(
        name = "Timer",
        aliases = {"Timer", "Time"},
        category = ModuleCategory.MOVEMENT,
        description = "Change the client side tick rate",
        noSave = true
)
public final class Timer extends StateModule
{
    @Setting(name = "Mode", list = @List({"Normal", "Switch"}))
    public String mode = "Normal";
    @Setting(name = "TPS", description = "Tick rate", number = @Number(min = 1, max = 150))
    public double tps = 40;
    @Setting(name = "FastSpeed", description = "Fast speed for timer switch", number = @Number(min = 20, max = 1000, increment = 5))
    public double fastTps = 300;
    @Setting(name = "ToFast", description = "Ticks to enabled fast", number = @Number(max = 20))
    public double tickToFast = 4;
    @Setting(name = "ToSlow", description = "Ticks to disable fast", number = @Number(max = 20))
    public double tickToNoFast = 7;
    private int tickWait;

    @SubscribeEvent
    public void onTickClientTick(TickEvent.ClientTickEvent event)
    {
        if (isSafe())
        {
            if (mode.equals("Normal")) WorldUtil.setTPS(tps);
            else
            {
                if (tickWait == tickToFast) WorldUtil.setTPS(fastTps);
                if (tickWait >= tickToNoFast)
                {
                    tickWait = 0;
                    WorldUtil.setTPS(tps);
                }
                tickWait++;
            }
        }
    }

    @Override
    public void onDisable()
    {
        WorldUtil.setTPS(20);
    }
}

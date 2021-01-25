package cat.yoink.xanax.external;

import cat.yoink.xanax.internal.XANAX;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * @author yoink
 */
@Mod(modid = "xanax", name = "XANAX")
@IFMLLoadingPlugin.MCVersion("1.12.2")
public final class EntryPoint implements IFMLLoadingPlugin
{
    @Mod.EventHandler
    public void initialize(FMLInitializationEvent event)
    {
        XANAX.INSTANCE.run();
    }

    @Override
    public void injectData(Map<String, Object> data)
    {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixin.xanax.json");
    }

    @Override
    public String[] getASMTransformerClass()
    {
        return new String[0];
    }

    @Override
    public String getModContainerClass()
    {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass()
    {
        return null;
    }

    @Override
    public String getAccessTransformerClass()
    {
        return Transformer.class.getName();
    }
}

package cat.yoink.xanax.internal.other;

import cat.yoink.xanax.internal.traits.manager.MapRegistry;
import cat.yoink.xanax.internal.util.WebUtil;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;

/**
 * @author yoink
 */
public final class CapeManager extends MapRegistry<String, ResourceLocation>
{
    public static final CapeManager INSTANCE = new CapeManager("https://raw.githubusercontent.com/Katatje/XANAX-Rewrite/master/capes.txt");

    private CapeManager(String url)
    { // TODO: 12/27/2020 Make this customizable. When i'm back home O_O
        ResourceLocation cape = new ResourceLocation("cape.png");
        String content = WebUtil.getContent(url);
        Arrays.stream(content.split("\n")).forEach(s -> add(s, cape));
    }
}

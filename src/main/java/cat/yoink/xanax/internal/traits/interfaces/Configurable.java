package cat.yoink.xanax.internal.traits.interfaces;

import cat.yoink.xanax.internal.XANAX;

import java.io.File;

/**
 * @author yoink
 */
public interface Configurable
{
    File directory = new File(Minecraft.mc.gameDir + File.separator + XANAX.INSTANCE.getName());

    boolean save(String name);

    boolean load(String name);
}

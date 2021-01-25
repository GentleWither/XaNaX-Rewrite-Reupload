package cat.yoink.xanax.external;

import net.minecraftforge.fml.common.asm.transformers.AccessTransformer;

import java.io.IOException;

/**
 * @author yoink
 */
public final class Transformer extends AccessTransformer
{
    public Transformer() throws IOException
    {
        super("xanax_at.cfg");
    }
}

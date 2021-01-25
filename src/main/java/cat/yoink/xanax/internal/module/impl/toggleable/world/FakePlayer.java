package cat.yoink.xanax.internal.module.impl.toggleable.world;

import cat.yoink.xanax.internal.module.ModuleCategory;
import cat.yoink.xanax.internal.module.main.ModuleData;
import cat.yoink.xanax.internal.module.state.StateModule;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.EntityOtherPlayerMP;

import java.util.UUID;

@ModuleData(
        name = "FakePlayer",
        aliases = {"FakePlayer", "Player"},
        category = ModuleCategory.WORLD,
        description = "Spawns a fake player",
        noSave = true
)
public final class FakePlayer extends StateModule
{
    private int id;

    @Override
    public void onEnable()
    {
        new Thread(() -> {
            GameProfile profile = new GameProfile(UUID.fromString("f0dd2877-9aed-471f-ba45-07674c0e9f8e"), "cat");
            EntityOtherPlayerMP fakePlayer = new EntityOtherPlayerMP(mc.world, profile);

            fakePlayer.copyLocationAndAnglesFrom(mc.player);
            fakePlayer.setHealth(mc.player.getHealth() + mc.player.getAbsorptionAmount());

            mc.world.addEntityToWorld(-69, fakePlayer);

            id = -69;
        }).start();
    }

    @Override
    public void onDisable()
    {
        if (id == -69)
        {
            mc.world.removeEntityFromWorld(id);
            id = 0;
        }
    }
}

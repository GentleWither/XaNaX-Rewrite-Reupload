package cat.yoink.xanax.internal.module.impl.toggleable.combat;

import cat.yoink.xanax.internal.module.ModuleCategory;
import cat.yoink.xanax.internal.module.main.ModuleData;
import cat.yoink.xanax.internal.module.state.StateModule;
import cat.yoink.xanax.internal.setting.annotation.Setting;
import cat.yoink.xanax.internal.setting.annotation.types.Number;
import cat.yoink.xanax.internal.util.InventoryUtil;
import cat.yoink.xanax.internal.util.PlayerUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;
import java.util.Objects;

/**
 * @author yoink
 */
@ModuleData(
        name = "Quiver",
        aliases = {"Quiver", "AutoBow", "Bow"},
        category = ModuleCategory.COMBAT,
        description = "Automatically shoots arrows at you"
)
public final class Quiver extends StateModule
{
    @Setting(name = "Release", description = "Automatically releases the bow")
    public boolean release = true;
    @Setting(name = "Arrange", description = "Arranges the items in your inventory")
    public boolean arrange = true;
    @Setting(name = "Delay", description = "Bow pull delay", number = @Number(min = 2))
    public double delay = 3;

    @SubscribeEvent
    public void onTickClientTick(TickEvent.ClientTickEvent event)
    {
        if (isSafe())
        {
            boolean hasSpeed = mc.player.getActivePotionEffect(Objects.requireNonNull(Potion.getPotionById(1))) != null;
            boolean hasStrength = mc.player.getActivePotionEffect(Objects.requireNonNull(Potion.getPotionById(5))) != null;

            if (release && (!hasSpeed || !hasStrength)  && mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow && mc.player.isHandActive() && mc.player.getItemInUseMaxCount() > delay) PlayerUtil.shootSelf();

            if (arrange)
            {
                List<Integer> arrowSlots = InventoryUtil.getInventorySlots(Items.TIPPED_ARROW);

                int speedSlot = -1;
                int strengthSlot = -1;
                for (Integer slot : arrowSlots)
                {
                    if (Objects.requireNonNull(PotionUtils.getPotionFromItem(mc.player.inventory.getStackInSlot(slot)).getRegistryName()).getPath().contains("swiftness")) speedSlot = slot;
                    else if (Objects.requireNonNull(PotionUtils.getPotionFromItem(mc.player.inventory.getStackInSlot(slot)).getRegistryName()).getPath().contains("strength")) strengthSlot = slot;
                }

                if (strengthSlot == -1 || speedSlot == -1 || hasSpeed && hasStrength) return;
                if ((hasSpeed && speedSlot < strengthSlot) || (hasStrength && strengthSlot < speedSlot)) InventoryUtil.swapSlots(strengthSlot, speedSlot);
            }
        }
    }
}
